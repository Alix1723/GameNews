package com.student.alix.gamenews;

/**
 * LogoSpinSurfaceView
 * (Code partially duplicated from Labs by Bobby Law)
 */

public class DevMapData {

    private int entryID;

    private String Name;
    private String KnownFor;

    private float Latitude;
    private float Longitude;

    private static final long serialVersionUID = 0L;

    public int getEntryID()
    {
        return entryID;
    }

    public void setEntryID(int entID)
    {
        this.entryID = entID;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String nm)
    {
        this.Name = nm;
    }

    public String getKnownFor()
    {
        return KnownFor;
    }

    public void setKnownFor(String gamesKnownFor)
    {
        this.KnownFor = gamesKnownFor;
    }

    public float getLatitude()
    {
        return Latitude;
    }

    public void setLatitude(float lat)
    {
        this.Latitude = lat;
    }

    public float getLongitude()
    {
        return this.Longitude;
    }

    public void setLongitude(float lon)
    {
        this.Longitude = lon;
    }

    @Override
    public String toString()
    {
        String out = "Dev " + entryID + ", Name: " + Name + ", Known for: " + KnownFor + ", Latitude: " + Latitude + ", Longitude: " + Longitude;

        return out;
    }
}
