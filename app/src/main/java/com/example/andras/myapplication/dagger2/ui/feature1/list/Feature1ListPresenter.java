package com.example.andras.myapplication.dagger2.ui.feature1.list;

import android.os.Handler;

import com.example.andras.myapplication.dagger2.interactor.Feature1Interactor;
import com.example.andras.myapplication.dagger2.ui.common.Navigator;

/**
 * Created by Andras Nemeth on 2017. 06. 04..
 */

public class Feature1ListPresenter {

    private Feature1Interactor interactor;
    private Navigator navigator;

    public Feature1ListPresenter(Feature1Interactor interactor, Navigator navigator) {
        this.interactor = interactor;
        this.navigator = navigator;
    }

    void setView(Feature1ListActivity view) {
        view.display(interactor.getFeature1Stuff());
        new Handler().postDelayed(() -> navigator.goToFeature1DetailScreen(), 2000);
    }
}
