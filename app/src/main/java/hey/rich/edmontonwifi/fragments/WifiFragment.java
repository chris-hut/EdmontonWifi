package hey.rich.edmontonwifi.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
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
import java.util.Comparator;
import java.util.List;

import hey.rich.edmontonwifi.EdmontonWifi;
import hey.rich.edmontonwifi.R;
import hey.rich.edmontonwifi.Objects.Wifi;
import hey.rich.edmontonwifi.adapters.WifiArrayAdapter;
import hey.rich.edmontonwifi.Objects.WifiList;
import hey.rich.edmontonwifi.activities.WifiViewActivity;

/**
 * Created by chris on 12/08/14.
 */
public class WifiFragment extends Fragment {

    private List<Wifi> wifis;
    private WifiArrayAdapter adapter;
    private SharedPreferences prefs;
    private int sortChoice;

    public WifiFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wifi, container, false);

        setupList(rootView);

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

        // By default let's sort by distance
        Collections.sort(wifis, new DistanceComparator());
        adapter.notifyDataSetChanged();
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

        prefs = getActivity().getSharedPreferences("hey.rich.EdmontonWifi",
                Context.MODE_PRIVATE);
        // From this beauty: http://stackoverflow.com/a/5878986
        sortChoice = prefs.getInt("sort_choice", 0);

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
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt("sort_choice", sortChoice);
        edit.apply();
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