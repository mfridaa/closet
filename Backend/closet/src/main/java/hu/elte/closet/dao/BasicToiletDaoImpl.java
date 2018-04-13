package hu.elte.closet.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.closet.model.BasicToilet;
import hu.elte.closet.model.Location;
import hu.elte.closet.repository.BasicToiletRepository;

@Service
public class BasicToiletDaoImpl implements BasicToiletDao {
	
	private Logger log = LoggerFactory.getLogger(BasicToiletDaoImpl.class);

	private BasicToiletRepository basicToiletRepository;

	@Autowired
	public BasicToiletDaoImpl(BasicToiletRepository basicToiletRepository) {
		this.basicToiletRepository = basicToiletRepository;
	}

	public void addBasicToilet(BasicToilet basicToilet) {
		Location location = basicToilet.getLocation();
		ArrayList<BasicToilet> basicToiletList = getBasicToiletsByLocation(location);
		if(!basicToiletList.contains(basicToilet))
			basicToiletRepository.save(basicToilet);
		else
			log.debug("There is existing toilet with this parameters!");
	}

	public BasicToilet getBasicToiletById(int id) {
		return basicToiletRepository.findById(id);
	}
	
	public ArrayList<BasicToilet> getBasicToiletsByLocation(Location location) {
		float latitude = location.getLatitude();
		float longitude = location.getLongitude();
		return basicToiletRepository.findByLocation(latitude, longitude);
	}
	
	public ArrayList<BasicToilet> getAll(){
		ArrayList<BasicToilet>  asd = basicToiletRepository.findAll();
		return basicToiletRepository.findAll();
	}
	
}
