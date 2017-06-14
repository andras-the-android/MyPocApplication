package com.example.andras.myapplication.dagger2.di;

import com.example.andras.myapplication.dagger2.interactor.Feature1Interactor;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Andras_Nemeth on 2017. 06. 14..
 */

@Singleton
@Component(modules = InteractorModule.class)
interface InteractorComponent {

    Feature1Interactor feature1Interactor();

    final class Get {
        private Get(){}

        private static InteractorComponent component;

        public static InteractorComponent component() {
            if (component == null) {
                component = DaggerInteractorComponent.builder()
                        .networkModule(new NetworkModule())
                        .interactorModule(new InteractorModule())
                        .build();
            }
            return component;
        }

    }
}
