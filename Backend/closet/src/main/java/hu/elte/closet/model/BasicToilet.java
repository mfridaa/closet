package hu.elte.closet.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TOILETS")
public class BasicToilet extends BaseEntity {

	public BasicToilet(Float latitude, Float longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Column
	private Float latitude;

	@Column
	private Float longitude;

	@Column
	private int rating;

	@Column
	private String status;

	@OneToMany(mappedBy = "toilet", cascade = { CascadeType.ALL })
	private List<Rating> ratings;

	@OneToMany(mappedBy = "toilet", cascade = { CascadeType.ALL })
	private List<OpeningHours> openingHours;

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}