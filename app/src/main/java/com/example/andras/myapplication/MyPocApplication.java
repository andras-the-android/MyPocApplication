package com.example.andras.myapplication;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by Andras_Nemeth on 2015.11.03..
 */
public class MyPocApplication extends Application {

    private static final String TRACKING_ID = "UA-69644747-1";

    private Tracker tracker;

    @Override
    public void onCreate() {
        super.onCreate();
        createTracker();
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (tracker == null) {
            createTracker();
        }
        return tracker;
    }

    private void createTracker() {
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        tracker = analytics.newTracker(TRACKING_ID);
        tracker.enableAutoActivityTracking(true);
    }
}
