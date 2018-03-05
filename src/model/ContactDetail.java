package model;


public class ContactDetail {
    private long contactId;
    private long userId;
    private String name;

    public ContactDetail(long contactId, long userId, String name) {
        this.contactId = contactId;
        this.userId = userId;
        this.name = name;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
