package hu.elte.closet.model.request;

import java.util.List;

import javax.persistence.Column;

import hu.elte.closet.model.OpeningHour;

public class NameAndLatitudeAndLongitudeAndOpeningHours extends NameAndLatitudeAndLongitude{
	
	@Column(nullable = false)
	private List<OpeningHour> openingHours;
	

	public NameAndLatitudeAndLongitudeAndOpeningHours(String name, float latitude, float longitude,
			List<OpeningHour> openingHours) {
		super(name, latitude, longitude);
		this.openingHours = openingHours;
	}
	
	public NameAndLatitudeAndLongitudeAndOpeningHours() {
	}

	public List<OpeningHour> getOpeningHours() {
		return this.openingHours;
	}

	public void setOpeningHours(List<OpeningHour> openingHours) {
		this.openingHours = openingHours;
	}
	
	
}
