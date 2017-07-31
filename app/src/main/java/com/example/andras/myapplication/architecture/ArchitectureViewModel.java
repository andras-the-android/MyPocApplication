package com.example.andras.myapplication.architecture;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;

/**
 * Created by Andras_Nemeth on 2017. 07. 31..
 */

public class ArchitectureViewModel extends ViewModel implements LifecycleObserver {

    private SomeDataProducerService service;
    private ArchitectureActivity view;

    public ArchitectureViewModel(SomeDataProducerService service) {
        this.service = service;
        service.setCallback(this::onDataReceived);
    }

    public void setView(ArchitectureActivity view) {
        this.view = view;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart() {
        service.start();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop() {
        service.stop();
    }

    private void onDataReceived(String s) {
        view.displayText(s);
    }
}
