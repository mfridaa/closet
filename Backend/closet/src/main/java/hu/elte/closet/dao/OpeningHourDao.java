package hu.elte.closet.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import hu.elte.closet.model.OpeningHour;

@Service
public interface OpeningHourDao {
	
	void addOpeningHour(OpeningHour openingHour);
	
	void addOpeningHours(List<OpeningHour> openingHours);
	
	OpeningHour getOpeningHourById(int id);
}
