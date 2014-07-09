package hey.rich.edmontonwifi;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WifiViewActivity extends Activity {

	public static final String WIFI_ID = "wifi";
	private static final String TAG = "WifiViewActivity";
	private Wifi wifi;

	// TextViews
	private TextView titleText, addressText, statusText, facilityText,
			providerText, distanceText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wifi_view);

		Intent i = getIntent();
		int position = i.getExtras().getInt(WIFI_ID, -1);
		try {
			this.wifi = EdmontonWifi.getWifi(this, position);
		} catch (IndexOutOfBoundsException e) {
			Log.i(TAG,
					"Got passed an invalid position for a Wifi in the Wifilist");
			Toast.makeText(this, "An error occured.\n Please try again",
					Toast.LENGTH_SHORT).show();
			finish();
		}

		setupButtons();
		setupTextViews();

	}

	private void setupButtons() {
		// Allocate buttons
		final Button mapsButton = (Button) findViewById(R.id.wifi_view_wifi_maps);
		final Button copyButton = (Button) findViewById(R.id.wifi_view_wifi_clipboard);
		final Button removeButton = (Button) findViewById(R.id.wifi_view_wifi_remove);

		mapsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Toast.makeText(
						getApplicationContext(),
						String.format("Loading directions to: %s",
								wifi.getName()), Toast.LENGTH_SHORT).show();

				Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri
						.parse(String.format(
								"http://maps.google.com/maps?q=%s",
								wifi.getAddress())));
				startActivity(i);

			}
		});

		copyButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),
						"Copying address to clipbaord", Toast.LENGTH_SHORT)
						.show();
				ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				ClipData clip = ClipData.newPlainText("Address to Wifi",
						wifi.getAddress());
				clipboard.setPrimaryClip(clip);
			}
		});

		removeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),
						"Removing Wifi is not yet implemented",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void setupTextViews() {
		this.titleText = (TextView) findViewById(R.id.wifi_view_wifi_title);
		this.addressText = (TextView) findViewById(R.id.wifi_view_wifi_address);
		this.statusText = (TextView) findViewById(R.id.wifi_view_wifi_status);
		this.facilityText = (TextView) findViewById(R.id.wifi_view_wifi_facility);
		this.providerText = (TextView) findViewById(R.id.wifi_view_wifi_provider);
		this.distanceText = (TextView) findViewById(R.id.wifi_view_wifi_distance);
		
		this.titleText.setText(wifi.getName());
		this.addressText.setText(wifi.getAddress());
		this.statusText.setText(wifi.getStatusString());
		this.facilityText.setText(wifi.getFacilityString());
		this.providerText.setText(wifi.getProvider());
		this.distanceText.setText("Distance: " + wifi.getDistance());
	}

}
