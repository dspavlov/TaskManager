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

/**
 * Adds new goal
 */
@WebServlet("/insertGoal")
public class AddNewGoalServlet extends HttpServlet {

    private UserDataManipulation userDataManipulation;
    private GoalDataManipulation goalDataManipulation;

    @Override
    public void init() throws ServletException {
        userDataManipulation = DAOFactory.getDaoFactory().getUserDataManipulationInstance(DAOTypes.SQL);
        goalDataManipulation = DAOFactory.getDaoFactory().getGoalDataManipulationInstance(DAOTypes.SQL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) (req.getSession().getAttribute("userName"));
        int userId = userDataManipulation.get(user.getUserName()).getUserId();
        String name = req.getParameter("name");
        Goal goal = new Goal(name, userId);
        goalDataManipulation.add(goal);
        RequestDispatcher dispatcher = req.getRequestDispatcher("goals");
        dispatcher.forward(req, resp);
    }


}
