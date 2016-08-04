package com.example.andras.myapplication.dagger.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Andras_Nemeth on 2016. 07. 29..
 */
@Scope
@Documented
@Retention(value= RetentionPolicy.RUNTIME)
public @interface MyCustomScope {
}
