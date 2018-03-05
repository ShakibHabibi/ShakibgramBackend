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

@WebServlet("/auth/changepasswordverification")
public class ChangePasswordVerificationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthDao authDao = new AuthDaoImpl();
        String username = req.getParameter("username");
        String emailAddress = req.getParameter("emailAddress");
        resp.setContentType("application/json; charset=utf-8");
        Response response = authDao.changePasswordVerification(username, emailAddress);
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(Util.toJson(response));
    }
}
