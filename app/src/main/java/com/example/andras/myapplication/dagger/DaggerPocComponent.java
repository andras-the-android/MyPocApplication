package com.example.andras.myapplication.dagger;

import com.example.andras.myapplication.MainActivity;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Andras_Nemeth on 2016. 07. 27..
 */
@Component(modules = DaggerPocModule.class)
@Singleton
public interface DaggerPocComponent {

    @Named("real")
    Dependency1 getRealDependecy1();

    @Named("fake")
    Dependency1 getFakeDependecy1();

    void injectMainActivity(DaggerPoc daggerPoc);
}
