package hu.elte.closet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.closet.exception.ClosetException;
import hu.elte.closet.service.RatingService;

@RestController
@RequestMapping("ratings")
public class RatingController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private RatingService ratingService;

	@Autowired
	public RatingController(RatingService ratingService) {
		this.ratingService = ratingService;
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity getRatingById(@PathVariable int id) {
		try{
			return ResponseEntity.ok(ratingService.getRatingById(id));
		}catch (ClosetException e) {
			log.error(e.getMessage());
			return ResponseEntity.notFound().build();
		}
		
	}
}
