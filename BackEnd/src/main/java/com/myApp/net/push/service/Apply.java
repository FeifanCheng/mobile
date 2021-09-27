package mobile;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * ÉêÇë¼ÇÂ¼±í
 * Application Record Form
 *
 * @author  Group T01/01-5
 * @version 1
 */
@Entity
@Table(name = "TB_APPLY")
public class Apply {

	// add friend
    public static final int TYPE_ADD_USER = 1; 
    // Apply to join the group
    public static final int TYPE_ADD_GROUP = 2;
    
    @Id
    @PrimaryKeyJoinColumn
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(updatable = false, nullable = false)
    private String id;

    // Describe application information
    @Column(nullable = false)
    private String description;


    // Attachment can be empty
    // You can attach a picture address, or other
    @Column(columnDefinition = "TEXT")
    private String attach;


    // Type of current application
    @Column(nullable = false)
    private int type;


    // The target Id is not strongly associated, and the primary and foreign key relationship is not established
    // type->TYPE_ADD_USER£ºUser.id
    // type->TYPE_ADD_GROUP£ºGroup.id
    @Column(nullable = false)
    private String targetId;


    // Defined as the creation timestamp, which has been written at the time of creation
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    // Defined as an update timestamp, which has been written at the time of creation
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();


    // Applicant can be empty for system information
    // One person can have many applications
    @ManyToOne
    @JoinColumn(name = "applicantId")
    private User applicant;
    @Column(updatable = false, insertable = false)
    private String applicantId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
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

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }
}

