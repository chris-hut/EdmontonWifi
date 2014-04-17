package hey.rich.edmontonwifi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class SortWifiListDialogFragment extends DialogFragment {

	private static final String[] sortList = { "Name", "Address", "Status",
			"Provider", "Distance" };

	private SortWifiListDialogListener mListener;

	public interface SortWifiListDialogListener {
		public void onDialogClick(int position);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that host activity implements the callback interface
		try {
			mListener = (SortWifiListDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement SortWifiListDialogListener");
		}

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Sort By").setItems(R.array.array_wifi_sort_list,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getActivity(),
								"Sort by: " + sortList[which],
								Toast.LENGTH_SHORT).show();
						mListener.onDialogClick(which);
					}
				});
		return builder.create();
	}
}
