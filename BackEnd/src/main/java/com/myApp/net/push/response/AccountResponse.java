package com.myApp.net.push.response;

import com.google.gson.annotations.Expose;
import com.myApp.net.push.db.entity.User;
import com.myApp.net.push.identity.UserIdentity;
import com.mysql.cj.util.StringUtils;

/**
 * 和账户有关的返回
 */
public class AccountResponse {
    @Expose
    private UserIdentity userIdentity;
    @Expose
    private String token;
    @Expose
    private boolean isBindService; // 是否绑定手机了

    public AccountResponse(User user) {
        this.userIdentity = new UserIdentity(user);
        this.token = user.getToken();
        // 只要不为空就是绑定手机了
        this.isBindService = !StringUtils.isNullOrEmpty(user.getPushId());
    }

    public UserIdentity getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(UserIdentity userIdentity) {
        this.userIdentity = userIdentity;
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
}
