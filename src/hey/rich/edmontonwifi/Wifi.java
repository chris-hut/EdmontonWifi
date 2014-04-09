package hey.rich.edmontonwifi;

import android.location.Location;

/**
 * A Wifi object that whose parameters will be filled by the json in
 * assets/wifi.json
 */
public class Wifi {

	public enum Facility {
		CITY, TRANSIT;
	};

	public enum Status {
		ACTIVE, IN_PROGRESS, IN_FUTURE
	};

	private long id;
	/** Name of the place where the WiFi antenna is located */
	private String name;
	/** Physical street address where the WiFi antenna is located */
	private String address;
	/**
	 * Type of location where WiFi antenna is found.
	 * */
	private Facility facility;
	/** Implementation status of wifi */
	private Status status;
	/** Name of organization providing the WiFi service */
	private String provider;
	/** Spatial coordinates of location */
	private Location location;

	public Wifi(long id, String name, String address, Facility facility,
			Status status, String provider, Location location) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.facility = facility;
		this.status = status;
		this.provider = provider;
		this.location = location;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
