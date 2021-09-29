package com.mobile.util.helper.account;

import com.mobile.util.model.api.RegisterModel;
import com.mobile.util.model.db.User;

/**
 * 账户进行网络请求的类
 */
public class AccountHelper {
    /**
     * callback接口，客户端成功接收到数据后做回调
     */
    interface Callback {
        /**
         * 成功时传进来一个user
         *
         * @param user
         */
        void onSuccess(User user);
    }

    /**
     * 封装注册请求，传进来一个请求model
     *
     * @param registerModel
     */
    public static void register(RegisterModel registerModel, Callback callback) {

    }
}
