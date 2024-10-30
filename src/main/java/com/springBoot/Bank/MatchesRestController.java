package com.springBoot.Bank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import domain.Match;
import repository.MatchRepository;

@RestController
@RequestMapping("/rest")
public class MatchesRestController {
	
	@Autowired
	private MatchRepository matchRepo;
	
	@GetMapping(value="/games/{sport}")
	public List<Match> getAllMatchesSport(@PathVariable String sport) {
		return matchRepo.findByNameOfSport(sport);
	}
	
	@GetMapping(value="/games/{sport}/{id}")
	public Map<String, Integer> getMatchDetail(@PathVariable String sport, @PathVariable Long id) {
		int availablePlaces = matchRepo.findById(id).map(Match::getAvailablePlaces).get();
		Map<String, Integer> response = new HashMap<>();
		response.put("availablePlaces", availablePlaces);
		return response;
	}
}
