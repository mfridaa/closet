package hu.elte.closet.model.request;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class NameAndLatitudeAndLongitude {
	
	@Column(nullable = false)
	@NotNull
	private String name;
	
	@Column(nullable = false)
	@NotNull
	private float latitude;
	
	@Column(nullable = false)
	@NotNull
	private float longitude;

	public NameAndLatitudeAndLongitude(String name, float latitude, float longitude) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public NameAndLatitudeAndLongitude() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

}
