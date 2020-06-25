package ru.sheykin.servlet;

import ru.sheykin.DAO.DAOFactory;
import ru.sheykin.DAO.DAOTypes;
import ru.sheykin.DAO.TaskDAO;
import ru.sheykin.DAO.TaskDataManipulation;
import ru.sheykin.model.Task;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/edit")
public class EditTaskServlet extends HttpServlet {

    private TaskDataManipulation taskDataManipulation;

    @Override
    public void init() throws ServletException {
        taskDataManipulation = DAOFactory.getDaoFactory().getTaskDataManipulationInstance(DAOTypes.SQL);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Task task = taskDataManipulation.selectTask(id);
        RequestDispatcher dispatcher = req.getRequestDispatcher("taskForm.jsp");
        req.setAttribute("task", task);
        dispatcher.forward(req, resp);
    }
}
