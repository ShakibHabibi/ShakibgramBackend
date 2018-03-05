package servlet.auth;

import db.dao.AuthDao;
import db.dao.daoImpl.AuthDaoImpl;
import model.response.Response;
import utils.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/auth/changepassword")
public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthDao authDao = new AuthDaoImpl();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        resp.setContentType("application/json; charset=utf-8");
        Response response = authDao.changePassword(password, username);
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(Util.toJson(response));
    }
}
