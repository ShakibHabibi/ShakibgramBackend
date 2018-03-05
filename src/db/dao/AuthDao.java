package db.dao;

import model.UserInfo;
import model.response.*;

import java.util.List;

public interface AuthDao {

    SignUpResponse signUp(String userName, String name, String password, int isAdmin, String emailAddress);

    CheckUsernameResponse checkUserName(String userName);

    Response changePassword(String password, String userName);

    VerifyCodeResponse verifyCode(String userName, String code);

    Response updateCode(String userName, String code);

    LoginResponse login(String userName, String password);

    ChangePasswordVerificationResponse changePasswordVerification(String username, String emailAddress);

    boolean saveCode(String username, String email);

    List<UserInfo> getAllUsers();
}