package com.friday.keller2.models;

import android.util.Log;
import com.friday.keller2.BuildConfig;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created By srivmanu on 11/4/2019 for Keller 2
 * This will always be a test run.
 * Unless you are compiling to submit on play store.
 * In which case, God help your soul.
 */
public class EventModel {

    private static final String TAG = "EventModel";

    String color;

    Calendar end;

    LocationModel location;

    WeatherModel model;

    Calendar start;

    String title;

    public EventModel() {
        this.title = "Demo";
        this.start = Calendar.getInstance();
        this.end = start;
        end.add(Calendar.HOUR, 2);
        this.location = new LocationModel();
        this.color = "#00000000";
    }

    public EventModel(final JSONObject nextEvent) {
        //TODO GET FROM SERVER EVENT PARSE HERE
        //TODO SAVE NEXT WEATHER TOO
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "RECD : : " + nextEvent.toString());
        }

        try {
            String id = nextEvent.getString("id");
            if (id != null && !id.equals("null")) {
                //TODO STUFF HERE
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(final Calendar end) {
        this.end = end;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(final LocationModel location) {
        this.location = location;
    }

    public WeatherModel getModel() {
        return model;
    }

    public void setModel(final WeatherModel model) {
        this.model = model;
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(final Calendar start) {
        this.start = start;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void log() {
        Log.d(TAG, logString());
    }

    public String logString() {
        return "\nEvent : \nTitle : " + title
                + "\nStarts : " + start.getTime()
                + "\nEnds : " + end.getTime()
                + "\nLocation : " + location.logString()
                + "\nColor : " + color;
    }

    public Calendar parseDate(final String time, final String date) {
        Calendar cal = Calendar.getInstance();
        Log.d(TAG, "parseDate() called with: time = [" + time + "], date = [" + date + "]");
        String text = time + " " + date;
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm MM/dd/yyyy");
        try {
            cal.setTime(sdf.parse(text));
        } catch (ParseException e) {
            Log.d(TAG, "parseDate Err" + e.getMessage());
        }
        return cal;
    }

    public void sendToServer() {
        log();
        //API Call Send to Server : Todo
    }
}
