package hey.rich.edmontonwifi.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import hey.rich.edmontonwifi.R;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String KEY_GLOBAL_SORT_PREEFERNCE = "sort_choice";

    public SettingsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        /* If this is crashing a lot, check out:
           https://developer.android.com/guide/topics/ui/settings.html#Listening
           I couldn't really figure it out
         */


        if (key.equals(KEY_GLOBAL_SORT_PREEFERNCE)) {
            // TODO: Update the sort summary text based on the current selection

            // Preference sortPref = findPreference(key);
            // sortPref.setSummary(sharedPreferences.getString(key, ""));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
