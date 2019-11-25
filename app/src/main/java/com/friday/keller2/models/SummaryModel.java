package com.friday.keller2.models;

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

    public EventModel getEvent_next() {
        return event_next;
    }

    public String getSummaryText() {
        return summaryText;
    }

    public WeatherModel getWeather_now() {
        return weather_now;
    }

    public void getSummaryFromServer(){
        //TODO getsummary from server and save all values
    }

}
