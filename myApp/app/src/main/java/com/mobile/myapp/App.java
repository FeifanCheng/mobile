package com.mobile.myapp;

import android.app.Application;

import com.mobile.factory.Factory;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 在app加载的时候就初始化数据
        Factory.initial();
    }
}
