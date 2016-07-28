package com.example.andras.myapplication.dagger;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Andras_Nemeth on 2016. 07. 27..
 */
@Module
public class DaggerPocModule {

    @Provides @Named("real") @Singleton
    Dependency1 provideDependency1() {
        return new Dependency1Impl();
    }

    @Provides @Named("fake")
    Dependency1 provideFakeDependency1() {
        return new Dependency1FakeImpl();
    }

    @Provides
    Dependency2 provideDependency2(Dependency2Impl impl) {
        return impl;
    }

    @Provides
    ClassToInject provideClassToInject(@Named("real") Dependency1 dependency1, Dependency2 dependency2) {
        return new ClassToInject(dependency1, dependency2);
    }
}
