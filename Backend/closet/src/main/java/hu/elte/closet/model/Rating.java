package hu.elte.closet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RATINGS")
public class Rating extends BaseEntity {

	public Rating(long rater, int rating, String description) {
		this.rater = rater;
		this.rating = rating;
		this.description = description;
	}

	@Column
	private long rater;

	@Column
	private int rating;

	@Column
	private String description;

	@ManyToOne
	@JoinColumn(name = "toilet")
	private BasicToilet toilet;

	public long getRater() {
		return rater;
	}

	public void setRater(long rater) {
		this.rater = rater;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
