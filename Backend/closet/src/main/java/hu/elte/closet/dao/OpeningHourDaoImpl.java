package hu.elte.closet.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.closet.model.OpeningHour;
import hu.elte.closet.repository.OpeningHourRepository;

@Service
public class OpeningHourDaoImpl implements OpeningHourDao {

	private OpeningHourRepository openingHourRepository;

	@Autowired
	public OpeningHourDaoImpl(OpeningHourRepository openingHourRepository) {
		this.openingHourRepository = openingHourRepository;
	}

	@Override
	public void addOpeningHour(OpeningHour openingHour) {
		openingHourRepository.save(openingHour);
	}
	
	@Override
	public void addOpeningHours(List<OpeningHour> openingHours) {
		openingHourRepository.save(openingHours);
	}

	@Override
	public OpeningHour getOpeningHourById(int id) {
		return openingHourRepository.findById(id);
	}

}
