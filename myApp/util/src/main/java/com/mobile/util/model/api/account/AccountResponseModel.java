package com.mobile.util.model.api.account;

import com.mobile.util.model.db.entity.User;

/**
 * 接口返回账户信息的映射 !! 名字一定要一致
 */
public class AccountResponseModel {
    private User userIdentity;
    private String token;
    private boolean isBindService; // 是否绑定手机了

    public User getUser() {
        return userIdentity;
    }

    public void setUser(User user) {
        this.userIdentity = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isBindService() {
        return isBindService;
    }

    public void setBindService(boolean bindService) {
        isBindService = bindService;
    }

    @Override
    public String toString() {
        return "AccountResponseModel{" +
                "user=" + userIdentity +
                ", token='" + token + '\'' +
                ", isBindService=" + isBindService +
                '}';
    }
}
