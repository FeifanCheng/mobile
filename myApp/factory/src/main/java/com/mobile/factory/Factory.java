package com.mobile.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobile.util.app.Application;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Factory {
    // 单例模式ø
    private static final Factory factory = new Factory();
    // 全局的线程池
    private final Executor executor;
    // 全局的Gson
    private final Gson gson;


    private Factory() {
        executor = Executors.newFixedThreadPool(4);
        gson = new GsonBuilder()
                // 设置时间格式
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
    }

    public static Factory getFactory(){
        return factory;
    }


    /**
     * 返回全局的Application
     *
     * @return Application
     */
    public static Application app() {
        return Application.getApp();
    }


    /**
     * 异步运行的方法
     *
     * @param runnable Runnable
     */
    public static void runOnAsync(Runnable runnable) {
        // 拿到单例，拿到线程池，然后异步执行
        factory.executor.execute(runnable);
    }

    /**
     * 返回一个全局的Gson
     *
     * @return Gson
     */
    public static Gson getGson() {
        return factory.gson;
    }



}

