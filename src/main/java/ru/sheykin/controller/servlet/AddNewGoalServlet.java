package ru.sheykin.controller.servlet;

import ru.sheykin.DAO.GoalDataManipulation;
import ru.sheykin.DAO.UserDataManipulation;
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

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;

/**
 * Adds new goal
 */
@WebServlet("/goal")
public class AddNewGoalServlet extends HttpServlet {

    private UserDataManipulation userDataManipulation;
    private GoalDataManipulation goalDataManipulation;

    @Override
    public void init() throws ServletException {
        userDataManipulation = DAOFactory.getDaoFactory().getUserDataManipulationInstance(DAOTypes.SQL);
        goalDataManipulation = DAOFactory.getDaoFactory().getGoalDataManipulationInstance(DAOTypes.SQL);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("goalForm.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = userDataManipulation.getUserId((User) (req.getSession().getAttribute("userName")));
        String name = req.getParameter("name");
        Goal goal = new Goal(name, userId);
        if (goalDataManipulation.addGoal(goal) == 1) {
            resp.setStatus(SC_CREATED);
            RequestDispatcher dispatcher = req.getRequestDispatcher("list");
            dispatcher.forward(req, resp);
        } else
            resp.setStatus(SC_BAD_REQUEST);
    }


}
