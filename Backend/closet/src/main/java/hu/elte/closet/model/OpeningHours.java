package hu.elte.closet.model;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OpeningHours extends BaseEntity {

	public OpeningHours(Day day, LocalTime openingHour, LocalTime closingHour) {
		this.day = day;
		this.openingHour = openingHour;
		this.closingHour = closingHour;
	}

	@Column(nullable = false)
	private Day day;

	@Column(nullable = false)
	private LocalTime openingHour;

	@Column(nullable = false)
	private LocalTime closingHour;

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

}

enum Day {
	Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
}
