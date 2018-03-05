package servlet.contact;

import db.dao.ContactDao;
import db.dao.daoImpl.ContactDaoImpl;
import model.response.Response;
import utils.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/contact/getcontact")
public class GetContactServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ContactDao contactDao = new ContactDaoImpl();
        long userId = Long.parseLong(req.getParameter("userId"));
        resp.setContentType("application/json; charset=utf-8");
        Response response = contactDao.getContact(userId);
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(Util.toJson(response));
    }
}
