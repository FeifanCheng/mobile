package com.mobile.util.model.api.account;

/**
 * 更新用户信息，不更新名字
 */
public class UserUpdateInfoModel {
    private String name;
    private String portrait;
    private int sex;
    private String description;

    public UserUpdateInfoModel(String name, String portrait, int sex, String description) {
        this.name = name;
        this.portrait = portrait;
        this.sex = sex;
        this.description = description;
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
