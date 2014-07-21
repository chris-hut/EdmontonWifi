package hey.rich.edmontonwifi;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Provides recent search functionality to the search activity. from:
 * http://www.grokkingandroid.com/android-tutorial-adding-suggestions-to-search/
 */
public class WifiSearchRecentSuggestionsProvider extends
		SearchRecentSuggestionsProvider {

	public static final String AUTHORITY = WifiSearchRecentSuggestionsProvider.class
			.getName();
	
	public static final int MODE = DATABASE_MODE_QUERIES;
	
	public WifiSearchRecentSuggestionsProvider(){
		setupSuggestions(AUTHORITY, MODE);
	}
}
