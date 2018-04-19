package hu.elte.closet.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.closet.exception.ClosetException;
import hu.elte.closet.model.BasicToilet;
import hu.elte.closet.model.OpeningHour;
import hu.elte.closet.model.Rating;
import hu.elte.closet.model.request.NameAndLatitudeAndLongitude;
import hu.elte.closet.service.BasicToiletService;

@RestController
@RequestMapping("toilet")
public class BasicToiletController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	private BasicToiletService basicToiletService;

	@Autowired
	public BasicToiletController(BasicToiletService basicToiletService) {
		this.basicToiletService = basicToiletService;
	}

	@PostMapping("/add")
	public ResponseEntity addBasicToilet(@RequestBody NameAndLatitudeAndLongitude nameAndLatitudeAndLongitude) {
		int id;
		try{
			id = basicToiletService.addBasicToilet(nameAndLatitudeAndLongitude);
		}catch (ClosetException e) {
			log.warn(e.getMessage());
			return ResponseEntity.unprocessableEntity().build();
		}
		return ResponseEntity.ok(id);
		
	}

	@GetMapping("/get/{id}")
	public BasicToilet getBasicToiletById(@PathVariable int id) {
		return basicToiletService.getBasicToiletById(id);
	}
	
	@GetMapping("/rating/{id}")
	public List<Rating> getRatingsById(@PathVariable int id) {
		return basicToiletService.getRatingsById(id);
	}
	
	@PostMapping("/rating/add/{id}")
	public void addRating(@PathVariable int id, @RequestBody Rating rating) {
		basicToiletService.addRating(id, rating);
	}
	
	@PostMapping("/openinghour/add/{id}")
	public void addOpeningHour(@PathVariable int id, @RequestBody OpeningHour openingHour) {
		basicToiletService.addOpeningHour(id, openingHour);
	}
	
	@GetMapping("/all")
	public ArrayList<BasicToilet> getAll() {
		return basicToiletService.getAll();
	}
	
	@GetMapping("/status/{id}")
	public ResponseEntity getStatus(@PathVariable int id) {
		String status;
		try{
			status = basicToiletService.getStatus(id);
		}catch (ClosetException e) {
			log.error(e.getMessage());
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(status);
	}
	
	
}
