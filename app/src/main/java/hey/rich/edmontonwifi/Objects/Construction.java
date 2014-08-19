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

    public Construction(String id, String address, Asset asset,
                        int startYear, String startDate, String finishDate,
                        String limits, String constructionType, String supervisor,
                        String phoneNumber, int ward, Location location) {
        super(id, "constructionAd", address, location);
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

    public String getStartYearText() {
        return String.format("Start year: %d", startYear);
    }


    public String getStartDate() {
        return startDate;
    }

    public String getStartDateText() {
        return String.format("Started on: %s", startDate);
    }

    public String getFinishDate() {
        return finishDate;
    }

    public String getFinishDateText(){
        return String.format("Finished by: %s", finishDate);
    }

    public String getLimits() {
        return limits;
    }

    public String getLimitsText(){
        return String.format("Construction limits: %s", limits);
    }

    public String getConstructionType() {
        return constructionType;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public String getSupervisorText(){
        return String.format("Supervisor: %s", supervisor);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPhoneNumberText(){
        return String.format("Supervisor Phone: %s", phoneNumber);
    }

    public int getWard() {
        return ward;
    }

    public String getWardText(){
        return String.format("Ward: %s", ward);
    }

    public String getAssetText() {
        return String.format("Construction type: %s", this.asset.toString());
    }

    public enum Asset {
        ROAD, BRIDGE, LIGHTS, SIDEWALK, TRANSIT, UNKNOWN
    }
}
