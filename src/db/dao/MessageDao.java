package db.dao;

import model.MessageSocket;
import model.UpdateStatus;
import model.response.GetChatMessagesResponse;
import model.response.Response;

public interface MessageDao {
    Response sendMessage(MessageSocket messageSocket);

    GetChatMessagesResponse getChatMessages(long chatId, long lastMessageId, long userId);

    void updateStatus(UpdateStatus updateStatus);
}
