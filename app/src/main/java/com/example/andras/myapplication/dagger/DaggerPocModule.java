package com.example.andras.myapplication.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Andras_Nemeth on 2016. 07. 27..
 */
@Module
public class DaggerPocModule {

    @Provides
    Dependency1 provideDependency1() {
        return new Dependency1Impl();
    }

    @Provides
    Dependency2 provideDependency2(Dependency2Impl impl) {
        return impl;
    }

    @Provides
    ClassToInject provideClassToInject(Dependency1 dependency1, Dependency2 dependency2) {
        return new ClassToInject(dependency1, dependency2);
    }
}
