package ru.sheykin.servlet;

import ru.sheykin.model.User;
import ru.sheykin.DAO.AuthenticationDAO;
import ru.sheykin.util.PasswordAuth;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    private AuthenticationDAO authenticationDAO;

    @Override
    public void init() throws ServletException {
        authenticationDAO = new AuthenticationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher dispatcher = req.getRequestDispatcher("registerForm.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userName = req.getParameter("userName");
        String password = null;

        try {
            password = PasswordAuth.getSaltedHash(req.getParameter("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        authenticationDAO.addUser(user);

        RequestDispatcher dispatcher = req.getRequestDispatcher("registerDetails.jsp");
        dispatcher.forward(req, resp);
    }
}
