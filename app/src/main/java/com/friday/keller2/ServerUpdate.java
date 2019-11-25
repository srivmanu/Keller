package com.friday.keller2;

import android.util.Log;
import com.friday.keller2.models.EventModel;
import com.friday.keller2.models.LocationModel;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created By srivmanu on 11/5/2019 for Keller 2
 * This will always be a test run.
 * Unless you are compiling to submit on play store.
 * In which case, God help your soul.
 */
public class ServerUpdate {

    String TAG = "ServerUpdate";

    void getCalendarDataResponseFromServer() {
        /*{
            "status": "SUCCESS|FAIL",
                "time": "UNIX_TIMESTAMP",
                "id":"REQ_ID",
                "data":{
            "events":[
            {
                "id":"EVENT_ID",
                    "startTime": "UNIX_TIMESTAMP",
                    "endTime": "UNIX_TIMESTAMP",
                    "title": "TEXT",
                    "periodicity": "WEEKLY|DAILY|MONTHLY",
                    "alerts": [
                "relative difference 1",
                        "relative difference2"
                ],
                "location": {
                "latitude": "27.52546",
                        "longitude": "97.26585"
            },
                "color": "#ffffff"
            },
            {
                "id":"EVENT_ID",
                    "startTime": "UNIX_TIMESTAMP",
                    "endTime": "UNIX_TIMESTAMP",
                    "title": "TEXT",
                    "periodicity": "WEEKLY|DAILY|MONTHLY",
                    "alerts": [
                "relative difference 1",
                        "relative difference2"
                ],
                "location": {
                "latitude": "27.52546",
                        "longitude": "97.26585"
            },
                "color": "#ffffff"
            }
        ]
        }
        }*/
    }

    String getCalendarRequestJson(String startTime, String endTime) {
        /*
     {
        "type": "request",
        "time": "UNIX_TIMESTAMP",
        "id":"REQ_ID",
        "data" : {
            "type" : "calendar",
            "calendar":{
                "startTime":"UNIX_TIMESTAMP",
                "endTime":"UNIX_TIMESTAMP"
            }
        }
    }*/
        String jsonString = null;
        try {
            jsonString = new JSONObject()
                    .put("type", RequestType.request.name())
                    .put("time", getTimeStamp())
                    .put("id", getNewRequestId())
                    .put("data", new JSONObject()
                            .put("type", RequestDataType.calendar.name())
                            .put("calendar", new JSONObject()
                                    .put("startTime", startTime)
                                    .put("endTime", endTime)
                            )
                    )
                    .toString();
        } catch (JSONException e) {
            Log.d(TAG, "getCalendarRequestJson() called with: startTime = [" + startTime + "], endTime = [" + endTime
                    + "]");
        }
        return jsonString;
    }

    String getGPSUpdateJson(LocationModel location) {
            /*{
            "type": "add",
            "time": "UNIX_TIMESTAMP",
            "id":"REQ_ID",
            "data" : {
                "type":"GPS",
                "location":{
                    "latitude": "27.52546",
                    "longitude": "97.26585"
                }
            }
        }*/
        String jsonString = null;
        try {
            jsonString = new JSONObject()
                    .put("type", RequestType.update.name())
                    .put("time", getTimeStamp())
                    .put("id", getNewRequestId())
                    .put("data", location.getJson())
                    .toString();
        } catch (JSONException e) {
            Log.d(TAG, "sendGPStoServer() called with: location = [" + location.logString() + "]");
        }
        return jsonString;
    }

    String getRequestResultJson(RequestResult result) {
        /*
        {
            "status": "SUCCESS|FAIL",
            "time": "UNIX_TIMESTAMP",
            "id":"REQ_ID"
        }
        * */
        String jsonString = null;
        try {
            jsonString = new JSONObject()
                    .put("status", result.name())
                    .put("time", getTimeStamp())
                    .put("id", getNewRequestId())
                    .toString();
        } catch (JSONException e) {
            Log.d(TAG, "getRequestResultJson() called with: result = [" + result + "]");
        }
        return jsonString;
    }

