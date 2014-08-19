package hey.rich.edmontonwifi.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import hey.rich.edmontonwifi.EdmontonWifi;
import hey.rich.edmontonwifi.Objects.Construction;
import hey.rich.edmontonwifi.R;

public class ConstructionActivity extends Activity {

    public static final String CONSTRUCTION_ID = "construction";
    private static final String TAG = ConstructionActivity.class.getName();
    private Construction construction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction);

        Intent i = getIntent();
        int position = i.getExtras().getInt(CONSTRUCTION_ID, -1);
        try {
            this.construction = EdmontonWifi.getConstruction(this, position);
        } catch (IndexOutOfBoundsException e) {
            Log.i(TAG, "Got passed an invalid position for a Construction in the constructionlist"
                    + position);
            Toast.makeText(this, "An error occurred.\n Please try again", Toast.LENGTH_SHORT).show();
            finish();
        }

        setupButtons();
        setupTextView();

    }

    private void setupButtons() {
        // Allocation buttons
        // TODO: add buttons
    }

    private void setupTextView() {
        TextView address = (TextView) findViewById(R.id.construction_view_address);
        TextView asset = (TextView) findViewById(R.id.construction_view_asset);
        TextView startYear = (TextView) findViewById(R.id.construction_view_start_year);
        TextView startDate = (TextView) findViewById(R.id.construction_view_start_date);
        TextView finishDate = (TextView) findViewById(R.id.construction_view_finish_date);
        TextView limits = (TextView) findViewById(R.id.construction_view_limits);
        TextView supervisor = (TextView) findViewById(R.id.construction_view_supervisor);
        TextView phoneNumber = (TextView) findViewById(R.id.construction_view_phone_number);
        TextView ward = (TextView) findViewById(R.id.construction_view_ward);

        address.setText(this.construction.getAddress());
        asset.setText(this.construction.getAssetText());
        startYear.setText(this.construction.getStartYearText());
        startDate.setText(this.construction.getStartDateText());
        limits.setText(this.construction.getLimitsText());
        finishDate.setText(this.construction.getFinishDateText());
        supervisor.setText(this.construction.getSupervisorText());
        phoneNumber.setText(this.construction.getPhoneNumberText());
        ward.setText(this.construction.getWardText());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.construction, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
