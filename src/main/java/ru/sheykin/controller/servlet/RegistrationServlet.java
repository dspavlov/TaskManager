package ru.sheykin.controller.servlet;

import ru.sheykin.DAO.implementations.DAOFactory;
import ru.sheykin.DAO.implementations.DAOTypes;
import ru.sheykin.DAO.UserDao;
import ru.sheykin.model.User;
import ru.sheykin.util.PasswordAuth;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.servlet.http.HttpServletResponse.*;

/**
 * Provides registration, e-mail and password validation
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = DAOFactory.getDaoFactory().getUserDataManipulationInstance((DAOTypes.SQL));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("registerForm.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+!=])(?=\\S+$).{8,}$";

        String userName = req.getParameter("userName");
        String password = req.getParameter("password");

        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher passwordMatcher = passwordPattern.matcher(password);

        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher emailMatcher = emailPattern.matcher(userName);

        if (emailMatcher.matches() && passwordMatcher.matches()) {
            if (userDao.isExist(userName)) {
                resp.setStatus(SC_CONFLICT);
                RequestDispatcher dispatcher = req.getRequestDispatcher("registerForm.jsp");
                dispatcher.forward(req, resp);
            } else {
                User user = new User();
                user.setUserName(userName);
                user.setPassword(PasswordAuth.getSaltedHash(password));
                userDao.add(user);

                RequestDispatcher dispatcher = req.getRequestDispatcher("registerDetails.jsp");
                dispatcher.forward(req, resp);
            }
        } else {
            resp.setStatus(SC_BAD_REQUEST);
            RequestDispatcher dispatcher = req.getRequestDispatcher("registerForm.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
