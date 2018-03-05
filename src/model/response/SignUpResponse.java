package model.response;

public class SignUpResponse extends Response {
    private String username;

    public SignUpResponse(boolean isOk, String message, String username) {
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
