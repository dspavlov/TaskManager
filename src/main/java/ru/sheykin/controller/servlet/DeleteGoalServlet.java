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

import static javax.servlet.http.HttpServletResponse.SC_NO_CONTENT;

@WebServlet("/deleteGoal")
public class DeleteGoalServlet extends HttpServlet {
    //todo
    private GoalDao<Goal> goalDao;

    @Override
    public void init() throws ServletException {
        //todo see similar comments
        goalDao = DAOFactory.getDaoFactory().getGoalDataManipulationInstance(DAOTypes.SQL);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDelete(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        if (goalDao.delete(id) == 1) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("goals");
            dispatcher.forward(req, resp);
        } else
            resp.setStatus(SC_NO_CONTENT);
    }
}
