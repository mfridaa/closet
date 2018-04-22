package hu.elte.closet.dao;

import java.util.List;

import hu.elte.closet.model.BasicToilet;
import hu.elte.closet.model.Location;
import hu.elte.closet.model.OpeningHour;

public interface BasicToiletDao {

	BasicToilet addBasicToilet(BasicToilet basicToilet);

	BasicToilet getBasicToiletById(int id);
	
	List<BasicToilet> getBasicToiletsByLocation(Location location);
	
	List<BasicToilet> getAll();
	
	List<OpeningHour> getOpeningHours(int id);

}
