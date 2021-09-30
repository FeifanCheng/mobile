package com.mobile.factory.StaticData;

import android.content.Context;
import android.content.SharedPreferences;

import com.mobile.factory.Factory;
import com.mobile.util.model.api.account.AccountResponseModel;
import com.mobile.util.model.db.entity.User;
import com.raizlabs.android.dbflow.StringUtils;

/**
 * 存储账户持久化数据（token）
 */
public class AccountData {
    private static String token;
    private static String userId;
    private static String userAccount;

    /**
     * 把token持久化
     *
     * @param context
     */
    public static void save(Context context) {
        SharedPreferences sp = context.getSharedPreferences(AccountData.class.getName(), Context.MODE_PRIVATE);
        sp.edit().putString("token", token).apply();
    }

    /**
     * 把user的信息持久化
     *
     * @param accountResponseModel
     */
    public static void save(AccountResponseModel accountResponseModel) {
        AccountData.userId = accountResponseModel.getUser().getId();
        AccountData.userAccount = accountResponseModel.getUser().getPhone();
    }

    /**
     * 加载持久化的token
     *
     * @param context
     * @return 获取token，默认为空
     */
    public static void load(Context context) {
        SharedPreferences sp = context.getSharedPreferences(AccountData.class.getName(), Context.MODE_PRIVATE);
        AccountData.token = sp.getString("token", "");
    }

    /**
     * 如果token不为空，则是已经登录了
     * @return
     */
    public static boolean isLogin() {
        return !StringUtils.isNullOrEmpty(AccountData.token);
    }


    /**
     * 设置并存储token
     * @param token
     */
    public static void setToken(String token) {
        AccountData.token = token;
        AccountData.save(Factory.app());
    }

    /**
     * 获取当前user
     */
    public static User
}
