package hu.elte.closet.utils;

import hu.elte.closet.model.Day;

public class ClosetUtils {
	
	public static Day CalendarDayToDay(int day) {
		return Day.values()[(day+5)%7];
	}
	
}
