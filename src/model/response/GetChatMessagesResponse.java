package model.response;

import model.MessageInfo;

import java.util.List;

public class GetChatMessagesResponse extends Response {

    private boolean isLoadMore;
    private List<MessageInfo> messages;

    public GetChatMessagesResponse(boolean isOk, String message, boolean isLoadMore, List<MessageInfo> messages) {
        super(isOk, message);
        this.isLoadMore = isLoadMore;
        this.messages = messages;
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    public List<MessageInfo> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageInfo> messages) {
        this.messages = messages;
    }
}
