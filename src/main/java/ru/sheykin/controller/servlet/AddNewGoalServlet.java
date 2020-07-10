package ru.sheykin.controller.servlet;

import ru.sheykin.DAO.GoalDao;
import ru.sheykin.DAO.UserDao;
import ru.sheykin.DAO.implementations.DAOFactory;
import ru.sheykin.DAO.implementations.DAOTypes;
import ru.sheykin.model.Goal;
import ru.sheykin.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Adds new goal
 */
@WebServlet("/insertGoal")
public class AddNewGoalServlet extends HttpServlet {

    private UserDao<User> userDao;
    private GoalDao<Goal> goalDao;

    @Override
    public void init() throws ServletException {
        userDao = DAOFactory.getDaoFactory().getUserDataManipulationInstance(DAOTypes.SQL);
        goalDao = DAOFactory.getDaoFactory().getGoalDataManipulationInstance(DAOTypes.SQL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) (req.getSession().getAttribute("userName"));
        int userId = userDao.get(user.getUserName()).get().getUserId();
        String name = req.getParameter("name");
        Goal goal = new Goal(name, userId);
        goalDao.add(goal);
        RequestDispatcher dispatcher = req.getRequestDispatcher("goals");
        dispatcher.forward(req, resp);
    }


}
