package com.mobile.util.app;

public class Application extends android.app.Application {
    private static Application application;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Application getApp() {
        return application;
    }

}
