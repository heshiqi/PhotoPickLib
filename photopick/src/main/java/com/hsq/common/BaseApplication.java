package com.hsq.common;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by hsq on 2016/5/21.
 */
public class BaseApplication extends Application {

    private static BaseApplication application;
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        application=this;
    }

    public static BaseApplication getApplication() {
        return application;
    }
}
