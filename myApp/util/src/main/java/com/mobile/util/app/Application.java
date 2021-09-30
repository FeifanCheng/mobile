package com.mobile.util.app;

import com.mobile.util.StaticData.AccountData;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

public class Application extends android.app.Application {
    private static Application application;

    /**
     * 加载数据库和已经持久化的token !! 不要改
     */
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());
        AccountData.load(this);
    }


    public static Application getApp() {
        return application;
    }

}
