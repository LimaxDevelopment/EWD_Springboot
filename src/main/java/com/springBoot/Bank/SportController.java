package com.springBoot.Bank;

import java.security.Principal;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.Match;
import domain.MyUser;
import domain.Ticket;
import jakarta.validation.Valid;
import repository.MatchRepository;
import repository.SportRepository;
import repository.TicketRepository;
import repository.UserRepository;

@Controller
@RequestMapping("/sports")
public class SportController {
	
	@Autowired
	private UserRepository repoUser;
	
	@Autowired
	private SportRepository repoSport;
	
	@Autowired
	private MatchRepository repoMatch;
	
	@Autowired
	private TicketRepository repoTicket;
	
	@GetMapping(value="/overview")
	public String listSports(Principal principal, Model model) {
		MyUser user = repoUser.findByUsername(principal.getName());
		model.addAttribute("sportList", repoSport.findAll());
		model.addAttribute("user", user);
		return "sports/overview";
	}
	
	@GetMapping(value="/tickets")
	public String tickets(Model model) {
		model.addAttribute("tickets", repoTicket.findTickets());
		return "sports/tickets";
	}
	
	@GetMapping(value="/overview/{sport}")
	public String showMatches(Principal principal, @PathVariable String sport, Model model) {
		model.addAttribute("sport", sport);
		model.addAttribute("matches", repoMatch.findByNameOfSport(sport));
		model.addAttribute("tickets", repoTicket.findTickets());
		return "sports/matches";
	}
	
	@GetMapping(value="/addMatch/{sport}")
	public String addMatchForm(@PathVariable String sport, Match match, Model model) {
		model.addAttribute("sport", sport);
		model.addAttribute("match", match);
		model.addAttribute("stadiums", repoMatch.findByNameOfSport(sport).stream().map(m -> m.getStadium()).distinct().collect(Collectors.toList()));
		return "sports/addMatch";
	}
	
	@PostMapping(value="/addMatch/{sport}")
	public String addMatch(@PathVariable String sport, @Valid Match match, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("stadiums", repoMatch.findByNameOfSport(sport).stream().map(m -> m.getStadium()).distinct().collect(Collectors.toList()));
			return "sports/addMatch";
		}
		match.setSport(sport);
		repoMatch.save(match);
		return "redirect:/sports/overview/" + sport;
	}
	
	@GetMapping(value="/buyTickets/{sport}/{id}")
	public String buyTicketsForm(Principal principal, @PathVariable String sport, @PathVariable Long id, Model model) {
		MyUser user = repoUser.findByUsername(principal.getName());
		Match match = repoMatch.findById(id).get();
		if(repoTicket.findByIds(user.getId(), id) == null) {
			repoTicket.save(new Ticket(user.getId(), id, 0, 0, match.getSport(), match.getDiscipline1(), match.getDiscipline2(), match.getDate(), match.getStadium()));
		}
		model.addAttribute("user", user);
		model.addAttribute("match", match);
		model.addAttribute("ticket", repoTicket.findByIds(user.getId(), id));
		return "sports/buyTickets";
	}
	
	@PostMapping(value="/buyTickets/{sport}/{id}")
	public String buyTickets(Principal principal, @PathVariable String sport, @PathVariable Long id, @Valid Ticket ticket, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		MyUser user = repoUser.findByUsername(principal.getName());
		Match match = repoMatch.findById(id).get();
		Ticket t = repoTicket.findByIds(user.getId(), id);
		/*if(bindingResult.hasErrors()) {
			System.out.println("test");
			model.addAttribute("user", user);
		 	model.addAttribute("match", match);
		 	model.addAttribute("ticket", repoTicket.findByIds(user.getId(), id));
			return "sports/buyTickets";
		}*/
		t.setTotalTicketsMatch(t.getTotalTicketsMatch() + ticket.getNumberOfTickets());
		user.setTotalTickets(user.getTotalTickets() + ticket.getNumberOfTickets());
		match.setAvailablePlaces(match.getAvailablePlaces() - ticket.getNumberOfTickets());
		repoTicket.save(t);
		repoUser.save(user);
		repoMatch.save(match);
		redirectAttributes.addFlashAttribute("successMessage", ticket.getNumberOfTickets() + " tickets werden aangekocht.");
		return "redirect:/sports/overview/" + sport;
	}
}
