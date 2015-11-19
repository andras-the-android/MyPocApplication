package com.example.andras.myapplication.ormlite;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;

/**
 * Created by Andras_Nemeth on 2015.11.09..
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    public static void main(String[] args) throws Exception {
        writeConfigFile("rawormlite_config.txt", new Class[] {SimpleData.class});
        System.out.println(new File(".").getAbsolutePath());
    }
}
