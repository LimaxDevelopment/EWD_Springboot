package domain;

import java.io.Serializable;


import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import validator.DateTimeRange;
import validator.DifferentFirstLastDigit;
import validator.DisciplineValidation;
import validator.OlympicNr2Range;
import validator.UniqueOlympicNr;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) 
@AllArgsConstructor
@Table(name="matchSport")
@DisciplineValidation
public class Match implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String sport;

	private String discipline1;
	
	private String discipline2;
	
	@NotNull(message="{validation.NotNull.message}")
	@DateTimeFormat(style = "SS")
	@DateTimeRange(startDate="26/07/2024 08:00", endDate="11/08/2024 23:59")
	private LocalDateTime date;
	@NotBlank(message="{validation.NotBlank.message}")
	private String stadium;
	
	@NotNull(message="{validation.NotNull.message}")
	@Range(min=0, max=50, message="{validation.availablePlaces.Range.message}")
	@JsonProperty("match_availablePlaces")
	private int availablePlaces;
	
	@NotNull(message="{validation.NotNull}")
	@Range(min=1, max=149, message="{validation.priceTicket.Range.message}")
	private double priceTicket;
	
	@NotBlank(message="{validation.NotBlank.message}")
	@Pattern(regexp = "^[1-9]\\d{4}$", message="{validation.olympicNr1.Pattern.message}")
	@DifferentFirstLastDigit(message="{validation.olympicNr1.DifferentFirstLastDigit.message}")
	//@UniqueOlympicNr(message="{validation.olympicNr1.UniqueOlympicNr.message}")
	private String olympicNr1;
	
	@NotBlank(message="{validation.NotBlank.message}")
	//@OlympicNr2Range(message = "{validation.olympicNr2.OlympicNr2Range.message}")
	private String olympicNr2;
	
	public Match(String sport, String discipline1, String discipline2, LocalDateTime date, String stadium, int availablePlaces, double priceTicket, String olympicNr1, String olympicNr2) {
		this.sport = sport;
		this.discipline1 = discipline1;
		this.discipline2 = discipline2;
		this.date = date;
		this.stadium = stadium;
		this.availablePlaces = availablePlaces;
		this.priceTicket = priceTicket;
		this.olympicNr1 = olympicNr1;
		this.olympicNr2 = olympicNr2;
	}
}
