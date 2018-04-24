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
import hu.elte.closet.model.request.NameAndLatitudeAndLongitudeAndOpeningHours;
import hu.elte.closet.model.request.RatingAndStatus;
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
	public ResponseEntity addBasicToilet(
			@RequestBody List<NameAndLatitudeAndLongitudeAndOpeningHours> nameAndLatitudeAndLongitudeAndOpeningHours) {
		List<BasicToilet> basicToilets;
		try {
			basicToilets = basicToiletService.addBasicToilet(nameAndLatitudeAndLongitudeAndOpeningHours);
		} catch (ClosetException e) {
			log.warn(e.getMessage());
			return ResponseEntity.unprocessableEntity().build();
		}
		return ResponseEntity.ok(basicToilets);
	}

	@PostMapping("/addOne")
	public ResponseEntity addBasicToilet(
			@RequestBody NameAndLatitudeAndLongitudeAndOpeningHours nameAndLatitudeAndLongitudeAndOpeningHours) {
		BasicToilet basicToilet;
		try {
			basicToilet = basicToiletService.addBasicToilet(nameAndLatitudeAndLongitudeAndOpeningHours);
		} catch (ClosetException e) {
			log.warn(e.getMessage());
			return ResponseEntity.unprocessableEntity().build();
		}
		return ResponseEntity.ok(basicToilet);
	}

	@GetMapping("/ratingAndStatus/{id}")
	public ResponseEntity getRatingAndStatus(@PathVariable int id) {
		RatingAndStatus ratingAndStatus;
		try {
			ratingAndStatus = basicToiletService.getRatingAndStatusById(id);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(ratingAndStatus);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity getBasicToiletById(@PathVariable int id) {
		BasicToilet basicToilet;
		try{
			basicToilet = basicToiletService.getBasicToiletById(id);
		}catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(basicToilet);
	}

	@GetMapping("/rating/{id}")
	public ResponseEntity getRatingsById(@PathVariable int id) {
		List<Rating> ratings;
		try{
			ratings = basicToiletService.getRatingsById(id);
		}catch (ClosetException e) {
			log.error(e.getMessage());
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(ratings);
	}

	@PostMapping("/rating/add/{id}")
	public void addRating(@PathVariable int id, @RequestBody Rating rating) {
		basicToiletService.addRating(id, rating);
	}

	@PostMapping("/openinghour/add/{id}")
	public void addOpeningHour(@PathVariable int id, @RequestBody OpeningHour openingHour) {
		basicToiletService.addOpeningHour(id, openingHour);
	}

	@GetMapping("/openinghour/all/{id}")
	public ResponseEntity getAllOpeningHours(@PathVariable int id) {
		try {
			return ResponseEntity.ok(basicToiletService.getOpeningHours(id));
		} catch (ClosetException e) {
			log.error(e.getMessage());
			return ResponseEntity.unprocessableEntity().build();
		}
	}

	@GetMapping("/all")
	public ResponseEntity getAll() {
		ArrayList<BasicToilet> allBasicToilet;
		try{
			 allBasicToilet = basicToiletService.getAll();
		}catch (ClosetException e) {
			log.error(e.getMessage());
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(allBasicToilet);
	}

	@GetMapping("/status/{id}")
	public ResponseEntity getStatus(@PathVariable int id) {
		String status;
		try {
			status = basicToiletService.getStatus(id);
		} catch (ClosetException e) {
			log.error(e.getMessage());
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(status);
	}

}
