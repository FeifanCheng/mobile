package mobile;

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
@Table(name = "TB_MESSAGE")
public class Message {
	
	// string type
    public static final int TYPE_STR = 1;
    // Image type
    public static final int TYPE_PIC = 1;
    // file type
    public static final int TYPE_FILE = 3;
    // Voice type
    public static final int TYPE_AUDIO = 4; 


    @Id
    @PrimaryKeyJoinColumn
    // This is a primary key
    // The type of primary key generation and storage is UUID
    // UUID is not automatically generated here, Id is written by the code, and the client is responsible for generating
    // Avoid complicated server and client mapping
    //@GeneratedValue(generator = "uuid")
    // Define the generator of uuid as uuid2, uuid2 is the regular UUID toString
    //No change is allowed, null is not allowed
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(updatable = false, nullable = false)
    private String id;

    // The content is not allowed to be empty, the type is text
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // Appendix
    @Column
    private String attach;

    // Message type
    @Column(nullable = false)
    private int type;

    // Defined as the creation timestamp, which has been written at the time of creation
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    // Defined as an update timestamp, which has been written when it is created
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();


    // sender is not empty
    // Multiple messages correspond to one sender
    @JoinColumn(name = "senderId")
    @ManyToOne(optional = false)
    private User sender;
    
    // This field is just to correspond to the sender's database field senderId
    // Manual update or insert is not allowed
    @Column(nullable = false, updatable = false, insertable = false)
    private String senderId;

    // receiver can be empty
    // Multiple messages correspond to one receiver
    @ManyToOne
    @JoinColumn(name = "receiverId")
    private User receiver;
    @Column(updatable = false, insertable = false)
    private String receiverId;


    // A group can receive multiple messages
    @ManyToOne
    @JoinColumn(name = "groupId")
    private Group group;
    @Column(updatable = false, insertable = false)
    private String groupId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
