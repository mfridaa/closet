package hu.elte.closet.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import hu.elte.closet.dao.BasicToiletDaoImpl;
import hu.elte.closet.model.BasicToilet;
import hu.elte.closet.model.Day;
import hu.elte.closet.model.OpeningHour;
import hu.elte.closet.model.Rating;
import hu.elte.closet.model.request.NameAndLatitudeAndLongitudeAndOpeningHours;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class BasicToiletServiceTest {

	private BasicToiletDaoImpl basicToiletDaoImpl;
	private BasicToiletService basicToiletService;
	private NameAndLatitudeAndLongitudeAndOpeningHours nameAndLatitudeAndLongitudeAndOpeningHours;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Before
	public void setUp() {
		this.basicToiletDaoImpl = Mockito.mock(BasicToiletDaoImpl.class);
		this.basicToiletService = new BasicToiletService(basicToiletDaoImpl);
		this.nameAndLatitudeAndLongitudeAndOpeningHours = new NameAndLatitudeAndLongitudeAndOpeningHours("TEST", 1f, 1f, new ArrayList<OpeningHour>());
	}
	
	
	@Test
	public void testAddBasicToiletOneObjectWithoutOpeningHours(){
		BasicToilet expected = new BasicToilet("TEST", 1f, 1f);
		when(basicToiletDaoImpl.addBasicToilet(expected)).thenReturn(expected);
		
		BasicToilet result = basicToiletService.addBasicToilet(nameAndLatitudeAndLongitudeAndOpeningHours);
		
		assertEquals(expected, result);
		verify(basicToiletDaoImpl, times(1)).addBasicToilet(expected);
	}
	
	@Test
	public void testAddBasicToiletOneObjectWithOpeningHours(){
		ArrayList<OpeningHour> openingHours = new ArrayList<>();
		openingHours.add(new OpeningHour(Day.Monday, LocalTime.now(), LocalTime.now()));
		BasicToilet expected = new BasicToilet("TEST", 1f, 1f, openingHours);
		when(basicToiletDaoImpl.addBasicToilet(expected)).thenReturn(expected);
		
		BasicToilet result = basicToiletService.addBasicToilet(nameAndLatitudeAndLongitudeAndOpeningHours);
		
		assertEquals(expected, result);
		verify(basicToiletDaoImpl, times(1)).addBasicToilet(expected);
	}
	
	@Test
	public void testAddBasicToiletMoreObjectWithoutOpeningHours(){
		List<NameAndLatitudeAndLongitudeAndOpeningHours> nameAndLatitudeAndLongitudeAndOpeningHoursList = new ArrayList<>();
		nameAndLatitudeAndLongitudeAndOpeningHoursList.add(this.nameAndLatitudeAndLongitudeAndOpeningHours);
		nameAndLatitudeAndLongitudeAndOpeningHoursList.add(new NameAndLatitudeAndLongitudeAndOpeningHours("TEST2", 2f, 2f, new ArrayList<OpeningHour>()));
		BasicToilet toilet1 = new BasicToilet("TEST", 1f, 1f);
		BasicToilet toilet2 = new BasicToilet("TEST2", 2f, 2f);
		List<BasicToilet> expected = new ArrayList<>();
		expected.add(toilet1);
		expected.add(toilet2);
		when(basicToiletDaoImpl.addBasicToilet(expected)).thenReturn(expected);
		
		List<BasicToilet> result = basicToiletService.addBasicToilet(nameAndLatitudeAndLongitudeAndOpeningHoursList);
		
		assertEquals(expected, result);
		verify(basicToiletDaoImpl, times(1)).addBasicToilet(expected);
	}
	
	@Test
	public void testAddBasicToiletMoreObjectWithOpeningHours(){
		ArrayList<OpeningHour> openingHours = new ArrayList<>();
		openingHours.add(new OpeningHour(Day.Monday, LocalTime.now(), LocalTime.now()));
		
		List<NameAndLatitudeAndLongitudeAndOpeningHours> nameAndLatitudeAndLongitudeAndOpeningHoursList = new ArrayList<>();
		nameAndLatitudeAndLongitudeAndOpeningHoursList.add(this.nameAndLatitudeAndLongitudeAndOpeningHours);
		nameAndLatitudeAndLongitudeAndOpeningHoursList.add(new NameAndLatitudeAndLongitudeAndOpeningHours("TEST2", 2f, 2f, new ArrayList<OpeningHour>()));
		BasicToilet toilet1 = new BasicToilet("TEST", 1f, 1f, openingHours);
		BasicToilet toilet2 = new BasicToilet("TEST2", 2f, 2f, openingHours);
		List<BasicToilet> expected = new ArrayList<>();
		expected.add(toilet1);
		expected.add(toilet2);
		when(basicToiletDaoImpl.addBasicToilet(expected)).thenReturn(expected);
		
		List<BasicToilet> result = basicToiletService.addBasicToilet(nameAndLatitudeAndLongitudeAndOpeningHoursList);
		
		assertEquals(expected, result);
		verify(basicToiletDaoImpl, times(1)).addBasicToilet(expected);
	}
	
	@Test
	public void testAddRating(){
		BasicToilet basicToilet = new BasicToilet("TEST", 1f, 1f);
		Rating rating = new Rating("A", 5, "a");
		ArrayList<Rating> ratings = new ArrayList<>();
		ratings.add(rating);
		basicToilet.setRatings(ratings);
		when(basicToiletDaoImpl.getBasicToiletById(anyInt())).thenReturn(basicToilet);
		basicToiletService.addRating(1, rating);
		verify(basicToiletDaoImpl, times(1)).getBasicToiletById(1);
		verify(basicToiletDaoImpl, times(1)).saveBasicToilet(basicToilet);
	}
	
	@Test
	public void testAddOpeningHour(){
		BasicToilet basicToilet = new BasicToilet("TEST", 1f, 1f);
		OpeningHour openingHour = new OpeningHour(Day.Friday, LocalTime.now(), LocalTime.now());
		when(basicToiletDaoImpl.getBasicToiletById(1)).thenReturn(basicToilet);
		basicToiletService.addOpeningHour(1, openingHour);
		verify(basicToiletDaoImpl, times(1)).getBasicToiletById(1);
		verify(basicToiletDaoImpl, times(1)).saveBasicToilet(basicToilet);
	}
}
