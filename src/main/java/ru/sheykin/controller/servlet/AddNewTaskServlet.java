package ru.sheykin.controller.servlet;

import ru.sheykin.DAO.*;
import ru.sheykin.DAO.implementations.DAOFactory;
import ru.sheykin.DAO.implementations.DAOTypes;
import ru.sheykin.model.Task;
import ru.sheykin.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static javax.servlet.http.HttpServletResponse.*;

/**
 * Adds new task
 */
@WebServlet("/insert")
public class AddNewTaskServlet extends HttpServlet {
    private UserDataManipulation userDataManipulation;
    private TaskDataManipulation taskDataManipulation;

    @Override
    public void init() throws ServletException {
        taskDataManipulation = DAOFactory.getDaoFactory().getTaskDataManipulationInstance(DAOTypes.SQL);
        userDataManipulation = DAOFactory.getDaoFactory().getUserDataManipulationInstance(DAOTypes.SQL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) (req.getSession().getAttribute("userName"));
        int userId = userDataManipulation.get(user.getUserName()).getUserId();
        String name = req.getParameter("name");
        String details = req.getParameter("details");
        LocalDateTime ldt = LocalDateTime.now();
        String goal = req.getParameter("goalId");
        int goalId = 0;
        if(!goal.isEmpty())
            goalId = Integer.parseInt(goal);
        Task task = new Task(userId, name, details, userId, ldt, goalId);
        if (taskDataManipulation.addTask(task) == 1) {
            resp.setStatus(SC_CREATED);
            RequestDispatcher dispatcher = req.getRequestDispatcher("list");
            dispatcher.forward(req, resp);
        } else
            resp.setStatus(SC_BAD_REQUEST);
    }
}
