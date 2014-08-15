package hey.rich.edmontonwifi.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import hey.rich.edmontonwifi.activities.MainActivity;
import hey.rich.edmontonwifi.R;

public class SortWifiListDialogFragment extends DialogFragment {

	private static final String[] sortList = { "Name", "Address", "Provider",
			"Distance" };

	private SortWifiListDialogListener mListener;

	public interface SortWifiListDialogListener {
		public void onDialogClick(int position);
	}

	private static int currentChoice = 0;
	private static int newChoice = 0;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that host activity implements the callback interface
		try {
			mListener = (SortWifiListDialogListener) activity;
			currentChoice = ((MainActivity) activity).getSortChoice();
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement SortWifiListDialogListener");
		}

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Sort By")
				.setSingleChoiceItems(R.array.array_wifi_sort_list,
						currentChoice, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								newChoice = which;
							}
						})
				.setPositiveButton("Apply",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								currentChoice = newChoice;
								Toast.makeText(getActivity(),
										"Sort by: " + sortList[currentChoice],
										Toast.LENGTH_SHORT).show();
								mListener.onDialogClick(currentChoice);
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
		return builder.create();
	}
}
