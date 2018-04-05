package hu.elte.closet.utils;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import hu.elte.closet.model.Day;

@SpringBootTest
public class ClosetUtilsTest {
	
	@Test
	public void testCalendarDayToDay() {
		assertEquals(Day.Monday, ClosetUtils.CalendarDayToDay(Calendar.MONDAY));
		assertEquals(Day.Tuesday, ClosetUtils.CalendarDayToDay(Calendar.TUESDAY));
		assertEquals(Day.Wednesday, ClosetUtils.CalendarDayToDay(Calendar.WEDNESDAY));
		assertEquals(Day.Thursday, ClosetUtils.CalendarDayToDay(Calendar.THURSDAY));
		assertEquals(Day.Friday, ClosetUtils.CalendarDayToDay(Calendar.FRIDAY));
		assertEquals(Day.Saturday, ClosetUtils.CalendarDayToDay(Calendar.SATURDAY));
		assertEquals(Day.Sunday, ClosetUtils.CalendarDayToDay(Calendar.SUNDAY));
	}
}
