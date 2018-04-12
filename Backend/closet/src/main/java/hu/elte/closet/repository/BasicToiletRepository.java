package hu.elte.closet.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hu.elte.closet.model.BasicToilet;

@Repository
public interface BasicToiletRepository extends CrudRepository<BasicToilet, Integer> {
	
	public BasicToilet findById(int id);
	
	@Query(value = "select * from toilets where CAST(latitude AS DECIMAL) = CAST(?1 AS DECIMAL) and CAST(longitude AS DECIMAL) = CAST(?2 AS DECIMAL)", nativeQuery=true)
	public ArrayList<BasicToilet> findByLocation(float latitude, float longitude);
	
	public ArrayList<BasicToilet> findAll();
}
