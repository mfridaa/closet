package hu.elte.closet.model.request;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import hu.elte.closet.model.Status;

public class RatingAndStatus {

	private float rating;

	private Status status;

	public RatingAndStatus(float rating, Status status) {
		this.rating = rating;
		this.status = status;
	}
	
	public RatingAndStatus() {
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
