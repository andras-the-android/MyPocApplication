package com.example.andras.myapplication.sniplets;

import android.graphics.Point;
import android.location.Location;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Andras on 2015.08.20..
 */
public final class MapUtils {

    private static final String TAG = "MapUtils";
    public static final int GOOGLE_MAPS_DEFAULT_ZOOM_LEVEL = 14;
    private static final LatLng LAT_LNG_SLOVAKIA = new LatLng(48.7158451d,19.7339155d);
    private static final float ZOOM_SLOVAKIA = 6.5f;

    private MapUtils() {
    }

    public static Marker setMarkerPosition(GoogleMap map, Marker marker, LatLng latLng) {
        return setMarkerPosition(map, marker, latLng, GOOGLE_MAPS_DEFAULT_ZOOM_LEVEL);
    }

    public static Marker setMarkerPosition(GoogleMap map, Marker marker, LatLng latLng, float zoomLevel) {
        if (marker == null) {
            marker = map.addMarker(new MarkerOptions().position(latLng));
        } else {
            marker.setPosition(latLng);
            //TODO sadly the animation collides with zooming and the result is a bit misplaced marker.
            //It would be good to find a solution because this way it works properly but looks poor
            //animateMarker(map, marker, latLng, false);
        }
        zoomToLocation(map, convertToGoogleLocation(latLng.latitude, latLng.longitude), zoomLevel);
        return marker;
    }

    public static Location convertToGoogleLocation(double latitude, double longitude) {
        Location googleLocation = new Location("current");
        googleLocation.setLatitude(latitude);
        googleLocation.setLongitude(longitude);
        googleLocation.setTime(System.currentTimeMillis());
        return googleLocation;
    }

    public static void animateMarker(final GoogleMap map, final Marker marker, final LatLng toPosition,
                                     final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = map.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }

    public static void zoomToLocation(GoogleMap map, android.location.Location location) {
        zoomToLocation(map, location, GOOGLE_MAPS_DEFAULT_ZOOM_LEVEL);
    }

    public static void zoomToLocation(GoogleMap map, android.location.Location location, float zoomLevel) {
        if (location != null) {
            float currentZoomLevel = map.getCameraPosition().zoom;
            float newZoomLevel = currentZoomLevel < zoomLevel ? zoomLevel : currentZoomLevel;
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, newZoomLevel));
        }
    }

    public static void zoomToSlovakia(GoogleMap map) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(LAT_LNG_SLOVAKIA, ZOOM_SLOVAKIA));
    }

    public static void logZoomLevel(GoogleMap map) {
        map.setOnCameraMoveListener(() -> Log.d(TAG, "Camera zoom level: " + map.getCameraPosition().zoom));
    }
}
