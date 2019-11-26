package com.friday.keller2.models;

import com.friday.keller2.App;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created By srivmanu on 11/4/2019 for Keller 2
 * This will always be a test run.
 * Unless you are compiling to submit on play store.
 * In which case, God help your soul.
 */
public class SummaryModel {

    EventModel event_next;

    String summaryText;

    WeatherModel weather_now;

    public SummaryModel(final JSONObject obj) {
        try {
            this.summaryText = obj.getString("summary");
            this.event_next = new EventModel(obj.getJSONObject("nextEvent"));
            this.weather_now = App.getInstance().getCurrentWeather();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public EventModel getEvent_next() {
        return event_next;
    }

    public void setEvent_next(final EventModel event_next) {
        this.event_next = event_next;
    }


    public String getSummaryText() {
        return summaryText;
    }

    public void setSummaryText(final String summaryText) {
        this.summaryText = summaryText;
    }

    public WeatherModel getWeather_now() {
        return weather_now;
    }

    public void setWeather_now(final WeatherModel weather_now) {
        this.weather_now = weather_now;
    }

}
