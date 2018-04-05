package hu.elte.closet.dao;

import org.springframework.stereotype.Service;

import hu.elte.closet.model.OpeningHour;

@Service
public interface OpeningHourDao {
	
	void addOpeningHour(OpeningHour openingHour);
	
	OpeningHour getOpeningHourById(int id);
}
