package com.myApp.net.push.model.account;

import com.google.gson.annotations.Expose;

public class RegisterModel {
    @Expose
    private String name;
    @Expose
    private String pwd;
    @Expose
    private String accountNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
}
