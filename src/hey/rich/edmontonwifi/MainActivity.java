package hey.rich.edmontonwifi;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity implements OnNavigationListener {

	private List<Wifi> wifis;
	private List<String> sortStrings;
	private WifiArrayAdapter adapter;
	private WifiList wifiList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Setup actionBar
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		sortStrings = getSortStrings();
		ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				sortStrings);
		actionBar.setListNavigationCallbacks(sortAdapter, this);

		setContentView(R.layout.activity_main);

		wifiList = EdmontonWifi.getWifiList(getApplicationContext());
		wifis = wifiList.getAllWifis();
		adapter = new WifiArrayAdapter(this, wifis);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private List<String> getSortStrings() {
		List<String> list = new ArrayList<String>();
		list.add("Name");
		list.add("Status");
		return list;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Wifi wifi = wifis.get(position);
		Toast.makeText(getApplicationContext(),
				String.format("Loading directions to: %s", wifi.getName()),
				Toast.LENGTH_SHORT).show();
		// Format of url is latitude, longitude, title
		// From: http://stackoverflow.com/a/17973122
		/*
		 * Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
		 * Uri.parse(String.format(
		 * "http://maps.google.com/maps?q=loc:%f,%f(%s)", wifi
		 * .getLocation().getLatitude(), wifi .getLocation().getLongitude(),
		 * wifi.getName())));
		 */
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
				Uri.parse(String.format("http://maps.google.com/maps?q=%s",
						wifi.getAddress())));
		startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// TODO: Make comparators and sort list
		switch (position) {
		case 1: // Name
			return true;
		case 2: // Status
			return true;
		default: // Unknown
			return false;
		}
	}
}
