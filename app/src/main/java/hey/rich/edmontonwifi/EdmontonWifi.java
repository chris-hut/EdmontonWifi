package hey.rich.edmontonwifi;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import java.util.List;

import hey.rich.edmontonwifi.Objects.Construction;
import hey.rich.edmontonwifi.Objects.ConstructionList;
import hey.rich.edmontonwifi.Objects.Wifi;
import hey.rich.edmontonwifi.Objects.WifiList;
import hey.rich.edmontonwifi.utils.JsonReader;

/**
 * Singleton Class
 */
public class EdmontonWifi extends Application {

    private static final String WIFI_FILE_NAME = "wifi.json";
    private static final String CONSTRUCTION_FILE_NAME = "construction.json";

    private static WifiList wifiList = null;
    private static ConstructionList constructionList = null;

    /**
     * Returns the wifiList, if one doesn't exist, we will create it here.
     */
    public static WifiList getWifiList(Context context) {
        if (wifiList == null) {
            // load wifilist
            wifiList = new WifiList();
            wifiList.setAllWifis(JsonReader.jsonStringToWifiList(JsonReader
                    .loadJSONFromAsset(context.getAssets(), WIFI_FILE_NAME)));
        }

        return wifiList;
    }

    public static ConstructionList getConstructionList(Context context) {
        if (constructionList == null) {
            // load constructionList
            constructionList = new ConstructionList();
            constructionList.setAllConstructions(JsonReader.jsonStringToConstructionList(JsonReader.
                    loadJSONFromAsset(context.getAssets(), CONSTRUCTION_FILE_NAME)));
        }

        return constructionList;
    }

    public static Wifi getWifi(Context context, int position) {
        if (wifiList == null) {
            wifiList = getWifiList(context);
        }

        return wifiList.getWifiAtPos(position);
    }

    public static Construction getConstruction(Context context, int position){
        if(constructionList == null){
            constructionList = getConstructionList(context);
        }
        return constructionList.getConstructionAtPos(position);
    }


    /**
     * Gets the last known location of the device.
     * If this can't be found for some reason, null will be returned.
     */
    public static Location getLocation(Context context) {
        // From: http://stackoverflow.com/a/20465781/1684866
        LocationManager manager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        List<String> providers = manager.getProviders(true);
        Location bestLocation = null;

        for (String provider : providers) {
            Location l = manager.getLastKnownLocation(provider);

            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }
}
