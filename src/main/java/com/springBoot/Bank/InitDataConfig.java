package com.springBoot.Bank;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import domain.Match;
import domain.MyUser;
import domain.Role;
import domain.Sport;
import repository.MatchRepository;
import repository.SportRepository;
import repository.UserRepository;

@Component
public class InitDataConfig implements CommandLineRunner {
	
	private PasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Autowired
	private UserRepository repoUser;
	
	@Autowired
	private SportRepository repoSport;
	
	@Autowired
	private MatchRepository repoMatch;
	
	
	public void run(String... args) {
		
		//Users
		var admin = MyUser.builder().username("mlison")
						.role(Role.ADMIN)
						.password(encoder.encode("Admin123!"))
						.build();
		var user = MyUser.builder().username("jvermeulen")
						.role(Role.USER)
						.password(encoder.encode("User123!"))
						.build();
		List<MyUser> usersList = Arrays.asList(admin, user);
		repoUser.saveAll(usersList);
		
		
		//Sports
		repoSport.save(new Sport("BasketBall"));
		repoSport.save(new Sport("Cycling"));
		repoSport.save(new Sport("Football"));
		repoSport.save(new Sport("Karate"));
		repoSport.save(new Sport("Powerlifting"));
		repoSport.save(new Sport("Swimming"));

		//Matches
		repoMatch.save(new Match("Basketball", "3-Point Shoutout", "Slam Dunk Contest", LocalDateTime.of(2024, 7, 27, 10, 0), "AccorHotels Arena", 45, 11.75, "12345", "13345"));
		repoMatch.save(new Match("Cycling", "Road Race", "Track Cycling",LocalDateTime.of(2024, 7, 28, 9, 30), "Vélodrome de Saint-Quentin-en-Yvelines", 30, 18.00, "23456", "24456"));
		repoMatch.save(new Match("Football", "Football Tournament", null, LocalDateTime.of(2024, 7, 29, 11, 0), "Stade de France", 40, 22.50, "34567", "35567"));
		repoMatch.save(new Match("Karate", "Kata", "Team Kata", LocalDateTime.of(2024, 7, 30, 8, 0), "Paris Expo Porte de Versailles", 20, 9.50, "45678", "46678"));
		repoMatch.save(new Match("Powerlifting", "Deadlift", "Bench Press", LocalDateTime.of(2024, 8, 1, 14, 0), "Palais des Sports Marcel-Cerdan", 35, 14.25, "56789", "57789"));
		repoMatch.save(new Match("Swimming", "50m Men", "50m Women",LocalDateTime.of(2024, 8, 1, 10, 0), "Stade Aquatique Olympique", 25, 6.75, "67891", "68891"));
		repoMatch.save(new Match("Basketball", "3x3 Basketball Tournament", null, LocalDateTime.of(2024, 8, 2, 9, 0), "Paris La Défense Arena", 15, 13.00, "78912", "79912"));
		repoMatch.save(new Match("Cycling", "Mountain Biking Men", "Mountain Biking Women", LocalDateTime.of(2024, 8, 3, 8, 0), "Château de Versailles", 10, 16.75, "89123", "90123"));
		repoMatch.save(new Match("Football", "Women's Football Tournament", null, LocalDateTime.of(2024, 8, 4, 12, 0), "Parc des Princes", 28, 19.25, "91234", "92234"));
		repoMatch.save(new Match("Karate", "Kata and Kumite", "Weapons Kata", LocalDateTime.of(2024, 8, 5, 11, 0), "Palais des Congrès de Paris", 15, 7.50, "12456", "13456"));
		repoMatch.save(new Match("Powerlifting", "Powerlifting Total", "Squat", LocalDateTime.of(2024, 8, 6, 13, 0), "Stade Pierre de Coubertin", 18, 11.00, "23567", "24567"));
		repoMatch.save(new Match("Swimming", "100m Men", "100m Women", LocalDateTime.of(2024, 8, 7, 14, 0), "Piscine Olympique d'Île-de-France", 12, 4.50, "34678", "35678"));
		repoMatch.save(new Match("Basketball", "Basketball Finals Men", "Basketball Finals Women",LocalDateTime.of(2024, 8, 8, 10, 0), "AccorHotels Arena", 25, 10.25, "45789", "46789"));
		repoMatch.save(new Match("Cycling", "Track Cycling Men", "Track Cycling Women", LocalDateTime.of(2024, 8, 9, 9, 0), "Vélodrome National de Saint-Quentin-en-Yvelines", 40, 14.75, "56891", "57891"));
		repoMatch.save(new Match("Football", "Men's Football Tournament Final", null, LocalDateTime.of(2024, 8, 10, 16, 0), "Stade de France", 35, 21.00, "67912", "68912"));
		repoMatch.save(new Match("Karate", "Karate Finals", null, LocalDateTime.of(2024, 8, 11, 15, 0), "Palais des Congrès de Paris", 18, 8.25, "78123", "79123"));
		repoMatch.save(new Match("Powerlifting", "Weightlifting Finals Men", "Weigtlifting Finals Women", LocalDateTime.of(2024, 8, 3, 8, 0), "Stade Pierre de Coubertin", 20, 13.50, "89234", "90234"));
		repoMatch.save(new Match("Swimming", "200m Men", "200m Women", LocalDateTime.of(2024, 7, 29, 13, 0), "Piscine Olympique d'Île-de-France", 30, 5.75, "91345", "92345"));
		repoMatch.save(new Match("Basketball", "Basketball Semifinals Women", "Basketball Semifinals Men", LocalDateTime.of(2024, 7, 28, 15, 0), "Paris La Défense Arena", 22, 12.75, "12567", "13567"));
		repoMatch.save(new Match("Cycling", "BMX Racing", null, LocalDateTime.of(2024, 7, 29, 9, 0), "Château de Versailles", 15, 17.00, "23678", "24678"));

	    
	}
}
