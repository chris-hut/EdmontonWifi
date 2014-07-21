package hey.rich.edmontonwifi;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SearchActivity extends Activity {
	private List<Wifi> wifis;
	private WifiArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		ListView lView = (ListView) findViewById(R.id.search_activity_listview);

		wifis = new ArrayList<Wifi>(EdmontonWifi.getWifiList(
				getApplicationContext()).getAllWifis());
		adapter = new WifiArrayAdapter(this, wifis);
		lView.setAdapter(adapter);

		lView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getApplicationContext(),
						WifiViewActivity.class);
				i.putExtra(WifiViewActivity.WIFI_ID, position);
				startActivity(i);

			}
		});

		handleIntent(getIntent());

	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent i) {

		if (Intent.ACTION_SEARCH.equals(i.getAction())) {
			String query = i.getStringExtra(SearchManager.QUERY);
			

			// Save recent search
			SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
					WifiSearchRecentSuggestionsProvider.AUTHORITY,
					WifiSearchRecentSuggestionsProvider.MODE);
			suggestions.saveRecentQuery(query, null);
			
			performSearch(query);
		}
	}

	private void performSearch(String query) {
		// Should probably optimize this somehow
		List<Wifi> newWifis = new ArrayList<Wifi>();
		for (Wifi w : wifis) {
			if (w.getAddress().toLowerCase().contains(query.toLowerCase())
					|| w.getName().toLowerCase().contains(query.toLowerCase())) {
				newWifis.add(w);
			}
		}


		wifis.clear();
		wifis.addAll(newWifis);
		adapter.notifyDataSetChanged();
	}
}
