package com.myApp.net.push.model.user;

import com.google.gson.annotations.Expose;
import com.mysql.cj.util.StringUtils;

public class UpdateInfoModel {
    @Expose
    private String name;
    @Expose
    private String portrait;
    @Expose
    private String phone;
    @Expose
    private String description;
    @Expose
    private int sex = 0;
    @Expose
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public static boolean isValid(UpdateInfoModel updateInfoModel) {
        if (updateInfoModel != null) {
            return !StringUtils.isNullOrEmpty(updateInfoModel.getDescription())
                    || !StringUtils.isNullOrEmpty(updateInfoModel.getName())
                    || !StringUtils.isNullOrEmpty(updateInfoModel.getPassword())
                    || !StringUtils.isNullOrEmpty(updateInfoModel.getPhone())
                    || !StringUtils.isNullOrEmpty(updateInfoModel.getPortrait())
                    || updateInfoModel.getSex() != 0;
        }
        return false;
    }
}
