package com.example.andras.myapplication.dagger2.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Andras_Nemeth on 2017. 06. 14..
 */

@Scope
@Documented
@Retention(value= RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
