package ru.sheykin.servlet;

import ru.sheykin.DAO.AuthenticationDAO;
import ru.sheykin.model.User;
import ru.sheykin.util.PasswordAuth;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    AuthenticationDAO authenticationDAO;

    @Override
    public void init() throws ServletException {

        authenticationDAO = new AuthenticationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("loginForm.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        final String currentUserName = req.getParameter("userName");
        final String currentUserPassword = req.getParameter("password");
        final User user = authenticationDAO.getUser(currentUserName);
        final String userPasswordFromDB = user.getPassword();

        if(user.getUserName() != null
                && user.getUserName().equals(currentUserName)
                && PasswordAuth.check(currentUserPassword, userPasswordFromDB)) {
            final HttpSession httpSession = req.getSession();
            httpSession.setAttribute("userName", user);
            resp.sendRedirect("loginSuccess.jsp");
        } else {
            resp.sendRedirect("loginForm.jsp");
        }
    }
}