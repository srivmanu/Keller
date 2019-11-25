package com.friday.keller2.models;

import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.json.JSONArray;
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

    Calendar end;

    LocationModel location;

    List<NotificationModel> notificationList;

    Calendar start;

    String title;

    WeatherModel weather;

    public void setColor(final String color) {
        this.color = color;
    }

    String color;


    public EventModel() {
        this.title = "Demo";
        this.start = Calendar.getInstance();
        this.end = start;
        end.add(Calendar.HOUR, 2);
        this.location = new LocationModel();
        this.weather = new WeatherModel();
        this.color = "#00000000";
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(final Calendar end) {
        this.end = end;
    }

    public JSONObject getJson() {
//        {
//            "id":"EVENT_ID",
//            "startTime": "UNIX_TIMESTAMP",
//            "endTime": "UNIX_TIMESTAMP",
//            "title": "TEXT",
//            "periodicity": "WEEKLY|DAILY|MONTHLY",
//            "alerts": [
//                     "relative difference 1",
//                    "relative difference2"
//                ],
//            "location": {
//                "latitude": "27.52546",
//                "longitude": "97.26585"
//            },
//            "color": "#ffffff"
//        }
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject()
                    .put("id", "EVENT_ID")
                    .put("startTime", (start.getTimeInMillis() / 1000))
                    .put("endTime", (end.getTimeInMillis() / 1000))
                    .put("title", title)
                    .put("repeat", "TODO")//TODO
                    .put("alerts", getNotificationJson(notificationList))
                    .put("location", location.getJson())
                    .put("color", color);//TODO;
        } catch (JSONException e) {
            Log.d(TAG, "getJson() called");
        }
        return jsonObj;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(final LocationModel location) {
        this.location = location;
    }

    public List<NotificationModel> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(final List<NotificationModel> notificationList) {
        this.notificationList = notificationList;
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

    public WeatherModel getWeather() {
        return weather;
    }

    public void setWeather(final WeatherModel weather) {
        this.weather = weather;
    }

    public void log() {
        Log.d(TAG, logString());
    }

    public String logString() {
        return "\nEvent : \nTitle : " + title
                + "\nStarts : " + start.getTime()
                + "\nEnds : " + end.getTime()
                + "\nLocation : " + location.logString()
                + "\nWeather : " + weather.logString()
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

    private JSONArray getNotificationJson(final List<NotificationModel> notificationList) {
//        [
//                     "relative difference 1",
//                    "relative difference2"
//                ],
        ArrayList<Long> list = new ArrayList<>();
        for (NotificationModel item : notificationList) {
            list.add(item.getTimeDiff());
        }
        return new JSONArray(list);
    }

    public String getColor() {
        return color;
    }
}
