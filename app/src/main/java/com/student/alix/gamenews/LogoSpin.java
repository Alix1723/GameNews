package com.student.alix.gamenews;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

import java.util.concurrent.ExecutionException;

public class LogoSpin extends Activity {

    LogoSpinSurfaceView srfv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_spin);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        srfv = new LogoSpinSurfaceView(this);

        setContentView(srfv);

        //Handler to run task and update progress
        final Handler renderLoop = new Handler();
        //Runnable
        /*renderLoop.post(new Runnable() {
                     public void run() {

                        srfv.draw();
                        renderLoop.postDelayed(this, 1000);
                     }
                 }
        );*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logo_spin, menu);
        return true;
    }

}
