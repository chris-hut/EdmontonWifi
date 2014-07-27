package hey.rich.edmontonwifi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;

public class ClearSearchHistoryDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstance) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("Clear Search History?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// Clear history
								SearchRecentSuggestions suggestions = new SearchRecentSuggestions(
										getActivity(),
										WifiSearchRecentSuggestionsProvider.AUTHORITY,
										WifiSearchRecentSuggestionsProvider.MODE);
								suggestions.clearHistory();
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// Do nothing
							}
						});
		return builder.create();

	}
}
