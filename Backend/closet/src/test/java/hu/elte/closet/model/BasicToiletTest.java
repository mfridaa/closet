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
}
