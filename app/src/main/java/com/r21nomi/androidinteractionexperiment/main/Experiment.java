package com.r21nomi.androidinteractionexperiment.main;

/**
 * Created by r21nomi on 2016/12/11.
 */

public class Experiment {

    private String title;
    private Class aClass;

    public Experiment(String title, Class aClass) {
        this.title = title;
        this.aClass = aClass;
    }

    public String getTitle() {
        return title;
    }

    public Class getaClass() {
        return aClass;
    }
}
