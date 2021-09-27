package com.myApp.net.push.db.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * �û���ϵ
 * User relations
 *
 * @author Group T01/01-5
 * @version 1
 */
@Entity
@Table(name = "TB_USER_FOLLOW")
public class UserFollow {

    @Id
    @PrimaryKeyJoinColumn
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(updatable = false, nullable = false)
    private String id;


    // Many to one -> You can follow many people, each time you follow is a record
    // You can create many following messages, all of which are many-to-one; The many-to-one here is: User corresponds to multiple UserFollow
    // optional is not optional, it must be stored, there must be a "you" in a follower record
    @ManyToOne(optional = false)
    @JoinColumn(name = "originId")
    private User origin;
    // Extract this column into our Model, it is not allowed to be null, not allowed to update, insert
    @Column(nullable = false, updatable = false, insertable = false)
    private String originId;

    // Define the goal of the focus, the people you follow
    // It is also many-to-one, you can be followed by many people, and each follow is a record
    // All is the situation where multiple UserFollow corresponds to one User
    @ManyToOne(optional = false)
    @JoinColumn(name = "targetId")
    private User target;
    // Extract this column to our Model, it is not allowed to be null, not allowed to update, insert
    @Column(nullable = false, updatable = false, insertable = false)
    private String targetId;

    // ����(���п���)
    @Column
    private String alias;

    // Defined as the creation timestamp, which has been written at the time of creation
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    // Defined as an update timestamp, which has been written when it is created
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getOrigin() {
        return origin;
    }

    public void setOrigin(User origin) {
        this.origin = origin;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}

