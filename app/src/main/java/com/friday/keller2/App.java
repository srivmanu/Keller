package com.friday.keller2;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.friday.keller2.enums.TempUnitEnum;
import com.friday.keller2.enums.WeatherEnum;
import com.friday.keller2.models.EventModel;
import com.friday.keller2.models.LocationModel;
import com.friday.keller2.models.SummaryModel;
import com.friday.keller2.models.WeatherModel;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created By srivmanu on 11/25/2019 for Keller 2
 * This will always be a test run.
 * Unless you are compiling to submit on play store.
 * In which case, God help your soul.
 */
public class App extends Application {

    private static App app;

    private static final String TAG = "App";

    WeatherModel currenWeather;

    private String KELLER_PREFS = "KELLER_PREFS_KEY";

    private String SERVER_URL = "SERVER_URL";

    private String choiceKey = "USER_TEMP_CHOICE_KEY";

    private List<WeatherModel> hourlyWeather;

    private String idKey = "ID_KEY";

    private ArrayList<EventModel> localEventList;

    private SummaryModel model;

    private String urlKey = "SERVER_URL";

    public static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public String convertTimeStampToDateString(final Long time) {
        Date date = new Date(time * 1000);
        final String dateStr = new SimpleDateFormat("K:mm a", Locale.getDefault()).format(date);

        return dateStr;
    }

