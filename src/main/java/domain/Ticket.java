package domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tickets")
public class Ticket implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long userId;
	
	private Long matchId;
	
	@Max(value=20, message="{validation.Max.message}")
	private int totalTicketsMatch;
	
	@Max(value=20, message="{validation.Max.message}")
	private int numberOfTickets;
	
	private String sport;
	private String discipline1;
	private String discipline2;
	private String stadium;
	
	@DateTimeFormat(style = "SS")
	private LocalDateTime date;
	
	public Ticket(Long userId, Long matchId, int totalTicketsMatch, int numberOfTickets, String sport, String discipline1, String discipline2, LocalDateTime date, String stadium) {
		this.userId = userId;
		this.matchId = matchId;
		this.totalTicketsMatch = totalTicketsMatch;
		this.numberOfTickets = numberOfTickets;
		this.sport = sport;
		this.discipline1 = discipline1;
		this.discipline2 = discipline2;
		this.date = date;
		this.stadium = stadium;
	}
}
