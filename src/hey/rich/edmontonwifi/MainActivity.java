package hey.rich.edmontonwifi;

import hey.rich.edmontonwifi.SortWifiListDialogFragment.SortWifiListDialogListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.DialogFragment;
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

public class MainActivity extends ListActivity implements OnNavigationListener,
		SortWifiListDialogListener {

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
		if (id == R.id.menu_sort_wifi_list) {
			// Pop open a dialog to ask user how they want to sort thsi list by
			showSortListDialog();
			return false;
		}
		return super.onOptionsItemSelected(item);
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
