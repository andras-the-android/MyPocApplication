package com.example.andras.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andras.myapplication.sniplets.MapUtils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends AppCompatActivity {

    private static final String TAG = "MapsActivity";
    private MapFragment mapFragment;

    @BindView(R.id.map_layout)
    LinearLayout layout;
    public static final int PLACE_PICKER_REQUEST = 1;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
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
     * call {@link #setUpMap(GoogleMap)} once when {@link #mMap} is not null.
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
            //set different option to mapOptions
            mapFragment = MapFragment.newInstance(mapOptions);
            getFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
            mapFragment.getMapAsync(new MyMapReadyCallback());

        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * @param googleMap
     */
    private void setUpMap(GoogleMap googleMap) {
        map = googleMap;
        LatLng latLng = new LatLng(47.485447d, 19.070975d);
        Marker marker = map.addMarker(new MarkerOptions().position(latLng).title("Marker"));
        map.setIndoorEnabled(false);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }
        map.setMyLocationEnabled(true);
        //Location location = map.getMyLocation();
//        CameraPosition position = new CameraPosition.Builder().target(new LatLng(location.getLatitude(),location.getLongitude())).build();
        CameraPosition position = new CameraPosition.Builder().target(latLng).zoom(20).build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(position));
//        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 2f);
//                valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                        layout.setWeightSum((Float) animation.getAnimatedValue());
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        layout.requestLayout();
//                    }
//                });
//                valueAnimator.start();
//            }
//        });

        //Markers
        IconGenerator iconGenerator = new IconGenerator(this);
        iconGenerator.setBackground(ContextCompat.getDrawable(this, R.drawable.orszag_cluster));
        //iconGenerator.setStyle(IconGenerator.STYLE_RED);
        iconGenerator.setTextAppearance(R.style.mapMarkerIconText);
        Bitmap bmp = iconGenerator.makeIcon("66666");
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(bmp));
        //marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.mentve_pin2));
    }

    private class MyMapReadyCallback implements OnMapReadyCallback {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            ((TextView)findViewById(R.id.text_view)).setText("Map ready!");
            setUpMap(googleMap);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                handlePlacePickerResult(data);
            }
        }
    }


    @OnClick(R.id.button)
    public void onLabelClick(View view) {
        openGooglePlacePicker();
    }

    /**
     * This demands an api key and "Google places api for Android" enabled.
     */
    private void openGooglePlacePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            android.util.Log.e(TAG, e.getMessage(), e);
        }
    }

    private void handlePlacePickerResult(Intent data) {
        if (map != null) {
            Place place = PlacePicker.getPlace(this, data);
            String titleMsg = String.format("Place: %s", place.getName());
            Toast.makeText(this, titleMsg, Toast.LENGTH_LONG).show();
            LatLng latLng = place.getLatLng();
            MapUtils.zoomToLocation(map, MapUtils.convertToGoogleLocation(latLng.latitude, latLng.longitude));
            map.addMarker(new MarkerOptions().position(latLng).title(titleMsg));
        }
    }

}
