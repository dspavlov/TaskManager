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
import java.util.List;

/**
 * Shows all the tasks for current user
 */
@WebServlet("/list")
public class ShowTasksListServlet extends HttpServlet {

    private UserDao<User> userDao;
    private TaskDao<Task> taskDao;

    @Override
    public void init() throws ServletException {
        taskDao = DAOFactory.getDaoFactory().getTaskDataManipulationInstance(DAOTypes.SQL);
        userDao = DAOFactory.getDaoFactory().getUserDataManipulationInstance(DAOTypes.SQL);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) (req.getSession().getAttribute("userName"));
        int userId = userDao.get(user.getUserName()).get().getUserId();
        List<Task> taskList = taskDao.getAll(userId);
        req.setAttribute("taskList", taskList);

        RequestDispatcher dispatcher = req.getRequestDispatcher("taskList.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
