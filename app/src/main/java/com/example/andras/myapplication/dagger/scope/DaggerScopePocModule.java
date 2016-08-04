package com.example.andras.myapplication.dagger.scope;

import com.example.andras.myapplication.dagger.DaggerPocModule;
import com.example.andras.myapplication.dagger.Dependency1;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Andras_Nemeth on 2016. 07. 29..
 */
@Module(includes = DaggerPocModule.class)
public class DaggerScopePocModule {

    private int id;

    public DaggerScopePocModule(int id) {
        this.id = id;
    }

    @Provides
    DaggerScopedDependency provideDependency(@Named("real")Dependency1 dependency1) {
        return new DaggerScopedDependency(dependency1, id);
    }


}
