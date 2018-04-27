package hu.elte.closet.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import hu.elte.closet.utils.ClosetUtils;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class BasicToiletTest {

	private BasicToilet basicToilet;
	private List<Rating> ratings;
	
	@Before
	public void setUp() {
		this.basicToilet = new BasicToilet("Test", 1f, 1f);
		this.ratings = new ArrayList<>();
	}
	
	@Test
	public void testUpdateRatingEmptyRatings(){
		assertEquals(0f, basicToilet.getRating(), 0.0);
	}
	
	@Test
	public void testUpdateRatingNotEmptyRatings(){
		ratings.add(new Rating("a", 3, "test"));
		ratings.add(new Rating("b", 5, "test1"));
		basicToilet.setRatings(ratings);
		assertEquals(4f, basicToilet.getRating(), 0.0);
	}
	
	@Test
	public void testUpdateStatusUnknown(){
		assertEquals(Status.Unknown, basicToilet.getStatus());
	}
	
	@Test
	public void testUpdateStatusOpened(){
		Calendar calendar = Calendar.getInstance();
		OpeningHour openingHour = new OpeningHour(ClosetUtils.CalendarDayToDay(calendar.get(Calendar.DAY_OF_WEEK)),LocalTime.now().minusHours(1), LocalTime.now().plusHours(1));
		Map<Day, OpeningHour> openingHours = new HashMap<Day, OpeningHour>();
		openingHours.put(openingHour.getDay(), openingHour);
		basicToilet.setOpeningHours(openingHours);
		assertEquals(Status.Opened, basicToilet.getStatus());
	}
	
	@Test
	public void testUpdateStatusClosed(){
		Calendar calendar = Calendar.getInstance();
		OpeningHour openingHour = new OpeningHour(ClosetUtils.CalendarDayToDay(calendar.get(Calendar.DAY_OF_WEEK)),LocalTime.now().minusHours(2), LocalTime.now());
		Map<Day, OpeningHour> openingHours = new HashMap<Day, OpeningHour>();
		openingHours.put(openingHour.getDay(), openingHour);
		basicToilet.setOpeningHours(openingHours);
		assertEquals(Status.Closed, basicToilet.getStatus());
	}
}
