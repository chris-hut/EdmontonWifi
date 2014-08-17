package hey.rich.edmontonwifi.Objects;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton List of Wifis Object
 */
public class WifiList {

    private List<Wifi> wifis;

    public WifiList() {
        this.wifis = new ArrayList<Wifi>();
    }

    public List<Wifi> getAllWifis() {
        return this.wifis;
    }

    public void setAllWifis(List<Wifi> wifis) {
        this.wifis = wifis;
    }

    public Wifi getWifiAtPos(int pos) {
        return wifis.get(pos);
    }

}
