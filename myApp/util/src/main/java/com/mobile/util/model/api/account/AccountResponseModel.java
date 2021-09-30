package com.mobile.util.model.api.account;

import com.mobile.util.model.db.entity.User;

/**
 * 接口返回账户信息的映射
 */
public class AccountResponseModel {
    private User user;
    private String token;
    private boolean isBindService; // 是否绑定手机了

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
                "user=" + user +
                ", token='" + token + '\'' +
                ", isBindService=" + isBindService +
                '}';
    }
}
