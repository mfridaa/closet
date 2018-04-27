package hu.elte.closet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hu.elte.closet.model.BasicToilet;

@Repository
public interface BasicToiletRepository extends CrudRepository<BasicToilet, Integer> {

	public BasicToilet findById(int id);	
}
