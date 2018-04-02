package hu.elte.closet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.closet.model.Rating;
import hu.elte.closet.service.RatingService;

@RestController
@RequestMapping("ratings")
public class RatingController {
	
	private RatingService ratingService;

	@Autowired
	public RatingController(RatingService ratingService) {
		this.ratingService = ratingService;
	}
	
	@GetMapping("/get/{id}")
	public Rating getRatingById(@PathVariable int id) {
		return ratingService.getRatingById(id);
	}
}
