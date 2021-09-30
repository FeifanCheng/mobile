package com.mobile.util.model.api.account;

import com.google.gson.annotations.Expose;

/**
 * 注册请求model，和接口对应的参数一致
 */
public class RegisterModel {
    @Expose
    private String accountNo;  // 电话
    @Expose
    private String pwd;  // 密码
    @Expose
    private String name;  // 姓名
    @Expose
    private String pushId; // 绑定设备

    public RegisterModel(String accountNo, String pwd, String name) {
        this(accountNo, pwd, name, null);
    }

    public RegisterModel(String accountNo, String pwd, String name, String pushId) {
        this.accountNo = accountNo;
        this.pwd = pwd;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
