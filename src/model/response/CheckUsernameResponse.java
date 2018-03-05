package model.response;

public class CheckUsernameResponse extends Response {

    private String emailAddress;

    public CheckUsernameResponse(boolean isOk, String message, String emailAddress) {
        super(isOk, message);
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
