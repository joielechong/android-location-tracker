package com.rilixtech.simplelocationtracker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;

import com.rilixtech.locationtracker.LocationTracker;
import com.rilixtech.locationtracker.TrackerSettings;

public class MainActivity extends AppCompatActivity {
    LocationTracker tracker;
    private AppCompatTextView mTvValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvValue = findViewById(R.id.value_tv);

        TrackerSettings settings =
                new TrackerSettings()
                        .setUseGPS(true)
                        .setUseNetwork(true)
                        .setUsePassive(true)
                        .setTimeBetweenUpdates(1 * 60 * 1000)
                        .setMetersBetweenUpdates(100);

        tracker = new LocationTracker(this, settings) {

            @Override
            public void onLocationFound(Location location) {
                // Do some stuff when a new location has been found.
                mTvValue.setText(location.getLatitude() + ", " + location.getLongitude());
            }

            @Override
            public void onTimeout() {

            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // You need to ask the user to enable the permissions
        } else {

            tracker.startListening();
        }
    }

    @Override
    protected void onPause() {
        if(tracker != null) {
            tracker.stopListening();
        }
        super.onPause();
    }
}
