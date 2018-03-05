package db.dao.daoImpl;

import db.HibernateHelper;
import db.dao.AuthDao;
import model.UserInfo;
import model.response.*;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import utils.Util;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class AuthDaoImpl implements AuthDao {

    @Override
    public SignUpResponse signUp(String userName, String name, String password, int isAdmin, String emailAddress) {
        try {
            UserInfo userInfo = new UserInfo(userName, name, password, isAdmin, emailAddress, new Date(), new Date());
            final Session session = HibernateHelper.getSession();
            session.beginTransaction();
            session.save(userInfo);
            session.getTransaction().commit();
            boolean hasCodeSent = saveCode(userName, emailAddress);
            if (hasCodeSent)
                return new SignUpResponse(true, "حساب کاربری ایجاد شد", userName);
            else
                return new SignUpResponse(false, "مشکل در ایجاد حساب کاربری", null);
        } catch (ConstraintViolationException e) {
            return new SignUpResponse(false, "نام کاربری تکراری است", null);
        } catch (Exception e) {
            return new SignUpResponse(false, "مشکل در ایجاد حساب کاربری", null);
        }
    }

    @Override
    public CheckUsernameResponse checkUserName(String userName) {
        try {
            final Session session = HibernateHelper.getSession();
            session.beginTransaction();
            Query query = session.createQuery("from UserInfo where userName = :userName");
            query.setParameter("userName", userName);
            List list = query.list();
            session.getTransaction().commit();
            if (list == null || list.size() == 0) {
                return new CheckUsernameResponse(true, "نام کاربری موجود نیست", null);
            } else {
                return new CheckUsernameResponse(true, "نام کاربری تکراری است", ((UserInfo) list.get(0)).getEmailAddress());
            }
        } catch (Exception e) {
            return new CheckUsernameResponse(false, "مشکل در بررسی نام کاربری", null);
        }
    }

    @Override
    public Response changePassword(String password, String userName) {
        try {
            final Session session = HibernateHelper.getSession();
            session.beginTransaction();
            Query query = session.createQuery("update UserInfo set password = :password where userName = :username");
            query.setParameter("username", userName);
            query.setParameter("password", password);
            int result = query.executeUpdate();
            session.getTransaction().commit();
            if (result == 1) {
                return new Response(true, "رمز عبور تغییر کرد");
            } else {
                return new Response(false, "مشکل در تغییر رمز عبور");
            }
        } catch (Exception e) {
            return new Response(false, "مشکل در تغییر رمز عبور");
        }
    }

    @Override
    public VerifyCodeResponse verifyCode(String userName, String code) {
        try {
            final Session session = HibernateHelper.getSession();
            session.beginTransaction();
            Query query = session.createQuery("from UserInfo where userName = :username and verifyCode = :code");
            query.setParameter("username", userName);
            query.setParameter("code", code);
            List list = query.list();
            if (list == null || list.size() == 0) {
                session.getTransaction().commit();
                return new VerifyCodeResponse(false, "کد وارد شده اشتباه است", null);
            } else {
                UserInfo userInfo = (UserInfo) list.get(0);
                userInfo.setHasLoggedIn(1);
                userInfo.setVerifyCode("done");
                session.update(userInfo);
                session.getTransaction().commit();
                return new VerifyCodeResponse(true, null, userInfo);
            }
        } catch (Exception e) {
            return new VerifyCodeResponse(false, "مشکل در بررسی کد", null);
        }
    }

    @Override
    public Response updateCode(String userName, String code) {
        try {
            final Session session = HibernateHelper.getSession();
            session.beginTransaction();
            Query query = session.createQuery("update UserInfo set verifyCode = :code where userName = :username");
            query.setParameter("username", userName);
            query.setParameter("code", code);
            int result = query.executeUpdate();
            session.getTransaction().commit();
            if (result == 1) {
                return new Response(true, "کد آپدیت شد");
            } else {
                return new Response(false, "ایراد در آپدیت کد");
            }
        } catch (Exception e) {
            return new Response(false, "ایراد در آپدیت کد");
        }
    }

    @Override
    public LoginResponse login(String userName, String password) {
        try {
            final Session session = HibernateHelper.getSession();
            session.beginTransaction();
            Query query = session.createQuery("from UserInfo where userName = :userName and password = :password");
            query.setParameter("userName", userName);
            query.setParameter("password", password);
            List list = query.list();
            if (list == null || list.size() == 0) {
                session.getTransaction().commit();
                return new LoginResponse(false, "نام کاربری یا رمز عبور اشتباه است", null);
            } else {
                UserInfo userInfo = (UserInfo) list.get(0);
                if (userInfo.getVerifyCode().equals("done")) {
                    userInfo.setHasLoggedIn(1);
                    session.update(userInfo);
                    session.getTransaction().commit();
                    return new LoginResponse(true, null, userInfo);
                } else {
                    return new LoginResponse(false, "شما تایید هویت نشده اید", null);
                }
            }
        } catch (Exception e) {
            return new LoginResponse(false, "ایراد در عملیان ورود", null);
        }
    }

    @Override
    public ChangePasswordVerificationResponse changePasswordVerification(String username, String emailAddress) {
        try {
            final Session session = HibernateHelper.getSession();
            session.beginTransaction();
            Query query = session.createQuery("from UserInfo where userName = :username and emailAddress= :emailAddress");
            query.setParameter("username", username);
            query.setParameter("emailAddress", emailAddress);
            session.getTransaction().commit();
            List list = query.list();
            if (list == null || list.size() == 0) {
                return new ChangePasswordVerificationResponse(false, "کاربر موجود نیست", null);
            } else {
                boolean hasCodeSent = saveCode(username, emailAddress);
                if (hasCodeSent)
                    return new ChangePasswordVerificationResponse(true, "کد به ایمیل ارسال شد", ((UserInfo) list.get(0)).getUserName());
                else
                    return new ChangePasswordVerificationResponse(false, "مشکل در ارسال کد", null);
            }
        } catch (Exception e) {
            return new ChangePasswordVerificationResponse(false, "مشکل در بررسی حساب کاربری", null);
        }
    }

    @Override
    public boolean saveCode(String username, String email) {
        Random random = new Random();
        int code = random.nextInt(100000);
        try {
            Util.sendEmail(email, code);
            final Session session = HibernateHelper.getSession();
            session.beginTransaction();
            Query query = session.createQuery("update UserInfo set verifyCode = :code where userName = :username");
            query.setParameter("username", username);
            query.setParameter("code", String.valueOf(code));
            int result = query.executeUpdate();
            session.getTransaction().commit();
            return result == 1;
        } catch (MessagingException e) {
            return false;
        }
    }

    @Override
    public List<UserInfo> getAllUsers() {
        final Session session = HibernateHelper.getSession();
        session.beginTransaction();
        Query query = session.createQuery("from UserInfo ");
        List<UserInfo> list = query.list();
        session.getTransaction().commit();
        return list;
    }
}
