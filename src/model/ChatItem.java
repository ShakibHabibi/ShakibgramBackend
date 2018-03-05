package model;

public class ChatItem {
    private long chatId;
    private String username;
    private String othername;

    public ChatItem(long chatId, String username, String othername) {
        this.chatId = chatId;
        this.username = username;
        this.othername = othername;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOthername() {
        return othername;
    }

    public void setOthername(String othername) {
        this.othername = othername;
    }
}
