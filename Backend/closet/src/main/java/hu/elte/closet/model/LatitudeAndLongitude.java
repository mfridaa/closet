package hu.elte.closet.model;

import javax.persistence.Embeddable;

@Embeddable
public class LatitudeAndLongitude {

	private Float latitude;

	private Float longitude;

	public LatitudeAndLongitude(Float latitude, Float longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public LatitudeAndLongitude() {
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

}
