package com.example.andras.myapplication;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.andras.myapplication.googlesamples.GoogleFusedLocationExampleActivity;
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

public class LocationActivity extends AppCompatActivity {

    private static final int REQUEST_CHECK_SETTINGS = 666;
    private GoogleApiClient googleApiClient;
    private TextView twStatus;
    private TextView twLatitude;
    private TextView twLongitude;
    private LocationRequest locationReq;

    private GoogleApiClient.ConnectionCallbacks apiClientConnectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(Bundle bundle) {
            sendStatus("Connected");
            //we don't wait for the dialog result, but we can put this invocation tho the
            //settings result callback and we can wait/use location only when the user granted
            onLocationRequestFinished();
        }

        @Override
        public void onConnectionSuspended(int i) {
            sendStatus("Suspended: " + i);
        }
    };

//    private GoogleApiClient.OnConnectionFailedListener apiClientConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
//        @Override
//        public void onConnectionFailed(ConnectionResult connectionResult) {
//            sendStatus("Failed: " + connectionResult);
//        }
//    };

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            onLocationObtained(location);
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        twStatus = findViewById(R.id.tw_status);
        twLatitude = findViewById(R.id.tw_latitude);
        twLongitude = findViewById(R.id.tw_longitude);
        //requestTurnOnLocation();
        buildGoogleApiClient();
    }


    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(apiClientConnectionCallbacks)
//                .addOnConnectionFailedListener(apiClientConnectionFailedListener)
                .addApi(LocationServices.API)
                .build();

        sendStatus("Started");

        googleApiClient.connect();
        requestTurnOnLocation();

    }

    public void requestTurnOnLocation(){
        locationReq = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setFastestInterval(3000)
                .setInterval(5000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationReq);
        PendingResult<LocationSettingsResult> result=LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
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
                    status.startResolutionForResult(LocationActivity.this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    Log.e("", e.getMessage(), e);
                }
                break;
            //the user have chosen the the never button earlier, but if the location is turned on, we can use it
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Log.w("", "Unable to get accurate user location.");
                sendStatus("User have chosen the never button earlier");
                break;
        }
    }


    public void onLocationRequestFinished() {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null) {
            onLocationObtained(lastLocation);
        } else {
            sendStatus("Location is not available but requested");
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationReq, locationListener);
        }

    }

    private void onLocationObtained(Location location) {
        twLatitude.setText(String.valueOf(location.getLatitude()));
        twLongitude.setText(String.valueOf(location.getLongitude()));
        sendStatus("Location obtained");
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    public void showGoogleExample(View view) {
        startActivity(new Intent(this, GoogleFusedLocationExampleActivity.class));
    }

    //we can handle the result of the dialog here but more practical to request for location updates anyways
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
//        if (requestCode == REQUEST_CHECK_SETTINGS) {
//            if (resultCode == Activity.RESULT_OK) {
//                onLocationRequestFinished();
//            } else {
//                sendStatus("Location request denied");
//            }
//        }
//    }

    private void sendStatus(String message) {
        twStatus.setText(message);
    }

}
