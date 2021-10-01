package com.mobile.util.StaticData;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.mobile.util.app.Application;
import com.mobile.util.model.api.account.AccountResponseModel;
import com.mobile.util.model.db.entity.User;
import com.mobile.util.model.db.entity.User_Table;
import com.raizlabs.android.dbflow.StringUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * 存储账户持久化数据
 */
public class AccountData {
    private static String token;
    private static String userId;
    private static String userAccount;
    // TODO: 没做绑定
    private static String pushId;

    public static String getPushId() {
        return pushId;
    }

    public static void setPushId(String pushId) {
        AccountData.pushId = pushId;
    }

    /**
     * 把User数据持久化
     *
     * @param context
     */
    public static void save(Context context) {
        SharedPreferences sp = context.getSharedPreferences(AccountData.class.getName(), Context.MODE_PRIVATE);
        sp.edit().putString("token", token).apply();
        sp.edit().putString("userId", userId).apply();
    }

    /**
     * 把user的信息持久化
     *
     * @param accountResponseModel
     */
    public static void save(AccountResponseModel accountResponseModel) {
        AccountData.userId = accountResponseModel.getUser().getId();
        AccountData.userAccount = accountResponseModel.getUser().getPhone();
        setToken(accountResponseModel.getToken());
    }

    /**
     * 加载持久化的逆袭
     *
     * @param context
     * @return 获取token和userid，默认为空
     */
    public static void load(Context context) {
        SharedPreferences sp = context.getSharedPreferences(AccountData.class.getName(), Context.MODE_PRIVATE);
        AccountData.token = sp.getString("token", "");
        AccountData.userId = sp.getString("userId", "");
    }

    public static String getToken() {
        return token;
    }

    /**
     * 如果token不为空，则是已经登录了
     *
     * @return
     */
    public static boolean isLogin() {
        return !StringUtils.isNullOrEmpty(AccountData.token);
    }


    /**
     * 设置并存储token
     *
     * @param token
     */
    public static void setToken(String token) {
        AccountData.token = token;
        AccountData.save(Application.getApp());
    }

    /**
     * 获取当前user
     */
    public static User getUser() {
        User user = new User();
        if (!StringUtils.isNullOrEmpty(userId)) {
            user = SQLite.select().from(User.class).where(User_Table.id.eq(userId)).querySingle();
        }
        return user;
    }

    /**
     * 判断用户信息是否完善
     *
     * @return
     */
    public static boolean userInfoFinished() {
        User user = getUser();
        if (user != null) {
            if(user.getId() != null && user.getDescription() != null){
                Log.e("User", user.getId());
                Log.e("User", user.getDescription());
            }
            return !StringUtils.isNullOrEmpty(user.getDescription())
                    && user.getSex() != 0
                    && !StringUtils.isNullOrEmpty(user.getPortrait());
        }
        return false;
    }
}
