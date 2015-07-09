package com.example.andras.myapplication;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends Activity {


    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        // GoogleMap might be null if Google Play services APK is not available.
        if (mapFragment == null) {
            // Try to obtain the map from the SupportMapFragment.
            GoogleMapOptions mapOptions = new GoogleMapOptions();
            mapFragment = MapFragment.newInstance(mapOptions);
            getFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
            mapFragment.getMapAsync(new MyMapReadyCallback());

        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     */
    private void setUpMap() {
        GoogleMap map = mapFragment.getMap();
        LatLng latLng = new LatLng(47.485447d, 19.070975d);
        map.addMarker(new MarkerOptions().position(latLng).title("Marker"));
        map.setIndoorEnabled(false);
        map.setMyLocationEnabled(true);
        //Location location = map.getMyLocation();
//        CameraPosition position = new CameraPosition.Builder().target(new LatLng(location.getLatitude(),location.getLongitude())).build();
        CameraPosition position = new CameraPosition.Builder().target(latLng).zoom(20).build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(position));
    }

    private class MyMapReadyCallback implements OnMapReadyCallback {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            ((TextView)findViewById(R.id.text_view)).setText("Map ready!");
            setUpMap();
        }
    }

    private void doAutoFit() {

    }
}
