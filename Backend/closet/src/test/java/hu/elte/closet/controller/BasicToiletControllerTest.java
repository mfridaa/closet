package hu.elte.closet.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import hu.elte.closet.exception.ClosetException;
import hu.elte.closet.model.BasicToilet;
import hu.elte.closet.model.OpeningHour;
import hu.elte.closet.model.Rating;
import hu.elte.closet.model.Status;
import hu.elte.closet.model.request.NameAndLatitudeAndLongitudeAndOpeningHours;
import hu.elte.closet.model.request.RatingAndStatus;
import hu.elte.closet.service.BasicToiletService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class BasicToiletControllerTest {

	@InjectMocks
	protected BasicToiletController basicToiletController;
	@Mock
	protected BasicToiletService basicToiletService;

	private MockMvc mockMvc;
	private String nameAndLatitudeAndLongitudeList = "[{\"name\" : \"TEST\", \"latitude\" : 162.12, \"longitude\" : 342.12}]";
	private String nameAndLatitudeAndLongitudeSingle = "{\"name\" : \"TEST\", \"latitude\" : 162.12, \"longitude\" : 342.12}";

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(basicToiletController).build();
	}

	@Test
	public void testAddToiletBestCase() throws Exception {
		List<NameAndLatitudeAndLongitudeAndOpeningHours> list = new ArrayList<>();
		list.add(new NameAndLatitudeAndLongitudeAndOpeningHours());
		when(basicToiletService.addBasicToilet(list)).thenReturn(Arrays.asList(new BasicToilet("Test", 1f, 1f)));
		mockMvc.perform(post("/toilet/add").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(nameAndLatitudeAndLongitudeList)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}

	@Test
	public void testAddToiletWorstCase() throws Exception {
		Mockito.doThrow(new ClosetException()).when(basicToiletService).addBasicToilet(any(List.class));
		mockMvc.perform(post("/toilet/add").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(nameAndLatitudeAndLongitudeList)).andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void testAddToiletOneToiletBestCase() throws Exception {
		when(basicToiletService.addBasicToilet(any(NameAndLatitudeAndLongitudeAndOpeningHours.class)))
				.thenReturn(new BasicToilet("Test", 1f, 1f));
		mockMvc.perform(post("/toilet/addOne").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(nameAndLatitudeAndLongitudeSingle))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}

	@Test
	public void testAddToiletOneToiletWorstCase() throws Exception {
		Mockito.doThrow(new ClosetException()).when(basicToiletService)
				.addBasicToilet(any(NameAndLatitudeAndLongitudeAndOpeningHours.class));
		mockMvc.perform(post("/toilet/addOne").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(nameAndLatitudeAndLongitudeSingle))
				.andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void testGetRatingAndStatusBestCase() throws Exception{
		when(basicToiletService.getRatingAndStatusById(anyInt())).thenReturn(new RatingAndStatus(5f, Status.Closed));
		mockMvc.perform(get("/toilet/ratingAndStatus/{id}", 1)).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$.rating", is(5.0)));
	}
	
	@Test
	public void testGetRatingAndStatusWorstCase() throws Exception{
		Mockito.doThrow(new ClosetException()).when(basicToiletService).getRatingAndStatusById(anyInt());
		
		mockMvc.perform(get("/toilet/ratingAndStatus/{id}", 1)).andExpect(status().isNotFound());
	}
	
	@Test
	public void testGetToiletByIdBestCase() throws Exception{
		when(basicToiletService.getBasicToiletById(anyInt())).thenReturn(new BasicToilet("TEST", 1.0f, 1.0f));
		mockMvc.perform(get("/toilet/get/{id}", 1)).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$.name", is("TEST")));
	}
	
	@Test
	public void testGetToiletByIdWorstCase() throws Exception{
		Mockito.doThrow(new ClosetException()).when(basicToiletService).getBasicToiletById(anyInt());
		
		mockMvc.perform(get("/toilet/get/{id}", 1)).andExpect(status().isNotFound());
	}
	
	@Test
	public void testGetRatingsByIdBestCase() throws Exception{
		when(basicToiletService.getRatingsById(anyInt())).thenReturn(Arrays.asList(new Rating("TEST", 5, "a")));
		mockMvc.perform(get("/toilet/rating/{id}", 1)).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}
	
	@Test
	public void testGetRatingsByIdWorstCase() throws Exception{
		Mockito.doThrow(new ClosetException()).when(basicToiletService).getRatingsById(anyInt());
		
		mockMvc.perform(get("/toilet/rating/{id}", 1)).andExpect(status().isNotFound());
	}
	
	@Test
	public void testGetAllOpeningHoursBestCase() throws Exception{
		when(basicToiletService.getOpeningHours(anyInt())).thenReturn(new ArrayList<OpeningHour>());
		mockMvc.perform(get("/toilet/openinghour/all/{id}", 1)).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}
	
	@Test
	public void testGetAllOpeningHoursWorstCase() throws Exception{
		Mockito.doThrow(new ClosetException()).when(basicToiletService).getOpeningHours(anyInt());
		
		mockMvc.perform(get("/toilet/openinghour/all/{id}", 1)).andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void testGetAllBestCase() throws Exception{
		when(basicToiletService.getAll()).thenReturn(new ArrayList<BasicToilet>());
		mockMvc.perform(get("/toilet/all")).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}
	
	@Test
	public void testGetAllWorstCase() throws Exception{
		Mockito.doThrow(new ClosetException()).when(basicToiletService).getAll();
		
		mockMvc.perform(get("/toilet/all")).andExpect(status().isNotFound());
	}
		
	@Test
	public void testGetStatusBestCase() throws Exception{
		when(basicToiletService.getStatus(anyInt())).thenReturn("");
		mockMvc.perform(get("/toilet/status/{id}", 1)).andExpect(status().isOk());
	}
	
	@Test
	public void testGetStatusWorstCase() throws Exception{
		Mockito.doThrow(new ClosetException()).when(basicToiletService).getStatus(anyInt());
		
		mockMvc.perform(get("/toilet/status/{id}", 1)).andExpect(status().isNotFound());
	}
	
	@Test
	public void testAddOpeningHourBestCase() throws Exception {
		String openingHour = "{\"day\" : \"Friday\",\"openingHour\" : \"08:00\",\"closingHour\" : \"18:00\"}";
		mockMvc.perform(post("/toilet/openinghour/add/{id}", 1).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(openingHour)).andExpect(status().isOk());
	}

	@Test
	public void testAddOpeningHourWorstCase() throws Exception {
		String openingHour = "{\"day\" : \"Friday\",\"openingHour\" : \"08:00\",\"closingHour\" : \"18:00\"}";
		Mockito.doThrow(new ClosetException()).when(basicToiletService).addOpeningHour(anyInt(), any(OpeningHour.class));
		mockMvc.perform(post("/toilet/openinghour/add/{id}", 1).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(openingHour)).andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void testAddRatingBestCase() throws Exception {
		String rating = "{\"rater\" : \"adasd\",\"rating\" : 2,\"description\" : \"Nagasdzar\"}";
		mockMvc.perform(post("/toilet/rating/add/{id}", 1).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(rating)).andExpect(status().isOk());
	}

	@Test
	public void testAddRatingWorstCase() throws Exception {
		String rating = "{\"rater\" : \"adasd\",\"rating\" : 2,\"description\" : \"Nagasdzar\"}";
		Mockito.doThrow(new ClosetException()).when(basicToiletService).addRating(anyInt(), any(Rating.class));
		mockMvc.perform(post("/toilet/rating/add/{id}", 1).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(rating)).andExpect(status().isUnprocessableEntity());
	}
	
}
