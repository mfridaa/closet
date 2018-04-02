package hu.elte.closet.dao;

import hu.elte.closet.model.BasicToilet;

public interface BasicToiletDao {

	void addBasicToilet(BasicToilet basicToilet);

	BasicToilet getBasicToiletById(int id);

}
