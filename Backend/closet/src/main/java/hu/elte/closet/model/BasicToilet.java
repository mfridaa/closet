package hu.elte.closet.model;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import hu.elte.closet.utils.ClosetUtils;

@Entity
@Table(name = "TOILETS")
public class BasicToilet extends BaseEntity {

	public BasicToilet(String name, Float latitude, Float longitude) {
		this.name = name;
		this.location = new Location(latitude, longitude);
		updateRating();
		updateStatus();
	}	
	
	public BasicToilet(String name, Float latitude, Float longitude, List<OpeningHour> openingHours) {
		this.name = name;
		this.location = new Location(latitude, longitude);
		openingHours.forEach(openingHour -> this.openingHours.put(openingHour.getDay(), openingHour));
		updateStatus();
	}


	public BasicToilet(String name, Location location) {
		this.name = name;
		this.location = location;
	}

	public BasicToilet() {
	}
	
	@NotNull
	@Column
	private String name;

	@Embedded
	@NotNull
	private Location location;

	@Column
	private float rating;

	@Column
	@Enumerated(EnumType.STRING)
	private Status status;

	@JsonIgnore
	@OneToMany(mappedBy = "toilet", cascade = { CascadeType.ALL })
	private List<Rating> ratings = new LinkedList<Rating>();

	@JsonIgnore
	@OneToMany(mappedBy = "toilet", cascade = { CascadeType.ALL })
	@MapKey(name = "day")
	private Map<Day, OpeningHour> openingHours = new HashMap<Day, OpeningHour>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Embedded
	public Location getLocation() {
		return location;
	}

	public void setLocation(float latitude, float longitude) {
		this.location = new Location(latitude, longitude);
	}

	public float getRating() {
		updateRating();
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Status getStatus() {
		updateStatus();
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Rating> getRatings() {
		return ratings;
	}
	
	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public Map<Day, OpeningHour> getOpeningHours() {
		return openingHours;
	}
	
	public void setOpeningHours(Map<Day, OpeningHour> openingHours){
		this.openingHours = openingHours;
	}

	private void updateRating() {
		rating = (ratings.isEmpty()) ?  0 : (float) ratings.stream().mapToInt(r -> r.getRatingNum()).sum() / ratings.size();
	}
	
	private void updateStatus() {
		Calendar calendar = Calendar.getInstance();
		Day day = ClosetUtils.CalendarDayToDay(calendar.get(Calendar.DAY_OF_WEEK));
		if(openingHours.containsKey(day)) {
			OpeningHour openingHour =  openingHours.get(day);
			LocalTime currentTime = LocalTime.now();
			if(currentTime.isBefore(openingHour.getOpeningHour()) || currentTime.isAfter(openingHour.getClosingHour()))
				this.status = Status.Closed;
			else
				this.status = Status.Opened;
		}
		else
			this.status = Status.Unknown;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(name, location);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicToilet other = (BasicToilet) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
