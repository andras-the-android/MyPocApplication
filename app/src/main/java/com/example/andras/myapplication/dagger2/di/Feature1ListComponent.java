package com.example.andras.myapplication.dagger2.di;

import android.app.Activity;

import com.example.andras.myapplication.dagger2.ui.feature1.list.Feature1ListActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Andras Nemeth on 2017. 06. 05..
 */

@Component(modules = Feature1Module.class)
@Singleton
public interface Feature1ListComponent {

    void inject(Feature1ListActivity feature1ListActivity);

    final class Get {
        private Get(){}

        static Feature1ListComponent component;

        public static Feature1ListComponent component(Activity activity) {
            if (component == null) {
                component = DaggerFeature1ListComponent.builder()
                        .networkModule(new NetworkModule())
                        .commonModule(new CommonModule(activity))
                        .feature1Module(new Feature1Module())
                        .interactorModule(new InteractorModule())
                        .build();
            }
            return component;
        }

    }
}
