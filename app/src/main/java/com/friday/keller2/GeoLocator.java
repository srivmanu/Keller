package com.friday.keller2;

/**
 * Created By srivmanu on 11/11/2019 for Thesis-MS
 * This will always be a test run.
 * Unless you are compiling to submit on play store.
 * In which case, God help your soul.
 */

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import androidx.core.app.ActivityCompat;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeoLocator {

    private static final int REQUEST_LOCATION = 1;

    private static double lattitude;

    private static double longitude;

    private String address;

    private String city;

    private Context context;

    private String country;

    private String knownName;

    private String postalCode;

    private String state;

    public GeoLocator(Context context) {
        this.context = context;
        this.GetLocation();
        this.geoAddress();
    }

    public GeoLocator(Context context, double lat, double lon) {
        this.context = context;
//        this.GetLocation();
        lattitude = lat;
        longitude = lon;
        this.geoAddress();
    }

    public void GetLocation() {
        Context var10001 = this.context;
        LocationManager locationManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled("gps")) {
        } else if (locationManager.isProviderEnabled("gps")) {
            if (ActivityCompat.checkSelfPermission(this.context, "android.permission.ACCESS_FINE_LOCATION") != 0
                    && ActivityCompat.checkSelfPermission(this.context, "android.permission.ACCESS_COARSE_LOCATION")
                    != 0) {
            } else {
                Location location = locationManager.getLastKnownLocation("network");
                Location location1 = locationManager.getLastKnownLocation("gps");
                Location location2 = locationManager.getLastKnownLocation("passive");
                double latti;
                double longi;
                if (location != null) {
                    latti = location.getLatitude();
                    longi = location.getLongitude();
                    lattitude = latti;
                    longitude = longi;
                } else if (location1 != null) {
                    latti = location1.getLatitude();
                    longi = location1.getLongitude();
                    lattitude = latti;
                    longitude = longi;
                } else if (location2 != null) {
                    latti = location2.getLatitude();
                    longi = location2.getLongitude();
                    lattitude = latti;
                    longitude = longi;
                } else {
                    latti = -1;
                    longi = -1;
                    longitude = -1;
                    lattitude = -1;
                }
            }
        }

    }

    public void geoAddress() {
        Geocoder geocoder = new Geocoder(this.context, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(lattitude, longitude, 1);
            this.address = addresses.get(0).getAddressLine(0);
            this.city = addresses.get(0).getLocality();
            this.state = addresses.get(0).getAdminArea();
            this.country = addresses.get(0).getCountryName();
            this.postalCode = addresses.get(0).getPostalCode();
            this.knownName = addresses.get(0).getFeatureName();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public String getAddress() {
        return this.address;
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public String getKnownName() {
        return this.knownName;
    }

    public double getLattitude() {
        return lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public String getState() {
        return this.state;
    }


}