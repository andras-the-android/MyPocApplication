package com.example.andras.myapplication.databinding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.example.andras.myapplication.BR;

/**
 * Created by Andras_Nemeth on 2016. 06. 22..
 */
public class Switchable extends BaseObservable {

    private boolean turnedOn = false;

    /**
     * @param model We dont need this model here but we can get it, so why not? :)
     */
    public void turnOn(DummyModel model) {
        turnedOn = true;
        notifyPropertyChanged(BR.label);
    }

    public void turnOff(DummyModel model) {
        turnedOn = false;
        notifyPropertyChanged(BR.label);
    }

    public boolean isOn() {
        return turnedOn;
    }

    @Bindable
    public String getLabel() {
        return turnedOn ? "Turn Off" : "Turn On";
    }
}
