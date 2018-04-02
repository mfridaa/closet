package hu.elte.closet.dao;

import hu.elte.closet.model.Rating;

public interface RatingDao {
	
	void addRating(Rating rating);
	
	Rating getRatingById(int id);
}
