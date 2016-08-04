package com.example.andras.myapplication.dagger.scope;

import com.example.andras.myapplication.dagger.DaggerPocComponent;
import com.example.andras.myapplication.dagger.DaggerPocModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Andras_Nemeth on 2016. 07. 29..
 */
@Component(modules = {DaggerScopePocModule.class, DaggerPocModule.class})
@Singleton
public interface DaggerScopeComponent {

    DaggerScopedDependency getScopedDependency();
}
