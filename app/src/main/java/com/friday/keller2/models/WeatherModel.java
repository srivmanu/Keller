package com.friday.keller2.models;

import android.util.Log;
import android.widget.EditText;
import com.friday.keller2.App;
import com.friday.keller2.enums.TempUnitEnum;
import com.friday.keller2.enums.WeatherEnum;
import java.text.DecimalFormat;
import java.util.Calendar;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created By srivmanu on 11/4/2019 for Keller 2
 * This will always be a test run.
 * Unless you are compiling to submit on play store.
 * In which case, God help your soul.
 */
public class WeatherModel {

    private static final String TAG = "WeatherModel";

    String date;

    double temp;

    TempUnitEnum unit;

    WeatherEnum weather;

    public WeatherModel(final String date, final double temp, final TempUnitEnum unit, final WeatherEnum weather) {
        this.date = date;
        this.temp = temp;
        this.unit = unit;
        this.weather = weather;
        formatTemperature();
    }

    public WeatherModel() {
        this.weather = WeatherEnum.clear;
        this.temp = 0.0;
        this.unit = TempUnitEnum.celsius;
        this.date = "11/1";
        formatTemperature();
    }

    public WeatherModel(final JSONObject o) {
        try {
            this.date = App.getInstance().convertTimeStampToDateString(o.getLong("time"));
            this.temp = o.getDouble("temperature");
            this.weather = App.getInstance().getWeatherEnumFromJSONData(o.getString("icon"));
            this.unit = TempUnitEnum.fahrenheit;
            convertTempToUserChoice();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        formatTemperature();
        //TODO CREATE FROM SERVER RESPONSE
    }

    public void convertTemp() {
        if (unit == TempUnitEnum.celsius) {
            unit = TempUnitEnum.fahrenheit;
            temp = (temp * (9.0 / 5)) + 32.0;
        } else if (unit == TempUnitEnum.fahrenheit) {
            unit = TempUnitEnum.celsius;
            temp = (temp - 32.0) * (5.0 / 9);
        }
        formatTemperature();
    }

    public double getTemp() {
        formatTemperature();
        return temp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    private void convertTempToUserChoice() {
        TempUnitEnum choice = App.getInstance().getUserTemperatureUnitChoice();
        if (this.unit != choice) {
            convertTemp();
        }
    }

    public void setTemp(final double temp) {
        this.temp = temp;
    }

    public TempUnitEnum getUnit() {
        return unit;
    }

    public void setUnit(final TempUnitEnum unit) {
        this.unit = unit;
    }

    public String getUnitString() {
        final String degree = "°";
        if (getUnit() == TempUnitEnum.celsius) {
            return "℃";
        } else if (getUnit() == TempUnitEnum.fahrenheit) {
            return "℉";
        }
        return "℃";
    }

    public void getWeather(final EditText location, final Calendar start, final Calendar end) {
        //API Call to get Weather : TODO
    }

    public WeatherEnum getWeather() {
        return weather;
    }

    public void setWeather(final WeatherEnum weather) {
        this.weather = weather;
    }

    public void log() {
        Log.d(TAG, logString());
    }

    public String logString() {
        return "\nWeather : " + weather.name() + "\nTemp : " + temp + "\nUnits : " + unit;
    }

    private void formatTemperature() {
        DecimalFormat format = new DecimalFormat("#.#");
        final double v = Double.parseDouble(format.format(this.temp));

        this.temp = v;
    }
}
