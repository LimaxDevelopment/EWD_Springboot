package com.springBoot.Bank;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import domain.Match;
import domain.MyUser;
import domain.Role;
import domain.Ticket;
import repository.MatchRepository;
import repository.SportRepository;
import repository.TicketRepository;
import repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class SportControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository repoUser;

    @Mock
    private SportRepository repoSport;

    @Mock
    private MatchRepository repoMatch;

    @Mock
    private TicketRepository repoTicket;

    @Mock
    private Principal principal;

    @InjectMocks
    private SportController sportController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(principal.getName()).thenReturn("mlison");
    }

    @Test
    public void testListSports() throws Exception {
        MyUser user = new MyUser(3L, "mlison", "Admin123!", Role.ADMIN, 0);
        when(repoUser.findByUsername("mlison")).thenReturn(user);
        when(repoSport.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/sports/overview").principal(principal))
            .andExpect(status().isOk())
            .andExpect(view().name("sports/overview"))
            .andExpect(model().attribute("sportList", Collections.emptyList()))
            .andExpect(model().attribute("user", user));
    }

    @Test
    public void testTickets() throws Exception {
        when(repoTicket.findTickets()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/sports/tickets"))
            .andExpect(status().isOk())
            .andExpect(view().name("sports/tickets"))
            .andExpect(model().attribute("tickets", Collections.emptyList()));
    }

    @Test
    public void testShowMatches() throws Exception {
    	
        when(repoMatch.findByNameOfSport("Football")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/sports/overview/Football"))
            .andExpect(status().isOk())
            .andExpect(view().name("sports/matches"))
            .andExpect(model().attribute("sport", "Football"))
            .andExpect(model().attribute("matches", Collections.emptyList()));
    }

    @Test
    public void testAddMatchForm() throws Exception {
        when(repoMatch.findByNameOfSport("Football")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/sports/addMatch/Football"))
            .andExpect(status().isOk())
            .andExpect(view().name("sports/addMatch"))
            .andExpect(model().attribute("sport", "Football"))
            .andExpect(model().attributeExists("match"))
            .andExpect(model().attribute("stadiums", Collections.emptyList()));
    }

    @Test
    public void testAddMatch() throws Exception {
        Match match = new Match("Basketball", "3-Point Shoutout", "Slam Dunk Contest", LocalDateTime.of(2024, 7, 27, 10, 0), "AccorHotels Arena", 45, 11.75, "12345", "13345");
        when(repoMatch.findByNameOfSport("Football")).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/sports/addMatch/Football")
                .flashAttr("match", match))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/sports/overview/Football"));

        verify(repoMatch).save(match);
    }

    @Test
    public void testBuyTicketsForm() throws Exception {
        MyUser user = new MyUser(5L, "jvermeulen", "User123!", Role.USER, 0);
        Match match = new Match("Basketball", "3-Point Shoutout", "Slam Dunk Contest", LocalDateTime.of(2024, 7, 27, 10, 0), "AccorHotels Arena", 45, 11.75, "12345", "13345");
        match.setSport("Football");
        when(repoUser.findByUsername("jvermeulen")).thenReturn(user);
        when(repoMatch.findById(1L)).thenReturn(Optional.of(match));
        when(repoTicket.findByIds(user.getId(), 1L)).thenReturn(null);

        mockMvc.perform(get("/sports/buyTickets/Football/1").principal(principal))
            .andExpect(status().isOk())
            .andExpect(view().name("sports/buyTickets"))
            .andExpect(model().attribute("user", user))
            .andExpect(model().attribute("match", match))
            .andExpect(model().attributeExists("ticket"));

        verify(repoTicket).save(any(Ticket.class));
    }

    @Test
    public void testBuyTickets() throws Exception {
        MyUser user = new MyUser(6L, "jvermeulen", "User123!", Role.USER, 0);
        Match match = new Match("Basketball", "3-Point Shoutout", "Slam Dunk Contest", LocalDateTime.of(2024, 7, 27, 10, 0), "AccorHotels Arena", 45, 11.75, "12345", "13345");
        Ticket ticket = new Ticket(user.getId(), match.getId(), 0, 0, match.getSport(), match.getDiscipline1(), match.getDiscipline2(), match.getDate(), match.getStadium());
        ticket.setNumberOfTickets(1);

        when(repoUser.findByUsername("jvermeulen")).thenReturn(user);
        when(repoMatch.findById(1L)).thenReturn(Optional.of(match));
        when(repoTicket.findByIds(user.getId(), 1L)).thenReturn(ticket);

        mockMvc.perform(post("/sports/buyTickets/football/1")
                .principal(principal)
                .flashAttr("ticket", ticket))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/sports/overview/Football"));

        verify(repoTicket).save(ticket);
        verify(repoUser).save(user);
        verify(repoMatch).save(match);
    }
}