    public Call get(String url, final Map<String, String> params, Callback callback) {

        HttpUrl.Builder urlbuilder = HttpUrl.parse(url).newBuilder();
        if (params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                urlbuilder.addQueryParameter(param.getKey(), param.getValue());
            }
        }
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Final URL CALL: " + urlbuilder.build());
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlbuilder.build())
                .build();
        Call c = client.newCall(request);
        c.enqueue(callback);
        return c;
    }

    public String getCurrentTime(String format) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String time = sdf.format(d);
        return time;
    }

    public WeatherModel getCurrentWeather() {
        if (currenWeather == null) {
            getCurrentWeatherFromServer();
        }
        return currenWeather;
    }

    public void getCurrentWeatherFromServer() {
        Map<String, String> params = new HashMap<>();
        params.put("lat", getLatitude());
        params.put("lon", getLongitude());
        final String url = getServerURL() + "/weather/currentweather";
        FutureRe callback = new FutureRe();
        get(url, params, callback);
        try {
            Response response = callback.get();
            if (response.isSuccessful()) {
                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    currenWeather = new WeatherModel(obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void getDataFromServerAndStore() {
        getWeatherHourlyFromServer();
        getCurrentWeatherFromServer();
        getSummaryFromServer();
        getEventListFromServer();
    }

    public ArrayList<EventModel> getEventListlocal() {
        return localEventList;
    }

    public Drawable getImageBasedOnWeather(final WeatherEnum weather, Context context) {
        switch (weather) {

            case clear:
                return context.getDrawable(R.drawable.weather_clear);

            case rainy:
                return context.getDrawable(R.drawable.weather_rainy);

            case windy:
                return context.getDrawable(R.drawable.weather_windy);

            case cloudy:
                return context.getDrawable(R.drawable.weather_cloudy);
            case snowy:
                return context.getDrawable(R.drawable.weather_snowy);
            case foggy:
                return context.getDrawable(R.drawable.weather_foggy);
            default:
                return context.getDrawable(R.drawable.weather_unknown);
        }
    }

    public SummaryModel getLocalSummaryModel() {
        if (model == null) {
            getSummaryFromServer();
        }
        return model;
    }

    public String getNewId() {
        getPrefs().edit().putInt(idKey,
                getPrefs().getInt(idKey, 0) + 1).apply();
        return String.valueOf(getPrefs().getInt(idKey, 0));

    }

    public String getServerURL() {
        return getSharedPreferences(KELLER_PREFS, MODE_PRIVATE)
                .getString(SERVER_URL, getResources().getString(R.string.server_url)); //todo make null later
    }

    public void getSummaryFromServer() {
        String url = getServerURL() + "/summary";
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NotNull final Call call, @NotNull final IOException e) {

            }

            @Override
            public void onResponse(@NotNull final Call call, @NotNull final Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject obj = new JSONObject(response.body().string());
                        model = new SummaryModel(obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        get(url, null, callback);

    }

    public TempUnitEnum getUserTemperatureUnitChoice() {
        String choice = getPrefs().getString(choiceKey, TempUnitEnum.celsius.name());
        if (choice.equals(TempUnitEnum.celsius.name())) {
            return TempUnitEnum.celsius;
        } else if (choice.equals(TempUnitEnum.fahrenheit.name())) {
            return TempUnitEnum.fahrenheit;
        } else {
            return TempUnitEnum.celsius;
        }
    }

    public void setUserTemperatureUnitChoice(TempUnitEnum choice) {
        getPrefs().edit().putString(choiceKey, choice.name()).apply();
    }

    public WeatherEnum getWeatherEnumFromJSONData(final String icon) {

        if (icon.contains("clear")) {
            return WeatherEnum.clear;
        } else if (icon.equals("rain")) {
            return WeatherEnum.rainy;
        } else if (icon.equals("snow")) {
            return WeatherEnum.snowy;
        } else if (icon.equals("sleet")) {
            return WeatherEnum.snowy;
        } else if (icon.equals("wind")) {
            return WeatherEnum.windy;
        } else if (icon.equals("fog")) {
            return WeatherEnum.foggy;
        } else if (icon.contains("cloudy")) {
            return WeatherEnum.cloudy;
        } else {
            return WeatherEnum.unknown;
        }
    }

    public WeatherModel getWeatherForTimeAndLocation(final Date time, final LocationModel location) {
        final String url = getServerURL() + "/weather/currenweather";
        Map<String, String> params = new HashMap<>();
        params.put("lat", location.getLat());
        params.put("lon", location.getLon());
        params.put("time", String.valueOf(time.getTime()));
        FutureRe callback = new FutureRe();
        get(url, params, callback);
        try {
            Response response = callback.get();
            if (response.isSuccessful()) {
                WeatherModel m = new WeatherModel(new JSONObject(response.body().string()));
                return m;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getWeatherHourlyFromServer() {
        //call server and get hourly weather
        final String url = getServerURL() + "/weather/HourlyForecast";
        Map<String, String> params = new HashMap<>();
        getLocation();
        params.put("lat", getLatitude());
        params.put("lon", getLongitude());

        Callback future = new Callback() {
            @Override
            public void onFailure(@NotNull final Call call, @NotNull final IOException e) {

            }

            @Override
            public void onResponse(@NotNull final Call call, @NotNull final Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseString = response.body().string();
                    try {
                        JSONObject obj = new JSONObject(responseString);
                        JSONArray arr = obj.getJSONArray("data");
                        if (hourlyWeather == null) {
                            hourlyWeather = new ArrayList<>();
                        } else {
                            hourlyWeather.clear();
                        }
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject o = arr.getJSONObject(i);
                            WeatherModel model = new WeatherModel(o);
                            hourlyWeather.add(model);
                        }
                    } catch (JSONException e) {
                        if (BuildConfig.DEBUG) {
                            Log.d(TAG, " MALFORMED JSON: ");
                        }

                    }
                } else {
                    Log.d(TAG, "ERROR IN GETTING WEATHER + ");
                }
            }
        };
        get(url, params, future);
    }

    public List<WeatherModel> gethourlyweather() {
        return hourlyWeather;
    }

    public void saveServerURL(final String urlText) {
        getPrefs().edit().putString(SERVER_URL, urlText).apply();
    }

    public void post(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request);
//        try (Response response = client.newCall(request).execute()) {
//            return response.body().string();
//        }
    }

    public void setLocalEventList(final ArrayList<EventModel> localEventList) {
        this.localEventList = localEventList;
    }

    public void sendNewEventToServer(JSONObject model) {
        final String url = getServerURL() + "/event";
        try {
            post(url, model.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getEventListFromServer() {
        final String url = getServerURL() + "/event";
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NotNull final Call call, @NotNull final IOException e) {

            }

            @Override
            public void onResponse(@NotNull final Call call, @NotNull final Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        localEventList = App.getInstance()
                                .parseEventListJson(new JSONArray(response.body().string()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        get(url, null, callback);

    }

    private String getLatitude() {
        return String.valueOf(getLocation().getLattitude());
    }

    private GeoLocator getLocation() {
        return new GeoLocator(app);
    }

    private String getLongitude() {
        return String.valueOf(getLocation().getLongitude());
    }

    private SharedPreferences getPrefs() {
        return getSharedPreferences("KELLER_PREFS", MODE_PRIVATE);
    }

    private ArrayList<EventModel> parseEventListJson(final JSONArray jsonObject) {
        ArrayList<EventModel> list = new ArrayList<>();
        for (int i = 0; i < jsonObject.length(); i++) {
            try {
                EventModel m = new EventModel(jsonObject.getJSONObject(i));
                list.add(m);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
