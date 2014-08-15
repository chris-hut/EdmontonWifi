package hey.rich.edmontonwifi.utils;

import java.util.Comparator;

import hey.rich.edmontonwifi.Objects.Wifi;

/**
 * Created by chris on 14/08/14.
 */
public class Sorters {
    /**
     * Used to compare wifis by name their name in alphabetical order
     */
    public static class NameComparator implements Comparator<Wifi> {

        @Override
        public int compare(Wifi w1, Wifi w2) {
            return w1.getName().compareTo(w2.getName());
        }

    }

    /**
     * Used to compare wifis by their address in alphabetical order
     */
    public static class AddressComparator implements Comparator<Wifi> {

        @Override
        public int compare(Wifi w1, Wifi w2) {
            return w1.getAddress().compareTo(w2.getAddress());
        }
    }

    /**
     * Used to compare wifis by their Status. This comparator will compare the
     * wifis in alphabetical order.
     * <p/>
     * The order is: ACTIVE, IN_PROGRESS, IN_FUTURE
     */
    public static class StatusComparator implements Comparator<Wifi> {
        @Override
        public int compare(Wifi w1, Wifi w2) {
            return w1.getStatusString().compareTo(w2.getStatusString());
        }
    }

    /**
     * Used to compare wifis by their facility type. This comparator will
     * compare the wifis in alphabetical order.
     * <p/>
     * The order is: CITY, TRANSIT
     */
    public static class FacilityComparator implements Comparator<Wifi> {
        @Override
        public int compare(Wifi w1, Wifi w2) {
            return w1.getProvider().compareTo(w2.getProvider());
        }
    }

    public static class DistanceComparator implements Comparator<Wifi> {
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
