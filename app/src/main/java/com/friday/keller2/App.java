package com.friday.keller2;

import android.app.Application;
import android.content.SharedPreferences;
import com.friday.keller2.models.SummaryModel;

/**
 * Created By srivmanu on 11/25/2019 for Keller 2
 * This will always be a test run.
 * Unless you are compiling to submit on play store.
 * In which case, God help your soul.
 */
public class App extends Application {

    private static SummaryModel model;

    private String urlKey = "SERVER_URL";

    public static void getSummary() {
        model = new SummaryModel();
        //todo get from server
    }

    public static SummaryModel getSummaryModel() {
        return model;
    }

    public String getServerURL() {

        return getPrefs().getString(urlKey, "DEMO : " + getResources().getString(R.string.server_url));
    }

    public void setServerURL(String url) {
        getPrefs().edit().putString(urlKey, url).apply();
    }

    private SharedPreferences getPrefs() {
        return getSharedPreferences("KELLER_PREFS", MODE_PRIVATE);
    }
}
