package com.myApp.net.push.identity;

import com.google.gson.annotations.Expose;
import com.myApp.net.push.db.entity.User;

import java.time.LocalDateTime;

/**
 * 封装用户身份信息，和原视频card对应
 */
public class UserIdentity {
    @Expose
    private String id;
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
    private LocalDateTime updateAt; // 用户信息更新时间
    @Expose
    private int follows; // 关注数
    @Expose
    private int followings; // 粉丝数
    @Expose
    private Boolean isFollow; // 我与当前用户是否关注

    public UserIdentity(User user){
        this(user, false);
    }


    public UserIdentity(User user, boolean isFollow) {
        this.id = user.getId();
        this.description = user.getDescription();
        this.isFollow = isFollow;
        this.name = user.getName();
        this.phone = user.getPhone();
        this.sex = user.getSex();
        this.portrait = user.getPortrait();
        this.updateAt = user.getUpdateAt();
        // TODO：需要改
        this.followings = 0;
        this.follows = 0;

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

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
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

    public Boolean getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(Boolean isFollow) {
        this.isFollow = isFollow;
    }
}
