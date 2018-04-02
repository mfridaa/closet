package hu.elte.closet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "RATINGS")
public class Rating extends BaseEntity {

	public Rating(String rater, int ratingNum, String description) {
		this.rater = rater;
		this.ratingNum = ratingNum;
		this.description = description;
	}

	public Rating() {
	}

	@Column
	private String rater;

	@Column
	private int ratingNum;

	@Column
	private String description;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "toilet")
	private BasicToilet toilet = new BasicToilet();

	public String getRater() {
		return rater;
	}

	public void setRater(String rater) {
		this.rater = rater;
	}

	public int getRatingNum() {
		return ratingNum;
	}

	public void setRating(int ratingNum) {
		this.ratingNum = ratingNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BasicToilet getToilet() {
		return toilet;
	}

	public void setToilet(BasicToilet toilet) {
		this.toilet = toilet;
	}

}
