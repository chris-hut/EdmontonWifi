package hey.rich.edmontonwifi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.res.AssetManager;
import android.location.Location;

/** Modified from: http://stackoverflow.com/a/19945484 */
public class JsonReader {

	public static String loadJSONFromAsset(AssetManager am) {
		String json;

		try {
			InputStream is = am.open("wifi.json");

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

	public static List<Wifi> jsonStringToList(String json) {
		List<Wifi> wifis = new ArrayList<Wifi>();
		Wifi wifi;
		String id;
		String name;
		String address;
		Wifi.Facility facility;
		Wifi.Status status;
		String provider;
		// From: http://stackoverflow.com/a/17983974
		Location location = new Location("");
		try {
			JSONObject obj = new JSONObject(json);
			// TODO: This might not be right

			JSONArray array = obj.getJSONArray("data");
			JSONArray value;
			loopThroughWifis: for (int i = 0; i < array.length(); i++) {
				value = array.getJSONArray(i);
				id = value.getString(1);
				name = value.getString(8);
				address = value.getString(9);
				facility = (value.getString(10).equalsIgnoreCase(
						Wifi.Facility.CITY.name()) ? Wifi.Facility.CITY
						: Wifi.Facility.TRANSIT);

				switch (value.getString(11)) {
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
					continue loopThroughWifis;
				}

				provider = value.getString(12);
				location.setLatitude(Double.parseDouble(value.getString(13)));
				location.setLongitude(Double.parseDouble(value.getString(14)));
				wifi = new Wifi(id, name, address, facility, status, provider,
						location);
				wifis.add(wifi);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return wifis;
	}
}
