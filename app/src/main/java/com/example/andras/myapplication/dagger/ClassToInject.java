package com.example.andras.myapplication.dagger;

import javax.inject.Inject;

/**
 * Created by Andras_Nemeth on 2016. 07. 27..
 */
public class ClassToInject {

    private Dependency1 dependency1;
    private Dependency2 dependency2;

    public ClassToInject(Dependency1 dependency1, Dependency2 dependency2) {
        this.dependency1 = dependency1;
        this.dependency2 = dependency2;
    }

    public String getSentence() {
        return dependency1.getStart() + dependency2.getEnd();
    }
}
