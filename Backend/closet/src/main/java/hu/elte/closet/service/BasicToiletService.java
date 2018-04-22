package hu.elte.closet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.closet.dao.BasicToiletDaoImpl;
import hu.elte.closet.exception.ClosetException;
import hu.elte.closet.model.BasicToilet;
import hu.elte.closet.model.Day;
import hu.elte.closet.model.OpeningHour;
import hu.elte.closet.model.Rating;
import hu.elte.closet.model.request.NameAndLatitudeAndLongitude;
import hu.elte.closet.model.request.NameAndLatitudeAndLongitudeAndOpeningHours;

@Service
public class BasicToiletService {

	private Logger log = LoggerFactory.getLogger(BasicToiletService.class);
	
	private BasicToiletDaoImpl basicToiletDao;

	@Autowired
	public BasicToiletService(BasicToiletDaoImpl basicToiletDao) {
		this.basicToiletDao = basicToiletDao;
	}
	
	public BasicToilet addBasicToilet(NameAndLatitudeAndLongitudeAndOpeningHours nameAndLatitudeAndLongitudeAndOpeningHours) throws ClosetException{
		String name = nameAndLatitudeAndLongitudeAndOpeningHours.getName();
		Float latitude = nameAndLatitudeAndLongitudeAndOpeningHours.getLatitude();
		Float longitude = nameAndLatitudeAndLongitudeAndOpeningHours.getLongitude();
		List<OpeningHour> openingHours = nameAndLatitudeAndLongitudeAndOpeningHours.getOpeningHours();
		BasicToilet basicToilet;
		if(openingHours != null)
			basicToilet = new BasicToilet(name, latitude, longitude, openingHours);
		else
			basicToilet = new BasicToilet(name, latitude, longitude);
		return basicToiletDao.addBasicToilet(basicToilet);
	}
	
	public List<BasicToilet> addBasicToilet(List<NameAndLatitudeAndLongitudeAndOpeningHours> nameAndLatitudeAndLongitudeAndOpeningHours) throws ClosetException{
		List<BasicToilet> basicToilets = new ArrayList<>();
		
		for(NameAndLatitudeAndLongitudeAndOpeningHours t : nameAndLatitudeAndLongitudeAndOpeningHours){
			String name = t.getName();
			Float latitude = t.getLatitude();
			Float longitude = t.getLongitude();
			List<OpeningHour> openingHours = t.getOpeningHours();
			
			if(openingHours != null)
				basicToilets.add(new BasicToilet(name, latitude, longitude, openingHours));
			else
				basicToilets.add(new BasicToilet(name, latitude, longitude));
		}
		
		return basicToiletDao.addBasicToilet(basicToilets);
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
	
	public void addOpeningHour(int id, OpeningHour openingHour) {
		BasicToilet basicToilet = basicToiletDao.getBasicToiletById(id);
		openingHour.setToilet(basicToilet);
		basicToilet.getOpeningHours().put(openingHour.getDay(), openingHour);
		basicToiletDao.saveBasicToilet(basicToilet);
	}
	
	public Map<Day, OpeningHour> getOpeningHoursById(int id) {
		BasicToilet basicToilet = basicToiletDao.getBasicToiletById(id);
		return basicToilet.getOpeningHours();
	}
	
	public ArrayList<BasicToilet> getAll(){
		return basicToiletDao.getAll();
	}
	
	public String getStatus(int id) throws ClosetException{
		BasicToilet basicToilet = basicToiletDao.getBasicToiletById(id);
		if(basicToilet==null)
			throw new ClosetException("Got back null during getStatus.");
		return basicToilet.getStatus().toString();
	}
}
