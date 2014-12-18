package com.student.alix.gamenews;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;

/**
 * PlatformPreferences
 */

public class PlatformPreferences {
    SharedPreferences sharedPrefs;


    private String platform;

    private void setPlatform(String nplat)
    {
        platform = nplat;
    }

    public String getPlatform()
    {
        return platform;
    }

    public PlatformPreferences(SharedPreferences shPref)
    {
        setPlatform("none");

        try
        {
            this.sharedPrefs = shPref;
        }
        catch(Exception e)
        {
            Log.e("n", "Unable to open preference manager");
        }

        savePrefs("platform","xbox");
    }

    public void savePrefs(String key, String value)
    {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(key,value);
        editor.commit();
    }
}
