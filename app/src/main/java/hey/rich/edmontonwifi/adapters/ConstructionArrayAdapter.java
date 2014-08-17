package hey.rich.edmontonwifi.adapters;

import android.app.Activity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import hey.rich.edmontonwifi.Objects.Construction;
import hey.rich.edmontonwifi.R;

/**
 * Created by chris on 17/08/14.
 */
public class ConstructionArrayAdapter extends ArrayAdapter<Construction>{
    private final Activity context;
    private final List<Construction> values;

    public ConstructionArrayAdapter(Activity context, List<Construction> values){
        super(context, R.layout.construction_list_view, values);
        this.context = context;
        this.values = values;
    }

    static class ViewHolder{
        public TextView location, distance, asset;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View rowView = convertView;

        if(rowView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.construction_list_view, null);

            ViewHolder v = new ViewHolder();
            v.location = (TextView) rowView.findViewById(R.id.construction_list_location);
            v.distance = (TextView) rowView.findViewById(R.id.construction_list_distance);
            v.asset = (TextView) rowView.findViewById(R.id.construction_list_asset);
            rowView.setTag(v);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        Construction construction = values.get(position);
        holder.location.setText(construction.getAddress());
        holder.distance.setText(Construction.getDistanceString(construction));
        holder.asset.setText(construction.getAsset().toString());

        return rowView;
    }
}
