package perform;

import org.springframework.web.reactive.function.client.WebClient;

import domain.Match;
import reactor.core.publisher.Mono;

public class PerformRestSports {

	private final String SERVER_URI = "http://localhost:8080/rest";
	private WebClient webClient = WebClient.create();
	
	public PerformRestSports() {
		System.out.println("\n----- GET ALL MATCHES -----");
		getAllMatchesSport("Basketball");
		
		System.out.println("\n---GET AVAILABLE PLACES ---");
		getAvailablePlaces("BasketBall", 1L);
	}
	
	private void getAllMatchesSport(String sport) {
		webClient.get().uri(SERVER_URI + "/games/" + sport).retrieve()
        .bodyToFlux(Match.class)
        .flatMap(match -> {
            printMatchData(match);
            return Mono.empty();
        })
        .blockLast();
	}
	
	private void getAvailablePlaces(String sport, Long id) {
		webClient.get().uri(SERVER_URI + "/games/" + sport + "/" + id)
		.retrieve()
		.bodyToMono(Match.class)
		.doOnSuccess(match -> printAvailablePlacesData(match))
		.block();
	}
	
	private void printMatchData(Match match) {
		System.out.printf("ID=%s, Sport=%s, Discipline1=%s, Discipline2=%s, Stadium=%s, AvailablePlaces=%d, PriceTicket=%.2f%n",
				match.getId(), match.getSport(), match.getDiscipline1(), match.getDiscipline2(), match.getStadium(),
				match.getAvailablePlaces(), match.getPriceTicket());
	}
	
	private void printAvailablePlacesData(Match match) {
		System.out.printf("AvailablePlaces=%d%n", match.getAvailablePlaces());
	}
	
}
