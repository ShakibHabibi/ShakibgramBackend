package model.response;

import model.ChatDetail;
import model.ChatInfo;

import java.util.List;

public class GetChatsResponse extends Response {
    private List<ChatDetail> chatDetails;

    public GetChatsResponse(boolean isOk, String message, List<ChatDetail> chatDetails) {
        super(isOk, message);
        this.chatDetails = chatDetails;
    }

    public List<ChatDetail> getChatDetails() {
        return chatDetails;
    }

    public void setChatDetails(List<ChatDetail> chatDetails) {
        this.chatDetails = chatDetails;
    }
}
