package hu.elte.closet.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import hu.elte.closet.exception.ClosetException;
import hu.elte.closet.model.Rating;
import hu.elte.closet.service.RatingService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RatingControllerTest {

	@InjectMocks
	protected RatingController ratingController;
	@Mock
	protected RatingService ratingService;
	
	private MockMvc mockMvc;

	@Before
	public void setUp() {
	MockitoAnnotations.initMocks(this);
	mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();
	}
	
	@Test
	public void testGetRatingByIdBestCase() throws Exception{
		when(ratingService.getRatingById(anyInt())).thenReturn(new Rating("Rater",5,"Content"));
		mockMvc.perform(get("/ratings/get/{id}", 1)).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$.ratingNum", is(5)));
	}
	
	@Test
	public void testGetRatingByIdWorstCase() throws Exception{
		Mockito.doThrow(new ClosetException()).when(ratingService).getRatingById(anyInt());
		
		mockMvc.perform(get("/ratings/get/{id}", 1)).andExpect(status().isNotFound());
	}
}
