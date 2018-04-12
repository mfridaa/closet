package hu.elte.closet.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.closet.model.BasicToilet;
import hu.elte.closet.model.Rating;
import hu.elte.closet.model.request.NameAndLatitudeAndLongitude;
import hu.elte.closet.service.BasicToiletService;

@RestController
@RequestMapping("toilet")
public class BasicToiletController {

	private BasicToiletService basicToiletService;

	@Autowired
	public BasicToiletController(BasicToiletService basicToiletService) {
		this.basicToiletService = basicToiletService;
	}

//	@GetMapping
//	public ResponseEntity<BasicToilet> getAllToilets(){
//		
//	}
	
	@PostMapping("/add")
	public void addBasicToilet(@RequestBody NameAndLatitudeAndLongitude nameAndLatitudeAndLongitude) {
		basicToiletService.addBasicToilet(nameAndLatitudeAndLongitude);
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
}
