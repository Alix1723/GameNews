package com.student.alix.gamenews;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * MainMenu
 */

public class MainMenu extends Activity implements View.OnClickListener{

    Button NewsFeedButton;
    Button FamousDevsButton;
    SharedPreferences sharedPrefs;
    PlatformPreferences platPrefs;
    AboutFragment aboutDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Portrait Orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Buttons
        NewsFeedButton = (Button) findViewById(R.id.button_newsfeed);
        NewsFeedButton.setOnClickListener(this);

        FamousDevsButton = (Button) findViewById(R.id.button_famousdevs);
        FamousDevsButton.setOnClickListener(this);

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        platPrefs = new PlatformPreferences(sharedPrefs); //Defaults to PS

        aboutDialog = new AboutFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            //Open preference menu
            Intent openPrefsIntent = new Intent(getApplicationContext(), PreferencesActivity.class);
            startActivity(openPrefsIntent);
            return true;
        }
        else if(id == R.id.about)
        {
            //Open About dialog
            aboutDialog.show(getFragmentManager(),"AboutDialog");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view)
    {
        //Button presses
        if(view.getId() == R.id.button_newsfeed)
        {
            //Open news feed
            Intent newsFeedIntent = new Intent(getApplicationContext(),NewsFeed.class);
            startActivity(newsFeedIntent);
        }

        if(view.getId() == R.id.button_famousdevs)
        {
            //Open famous developers map
            Intent famousDevsIntent = new Intent(getApplicationContext(),FamousDevelopers.class);
            startActivity(famousDevsIntent);
        }

        if(view.getId() == R.id.button_logospin)
        {
            //Open spinning logo
            Intent spinLogoIntent = new Intent(getApplicationContext(),LogoSpin.class);
            startActivity(spinLogoIntent);
        }

    }
}
