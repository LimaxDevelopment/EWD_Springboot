package com.springBoot.Bank;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import domain.Match;
import repository.MatchRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class MatchesRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MatchRepository matchRepo;

    @InjectMocks
    private MatchesRestController matchesRestController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllMatchesSport() throws Exception {
        List<Match> matches = Collections.emptyList();
        when(matchRepo.findByNameOfSport("Basketball")).thenReturn(matches);

        mockMvc.perform(get("/rest/games/Basketball")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        verify(matchRepo, times(1)).findByNameOfSport("Basketball");
    }

    @Test
    public void testGetMatchDetail() throws Exception {
        List<Match> matches = Collections.emptyList();
        when(matchRepo.findByNameOfSport("Basketball")).thenReturn(matches);

        mockMvc.perform(get("/rest/games/Basketball/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("availablePlaces").value(45));

        verify(matchRepo, times(1)).findByNameOfSport("Basketball");
    }
}

