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

/**
 * Shows task edit form
 */
@WebServlet("/edit")
public class ShowEditTaskFormServlet extends HttpServlet {

    private TaskDao<Task> taskDao;

    @Override
    public void init() throws ServletException {
        taskDao = DAOFactory.getDaoFactory().getTaskDataManipulationInstance(DAOTypes.SQL);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Task task = taskDao.get(id).get();
        RequestDispatcher dispatcher = req.getRequestDispatcher("taskForm.jsp");
        req.setAttribute("task", task);
        dispatcher.forward(req, resp);
    }
}
