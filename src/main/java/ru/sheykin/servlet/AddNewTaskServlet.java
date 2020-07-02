package ru.sheykin.servlet;

import ru.sheykin.DAO.*;
import ru.sheykin.model.Task;
import ru.sheykin.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static javax.servlet.http.HttpServletResponse.*;

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
        int userId = userDataManipulation.getUserId((User) (req.getSession().getAttribute("userName")));
        String name = req.getParameter("name");
        String details = req.getParameter("details");
        LocalDateTime ldt = LocalDateTime.now();
        Task task = new Task(name, details, userId, ldt);
        if (taskDataManipulation.addTask(task) == 1) {
            resp.setStatus(SC_CREATED);
            resp.sendRedirect("list");
        } else
            resp.setStatus(SC_BAD_REQUEST);
    }
}
