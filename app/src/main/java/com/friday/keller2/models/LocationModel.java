package com.friday.keller2.models;

import android.util.Log;

/**
 * Created By srivmanu on 11/4/2019 for Keller 2
 * This will always be a test run.
 * Unless you are compiling to submit on play store.
 * In which case, God help your soul.
 */
public class LocationModel {

    private static final String TAG = "LocationModel";

    String lat;

    String lon;

    String name;

    public LocationModel(final String lat, final String lon, final String name) {
        this.lat = lat;
        this.lon = lon;
        this.name = name;
    }

    public LocationModel() {
        this.lat = "0.0";
        this.lon = "0.0";
        this.name = "Demo Loc";
    }

    public LocationModel(final String locationString) {
        this.name = locationString;
        //todo location --> lat long
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getName() {
        return name;
    }

    public void log() {
        Log.d(TAG, logString());
    }

    public String logString() {
        return "\nName : " + name + "\nLat : " + lat + "\nLong : " + lon;
    }
}
