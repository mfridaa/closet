package hu.elte.closet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hu.elte.closet.model.OpeningHour;
import hu.elte.closet.model.Rating;

@Repository
public interface OpeningHourRepository extends CrudRepository<OpeningHour, Integer>{
	
	public OpeningHour findById(int id);
}
