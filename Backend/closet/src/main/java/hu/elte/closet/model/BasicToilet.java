package hu.elte.closet.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TOILETS")
public class BasicToilet extends BaseEntity {

	public BasicToilet(String name, Float latitude, Float longitude) {
		this.name = name;
		this.latitudeAndLongitude = new LatitudeAndLongitude(latitude, longitude);
		updateRating();
		this.status = Status.Closed;
	}

	public BasicToilet() {
	}

	@Column
	String name;

	@Column
	LatitudeAndLongitude latitudeAndLongitude;

	@Column
	private float rating;

	@Column
	private Status status;

//	@JsonIgnore
	@OneToMany(mappedBy = "toilet", cascade = { CascadeType.ALL })
	private List<Rating> ratings = new LinkedList<Rating>();

	@OneToMany(mappedBy = "toilet", cascade = { CascadeType.ALL })
	@MapKey(name = "day")
	private Map<String, OpeningHours> openingHours = new HashMap<String, OpeningHours>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LatitudeAndLongitude getLatitudeAndLongitude() {
		return latitudeAndLongitude;
	}

	public void setLatitudeAndLongitude(float latitude, float longitude) {
		this.latitudeAndLongitude = new LatitudeAndLongitude(latitude, longitude);
	}

	public float getRating() {
		updateRating();
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Status getStatus() {
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

	public void updateRating() {
		rating = (ratings.isEmpty()) ?  0 : (float) ratings.stream().mapToInt(r -> r.getRatingNum()).sum() / ratings.size();
	}
}

enum Status {
	Opened, Closed
}