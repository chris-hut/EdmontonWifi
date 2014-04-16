package hey.rich.edmontonwifi;

import java.util.Comparator;

import android.location.Location;

/**
 * A Wifi object that whose parameters will be filled by the json in
 * assets/wifi.json
 */
public class Wifi {

	private static float INVALID_DISTANCE = -1;

	public enum Facility {
		CITY, TRANSIT;
	};

	/** The status of the wifi. If invalid, IN_FUTURE will be used */
	public enum Status {
		ACTIVE, IN_PROGRESS, IN_FUTURE
	};

	/** ID is a HEX value with dashes in it */
	private String id;
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
	/** Distance to a specified location */
	private double distance;

	public Wifi(String id, String name, String address, Facility facility,
			Status status, String provider, Location location) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.facility = facility;
		this.status = status;
		this.provider = provider;
		this.location = location;

		// Set distance to invalid since we don't know what location we want
		// distance from
		this.distance = -INVALID_DISTANCE;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getStatusString() {
		return status.toString();
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

	public void setDistance(Location l) {
		this.distance = l.distanceTo(this.location);
	}

	public double getDistance() {
		return this.distance;
	}

	/** Used to compare wifis by name their name in alphabetical order */
	public static class NameComparator implements Comparator<Wifi> {

		@Override
		public int compare(Wifi w1, Wifi w2) {
			return w1.getName().compareTo(w2.getName());
		}

	}

	/** Used to compare wifis by their address in alphabetical order */
	public static class AddressComparator implements Comparator<Wifi> {

		@Override
		public int compare(Wifi w1, Wifi w2) {
			return w1.getAddress().compareTo(w2.getAddress());
		}
	}

	/**
	 * Used to compare wifis by their Status. This comparator will compare the
	 * wifis in alphabetical order.
	 * <p>
	 * The order is: ACTIVE, IN_PROGRESS, IN_FUTURE
	 */
	public static class StatusComparator implements Comparator<Wifi> {
		@Override
		public int compare(Wifi w1, Wifi w2) {
			return w1.getStatusString().compareTo(w2.getStatusString());
		}
	}

	/**
	 * Used to compare wifis by their facility type. This comparator will
	 * compare the wifis in alphabetical order.
	 * <p>
	 * The order is: CITY, TRANSIT
	 */
	public static class FacilityComparator implements Comparator<Wifi> {
		@Override
		public int compare(Wifi w1, Wifi w2) {
			return w1.getProvider().compareTo(w2.getProvider());
		}
	}

	public static class DistanceComparator implements Comparator<Wifi> {
		@Override
		public int compare(Wifi w1, Wifi w2) {
			double d1 = w1.getDistance();
			double d2 = w2.getDistance();

			if (d1 == d2) {
				return 0;
			} else if (d1 != INVALID_DISTANCE && d2 == INVALID_DISTANCE) {
				// D2 is invalid so d1 must be closer
				return -1;
			} else if (d1 == INVALID_DISTANCE && d2 != INVALID_DISTANCE) {
				// D1 is invalid so d2 must be closer
				return 1;
			} else {
				return (int) (d1 - d2);
			}
		}
	}

}
