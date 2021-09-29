package com.mobile.util.helper.account;

import com.mobile.util.R;
import com.mobile.util.data.DataSource;
import com.mobile.util.model.api.RegisterModel;
import com.mobile.util.model.db.User;

/**
 * 账户进行网络请求的类
 */
public class AccountHelper {
    /**
     * 封装注册请求
     * @param registerModel 一个请求model
     * @param callback 处理请求结果的回调
     */
    public static void register(RegisterModel registerModel, DataSource.Callback<User> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    callback.onFail(R.string.data_network_error);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
