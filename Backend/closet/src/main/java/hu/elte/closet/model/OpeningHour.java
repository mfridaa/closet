package hu.elte.closet.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class OpeningHour extends BaseEntity {

	public OpeningHour(Day day, LocalTime openingHour, LocalTime closingHour) {
		this.day = day;
		this.openingHour = openingHour;
		this.closingHour = closingHour;
	}
	
	

	public OpeningHour(String day, String openingHour, String closingHour) {
		DateFormat formatter = new SimpleDateFormat("HH:mm");
		this.day = Day.valueOf(day);
		try {
			this.openingHour = LocalTime.ofNanoOfDay(formatter.parse(openingHour).getTime());
			this.openingHour = LocalTime.ofNanoOfDay(formatter.parse(closingHour).getTime());
		}catch(ParseException e) {
			//TODO:Log, openingHour construct
		}
		
	}

	@Column(nullable = false)
	private Day day;

	@Column(nullable = false)
	private LocalTime openingHour;

	@Column(nullable = false)
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

