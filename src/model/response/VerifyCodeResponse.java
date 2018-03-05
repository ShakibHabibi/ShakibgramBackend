package model.response;

import model.UserInfo;

public class VerifyCodeResponse extends Response {
    private UserInfo userInfo;

    public VerifyCodeResponse(boolean isOk, String message, UserInfo userInfo) {
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
