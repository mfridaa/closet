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
import hu.elte.closet.repository.OpeningHourRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OpeningHourDaoImplTest {

	private OpeningHourRepository openingHourRepository;
	private OpeningHourDaoImpl openingHourDaoImpl;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Before
	public void setUp() {
		this.openingHourRepository = Mockito.mock(OpeningHourRepository.class);
		this.openingHourDaoImpl = new OpeningHourDaoImpl(openingHourRepository);
	}

	@Test
	public void testGetOpeningHourByIdBestCase() {
		OpeningHour openingHour = new OpeningHour();
		when(openingHourRepository.findById(anyInt())).thenReturn(openingHour);

		OpeningHour result = openingHourDaoImpl.getOpeningHourById(0);
		assertEquals(openingHour, result);
		verify(openingHourRepository, times(1)).findById(0);
	}

	@Test
	public void testGetOpeningHourByIdWorstCase() {
		when(openingHourRepository.findById(anyInt())).thenReturn(null);
		expectedException.expect(ClosetException.class);
		expectedException.expectMessage("Cannot find openingHour!");
		openingHourDaoImpl.getOpeningHourById(0);

		verify(openingHourRepository, times(1)).findById(0);
	}
}
