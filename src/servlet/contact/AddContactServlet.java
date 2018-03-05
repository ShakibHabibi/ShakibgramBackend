package servlet.contact;

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

@WebServlet("/contact/addcontact")
public class AddContactServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ContactDao contactDao = new ContactDaoImpl();
        String username = req.getParameter("username");
        long userId = Long.parseLong(req.getParameter("userId"));
        resp.setContentType("application/json; charset=utf-8");
        Response response = contactDao.addContact(userId, username);
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(Util.toJson(response));
    }
}
