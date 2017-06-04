package com.example.andras.myapplication.dagger2.ui.common;

import android.app.Activity;
import android.content.Intent;

import com.example.andras.myapplication.dagger2.ui.feature1.detail.Feature1DetailActivity;

/**
 * Created by Andras Nemeth on 2017. 06. 04..
 */

public class Navigator {

    private Activity activity;

    public Navigator(Activity activity) {
        this.activity = activity;
    }

    public void goToFeature1DetailScreen() {
        activity.startActivity(new Intent(activity, Feature1DetailActivity.class));
    }
}
