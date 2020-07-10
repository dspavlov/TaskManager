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
import java.util.List;

@WebServlet("/goals")
public class ShowGoalsListServlet extends HttpServlet {

    private UserDao<User> userDao;
    private GoalDao<Goal> goalDao;

    @Override
    public void init() throws ServletException {
        userDao = DAOFactory.getDaoFactory().getUserDataManipulationInstance(DAOTypes.SQL);
        goalDao = DAOFactory.getDaoFactory().getGoalDataManipulationInstance(DAOTypes.SQL);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) (req.getSession().getAttribute("userName"));
        int userId = userDao.get(user.getUserName()).get().getUserId();
        List<Goal> goalList = goalDao.getAll(userId);
        req.setAttribute("taskList", goalList);

        RequestDispatcher dispatcher = req.getRequestDispatcher("goalList.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
