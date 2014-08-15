package hey.rich.edmontonwifi.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hey.rich.edmontonwifi.R;

/**
 * Created by chris on 14/08/14.
 */
public class ConstructionFragment extends Fragment {

    public ConstructionFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_construction, container, false);
        return rootView;
    }
}
