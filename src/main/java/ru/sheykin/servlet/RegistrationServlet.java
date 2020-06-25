package ru.sheykin.servlet;

import ru.sheykin.DAO.DAOFactory;
import ru.sheykin.DAO.DAOTypes;
import ru.sheykin.DAO.UserDataManipulation;
import ru.sheykin.model.User;
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

    private UserDataManipulation userDataManipulation;

    @Override
    public void init() throws ServletException {
        System.out.println(DAOFactory.getDaoFactory().getUserDataManipulationInstance(DAOTypes.SQL));
        userDataManipulation = DAOFactory.getDaoFactory().getUserDataManipulationInstance((DAOTypes.SQL));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher dispatcher = req.getRequestDispatcher("registerForm.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String userName = req.getParameter("userName");
        final String password = PasswordAuth.getSaltedHash(req.getParameter("password"));

        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        userDataManipulation.addUser(user);

        RequestDispatcher dispatcher = req.getRequestDispatcher("registerDetails.jsp");
        dispatcher.forward(req, resp);
    }
}
