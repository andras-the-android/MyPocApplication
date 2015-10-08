package com.example.andras.myapplication.sniplets;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Andras_Nemeth on 2015.10.05..
 */
public class SystemPermissionHelper {

    private static final String TAG = SystemPermissionHelper.class.getSimpleName();

    private final Activity activity;

    private SparseArray<Callback> callbackMap = new SparseArray<>();
    private volatile int requestCounter;

    public SystemPermissionHelper(Activity activity) {
        this.activity = activity;
    }

    public void requestPermission(Callback callback, String... requestedPermissions) {
        requestCounter++;
        Log.d(TAG, "Permissions requested: " + Arrays.toString(requestedPermissions));

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            sendAllPermissionGranted(callback);
        } else {
            List<String> permissionsToGrant = collectPermissionsNeedGrant(requestedPermissions);
            if (permissionsToGrant.isEmpty()) {
                sendAllPermissionGranted(callback);
            } else {
                requestPermissionsAsync(callback, permissionsToGrant);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private List<String> collectPermissionsNeedGrant(String[] requestedPermissions) {
        List<String> permissionsToGrant = new ArrayList<>();
        for (String requestedPermission : requestedPermissions) {
            if (activity.checkSelfPermission(requestedPermission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToGrant.add(requestedPermission);
            }
        }
        return permissionsToGrant;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissionsAsync(Callback callback, List<String> permissionsToGrant) {
        callbackMap.put(requestCounter, callback);
        activity.requestPermissions(permissionsToGrant.toArray(new String[permissionsToGrant.size()]), requestCounter);
    }

    private void sendAllPermissionGranted(Callback callback) {
        Log.d(TAG, "All permissions granted");
        callback.onAllPermissionGranted();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Set<String> deniedPermissions = collectDeniedPermissions(permissions, grantResults);
        Log.d(TAG, "Permissions denied: " + deniedPermissions);

        Callback callback = callbackMap.get(requestCode);
        if (deniedPermissions.isEmpty()) {
            callback.onAllPermissionGranted();
        } else {
            callback.onPermissionsDenied(deniedPermissions);
        }
        callbackMap.delete(requestCode);
    }

    private Set<String> collectDeniedPermissions(String[] permissions, int[] grantResults) {
        Set<String> deniedPermissions = new HashSet<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permissions[i]);
            }
        }
        return deniedPermissions;
    }

    /**
     * First you have to request permission from an activity. Later before you
     * would start use the desired resources, you can check whether they are granted or not.
     * Use this without {#SystemPermissionHelper} instance.
     * @param context
     * @param permission
     * @return
     */
    public static boolean hasPermission(Context context, String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        } else {
            return context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }
    }

    /**
     * First you have to request permission from an activity. Later before you
     * would start use the desired resources, you can check whether they are granted or not.
     * Use this with {#SystemPermissionHelper} instance.
     * @param permission
     * @return
     */
    public boolean hasPermission(String permission) {
        return hasPermission(activity, permission);
    }

    /**
     * Callback interface for delivering {@link #requestPermission(Callback, String...)} result.
     */
    interface Callback {

        /**
         * Called if all requested permission has been granted.
         */
        void onAllPermissionGranted();

        /**
         * Called if not all permissions has been granted.
         *
         * @param deniedPermissions the set of denied permissions
         */
        void onPermissionsDenied(Set<String> deniedPermissions);

    }
}
