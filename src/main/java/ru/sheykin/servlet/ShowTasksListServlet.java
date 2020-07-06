package ru.sheykin.servlet;

import ru.sheykin.DAO.*;
import ru.sheykin.model.Task;
import ru.sheykin.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Shows all the tasks for current user
 */
@WebServlet("/list")
public class ShowTasksListServlet extends HttpServlet {

    private UserDataManipulation userDataManipulation;
    private TaskDataManipulation taskDataManipulation;

    @Override
    public void init() throws ServletException {
        taskDataManipulation = DAOFactory.getDaoFactory().getTaskDataManipulationInstance(DAOTypes.SQL);
        userDataManipulation = DAOFactory.getDaoFactory().getUserDataManipulationInstance(DAOTypes.SQL);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = userDataManipulation.getUserId((User) (req.getSession().getAttribute("userName")));
        Task task = new Task();
        task.setUserId(userId);
        List<Task> taskList = taskDataManipulation.selectAllTasks(task);
        req.setAttribute("taskList", taskList);

        RequestDispatcher dispatcher = req.getRequestDispatcher("taskList.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
