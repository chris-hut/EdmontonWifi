package hey.rich.edmontonwifi;

import android.app.Application;
import android.content.Context;

/** Singleton Class */
public class EdmontonWifi extends Application {

	private static WifiList wifiList = null;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	/** Returns the wifiList, if one doesnt exist, we will create it here. */
	public static WifiList getWifiList(Context context) {
		if (wifiList == null) {
			// load wifilist
			wifiList = new WifiList();
			wifiList.setAllWifis(JsonReader.jsonStringToList(JsonReader
					.loadJSONFromAsset(context.getAssets())));
		}

		return wifiList;
	}
}
