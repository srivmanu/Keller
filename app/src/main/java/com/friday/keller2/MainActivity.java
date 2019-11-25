package com.friday.keller2;

import android.Manifest;
import android.Manifest.permission;
import android.Manifest.permission_group;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.action_home_bottom, R.id.action_weather_bottom,
                R.id.action_config_bottom)
                .build();
        getLocationPermission();
        App.getSummary()
//        summaryText.log("Main");
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private void getLocationPermission() {
        Dexter.withActivity(this)
                .withPermission(permission.ACCESS_FINE_LOCATION).withListener(
                DialogOnDeniedPermissionListener.Builder.withContext(getApplicationContext())
                        .withTitle(getString(R.string.app_name))
                        .withButtonText("Okay")
                        .withMessage("Location Permission Needed")
                        .build())
                .check();
    }

}
