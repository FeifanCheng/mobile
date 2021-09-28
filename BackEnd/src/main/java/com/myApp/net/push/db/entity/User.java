package com.myApp.net.push.db.entity;

import com.myApp.net.push.db.entity.Group;
import com.myApp.net.push.db.entity.UserFollow;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * �û����ݿ�
 * User database
 *
 * @author Group T01/01-5
 * @version 1.0.0
 */
@Entity
@Table(name = "TB_USER")
public class User implements Principal {

    @Id
    @PrimaryKeyJoinColumn
    // The type of primary key generation and storage is UUID
    @GeneratedValue(generator = "uuid")
    // Define the generator of uuid as uuid2, uuid2 is the regular UUID toString
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    // No change is allowed, null is not allowed
    @Column(updatable = false, nullable = false)
    private String id;

 	// Username must be unique
    @Column(nullable = false, length = 128, unique = true)
    private String name;

    // phone must be unique
    @Column(nullable = false, length = 62, unique = true)
    private String phone;

    @Column(nullable = false)
    private String password;

    // The avatar is allowed to be null
    @Column
    private String portrait;

    @Column
    private String description;

    // The gender has an initial value, all are not empty
    @Column(nullable = false)
    private int sex = 0;

    // token can pull user information, all tokens must be unique
    @Column(unique = true)
    private String token;

    // ���п���
    // Device ID used for push
    @Column
    private String pushId;

    // Defined as the creation timestamp, which has been written at the time of creation
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    // Defined as an update timestamp, which has been written when it is created
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    // The time of the last message received
    @Column
    private LocalDateTime lastReceivedAt = LocalDateTime.now();


    // List of people I follow
    @JoinColumn(name = "originId")
    //Defined as lazy loading, when User information is loaded by default, this collection is not queried
    @LazyCollection(LazyCollectionOption.EXTRA)
    // one to many, a user can have many followers, and each follow is a record
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserFollow> following = new HashSet<>();


    // List of followers
    @JoinColumn(name = "targetId")
    // Defined as lazy loading, when User information is loaded by default, this collection is not queried
    @LazyCollection(LazyCollectionOption.EXTRA)
    // 1 to many, a user can be followed by many people, and each follow is a record
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserFollow> followers = new HashSet<>();

    // Group created
    @JoinColumn(name = "ownerId")
    // When accessing groups.size(), only the number is queried, and no specific group information is loaded
    @LazyCollection(LazyCollectionOption.EXTRA)
    // FetchType.LAZY: Lazy loading, this collection is not loaded when loading user information
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Group> groups = new HashSet<>();


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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
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

    public LocalDateTime getLastReceivedAt() {
        return lastReceivedAt;
    }

    public void setLastReceivedAt(LocalDateTime lastReceivedAt) {
        this.lastReceivedAt = lastReceivedAt;
    }

    public Set<UserFollow> getFollowing() {
        return following;
    }

    public void setFollowing(Set<UserFollow> following) {
        this.following = following;
    }

    public Set<UserFollow> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<UserFollow> followers) {
        this.followers = followers;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
}

