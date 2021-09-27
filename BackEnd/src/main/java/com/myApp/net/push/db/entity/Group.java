package com.myApp.net.push.db.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author  Group T01/01-5
 * @version 1
 */
@Entity
@Table(name = "TB_GROUP")
public class Group {

	// This is a primary key
    @Id
    @PrimaryKeyJoinColumn
    // The type of primary key generation and storage is UUID, and UUID is automatically generated
    @GeneratedValue(generator = "uuid")
    // Define the generator of uuid as uuid2, uuid2 is the regular UUID toString
    // No change is allowed, null is not allowed
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(updatable = false, nullable = false)
    private String id;

    // group name
    @Column(nullable = false)
    private String name;

    // group description
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String picture;

    // Defined as the creation timestamp, which has been written at the time of creation
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    // Defined as an update timestamp, which has been written when it is created
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    // The creator of the group
    // optional: Optional is false, there must be a creator
    // fetch: loading method FetchType.EAGER, urgent loading,
    // means that the owner's information must be loaded when loading group information
    // cascade: The cascade level is ALL, all changes (updates, deletes, etc.) will be updated
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ownerId")
    private User owner;
    @Column(nullable = false, updatable = false, insertable = false)
    private String ownerId;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}

