package hey.rich.edmontonwifi.Objects;

import android.location.Location;

/**
 * A Wifi object that whose parameters will be filled by the json in
 * assets/wifi.json
 */
public class Wifi extends Data {

    /**
     * Type of location where WiFi antenna is found.
     */
    private Facility facility;
    /**
     * Implementation status of wifi
     */
    private Status status;
    /**
     * Name of organization providing the WiFi service
     */
    private String provider;

    public Wifi(String id, String name, String address, Facility facility,
                Status status, String provider, Location location) {
        super(id, name, address, location);
        this.facility = facility;
        this.status = status;
        this.provider = provider;

    }


    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public String getFacilityString() {
        return this.facility.toString();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getStatusString() {
        return status.toString();
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }



    public enum Facility {
        CITY, TRANSIT
    }

    /**
     * The status of the wifi. If invalid, IN_FUTURE will be used
     */
    public enum Status {
        ACTIVE, IN_PROGRESS, IN_FUTURE
    }

}
