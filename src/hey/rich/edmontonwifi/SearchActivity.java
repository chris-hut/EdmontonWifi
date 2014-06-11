package hey.rich.edmontonwifi;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class SearchActivity extends ListActivity {
	private List<Wifi> wifis;
	private WifiArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		wifis = EdmontonWifi.getWifiList(getApplicationContext()).getAllWifis();
		adapter = new WifiArrayAdapter(this, wifis);
		setListAdapter(adapter);

		handleIntent(getIntent());

	}
	
	@Override
	protected void onNewIntent(Intent intent){
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent i) {

		if (Intent.ACTION_SEARCH.equals(i.getAction())) {
			String query = i.getStringExtra(SearchManager.QUERY);
			performSearch(query);
		}
	}

	private void performSearch(String query) {
		// Should probably optimize this somehow
		List<Wifi> newWifis = new ArrayList<Wifi>();
		for (Wifi w : wifis) {
			if (w.getAddress().contains(query) || w.getName().contains(query)) {
				newWifis.add(w);
			}
		}

		wifis.clear();
		wifis.addAll(newWifis);
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Wifi wifi = wifis.get(position);

		Toast.makeText(this, String.format("Wifi: %s", wifi.getName()),
				Toast.LENGTH_SHORT).show();
	}
}
