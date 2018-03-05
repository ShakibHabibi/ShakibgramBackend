package model;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "ChatInfo")
public class ChatInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private long id;
    @Column(name = "UserId")
    private long userId;
    @Column(name = "OtherUserId")
    private long otherUserId;

    public ChatInfo(long id, long userId, long otherUserId) {
        this.id = id;
        this.userId = userId;
        this.otherUserId = otherUserId;
    }

    public ChatInfo() {
    }

    public ChatInfo(long userId, long otherUserId) {
        this.userId = userId;
        this.otherUserId = otherUserId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(long otherUserId) {
        this.otherUserId = otherUserId;
    }
}