    void getSummaryDataFromServer() {
       /* {
            "status": "SUCCESS|FAIL",
                "time": "UNIX_TIMESTAMP",
                "id":"REQ_ID",
                "data":{
            "summary":"TEXT",
                    "nextEvent":{
                "id":"EVENT_ID",
                        "startTime": "UNIX_TIMESTAMP",
                        "endTime": "UNIX_TIMESTAMP",
                        "title": "TEXT",
                        "periodicity": "WEEKLY|DAILY|MONTHLY",
                        "alerts": [
                "relative difference 1",
                        "relative difference2"
            ],
                "location": {
                    "latitude": "27.52546",
                            "longitude": "97.26585"
                },
                "color": "#ffffff"
            },
            "nextWeather":{
                "weather_type":"SUNNY|RAINY|WINDY|HAZE|SNOW...",
                        "temperature":"13F",
                        "temperatureHigh":"22F",
                        "temperatureLow":"10F",
                        "precipitation":"20%"
            }
        }
        }*/
    }

    String getSummaryRequestJson() {/*
    {
        "type": "request",
        "time": "UNIX_TIMESTAMP",
        "id":"REQ_ID",
        "data" : {
            "type":"summary"
        }
    }
    * */
        String jsonString = null;
        try {
            jsonString = new JSONObject()
                    .put("type", RequestType.add.name())
                    .put("time", getTimeStamp())
                    .put("id", getNewRequestId())
                    .put("data", new JSONObject()
                            .put("type", RequestDataType.summary.name())
                    )
                    .toString();
        } catch (JSONException e) {
            Log.d(TAG, "getSummaryRequestJson() called");
        }
        return jsonString;
    }

    String getWeatherRequestJson(LocationModel location, ArrayList<String> times) {/*{
        "type": "request",
        "time": "UNIX_TIMESTAMP",
        "id":"REQ_ID",
        "data" : {
        "type" : "weather",
                "location": {
                    "latitude": "27.52546",
                    "longitude": "97.26585"
        },
        "times":[
                "UNIX_TIMESTAMP1",
                "UNIX_TIMESTAMP2",
                "UNIX_TIMESTAMP3",
            ]
        }
    }*/
        String jsonString = null;
        try {
            jsonString = new JSONObject()
                    .put("type", RequestType.request.name())
                    .put("time", getTimeStamp())
                    .put("id", getNewRequestId())
                    .put("data", new JSONObject()
                            .put("type", RequestDataType.weather.name())
                            .put("location", location.getJson())
                    )
                    .put("times", new JSONArray(times))
                    .toString();
        } catch (JSONException e) {
            Log.d(TAG, "getWeatherFromServer() called");
        }
        return jsonString;
    }

    void getWeatherResponseFromServer() {
        /*{
            "status": "SUCCESS|FAIL",
                "time": "UNIX_TIMESTAMP",
                "id":"REQ_ID",
                "data":{
            "weather":{
                "UNIX_TIMESTAMP1":{
                    "weather_type":"SUNNY|RAINY|WINDY|HAZE|SNOW...",
                            "temperature":"13F",
                            "temperatureHigh":"22F",
                            "temperatureLow":"10F",
                            "precipitation":"20%"
                },
                "UNIX_TIMESTAMP2":{
                    "weather_type":"SUNNY|RAINY|WINDY|HAZE|SNOW...",
                            "temperature":"13F",
                            "temperatureHigh":"22F",
                            "temperatureLow":"10F",
                            "precipitation":"20%"
                }
            }
        }
        }*/
    }

    String sendEventToServer(EventModel eventModel) {
    /*
    {
        "type": "add",
        "time": "UNIX_TIMESTAMP",
        "id":"REQ_ID",
        "data": {
            "type": "event",
            "event": {
                "id":"EVENT_ID",
                "startTime": "UNIX_TIMESTAMP",
                "endTime": "UNIX_TIMESTAMP",
                "title": "TEXT",
                "periodicity": "WEEKLY|DAILY|MONTHLY",
                "alerts": [
                    "relative difference 1",
                    "relative difference2"
                ],
                "location": {
                    "latitude": "27.52546",
                    "longitude": "97.26585"
                },
                "color": "#ffffff"
            }
        }
    }
    * */
        String jsonString = null;
        try {
            jsonString = new JSONObject()
                    .put("type", RequestType.add.name())
                    .put("time", getTimeStamp())
                    .put("id", getNewRequestId())
                    .put("data", new JSONObject()
                            .put("type", RequestDataType.event.name())
                            .put("event", eventModel.getJson())
                    )
                    .toString();
        } catch (JSONException e) {
            Log.d(TAG, "sendEventToServer() called with: eventModel = [" + eventModel.logString() + "]");
        }
        return jsonString;
    }

    private String getNewRequestId() {
        //Todo
        return null;
    }

    private String getTimeStamp() {
        //Todo
        return null;
    }

//    TODO finish Functions.
}
