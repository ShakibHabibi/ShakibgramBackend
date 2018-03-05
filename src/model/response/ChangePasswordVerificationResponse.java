package model.response;

public class ChangePasswordVerificationResponse extends Response {
    private String username;

    public ChangePasswordVerificationResponse(boolean isOk, String message, String username) {
        super(isOk, message);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
