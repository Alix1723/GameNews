package com.student.alix.gamenews;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import com.student.alix.gamenews.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FamousDevelopers extends FragmentActivity {

    List<DevMapData> DevList;
    private Marker[] markerList = new Marker[3];
    private GoogleMap mapDevelopers;

    private float markerColours[]= {200.0f, 100.0f, 300.0f};

    private LatLng mapCentre = new LatLng(45.856876, -55.745154);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_famous_developers);

        DevList = new ArrayList<DevMapData>();

        DevMapDBManager database = new DevMapDBManager(this, "famousdevs.s3db", null, 1);
        try
        {
            database.dbCreate();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        DevList = database.allMapData();
        Log.e("n", "Size: " + DevList.size());

        SetupMap();
        SetupMarkers();
    }

    public void SetupMap()
    {
        mapDevelopers = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        if(mapDevelopers!=null)
        {
            mapDevelopers.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCentre,1));
            mapDevelopers.setMyLocationEnabled(true);
            mapDevelopers.getUiSettings().setCompassEnabled(true);
            mapDevelopers.getUiSettings().setMyLocationButtonEnabled(true);
            mapDevelopers.getUiSettings().setRotateGesturesEnabled(true);
        }
    }

    public void SetupMarkers()
    {
        MarkerOptions mrk;
        DevMapData data;
        String markerTitle;
        String markerText;

        for(int i = 0; i < DevList.size(); i++)
        {
            data = DevList.get(i);
            markerTitle = data.getName();
            markerText = data.getKnownFor();
            mrk = SetMarker(markerTitle,markerText,new LatLng(data.getLatitude(),data.getLongitude()),markerColours[i],false);
            mapDevelopers.addMarker(mrk);
        }
    }

    public MarkerOptions SetMarker(String title, String description, LatLng position, float markerColour, boolean centreAnchor)
    {
        float anchorX;
        float anchorY;

        if(centreAnchor)
        {
            anchorX = 0.5f;
            anchorY = 0.5f;
        }
        else
        {
            anchorX = 0.5f;
            anchorY = 1.0f;
        }
        MarkerOptions marker = new MarkerOptions().title(title).snippet(description).icon(BitmapDescriptorFactory.defaultMarker(markerColour)).anchor(anchorX,anchorY).position(position);
        return marker;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_famous_developers, menu);
        return true;
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
}
