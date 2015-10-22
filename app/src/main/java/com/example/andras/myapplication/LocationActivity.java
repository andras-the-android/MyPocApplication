package com.example.andras.myapplication;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.andras.myapplication.googlesamples.GoogleFusedLocationExampleActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class LocationActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

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
        buildGoogleApiClient();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        //use this for only one location data (this may deliver null when no location available)
        //mGoogleApiClient.connect();
        twStatus.setText("Started");

    }


    @Override
    public void onConnected(Bundle bundle) {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (lastLocation != null) {
            twLatitude.setText(String.valueOf(lastLocation.getLatitude()));
            twLongitude.setText(String.valueOf(lastLocation.getLongitude()));
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
}
