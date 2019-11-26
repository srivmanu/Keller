package com.friday.keller2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerDetails extends AppCompatActivity {


    private static final String TAG = "ServerDetails";


    private EditText url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_details);
        if (App.getInstance().getServerURL() != null) {
            ConstraintLayout splash = findViewById(R.id.splash);
            splash.setVisibility(View.VISIBLE);
            ConstraintLayout server = findViewById(R.id.serverDetailsView);
            server.setVisibility(View.GONE);
            openMainActivity();
        } else {
            ConstraintLayout splash = findViewById(R.id.splash);
            splash.setVisibility(View.GONE);
            ConstraintLayout server = findViewById(R.id.serverDetailsView);
            server.setVisibility(View.VISIBLE);
        }

        url = findViewById(R.id.server_url_input);
        FloatingActionButton fab = findViewById(R.id.fab_url_page);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String urlText = url.getText().toString();
                if (isValidURL(urlText)) {
                    url.setError(null);
                    makeConnection(urlText);
                } else {
                    url.setError("Error in URL");
                }
            }
        });
    }

    private void causeError(final String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        url.setError(s);
    }


    private boolean isValidURL(final CharSequence s) {
        return URLUtil.isValidUrl(s.toString());
    }

    private void makeConnection(String urlText) {
        urlText = urlText + "/weather/currentweather";
        boolean valid = URLUtil.isValidUrl(urlText) && urlText.endsWith("/api");
        if (!valid) {
            causeError("Invalid URL");
        }

        FutureRe callback = new FutureRe();
        App.getInstance().get(urlText, null, callback);
        try {
            Response response = callback.get();
            if (response.isSuccessful()) {
                final String responseStr = response.body().string();
                try {
                    JSONObject o = new JSONObject(responseStr);
                    saveURLandGoToNextPage(urlText);
                } catch (JSONException e) {
                    Log.d(TAG, "RES : " + responseStr);
                    causeError("Invalid Server");
                }
            } else {
                causeError("URL does not correspond to valid server");
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openMainActivity() {
        App.getInstance().getDataFromServerAndStore();
        final Handler handler = new Handler();
        final long delayTime = 2000; // 2s

        if (App.getInstance().getCurrentWeather() != null && App.getInstance().getLocalSummaryModel() != null
                && App.getInstance().gethourlyweather() != null) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    finish();
                    startActivity(i);
                }
            }, delayTime);
        }

    }


    private void saveURLandGoToNextPage(final String urlText) {
        App.getInstance().saveServerURL(urlText);
        openMainActivity();
    }
}
