package com.myApp.net.push.model.account;

import com.google.gson.annotations.Expose;
import com.mysql.cj.util.StringUtils;

/**
 * 登录传递进来的model
 */
public class LoginModel {
    @Expose
    private String accountNo;
    @Expose
    private String pwd;
    @Expose
    private String pushId; // 绑定设备，可以没有

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public LoginModel(String accountNo, String pwd) {
        this.accountNo = accountNo;
        this.pwd = pwd;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public static boolean isValid(LoginModel loginModel) {
        return loginModel != null
                && !StringUtils.isNullOrEmpty(loginModel.getAccountNo())
                && !StringUtils.isNullOrEmpty(loginModel.getPwd());
    }

}
