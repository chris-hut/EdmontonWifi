package hey.rich.edmontonwifi.Objects;

import android.location.Location;

/**
 * Created by chris on 17/08/14.
 */
public class Construction extends Data {

    /**
     * Type of asset
     */
    private Asset asset;

    /**
     * Start year
     */
    private int startYear;

    /**
     * Approx start date
     */
    private String startDate;

    /**
     * Approx finish
     */
    private String finishDate;

    /**
     * Project limits
     */
    private String limits;

    /**
     * Construction Type
     */
    private String constructionType;

    /**
     * Construction Supervisor
     */
    private String supervisor;

    /**
     * Phone Number
     */
    private String phoneNumber;

    /**
     * Ward
     */
    private int ward;

    public Construction(String id, String name, String address, Asset asset,
                        int startYear, String startDate, String finishDate,
                        String limits, String constructionType, String supervisor,
                        String phoneNumber, int ward, Location location) {
        super(id, name, address, location);
        this.asset = asset;
        this.startYear = startYear;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.limits = limits;
        this.constructionType = constructionType;
        this.supervisor = supervisor;
        this.phoneNumber = phoneNumber;
        this.ward = ward;
    }


    public Asset getAsset() {
        return asset;
    }

    public int getStartYear() {
        return startYear;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public String getLimits() {
        return limits;
    }

    public String getConstructionType() {
        return constructionType;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getWard() {
        return ward;
    }

    public enum Asset {
        ROAD, BRIDGE, LIGHTS, SIDEWALK, TRANSIT, UNKNOWN
    }
}
