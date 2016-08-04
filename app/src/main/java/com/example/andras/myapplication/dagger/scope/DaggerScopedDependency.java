package com.example.andras.myapplication.dagger.scope;

import com.example.andras.myapplication.dagger.Dependency1;

/**
 * Created by Andras_Nemeth on 2016. 07. 29..
 */
public class DaggerScopedDependency {

    private Dependency1 dependency1;
    private int id;

    public DaggerScopedDependency(Dependency1 dependency1, int id) {
        this.dependency1 = dependency1;
        this.id = id;
    }

    public String getIdentifier() {
        return dependency1.getStart() + " - " + id;
    }
}
