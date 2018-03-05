package model;

import java.util.Date;

public class ChatDetail {

    private long chatId;
    private String name;
    private String lastMessageText;
    private Date lastMessageDate;

    public ChatDetail(long chatId, String name, String lastMessageText, Date lastMessageDate) {
        this.chatId = chatId;
        this.name = name;
        this.lastMessageText = lastMessageText;
        this.lastMessageDate = lastMessageDate;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMessageText() {
        return lastMessageText;
    }

    public void setLastMessageText(String lastMessageText) {
        this.lastMessageText = lastMessageText;
    }

    public Date getLastMessageDate() {
        return lastMessageDate;
    }

    public void setLastMessageDate(Date lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }
}
