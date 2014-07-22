package hey.rich.edmontonwifi;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton List of Wifis Object
 */
// public class WifiList<V> {
public class WifiList {

    private List<Wifi> wifis;
    //private List<V> views;

    public WifiList() {
        this.wifis = new ArrayList<Wifi>();
    }

    public List<Wifi> getAllWifis() {
        return this.wifis;
    }

    public void setAllWifis(List<Wifi> wifis) {
        this.wifis = wifis;
        //notifyViews();
    }

    /**
     * Used to add a wifi to the list of wifis.
     * <p/>
     * Don't think this will ever be used
     */
    public void addWifi(Wifi w) {
        wifis.add(w);
        //notifyViews();
    }

    public Wifi getWifiAtPos(int pos) {
        return wifis.get(pos);
    }

    public void updateLocation(Location l) {
        for (Wifi wifi : wifis) wifi.setDistanceToLocation(l);
    }

	/*public void addViews(V view) {
        if (!views.contains(view)) {
			views.add(view);
		}
	}

	public void deleteView(V view) {
		views.remove(view);
	}*/

    /**
     * Notifies all of the listening views that the data has updated and that
     * they should update themselves.
     */
	/*public void notifyViews() {
		for (V view : views) {
			view.update(this);
		}
	}*/
}
