package hey.rich.edmontonwifi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.res.AssetManager;
import android.util.Log;

/** Modified from: http://stackoverflow.com/a/19945484 */
public class JsonReader {

	public static String loadJSONFromAsset(AssetManager am) {
		String json = null;

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
		try {
			JSONObject obj = new JSONObject(json);
			// TODO: This might not be right
			JSONArray mArray = obj.getJSONArray("data");
			
			for(int i = 0; i < mArray.length(); i++){
				JSONObject inside = mArray.getJSONObject(i);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		return wifis;
	}
}
