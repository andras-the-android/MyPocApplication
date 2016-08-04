package com.example.andras.myapplication.dagger.scope;

import com.example.andras.myapplication.Log;
import com.example.andras.myapplication.dagger.DaggerPocComponent;

/**
 * Created by Andras_Nemeth on 2016. 07. 29..
 */
public class DaggerScopedPoc {

    private static final String TAG = "DaggerScopedPoc";

    private DaggerScopeComponent component;
    private DaggerPocComponent parentComponent;

    public DaggerScopedPoc(DaggerPocComponent parentComponent) {
        this.parentComponent = parentComponent;

        DaggerScopedDependency dependency;

        initComponentWithId(123);
        dependency = this.component.getScopedDependency();
        Log.d(TAG, "Scoped id in first enter to scope: " + dependency.getIdentifier());

        Log.d(TAG, "Second provided dependency is the same as the first: " + (dependency == this.component.getScopedDependency()));

        initComponentWithId(666);
        dependency = this.component.getScopedDependency();
        Log.d(TAG, "Scoped id in second enter to scope: " + dependency.getIdentifier());

        //Log.d(TAG, "Value from parent: " + component.);
    }

    private void initComponentWithId(int id) {
        component = DaggerDaggerScopeComponent.builder().daggerScopePocModule(new DaggerScopePocModule(id)).build();
    }
}
