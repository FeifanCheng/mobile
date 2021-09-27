package mobile;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 消息推送历史记录表
 * Message push history table
 *
 * @author Group T01/01-5
 * @version 1
 */
@Entity
@Table(name = "TB_PUSH_HISTORY")
public class PushHistory {
    @Id
    @PrimaryKeyJoinColumn
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(updatable = false, nullable = false)
    private String id;


    // The specific entities pushed are all JSON strings
    // BLOB is more of a large field type than TEXT
    @Lob
    @Column(nullable = false, columnDefinition = "BLOB")
    private String entity;


    // Push entity type
    @Column(nullable = false)
    private int entityType;


    // Receiver
    // The receiver is not allowed to be empty
    // One receiver can receive many push messages
    // FetchType.EAGER: Load user information between when loading a push message
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // 默认是：receiver_id
    @JoinColumn(name = "receiverId")
    private User receiver;
    @Column(nullable = false, updatable = false, insertable = false)
    private String receiverId;

    // Sender
    // The sender can be empty, because it may be a system message
    // One sender can send many push messages
    // FetchType.EAGER: Load user information between when loading a push message
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "senderId")
    private User sender;
    @Column(updatable = false, insertable = false)
    private String senderId;


    // The device push ID of the receiver in the current state
    // User.pushId 可为null
    @Column
    private String receiverPushId;

    // Defined as the creation timestamp, which has been written at the time of creation
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    // Defined as an update timestamp, which has been written when it is created
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    // The time when the message was delivered, can be empty
    @Column
    private LocalDateTime arrivalAt;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverPushId() {
        return receiverPushId;
    }

    public void setReceiverPushId(String receiverPushId) {
        this.receiverPushId = receiverPushId;
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

    public LocalDateTime getArrivalAt() {
        return arrivalAt;
    }

    public void setArrivalAt(LocalDateTime arrivalAt) {
        this.arrivalAt = arrivalAt;
    }
}

