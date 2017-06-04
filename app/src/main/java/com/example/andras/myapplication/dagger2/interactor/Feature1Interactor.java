package com.example.andras.myapplication.dagger2.interactor;

import com.example.andras.myapplication.dagger2.network.NetworkApi1;

/**
 * Created by Andras Nemeth on 2017. 06. 04..
 */

public class Feature1Interactor {

    private NetworkApi1 networkApi1;

    public Feature1Interactor(NetworkApi1 networkApi1) {
        this.networkApi1 = networkApi1;
        System.out.println("ddd Feature1Interactor created " + this);
    }

    public String getFeature1Stuff() {
        return "##" + networkApi1.getRequest() + "feature1 interactor: " + this + "##";
    }
}
