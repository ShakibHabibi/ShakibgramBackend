package db.dao;

import model.ChatDetail;
import model.ChatItem;
import model.response.*;

import java.util.List;

public interface ChatDao {

    GetChatsResponse getChats(long userId);

    List<ChatItem> getAllChats();

    CreateChatResponse createChat(long userId, long otherUserId);
}