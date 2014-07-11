package hey.rich.edmontonwifi;

import hey.rich.edmontonwifi.SortWifiListDialogFragment.SortWifiListDialogListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;

public class MainActivity extends Activity implements OnNavigationListener,
		SortWifiListDialogListener {

	private List<Wifi> wifis;
	private WifiArrayAdapter adapter;
	private WifiList wifiList;
	private SharedPreferences prefs;
	private int sortChoice;
	private ListView lView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lView = (ListView) findViewById(R.id.main_activity_listview);
		wifiList = EdmontonWifi.getWifiList(getApplicationContext());
		wifis = wifiList.getAllWifis();
		adapter = new WifiArrayAdapter(this, wifis);
		lView.setAdapter(adapter);

		lView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Format of url is latitude, longitude, title
				// From: http://stackoverflow.com/a/17973122
				/*
				 * Intent intent = new
				 * Intent(android.content.Intent.ACTION_VIEW,
				 * Uri.parse(String.format(
				 * "http://maps.google.com/maps?q=loc:%f,%f(%s)", wifi
				 * .getLocation().getLatitude(), wifi
				 * .getLocation().getLongitude(), wifi.getName())));
				 */
				Intent i = new Intent(getApplicationContext(),
						WifiViewActivity.class);
				i.putExtra(WifiViewActivity.WIFI_ID, position);
				startActivity(i);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onStart();

		prefs = getSharedPreferences("hey.rich.EdmontonWifi",
				Context.MODE_PRIVATE);
		// From this beauty: http://stackoverflow.com/a/5878986
		sortChoice = prefs.getInt("sort_choice", 0);
	}

	@Override
	protected void onStop() {
		super.onStop();
		Editor edit = prefs.edit();
		edit.putInt("sort_choice", sortChoice);
		edit.apply();
	}

	@Override
	public boolean onSearchRequested() {
		// Pause anything that would be running
		return super.onSearchRequested();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inf = getMenuInflater();
		inf.inflate(R.menu.main, menu);

		// Get the searchview and set the searchable conf
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.menu_search)
				.getActionView();
		// Assumes current activity is the searchable activity
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		// Don't iconify the widget; expand it by default
		searchView.setIconifiedByDefault(true);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.menu_sort_wifi_list:
			showSortListDialog();
			return false;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void showSortListDialog() {
		SortWifiListDialogFragment dialog = new SortWifiListDialogFragment();
		dialog.show(getFragmentManager(), "SortWifiListDialogFragment");
	}

	@Override
	/** Callback from SortWifiListDialog, when called, sort our list of wifis by the choice selected.*/
	public void onDialogClick(int position) {
		/*
		 * User clicked a position From the string-array:
		 * R.array.array_sort_wifi_list Order is: Name, Address, Status,
		 * Facility, Distance
		 */
		sortChoice = position;
		switch (position) {
		case 0: // Name
			Collections.sort(wifis, new NameComparator());
			break;
		case 1: // Address
			Collections.sort(wifis, new AddressComparator());
			break;
		case 2: // Status
			Collections.sort(wifis, new StatusComparator());
			break;
		case 3: // Facility
			Collections.sort(wifis, new FacilityComparator());
			break;
		case 4: // Distance
			Collections.sort(wifis, new DistanceComparator());
			break;
		default: // Invalid
			return;
		}
		// Only if we got a non-invalid position we will be here
		adapter.notifyDataSetChanged();
	}

	public int getSortChoice() {
		return sortChoice;
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

	/** Used to compare wifis by name their name in alphabetical order */
	public class NameComparator implements Comparator<Wifi> {

		@Override
		public int compare(Wifi w1, Wifi w2) {
			return w1.getName().compareTo(w2.getName());
		}

	}

	/** Used to compare wifis by their address in alphabetical order */
	public class AddressComparator implements Comparator<Wifi> {

		@Override
		public int compare(Wifi w1, Wifi w2) {
			return w1.getAddress().compareTo(w2.getAddress());
		}
	}

	/**
	 * Used to compare wifis by their Status. This comparator will compare the
	 * wifis in alphabetical order.
	 * <p>
	 * The order is: ACTIVE, IN_PROGRESS, IN_FUTURE
	 */
	public class StatusComparator implements Comparator<Wifi> {
		@Override
		public int compare(Wifi w1, Wifi w2) {
			return w1.getStatusString().compareTo(w2.getStatusString());
		}
	}

	/**
	 * Used to compare wifis by their facility type. This comparator will
	 * compare the wifis in alphabetical order.
	 * <p>
	 * The order is: CITY, TRANSIT
	 */
	public class FacilityComparator implements Comparator<Wifi> {
		@Override
		public int compare(Wifi w1, Wifi w2) {
			return w1.getProvider().compareTo(w2.getProvider());
		}
	}

	public class DistanceComparator implements Comparator<Wifi> {
		@Override
		public int compare(Wifi w1, Wifi w2) {
			double d1 = w1.getDistance();
			double d2 = w2.getDistance();

			if (d1 == d2) {
				return 0;
			} else if (d1 != Wifi.INVALID_DISTANCE
					&& d2 == Wifi.INVALID_DISTANCE) {
				// D2 is invalid so d1 must be closer
				return -1;
			} else if (d1 == Wifi.INVALID_DISTANCE
					&& d2 != Wifi.INVALID_DISTANCE) {
				// D1 is invalid so d2 must be closer
				return 1;
			} else {
				return (int) (d1 - d2);
			}
		}
	}
}
