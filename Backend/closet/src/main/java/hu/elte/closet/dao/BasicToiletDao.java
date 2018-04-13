package hu.elte.closet.dao;

import java.util.List;

import hu.elte.closet.model.BasicToilet;
import hu.elte.closet.model.Location;

public interface BasicToiletDao {

	void addBasicToilet(BasicToilet basicToilet);

	BasicToilet getBasicToiletById(int id);
	
	List<BasicToilet> getBasicToiletsByLocation(Location location);
	
	List<BasicToilet> getAll();

}
