package com.example.andras.myapplication.dagger;

import com.example.andras.myapplication.Log;

import javax.inject.Inject;

/**
 * Created by Andras_Nemeth on 2016. 07. 27..
 */
public class DaggerPoc {

    private static final String TAG = "DaggerPoc";

    @Inject
    ClassToInject classToInject;

    public DaggerPoc() {
        DaggerPocComponent component = DaggerDaggerPocComponent.builder()
                .daggerPocModule(new DaggerPocModule()).build();
        component.injectMainActivity(this);
        Dependency1 dependency1 = component.getDeopendecy1();
        Log.d(TAG, "Dagger test: " + dependency1.getStart());
        Log.d(TAG, "Dagger test: " + classToInject.getSentence());
    }
}
