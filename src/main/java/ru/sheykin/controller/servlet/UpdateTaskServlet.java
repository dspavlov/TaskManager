package ru.sheykin.controller.servlet;

import ru.sheykin.DAO.implementations.DAOFactory;
import ru.sheykin.DAO.implementations.DAOTypes;
import ru.sheykin.DAO.TaskDao;
import ru.sheykin.model.Task;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Updates the selected task
 */
@WebServlet("/update")
public class UpdateTaskServlet extends HttpServlet {

    private TaskDao<Task> taskDao;

    @Override
    public void init() throws ServletException {
        taskDao = DAOFactory.getDaoFactory().getTaskDataManipulationInstance(DAOTypes.SQL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String details = req.getParameter("details");
        LocalDateTime ldt = LocalDateTime.now();
        String goal = req.getParameter("goalId");
        int goalId = 0;
        if(!goal.isEmpty())
            goalId = Integer.parseInt(goal);
        Task task = new Task(userId, name, details, userId, ldt, goalId);
        taskDao.update(task);
        RequestDispatcher dispatcher = req.getRequestDispatcher("list");
        dispatcher.forward(req, resp);
    }
}
