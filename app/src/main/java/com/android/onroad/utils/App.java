package com.android.onroad.utils;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    public static Context context;
    public static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        app=this;
    }
}
