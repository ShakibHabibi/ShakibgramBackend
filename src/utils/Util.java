package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.response.Response;
import org.hibernate.criterion.Example;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Util {
    public static String toJson(Response response) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(response);
    }

    public static void sendEmail(String email, int code) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("email_address", "password");
                    }
                });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("email_address"));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(email));
        message.setSubject(Constant.EMAIL_SUBJECT);
        message.setText(String.valueOf(code));
        Transport.send(message);
    }

    public static List upsideList(List list) {
        List temp = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            temp.add(list.get(i));
        }
        return temp;
    }
}
