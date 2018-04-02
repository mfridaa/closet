package hu.elte.closet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.closet.dao.RatingDaoImpl;
import hu.elte.closet.model.BasicToilet;
import hu.elte.closet.model.Rating;

@Service
public class RatingService {

	RatingDaoImpl ratingDao;

	@Autowired
	public RatingService(RatingDaoImpl ratingDao) {
		this.ratingDao = ratingDao;
	}

	public Rating getRatingById(int id) {
		return ratingDao.getRatingById(id);
	}
}
