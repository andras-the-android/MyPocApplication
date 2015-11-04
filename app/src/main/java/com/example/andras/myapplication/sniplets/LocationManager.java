package com.example.andras.myapplication.sniplets;

import android.app.Activity;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

/**
 * This class contains the common elements of obtaining location.
 * Initiates location service, requests user for turn on gps.
 * It provides only one coordinate.
 *
 */
public class LocationManager {

    private static final String TAG = LocationManager.class.getSimpleName();

    private GoogleApiClient googleApiClient;
    private LocationRequest locationReq;
    private Callback callback;
    private Activity activity;

    private GoogleApiClient.ConnectionCallbacks apiClientConnectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(Bundle bundle) {
            sendStatus("Location service connected");
            //we don't wait for the dialog result, but we can put this invocation tho the
            //settings result callback and we can wait/use location only when the user granted
            onLocationRequestFinished();
        }

        @Override
        public void onConnectionSuspended(int i) {
            sendStatus("Location service suspended: " + i);
        }
    };

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (callback != null) {
                callback.onLocationObtained(location);
            }
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    };

    public LocationManager(Activity activity) {
        this.activity = activity;
    }

    public LocationManager start() {
        buildGoogleApiClient();
        return this;
    }

    private synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(apiClientConnectionCallbacks)
                .addApi(LocationServices.API)
                .build();

        sendStatus("Location started");

        googleApiClient.connect();
        requestTurnOnLocation();

    }

    private void requestTurnOnLocation(){
        locationReq = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setFastestInterval(3000)
                .setInterval(5000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationReq);
        PendingResult<LocationSettingsResult> result= LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult callback) {
                onLocationStateCheckResult(callback);
            }
        });
    }

    private void onLocationStateCheckResult(LocationSettingsResult callback) {
        final Status status = callback.getStatus();
        switch (status.getStatusCode()) {
            //location already available - nothing to do
            case LocationSettingsStatusCodes.SUCCESS:
                sendStatus("Location is already turned on");
                break;
            //location turned off throw the dialog
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                try {
                    status.startResolutionForResult(activity, 0);
                } catch (IntentSender.SendIntentException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
                break;
            //the user have chosen the the never button earlier, but if the location is turned on, we can use it
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                sendStatus("User have chosen the never button earlier");
                break;
        }
    }


    private void onLocationRequestFinished() {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null) {
            if (callback != null) {
                callback.onLocationObtained(lastLocation);
            }
        } else {
            sendStatus("Location is not available but requested");
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationReq, locationListener);
        }

    }

    private void sendStatus(String message) {
        Log.d(TAG, message);
    }

    /**
     * Call this from the onStart method of your activity/fragment
     */
    public void onStart() {
        if (googleApiClient != null && !googleApiClient.isConnected()) {
            googleApiClient.connect();
        }
    }

    /**
     * Call this from the onStop method of your activity/fragment
     */
    public void onStop() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    public LocationManager setCallback(Callback callback) {
        this.callback = callback;
        return this;
    }

    public interface Callback {

        void onLocationObtained(Location location);
    }
}
