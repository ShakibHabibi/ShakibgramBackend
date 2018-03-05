package model.response;

import model.ContactDetail;

import java.util.List;

public class GetContactResponse extends Response {
    private List<ContactDetail> contacts;

    public GetContactResponse(boolean isOk, String message, List<ContactDetail> contacts) {
        super(isOk, message);
        this.contacts = contacts;
    }

    public List<ContactDetail> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactDetail> contacts) {
        this.contacts = contacts;
    }
}
