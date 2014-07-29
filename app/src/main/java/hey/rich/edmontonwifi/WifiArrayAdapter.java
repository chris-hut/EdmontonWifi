package hey.rich.edmontonwifi;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WifiArrayAdapter extends ArrayAdapter<Wifi> {
	private final Activity context;
	private final List<Wifi> values;

	public WifiArrayAdapter(Activity context, List<Wifi> values) {
		super(context, R.layout.wifi_list_view, values);
		this.context = context;
		this.values = values;
	}

	static class ViewHolder {
		public TextView name, distance;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;

		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.wifi_list_view, null);

			ViewHolder v = new ViewHolder();
			v.name = (TextView) rowView.findViewById(R.id.wifi_list_name);
			v.distance = (TextView) rowView.findViewById(R.id.wifi_list_distance);
			rowView.setTag(v);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		Wifi wifi = values.get(position);
		holder.name.setText(wifi.getName().split("\\(")[0]);
		holder.distance.setText(Wifi.getDistanceString(wifi));

		return rowView;
	}
}
