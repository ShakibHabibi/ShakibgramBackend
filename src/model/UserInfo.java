package model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "UserInfo")
public class UserInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private long id;
    @Column(name = "UserName", unique = true, nullable = false, length = 45)
    private String userName;
    @Column(name = "Password", nullable = false, length = 45)
    private String password;
    @Column(name = "Name", nullable = false, length = 45)
    private String name;
    @Column(name = "Status", length = 1)
    private int status;
    @Column(name = "VerifyCode", unique = true, length = 5)
    private String verifyCode;
    @Column(name = "EmailAddress", nullable = false, length = 45)
    private String emailAddress;
    @Column(name = "ImageUrl", length = 45)
    private String imageUrl;
    @Column(name = "HasLoggedIn", length = 1)
    private int hasLoggedIn;
    @Column(name = "IsAdmin", length = 1)
    private int isAdmin;
    @Column(name = "IpAddress", length = 16)
    private String ipAddress;
    @Column(name = "RegisterDate")
    private Date registerDate;
    @Column(name = "LastActivityDate")
    private Date lastActivityDate;

    public UserInfo(long id, String userName, String password, String name, int status, String verifyCode, String emailAddress, String imageUrl, int hasLoggedIn, int isAdmin, Date registerDate, Date lastActivityDate) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.status = status;
        this.verifyCode = verifyCode;
        this.emailAddress = emailAddress;
        this.imageUrl = imageUrl;
        this.hasLoggedIn = hasLoggedIn;
        this.isAdmin = isAdmin;
        this.registerDate = registerDate;
        this.lastActivityDate = lastActivityDate;
    }

    public UserInfo() {
    }

    public UserInfo(String userName, String name, String password, int isAdmin, String emailAddress, Date registerDate, Date lastActivityDate) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.registerDate = registerDate;
        this.lastActivityDate = lastActivityDate;
        this.status = status;
        this.verifyCode = verifyCode;
        this.emailAddress = emailAddress;
        this.imageUrl = imageUrl;
        this.hasLoggedIn = hasLoggedIn;
        this.isAdmin = isAdmin;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(Date lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getHasLoggedIn() {
        return hasLoggedIn;
    }

    public void setHasLoggedIn(int hasLoggedIn) {
        this.hasLoggedIn = hasLoggedIn;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }
}
