package com.example.andras.myapplication;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andras.myapplication.googlesamples.GoogleFusedLocationExampleActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

public class LocationActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_CHECK_SETTINGS = 666;
    private GoogleApiClient mGoogleApiClient;
    private TextView twStatus;
    private TextView twLatitude;
    private TextView twLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        twStatus = (TextView) findViewById(R.id.tw_status);
        twLatitude = (TextView) findViewById(R.id.tw_latitude);
        twLongitude = (TextView) findViewById(R.id.tw_longitude);
        //requestTurnOnLocation();
        buildGoogleApiClient();
    }

    private void requestTurnOnLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        new LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        twStatus.setText("Started");

        //use this for only one location data (this may deliver null when no location available)
        //mGoogleApiClient.connect();
        doRun();

    }

    public void doRun(){
        final LocationRequest locationReq=LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setFastestInterval(3000).setInterval(5000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationReq);
        PendingResult<LocationSettingsResult> result=LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                                     @Override
                                     public void onResult(LocationSettingsResult callback) {
                                         final Status status = callback.getStatus();
                                         switch (status.getStatusCode()) {
                                             case LocationSettingsStatusCodes.SUCCESS:
                                                 //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationReq, GoogleMapFragment.this);
                                                 onConnected(null);
                                                 break;
                                             case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                                 try {
                                                     status.startResolutionForResult(LocationActivity.this, REQUEST_CHECK_SETTINGS);
                                                 } catch (IntentSender.SendIntentException e) {
                                                     Log.e("", e.getMessage(), e);
                                                 }
                                                 break;
                                             case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                                 Log.w("", "Unable to get accurate user location.");
                                                 Toast.makeText(LocationActivity.this, "Unable to get accurate location. Please update your " + "location settings!", Toast.LENGTH_LONG).show();
                                                 break;
                                         }
                                     }
                                 }

        );
    }


    @Override
    public void onConnected(Bundle bundle) {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (lastLocation != null) {
            twLatitude.setText(String.valueOf(lastLocation.getLatitude()));
            twLongitude.setText(String.valueOf(lastLocation.getLongitude()));
        } else {
            twStatus.setText("Connected but last location is null");
        }
        twStatus.setText("Connected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        twStatus.setText("Suspended: " + i);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        twStatus.setText("Failed: " + connectionResult);
    }

    public void showGoogleExample(View view) {
        startActivity(new Intent(this, GoogleFusedLocationExampleActivity.class));
    }

    //*****************************************************************************

//    private void adesfswefvc() {
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                .addLocationRequest(mLocationRequest);
//        builder.setAlwaysShow(true);
//
//        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
//
//        result.setResultCallback(new ResultCallback<LocationSettingsResult>()
//        {
//            @Override
//            public void onResult(LocationSettingsResult result)
//            {
//                final Status status = result.getStatus();
//                final LocationSettingsStates locationSettingsStates = result.getLocationSettingsStates();
//                switch (status.getStatusCode())
//                {
//                    case LocationSettingsStatusCodes.SUCCESS:
//                        // All location settings are satisfied. The client can initialize location
//                        // requests here.
//                        ...
//                        Log.d("onResult", "SUCCESS");
//                        break;
//                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        // Location settings are not satisfied. But could be fixed by showing the user
//                        // a dialog.
//                        Log.d("onResult", "RESOLUTION_REQUIRED");
//                        try
//                        {
//                            // Show the dialog by calling startResolutionForResult(),
//                            // and check the result in onActivityResult().
//                            status.startResolutionForResult(OuterClass.this, REQUEST_LOCATION);
//                        }
//                        catch (IntentSender.SendIntentException e)
//                        {
//                            // Ignore the error.
//                        }
//                        break;
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        // Location settings are not satisfied. However, we have no way to fix the
//                        // settings so we won't show the dialog.
//                        ...
//                        Log.d("onResult", "UNAVAILABLE");
//                        break;
//                }
//            }
//        });
//    }
//
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        // This log is never called
//        Log.d("onActivityResult()", Integer.toString(resultCode));
//
//        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
//        switch (requestCode)
//        {
//            case REQUEST_LOCATION:
//                switch (resultCode)
//                {
//                    case Activity.RESULT_OK:
//                    {
//                        // All required changes were successfully made
//                        break;
//                    }
//                    case Activity.RESULT_CANCELED:
//                    {
//                        // The user was asked to change settings, but chose not to
//                        break;
//                    }
//                    default:
//                    {
//                        break;
//                    }
//                }
//                break;
//        }
//    }
}
