package hu.elte.closet.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.closet.model.BasicToilet;
import hu.elte.closet.repository.BasicToiletRepository;

@Service
public class BasicToiletDaoImpl implements BasicToiletDao {

	private BasicToiletRepository basicToiletRepository;

	@Autowired
	public BasicToiletDaoImpl(BasicToiletRepository basicToiletRepository) {
		this.basicToiletRepository = basicToiletRepository;
	}

	public void addBasicToilet(BasicToilet basicToilet) {
		basicToiletRepository.save(basicToilet);
	}

	public BasicToilet getBasicToiletById(int id) {
		return basicToiletRepository.findById(id);
	}
}
