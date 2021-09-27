package mobile;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Group T01/01-5
 * @version 1
 */
@Entity
@Table(name = "TB_GROUP_MEMBER")
public class GroupMember {
	
	// Default permissions, ordinary members
    public static final int PERMISSION_TYPE_NONE = 0; 
    // Admin
    public static final int PERMISSION_TYPE_ADMIN = 1;
    //Creator 
    public static final int PERMISSION_TYPE_ADMIN_SU = 100;

    // Do not receive messages by default
    public static final int NOTIFY_LEVEL_INVALID = -1; 
    // Default notification level
    public static final int NOTIFY_LEVEL_NONE = 0; 
    // No prompt when receiving messages
    public static final int NOTIFY_LEVEL_CLOSE = 1; 

    @Id
    @PrimaryKeyJoinColumn
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(updatable = false, nullable = false)
    private String id;

    // alias
    @Column
    private String alias;

    // Message notification level
    @Column(nullable = false)
    private int notifyLevel = NOTIFY_LEVEL_NONE;


    // Member's permission type
    @Column(nullable = false)
    private int permissionType = PERMISSION_TYPE_NONE;

    // Defined as the creation timestamp, which has been written at the time of creation
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    // Defined as an update timestamp, which has been written when it is created
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    // User information corresponding to member information
    @JoinColumn(name = "userId")
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;
    @Column(nullable = false, updatable = false, insertable = false)
    private String userId;

    // Group information corresponding to member information
    @JoinColumn(name = "groupId")
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Group group;
    @Column(nullable = false, updatable = false, insertable = false)
    private String groupId;


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

    public int getNotifyLevel() {
        return notifyLevel;
    }

    public void setNotifyLevel(int notifyLevel) {
        this.notifyLevel = notifyLevel;
    }

    public int getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(int permissionType) {
        this.permissionType = permissionType;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}

