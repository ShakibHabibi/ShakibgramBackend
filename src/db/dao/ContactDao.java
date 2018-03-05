package db.dao;

import model.response.GetContactResponse;
import model.response.Response;

public interface ContactDao {
    Response addContact(long userId, String username);

    GetContactResponse getContact(long userId);
}
