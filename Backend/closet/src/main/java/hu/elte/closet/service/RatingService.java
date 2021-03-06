package hu.elte.closet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.closet.dao.RatingDaoImpl;
import hu.elte.closet.exception.ClosetException;
import hu.elte.closet.model.Rating;

@Service
public class RatingService {

	RatingDaoImpl ratingDao;

	@Autowired
	public RatingService(RatingDaoImpl ratingDao) {
		this.ratingDao = ratingDao;
	}

	public void addRating(Rating rating) {
		ratingDao.addRating(rating);
	}
	
	public Rating getRatingById(int id) throws ClosetException{
		return ratingDao.getRatingById(id);
	}
	
	
}
