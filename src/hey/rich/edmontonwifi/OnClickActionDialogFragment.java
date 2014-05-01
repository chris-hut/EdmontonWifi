package hey.rich.edmontonwifi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class OnClickActionDialogFragment extends DialogFragment {

	private static final String[] actionList = { "Open address in maps",
			"Copy address to clipboard", "Remove from list", "Do nothing" };

	private OnClickActionDialogListener mListener;

	private static int currentChoice = 0;

	public interface OnClickActionDialogListener {
		public void onDialogActionClick(int position);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that host activity implements the callback interface
		try {
			mListener = (OnClickActionDialogListener) activity;
			// TODO: This is so dirty
			currentChoice = ((MainActivity) activity).getActionOnClick();
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnClickActionDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("On click action")
				.setSingleChoiceItems(R.array.array_action_click_list,
						currentChoice, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								currentChoice = which;
							}
						})
				.setPositiveButton("Appy",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(
										getActivity(),
										"On click action: " + actionList[currentChoice],
										Toast.LENGTH_SHORT).show();
								mListener.onDialogActionClick(currentChoice);

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
