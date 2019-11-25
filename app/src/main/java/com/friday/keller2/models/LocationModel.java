package com.friday.keller2.models;

import android.util.Log;
import com.friday.keller2.RequestDataType;
import org.json.JSONException;
import org.json.JSONObject;

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

    public JSONObject getJson() {
//        {
//            "type":"GPS",
//            "location":{
//                  "latitude": "27.52546",
//                  "longitude": "97.26585"
//             }
//        }
        JSONObject obj = null;
        try {
            obj = new JSONObject()
                    .put("type", RequestDataType.GPS.name())
                    .put("location", new JSONObject()
                            .put("latitude", lat)
                            .put("longitude", lon)
                    );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
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
