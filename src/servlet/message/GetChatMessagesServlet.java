package servlet.message;

import db.dao.ContactDao;
import db.dao.MessageDao;
import db.dao.daoImpl.ContactDaoImpl;
import db.dao.daoImpl.MessageDaoImpl;
import model.response.Response;
import utils.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/message/getchatmessages")
public class GetChatMessagesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MessageDao messageDao = new MessageDaoImpl();
        long chatId = Long.parseLong(req.getParameter("chatId"));
        long lastMessageId = Long.parseLong(req.getParameter("lastMessageId"));
        long userId = Long.parseLong(req.getParameter("userId"));
        resp.setContentType("application/json; charset=utf-8");
        Response response = messageDao.getChatMessages(chatId, lastMessageId,userId);
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(Util.toJson(response));
    }
}
