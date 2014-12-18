package com.student.alix.gamenews;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.student.alix.gamenews.R;

public class PreferencesActivity extends Activity implements View.OnClickListener {

    Button saveButton;
    SharedPreferences prefs;
    PlatformPreferences platPrefs;
    String platform;

    RadioButton rb_xbox;
    RadioButton rb_ps;
    RadioButton rb_nint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        saveButton = (Button)findViewById(R.id.button_save);
        rb_xbox = (RadioButton) findViewById(R.id.radio_xbox);
        rb_ps = (RadioButton) findViewById(R.id.radio_ps);
        rb_nint = (RadioButton) findViewById(R.id.radio_nint);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        //Load saved preference
        platform = String.valueOf(prefs.getString("platform","none"));

        platPrefs = new PlatformPreferences(prefs);


        if(platform != "none")
        {
            platPrefs.savePrefs("platform",platform);

            Log.e("n",platform );
            //Set radio button to the saved preference first
            switch (platform) {
                case "xbox":
                    rb_xbox.setChecked(true);
                    rb_ps.setChecked(false);
                    rb_nint.setChecked(false);
                    break;
                case "ps":
                    rb_xbox.setChecked(false);
                    rb_ps.setChecked(true);
                    rb_nint.setChecked(false);
                    break;
                case "nint":
                    rb_xbox.setChecked(false);
                    rb_ps.setChecked(false);
                    rb_nint.setChecked(true);
                    break;
            }

        }
    }

    public void onRadioButtonClicked(View view)
    {
        boolean isChecked = ((RadioButton) view ).isChecked();

        switch(view.getId())
        {   //Determine which button was checked, and set the platform to that selection
            case R.id.radio_xbox:
                platform = "xbox";
                break;
            case R.id.radio_ps:
                platform = "ps";
                break;
            case R.id.radio_nint:
                platform = "nint";
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view)
    {
        //Button pressed
        if(view.getId() == R.id.button_save)
        {
            //Done
            platPrefs.savePrefs("platform",platform);
            Log.e("n", "Saved platform as " + prefs.getString("platform", "ERROR"));
            Toast.makeText(getApplicationContext(),"Saved platform!",Toast.LENGTH_SHORT).show();
            setResult(Activity.RESULT_OK);
            finish();
        }
    }
}
