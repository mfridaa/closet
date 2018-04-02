package hu.elte.closet.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.closet.model.Rating;
import hu.elte.closet.repository.RatingRepository;

@Service
public class RatingDaoImpl implements RatingDao {

	private RatingRepository ratingRepository;

	@Autowired
	public RatingDaoImpl(RatingRepository ratingRepository) {
		this.ratingRepository = ratingRepository;
	}

	public void addRating(Rating rating) {
		ratingRepository.save(rating);
	}

	public Rating getRatingById(int id) {
		return ratingRepository.findById(id);
	}
}
