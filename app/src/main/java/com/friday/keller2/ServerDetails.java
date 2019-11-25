package com.friday.keller2;

import android.Manifest;
import android.Manifest.permission;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import dalvik.system.DexClassLoader;
import java.util.List;

public class ServerDetails extends AppCompatActivity {

    private String KELLER_PREFS = "KELLER_PREFS_KEY";

    private String SERVER_URL = "SERVER_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_details);
        if (true || getSharedPreferences(KELLER_PREFS, MODE_PRIVATE).getString(SERVER_URL, null) != null) {
            //todo remove true
            openMainActivity();
        }
        final EditText url = findViewById(R.id.server_url_input);
        FloatingActionButton fab = findViewById(R.id.fab_url_page);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String urlText = url.getText().toString();
                if (isValidURL(urlText)) {
                    url.setError(null);
                    if (isConnectionSuccessful(urlText)) {
                        saveURLandGoToNextPage(urlText);
                    } else {
                        url.setError("URL does not correspond to valid Server");
                    }
                } else {
                    url.setError("Error in URL");
                }
            }
        });
    }

    private boolean isConnectionSuccessful(final String text) {
        //TODO make connection and check if valid url
        return false;
    }

    private boolean isValidURL(final CharSequence s) {
        if (URLUtil.isValidUrl(s.toString())) {
            return true;
        } else {
            return false;
        }
    }

    private void openMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void saveURLandGoToNextPage(final String urlText) {
        SharedPreferences prefs = getSharedPreferences(KELLER_PREFS, MODE_PRIVATE);
        prefs.edit().putString(SERVER_URL, urlText).apply();
        openMainActivity();
    }
}
