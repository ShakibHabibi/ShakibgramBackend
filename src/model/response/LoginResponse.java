package model.response;

import model.UserInfo;

public class LoginResponse extends Response {

    private UserInfo userInfo;

    public LoginResponse(boolean isOk, String message, UserInfo userInfo) {
        super(isOk, message);
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
