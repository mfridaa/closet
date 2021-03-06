package hu.elte.closet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import hu.elte.closet.model.request.NameAndLatitudeAndLongitudeAndOpeningHours;
import hu.elte.closet.model.request.RatingAndStatus;

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
		if(openingHours!=null){
			basicToilet = new BasicToilet(name, latitude, longitude, openingHours);
			openingHours.forEach(o -> o.setToilet(basicToilet));
		}
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
			
			if(openingHours!=null){
				BasicToilet basicToilet = new BasicToilet(name, latitude, longitude, openingHours);
				openingHours.forEach(o -> o.setToilet(basicToilet));
				basicToilets.add(basicToilet);
			}
				
			else
				basicToilets.add(new BasicToilet(name, latitude, longitude));
		}
		
		return basicToiletDao.addBasicToilet(basicToilets);
	}

	public BasicToilet getBasicToiletById(int id) throws ClosetException{
		return basicToiletDao.getBasicToiletById(id);
	}

	public List<Rating> getRatingsById(int id) throws ClosetException{
		BasicToilet basicToilet = basicToiletDao.getBasicToiletById(id);
		return basicToilet.getRatings();
	}

	public void addRating(int id, Rating rating) throws ClosetException{
		BasicToilet basicToilet = basicToiletDao.getBasicToiletById(id);
		rating.setToilet(basicToilet);
		basicToilet.getRatings().add(rating);
		basicToiletDao.saveBasicToilet(basicToilet);
	}
	
	public void addOpeningHour(int id, OpeningHour openingHour) throws ClosetException{
		BasicToilet basicToilet = basicToiletDao.getBasicToiletById(id);
		openingHour.setToilet(basicToilet);
		basicToilet.getOpeningHours().put(openingHour.getDay(), openingHour);
		basicToiletDao.saveBasicToilet(basicToilet);
	}
	
	public Map<Day, OpeningHour> getOpeningHoursById(int id) throws ClosetException{
		BasicToilet basicToilet = basicToiletDao.getBasicToiletById(id);
		return basicToilet.getOpeningHours();
	}
	
	public ArrayList<BasicToilet> getAll() throws ClosetException{
		return basicToiletDao.getAll();
	}
	
	public List<OpeningHour> getOpeningHours(int id) throws ClosetException{
		return basicToiletDao.getOpeningHours(id);
	}
	
	public String getStatus(int id) throws ClosetException{
		return basicToiletDao.getBasicToiletById(id).getStatus().toString();
	}
	
	public RatingAndStatus getRatingAndStatusById(int id) throws ClosetException{
		BasicToilet basicToilet = getBasicToiletById(id);
		return new RatingAndStatus(basicToilet.getRating(), basicToilet.getStatus());
	}
}
