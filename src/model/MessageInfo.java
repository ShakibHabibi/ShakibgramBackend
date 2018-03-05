package model;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "MessageInfo")
public class MessageInfo {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private long id;
    @Column(name = "SenderUserId")
    private long senderUserId;
    @Column(name = "Message")
    private String message;
    @Column(name = "CreateDate")
    private Date createDate;
    @Column(name = "Status")
    private int status;
    @Column(name = "ChatId")
    private long chatId;
    @Column(name = "SenderName")
    private String senderName;


    public MessageInfo(long id, long senderUserId, String message, Date createData, int status, long chatId, String senderName) {
        this.id = id;
        this.senderUserId = senderUserId;
        this.message = message;
        this.createDate = createData;
        this.status = status;
        this.chatId = chatId;
        this.senderName = senderName;
    }

    public MessageInfo(long senderUserId, String message, Date createData, int status, long chatId, String senderName) {
        this.senderUserId = senderUserId;
        this.message = message;
        this.createDate = createData;
        this.status = status;
        this.chatId = chatId;
        this.senderName = senderName;
    }

    public MessageInfo() {
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(long senderUserId) {
        this.senderUserId = senderUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
}
