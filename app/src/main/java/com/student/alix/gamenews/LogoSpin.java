package com.student.alix.gamenews;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

/**
 * LogoSpin
 */

public class LogoSpin extends Activity {

    LogoSpinSurfaceView srfv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_spin);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Load and run the surface view, and its associated thread
        srfv = new LogoSpinSurfaceView(this);
        setContentView(srfv);
    }
}
