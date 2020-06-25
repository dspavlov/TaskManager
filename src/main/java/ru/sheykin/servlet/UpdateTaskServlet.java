package ru.sheykin.servlet;

import ru.sheykin.DAO.DAOFactory;
import ru.sheykin.DAO.DAOTypes;
import ru.sheykin.DAO.TaskDataManipulation;
import ru.sheykin.model.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/update")
public class UpdateTaskServlet extends HttpServlet {

    private TaskDataManipulation taskDataManipulation;

    @Override
    public void init() throws ServletException {
        taskDataManipulation = DAOFactory.getDaoFactory().getTaskDataManipulationInstance(DAOTypes.SQL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String details = req.getParameter("details");
        LocalDateTime ldt = LocalDateTime.now();
        Task task = new Task(id, name, details, ldt);
        taskDataManipulation.updateTask(task);
        resp.sendRedirect("list");
    }
}
