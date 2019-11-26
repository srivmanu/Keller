package com.friday.keller2.models;

import android.util.Log;
import com.friday.keller2.App;
import com.friday.keller2.BuildConfig;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
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
        /**
         * {
         *   "id": "5ddced7f119d2acc51f91f1a",
         *   "title": "Check",
         *   "rrule": "DTSTART:20191126T091630Z\nRRULE:INTERVAL\u003d1;COUNT\u003d1;UNTIL\u003d20191127T091630Z",
         *   "color": "#91a8fe",
         *   "startDate": "2019-11-26T03:16:30+00:00",
         *   "endDate": "2019-11-27T03:16:30+00:00",
         *   "location": {
         *     "latitude": 24.56,
         *     "longitude": 54.26
         *   }
         * }
         */
        try {
            this.title = nextEvent.getString("title");
            this.color = nextEvent.getString("color");
            this.start = parseServerDate(nextEvent.getString("startDate"));
            this.end = parseServerDate(nextEvent.getString("endDate"));
            this.location = new LocationModel(nextEvent.getJSONObject("location"));
            getWeatherInBackground();
        } catch (JSONException e) {
            e.printStackTrace();
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

    private void getWeatherInBackground() {
        Callback call = new Callback() {
            @Override
            public void onFailure(@NotNull final Call call, @NotNull final IOException e) {

            }

            @Override
            public void onResponse(@NotNull final Call call, @NotNull final Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject obj = new JSONObject(response.body().string());
                        model = new WeatherModel(obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        final Map<String, String> params = new HashMap<>();
        params.put("lat", this.location.getLat());
        params.put("lon", this.location.getLon());
        params.put("time", String.valueOf(this.start.getTime().getTime()));

        final String url = App.getInstance().getServerURL() + "/weather/currentweather";
        App.getInstance().get(url, params, call);
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

    private Calendar parseServerDate(final String date) {
        //2019-11-26T03:16:30+00:00
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
        try {
            cal.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }
}
