package com.mobile.util.model.db;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class User {
    private String id;
    private String name;
    private String portrait;
    private String phone;
    private String description;
    private int sex = 0;
    private Date updateAt; // 用户信息更新时间
    private int follows; // 关注数
    private int followings; // 粉丝数
    private Boolean isFollow; // 我与当前用户是否关注

    private String note;  // 备注信息

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getFollowings() {
        return followings;
    }

    public void setFollowings(int followings) {
        this.followings = followings;
    }

    public Boolean getFollow() {
        return isFollow;
    }

    public void setFollow(Boolean follow) {
        isFollow = follow;
    }
}