package hu.elte.closet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.closet.dao.OpeningHourDaoImpl;
import hu.elte.closet.model.OpeningHour;

@Service
public class OpeningHourService {

	private OpeningHourDaoImpl openingHourDao;

	@Autowired
	public OpeningHourService(OpeningHourDaoImpl openingHourDao) {
		this.openingHourDao = openingHourDao;
	}

	public void addOpeningHour(OpeningHour openingHour) {
		openingHourDao.addOpeningHour(openingHour);
	}

	public OpeningHour getOpenigHourById(int id) {
		return openingHourDao.getOpeningHourById(id);
	}

}