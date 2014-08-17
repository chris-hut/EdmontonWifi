package hey.rich.edmontonwifi.Objects;

import android.location.Location;

/**
 * Created by chris on 17/08/14.
 */
public class Data {
    public final static float INVALID_DISTANCE = -1;

    /**
     * ID is a HEX value with dashes in it
     */
    private String id;
    /**
     * Name of the place where the WiFi antenna is located
     */
    private String name;
    /**
     * Physical street address where the WiFi antenna is located
     */
    private String address;

    /**
     * Spatial coordinates of location
     */
    private Location location;
    /**
     * Distance to a specified location
     */
    private double distance;

    public Data(String id, String name, String address, Location location) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.location = location;

        // Set distance to invalid since we don't know what location we want
        // distance from
        this.distance = INVALID_DISTANCE;
    }

    /**
     * Returns a nicely formatted distance string for the given Wifi
     * Assumes that the distance to location is already set.
     */
    public static String getDistanceString(Data d) {
        double distance = d.getDistance();

        // Return invalid distance
        if (distance < 0) return "No distance available.";
        else if (distance < 500) {
            // Return distance in meters
            return String.format("Distance: %3.0f m", distance);
        } else { // distance >= 500
            // return distance in kms
            return String.format("Distance: %.1f km", distance / 1000);
        }
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


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setDistanceToLocation(Location l) {
        this.distance = l.distanceTo(this.location);
    }

    public double getDistance() {
        return this.distance;
    }
}
