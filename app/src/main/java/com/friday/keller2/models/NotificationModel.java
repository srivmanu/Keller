package com.friday.keller2.models;

import android.util.Log;
import com.friday.keller2.enums.TimeUnitEnum;

/**
 * Created By srivmanu on 11/4/2019 for Keller 2
 * This will always be a test run.
 * Unless you are compiling to submit on play store.
 * In which case, God help your soul.
 */
public class NotificationModel {

    private static final String TAG = "NotificationModel";

    int time;

    TimeUnitEnum units;

    public NotificationModel(final int time, final TimeUnitEnum units) {
        this.time = time;
        this.units = units;
    }

    public NotificationModel() {
        this.time = 0;
        this.units = TimeUnitEnum.minutes;
    }

    public void log() {
        Log.d(TAG, "\nTime : " + time +
                "\nUnits : " + units.name());

    }

    public void setTime(final int time) {
        this.time = time;
    }

    public void setUnits(final TimeUnitEnum units) {
        this.units = units;
    }

    long getTimeDiff() {
        long diff = 0;
        switch (units) {

            case minutes:
                diff = time * 60;
                break;
            case hours:
                diff = time * 60 * 60;
                break;
            case days:
                diff = time * 86400;
                break;
            case weeks:
                diff = time * 7 * 86400;
                break;
        }
        return diff;
    }
}
