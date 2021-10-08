package com.mobile.util.model.api.account;

import com.google.gson.annotations.Expose;

/**
 * 登录请求的封装
 */
public class LoginModel {
    @Expose
    private String accountNo;  // 电话
    @Expose
    private String pwd;  // 密码
    @Expose
    private String pushId; // 绑定设备

    public LoginModel(String accountNo, String pwd) {
        this(accountNo, pwd, null);
    }

    public LoginModel(String accountNo, String pwd, String pushId) {
        this.accountNo = accountNo;
        this.pwd = pwd;
        this.pushId = pushId;
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

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
