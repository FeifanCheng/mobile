package com.mobile.util.model.db.entity;

import com.mobile.util.model.db.Database;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

@Table(database = Database.class)
public class User extends BaseModel {
    @PrimaryKey
    private String id;
    @Column
    private String name;
    @Column
    private String portrait;
    @Column
    private String phone;
    @Column
    private String description;
    @Column
    private int sex;
    @Column
    private Date updateAt; // 用户信息更新时间
    @Column
    private int follows; // 关注数
    @Column
    private int followings; // 粉丝数
    @Column
    private Boolean isFollow; // 我与当前用户是否关注
    @Column
    private String note;  // 备注信息

    public static final int MALE = 1;
    public static final int FEMALE = 2;

    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", portrait='" + portrait + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                ", sex=" + sex +
                ", updateAt=" + updateAt +
                ", follows=" + follows +
                ", followings=" + followings +
                ", isFollow=" + isFollow +
                ", note='" + note + '\'' +
                '}';
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

    public Boolean isFollow() {
        return isFollow;
    }

    public void setFollow(Boolean follow) {
        isFollow = follow;
    }
}
