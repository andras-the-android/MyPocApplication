package com.example.andras.myapplication.architecture;

import android.arch.lifecycle.LiveData;

/**
 * Created by Andras_Nemeth on 2017. 08. 06..
 */

public class ArchitectureLiveData extends LiveData<String> {

    private SomeDataProducerService service;

    public ArchitectureLiveData(SomeDataProducerService service) {
        this.service = service;
        service.setCallback(this::onDataReceived);
    }

    private void onDataReceived(String s) {
        setValue(s);
    }

    @Override
    protected void onActive() {
        service.start();
    }

    @Override
    protected void onInactive() {
        service.stop();
    }
}
