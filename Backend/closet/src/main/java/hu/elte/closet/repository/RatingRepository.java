package hu.elte.closet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hu.elte.closet.model.Rating;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Integer>{
	
	public Rating findById(int id);
}
