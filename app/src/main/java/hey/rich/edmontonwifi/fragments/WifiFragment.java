package hey.rich.edmontonwifi.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.Collections;
import java.util.List;

import hey.rich.edmontonwifi.EdmontonWifi;
import hey.rich.edmontonwifi.Objects.Wifi;
import hey.rich.edmontonwifi.Objects.WifiList;
import hey.rich.edmontonwifi.R;
import hey.rich.edmontonwifi.activities.WifiViewActivity;
import hey.rich.edmontonwifi.adapters.WifiArrayAdapter;
import hey.rich.edmontonwifi.utils.Sorters;

/**
 * Created by chris on 12/08/14.
 */
public class WifiFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String KEY_GLOBAL_SORT_PREEFERNCE = "sort_choice";
    private final static String TAG = WifiFragment.class.getName();
    private List<Wifi> wifis;
    private WifiArrayAdapter adapter;
    private SharedPreferences prefs;

    public WifiFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wifi, container, false);

        setupList(rootView);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sortWifisOnPreference(sharedPref.getString(KEY_GLOBAL_SORT_PREEFERNCE, "distance"));

        getActivity().setTitle("Wifi");
        return rootView;
    }

    private void setupList(View v) {
        ListView lView = (ListView) v.findViewById(R.id.wifi_fragment_listview);
        WifiList wifiList;
        wifiList = EdmontonWifi.getWifiList(getActivity());
        wifis = wifiList.getAllWifis();

        adapter = new WifiArrayAdapter(getActivity(), wifis);
        lView.setAdapter(adapter);
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(getActivity(),
                        WifiViewActivity.class);
                i.putExtra(WifiViewActivity.WIFI_ID, position);
                if (i.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), "Error Trying to Open, Try Again Later", Toast.LENGTH_LONG).show();
                }

            }
        });

        setupRefreshLocationButton(v);

        updateLocation();

    }

    private void setupRefreshLocationButton(View v) {
        ImageButton button = (ImageButton) v.findViewById(R.id.main_activity_refresh_location_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLocation();
            }
        });
    }

    private void updateLocation() {
        Location l = EdmontonWifi.getLocation(getActivity());
        if (l == null) {
            return;
        }
        Toast.makeText(getActivity(), "Getting location", Toast.LENGTH_SHORT).show();

        for (Wifi wifi : wifis) wifi.setDistanceToLocation(l);

    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sp.registerOnSharedPreferenceChangeListener(this);

        prefs = getActivity().getSharedPreferences("hey.rich.EdmontonWifi",
                Context.MODE_PRIVATE);
        // From this beauty: http://stackoverflow.com/a/5878986

        if (prefs.getBoolean("firstrun", false)) {
            prefs.edit().putBoolean("firstrun", false).apply();
            new ShowcaseView.Builder(getActivity(), true)
                    .setTarget(new ViewTarget(getActivity().findViewById(R.id.wifi_fragment_listview)))
                    .setContentTitle("Select a Wifi")
                    .setContentText("To get more information about it and to perform actions")
                    .setStyle(R.style.ShowcaseViewTheme)
                    .build();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sp.unregisterOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
        if (key.equals(KEY_GLOBAL_SORT_PREEFERNCE)) {
            sortWifisOnPreference(sp.getString(key, "distance"));


        }
    }

    private void sortWifisOnPreference(String sort) {
        if (sort.equals("distance")) {
            // Sort by distance
            Collections.sort(wifis, new Sorters.DistanceComparator());
        } else if (sort.equals("address")) {
            // Sort by address
            Collections.sort(wifis, new Sorters.AddressComparator());
        } else if (sort.equals("name")) {
            // Sort by name
            Collections.sort(wifis, new Sorters.NameComparator());
        } else {
            // Sort type was invalid, should we set it back to distance here?
            Log.e(TAG, String.format("Invalid sort type: ", sort));
        }
        adapter.notifyDataSetChanged();
    }

}