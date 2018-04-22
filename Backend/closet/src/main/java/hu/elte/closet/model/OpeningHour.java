package hu.elte.closet.model;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class OpeningHour extends BaseEntity {

	
	public OpeningHour(Day day, LocalTime openingHour, LocalTime closingHour) {
		this.day = day;
		this.openingHour = openingHour;
		this.closingHour = closingHour;
	}

	public OpeningHour() {
	}

	@Column
	@NotNull
	private Day day;

	@Column
	@NotNull
	private LocalTime openingHour;

	@Column
	@NotNull
	private LocalTime closingHour;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "toilet")
	private BasicToilet toilet;

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public LocalTime getOpeningHour() {
		return openingHour;
	}

	public void setOpeningHour(LocalTime openingHour) {
		this.openingHour = openingHour;
	}

	public LocalTime getClosingHour() {
		return closingHour;
	}

	public void setClosingHour(LocalTime closingHour) {
		this.closingHour = closingHour;
	}

	public void setToilet(BasicToilet toilet) {
		this.toilet = toilet;
	}
	
	

}
