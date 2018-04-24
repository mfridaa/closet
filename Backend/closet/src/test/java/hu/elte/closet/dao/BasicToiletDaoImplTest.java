package hu.elte.closet.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyInt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import hu.elte.closet.exception.ClosetException;
import hu.elte.closet.model.BasicToilet;
import hu.elte.closet.model.Location;
import hu.elte.closet.model.OpeningHour;
import hu.elte.closet.repository.BasicToiletRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class BasicToiletDaoImplTest {
	
	private BasicToiletRepository basicToiletRepository;
	private BasicToiletDaoImpl basicToiletDaoImpl;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Before
	public void setUp() {
		this.basicToiletRepository = Mockito.mock(BasicToiletRepository.class);
		this.basicToiletDaoImpl = new BasicToiletDaoImpl(basicToiletRepository);
	}
	
	@Test
	public void testAddBasicToiletWithOneToiletBestCase(){
		Location location = new Location(1.0f, 1.0f);
		BasicToilet basicToilet = new BasicToilet("Test", location);
		
		when(basicToiletDaoImpl.getBasicToiletsByLocation(location)).thenReturn(new ArrayList<BasicToilet>());
		when(basicToiletRepository.save(basicToilet)).thenReturn(basicToilet);
		
		BasicToilet result = basicToiletDaoImpl.addBasicToilet(basicToilet);
		verify(basicToiletRepository, times(1)).save(basicToilet);
		assertEquals(basicToilet, result);
	}
	
	@Test
	public void testAddBasicToiletWithOneToiletExistsWithSameLocation() throws ClosetException{
		Location location = new Location(1.0f, 1.0f);
		BasicToilet basicToilet = new BasicToilet("Test", location);
		ArrayList<BasicToilet> list = new ArrayList<BasicToilet>();
		list.add(basicToilet);
		
		expectedException.expect(ClosetException.class);
		expectedException.expectMessage("There is existing toilet with these parameters!");
		when(basicToiletDaoImpl.getBasicToiletsByLocation(location)).thenReturn(list);
		
		basicToiletDaoImpl.addBasicToilet(basicToilet);		
		verify(basicToiletRepository, times(0)).save(basicToilet);

	}
	
//	public List<BasicToilet> addBasicToilet(List<BasicToilet> basicToilets) {
//		List<BasicToilet> returns = new ArrayList<>();
//		for(BasicToilet basicToilet : basicToilets){
//			Location location = basicToilet.getLocation();
//			ArrayList<BasicToilet> basicToiletList = getBasicToiletsByLocation(location);
//			if(!basicToiletList.contains(basicToilet))
//				returns.add(basicToiletRepository.save(basicToilet));
//			else
//				throw new ClosetException("There is existing toilet with these parameters!");
//		}
//		return returns;
//	}
	
	@Test
	public void testAddBasicToiletWithToiletListBestCase(){
		Location location1 = new Location(1.0f, 1.0f);
		BasicToilet basicToilet1 = new BasicToilet("Test", location1);
		Location location2 = new Location(1.1f, 1.0f);
		BasicToilet basicToilet2 = new BasicToilet("Test2", location2);
		
		when(basicToiletDaoImpl.getBasicToiletsByLocation(location1)).thenReturn(new ArrayList<BasicToilet>());
		when(basicToiletDaoImpl.getBasicToiletsByLocation(location2)).thenReturn(new ArrayList<BasicToilet>());
		when(basicToiletRepository.save(basicToilet1)).thenReturn(basicToilet1);
		when(basicToiletRepository.save(basicToilet2)).thenReturn(basicToilet2);
		
		List<BasicToilet> list = new ArrayList<>();
		list.add(basicToilet1);
		list.add(basicToilet2);
		
		List<BasicToilet> result = basicToiletDaoImpl.addBasicToilet(list);
		verify(basicToiletRepository, times(1)).save(basicToilet1);
		verify(basicToiletRepository, times(1)).save(basicToilet2);
		assertEquals(list, result);
	}
	
	@Test
	public void testAddBasicToiletWithToiletListExistsWithSameLocation() throws ClosetException{
		Location location1 = new Location(1.0f, 1.0f);
		BasicToilet basicToilet1 = new BasicToilet("Test", location1);
		Location location2 = new Location(1.1f, 1.0f);
		BasicToilet basicToilet2 = new BasicToilet("Test2", location2);
		
		when(basicToiletDaoImpl.getBasicToiletsByLocation(location1)).thenReturn(new ArrayList<BasicToilet>());
		when(basicToiletDaoImpl.getBasicToiletsByLocation(location2)).thenReturn(new ArrayList<BasicToilet>());
		
		ArrayList<BasicToilet> list = new ArrayList<BasicToilet>();
		list.add(basicToilet2);
		
		expectedException.expect(ClosetException.class);
		expectedException.expectMessage("There is existing toilet with these parameters!");
		when(basicToiletDaoImpl.getBasicToiletsByLocation(location2)).thenReturn(list);
		
		List<BasicToilet> parameterList = new ArrayList<>();
		parameterList.add(basicToilet1);
		parameterList.add(basicToilet2);
		
		basicToiletDaoImpl.addBasicToilet(parameterList);		
		verify(basicToiletRepository, times(1)).save(basicToilet1);
		verify(basicToiletRepository, times(0)).save(basicToilet2);
	}
	
	@Test
	public void testGetBasicToiletByIdBestCase(){
		Location location = new Location(1.0f, 1.0f);
		BasicToilet basicToilet = new BasicToilet("Test", location);
		when(basicToiletRepository.findById(anyInt())).thenReturn(basicToilet);
		
		BasicToilet result = basicToiletDaoImpl.getBasicToiletById(1);
		assertEquals(basicToilet, result);
		verify(basicToiletRepository, times(1)).findById(1);
	}
	
	@Test
	public void testGetBasicToiletByIdWorstCase() throws ClosetException{
		when(basicToiletRepository.findById(anyInt())).thenReturn(null);
		
		expectedException.expect(ClosetException.class);
		expectedException.expectMessage("Got back null during getBasicToiletById!");
		
		basicToiletDaoImpl.getBasicToiletById(1);
		
		verify(basicToiletRepository, times(1)).findById(1);
	}
	
	@Test
	public void testGetBasicToiletsByLocation(){
		Location location = new Location(1.0f, 1.0f);
		basicToiletDaoImpl.getBasicToiletsByLocation(location);
		verify(basicToiletRepository, times(1)).findByLocation(1.0f, 1.0f);
	}
//	public List<OpeningHour> getOpeningHours(int id) throws ClosetException{
//		BasicToilet basicToilet =  basicToiletRepository.findById(id);
//		ArrayList<OpeningHour> openingHours = new ArrayList<OpeningHour>();
//		if(basicToilet!=null){
//			Collection<OpeningHour> values = basicToilet.getOpeningHours().values();
//			return new ArrayList<>(values);
//		}
//		else
//			throw new ClosetException("Cannot find toilet by id!");
//	}
//	
//	
//	public ArrayList<BasicToilet> getAll() throws ClosetException{
//		ArrayList<BasicToilet> allBasicToilet = basicToiletRepository.findAll();
//		if(allBasicToilet == null)
//			throw new ClosetException("Got back null during getAll!");
//		return allBasicToilet;
//	}
	
	

}
