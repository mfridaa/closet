package hu.elte.closet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.closet.dao.BasicToiletDaoImpl;
import hu.elte.closet.model.BasicToilet;
import hu.elte.closet.model.Rating;
import hu.elte.closet.model.request.NameAndLatitudeAndLongitude;

@Service
public class BasicToiletService {

	private BasicToiletDaoImpl basicToiletDao;

	@Autowired
	public BasicToiletService(BasicToiletDaoImpl basicToiletDao) {
		this.basicToiletDao = basicToiletDao;
	}

	public void addBasicToilet(NameAndLatitudeAndLongitude nameAndLatitudeAndLongitude) {
		String name = nameAndLatitudeAndLongitude.getName();
		Float latitude = nameAndLatitudeAndLongitude.getLatitude();
		Float longitude = nameAndLatitudeAndLongitude.getLongitude();
		BasicToilet basicToilet = new BasicToilet(name, latitude, longitude);
		basicToiletDao.addBasicToilet(basicToilet);
	}

	public BasicToilet getBasicToiletById(int id) {
		return basicToiletDao.getBasicToiletById(id);
	}

	public List<Rating> getRatingsById(int id) {
		BasicToilet basicToilet = basicToiletDao.getBasicToiletById(id);
		return basicToilet.getRatings();
	}

	public void addRating(int id, Rating rating) {
		BasicToilet basicToilet = basicToiletDao.getBasicToiletById(id);
		rating.setToilet(basicToilet);
		basicToilet.getRatings().add(rating);
		basicToiletDao.addBasicToilet(basicToilet);
	}
}
