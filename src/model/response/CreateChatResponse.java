package model.response;

import model.ChatDetail;

public class CreateChatResponse extends Response {
    private ChatDetail chat;

    public CreateChatResponse(boolean isOk, String message, ChatDetail chat) {
        super(isOk, message);
        this.chat = chat;
    }

    public ChatDetail getChat() {
        return chat;
    }

    public void setChat(ChatDetail chat) {
        this.chat = chat;
    }
}
