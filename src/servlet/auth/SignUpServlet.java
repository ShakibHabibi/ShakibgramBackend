package servlet.auth;

import utils.Util;
import db.dao.AuthDao;
import db.dao.daoImpl.AuthDaoImpl;
import model.response.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/auth/signup")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthDao authDao = new AuthDaoImpl();
        String username = req.getParameter("username");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String emailAddress = req.getParameter("emailAddress");
        resp.setContentType("application/json; charset=utf-8");
        Response response = authDao.signUp(username, name, password, 0, emailAddress);
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(Util.toJson(response));
    }
}
