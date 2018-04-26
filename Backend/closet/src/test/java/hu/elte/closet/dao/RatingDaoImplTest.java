package hu.elte.closet.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import hu.elte.closet.exception.ClosetException;
import hu.elte.closet.model.OpeningHour;
import hu.elte.closet.model.Rating;
import hu.elte.closet.repository.OpeningHourRepository;
import hu.elte.closet.repository.RatingRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RatingDaoImplTest {

	private RatingRepository ratingRepository;
	private RatingDaoImpl ratingDaoImpl;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Before
	public void setUp() {
		this.ratingRepository = Mockito.mock(RatingRepository.class);
		this.ratingDaoImpl = new RatingDaoImpl(ratingRepository);
	}

	@Test
	public void testGetRatingByIdBestCase() {
		Rating rating = new Rating();
		when(ratingRepository.findById(anyInt())).thenReturn(rating);

		Rating result = ratingDaoImpl.getRatingById(0);
		assertEquals(rating, result);
		verify(ratingRepository, times(1)).findById(0);
	}

	@Test
	public void testGetOpeningHourByIdWorstCase() {
		when(ratingRepository.findById(anyInt())).thenReturn(null);
		expectedException.expect(ClosetException.class);
		expectedException.expectMessage("Got back null during getRatingById!");
		ratingDaoImpl.getRatingById(0);

		verify(ratingRepository, times(1)).findById(0);
	}
}