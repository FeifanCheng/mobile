package com.myApp.net.push.identity;

import com.google.gson.annotations.Expose;
import com.myApp.net.push.db.entity.GroupMember;

import java.time.LocalDateTime;

/**
 * 群成员Model
 * @author qiujuer Email:qiujuer.live.cn
 */
public class GroupMemberIdentity {
    @Expose
    private String id;// Id
    @Expose
    private String alias;// 别名／备注
    @Expose
    private boolean isAdmin;// 是否是管理员
    @Expose
    private boolean isOwner;// 是否是创建者
    @Expose
    private String userId;// 对于的用户Id
    @Expose
    private String groupId;// 对于的群Id
    @Expose
    private LocalDateTime updateAt;// 最后修改时间

    public GroupMemberIdentity(GroupMember member) {
        this.id = member.getId();
        this.alias = member.getAlias();
        this.isAdmin = member.getPermissionType() == GroupMember.PERMISSION_TYPE_ADMIN;
        this.isOwner = member.getPermissionType() == GroupMember.PERMISSION_TYPE_ADMIN_SU;
        this.userId = member.getUserId();
        this.groupId = member.getGroupId();
        this.updateAt = member.getUpdateAt();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}
