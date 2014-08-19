package hey.rich.edmontonwifi.utils;

import android.content.res.AssetManager;
import android.location.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import hey.rich.edmontonwifi.Objects.Construction;
import hey.rich.edmontonwifi.Objects.Wifi;

/**
 * Modified from: http://stackoverflow.com/a/19945484
 */
public class JsonReader {

    public static String loadJSONFromAsset(AssetManager am, String fileName) {
        String json;

        try {
            InputStream is = am.open(fileName);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    public static List<Wifi> jsonStringToWifiList(String json) {
        List<Wifi> wifis = new ArrayList<Wifi>();
        Wifi wifi;
        String id;
        String name;
        String address;
        Wifi.Facility facility;
        Wifi.Status status;
        String provider;
        // From: http://stackoverflow.com/a/17983974
        try {
            JSONObject obj = new JSONObject(json);
            // TODO: This might not be right

            JSONArray array = obj.getJSONArray("data");
            JSONArray value;
            loopThroughWifis:
            for (int i = 0; i < array.length(); i++) {
                value = array.getJSONArray(i);

                if (!value.getString(11).equals("Active")) {
                    continue;
                }
                status = Wifi.Status.ACTIVE;

                Location location = new Location("");
                id = value.getString(1);
                name = value.getString(8);
                address = value.getString(9);
                facility = (value.getString(10).equalsIgnoreCase(
                        Wifi.Facility.CITY.name()) ? Wifi.Facility.CITY
                        : Wifi.Facility.TRANSIT);

                provider = value.getString(12);
                location.setLatitude(Double.parseDouble(value.getString(13)));
                location.setLongitude(Double.parseDouble(value.getString(14)));
               /* switch (value.getString(11)) {
                    case "Active":
                        status = Wifi.Status.ACTIVE;
                        break;
                    case "In Progress":
                        //status = Wifi.Status.IN_PROGRESS;
                        // break;
                    case "Future":
                        //status = Wifi.Status.IN_FUTURE;
                        // break;
                    default:
                        //status = Wifi.Status.IN_FUTURE;
                    /*
                     * Until we have some method of filtering Wifis by their
					 * status, we will not display any IN_PROGRESS or IN_FUTURE
					 * wifis. Also no one probably cares about them
					 */
                      /*  continue loopThroughWifis;
                }*/


                wifi = new Wifi(id, name, address, facility, status, provider,
                        location);
                wifis.add(wifi);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return wifis;
    }

    public static List<Construction> jsonStringToConstructionList(String json) {

        List<Construction> constructions = new ArrayList<Construction>();
        Construction construction;
        String id;
        String name;
        String address;
        Construction.Asset asset;
        int startYear;
        String startDate;
        String finishDate;
        String limits;
        String constructionType;
        String supervisor;
        String phoneNumber;
        int ward;

        try {
            JSONObject obj = new JSONObject(json);

            JSONArray array = obj.getJSONArray("data");
            JSONArray value;
            loopThroughConstructions:
            for (int i = 0; i < array.length(); i++) {
                value = array.getJSONArray(i);

                switch (value.getString(8)) {
                    case "BRIDGE":
                        asset = Construction.Asset.BRIDGE;
                        break;
                    case "LIGHTS":
                        asset = Construction.Asset.LIGHTS;
                        break;
                    case "SIDEWALK":
                        asset = Construction.Asset.SIDEWALK;
                        break;
                    case "Transit":
                        asset = Construction.Asset.TRANSIT;
                        break;
                    case "ROAD":
                        asset = Construction.Asset.ROAD;
                        break;
                    default: // Invalid
                        asset = Construction.Asset.UNKNOWN;
                        break;
                }

                id = value.getString(9);
                Location location = new Location("");
                startYear = Integer.parseInt(value.getString(10));
                startDate = value.getString(11);
                finishDate = value.getString(12);
                limits = value.getString(13);
                address = value.getString(14);
                String[] addressArray = address.split(" ");
                address = "";
                for(int j = 1; j < addressArray.length; j++){
                    address += addressArray[j];
                }
                location.setLatitude(Double.parseDouble(value.getString(15)));
                location.setLongitude(Double.parseDouble(value.getString(16)));

                constructionType = value.getString(19);
                supervisor = value.getString(20);
                phoneNumber = value.getString(21);

                ward = Integer.parseInt(value.getString(22).split(" ")[1]);

                construction = new Construction(id, address, asset, startYear,
                        startDate, finishDate, limits, constructionType, supervisor,
                        phoneNumber, ward, location);
                constructions.add(construction);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return constructions;
    }
}
