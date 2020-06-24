package ru.sheykin.servlet;

import ru.sheykin.DAO.AuthenticationDAO;
import ru.sheykin.DAO.TaskDAO;
import ru.sheykin.DAO.TaskDataManipulation;
import ru.sheykin.DAO.UserDataManipulation;
import ru.sheykin.model.Task;
import ru.sheykin.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/insert")
public class AddNewTaskServlet extends HttpServlet {
    private UserDataManipulation userDataManipulation;
    private TaskDataManipulation taskDataManipulation;

    @Override
    public void init() throws ServletException {
        userDataManipulation = new AuthenticationDAO();
        taskDataManipulation = new TaskDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = userDataManipulation.getUserId((User) (req.getSession().getAttribute("userName")));
        String name = req.getParameter("name");
        String details = req.getParameter("details");
        LocalDateTime ldt = LocalDateTime.now();
        Task task = new Task(name, details, userId, ldt);
        taskDataManipulation.insertTask(task);
        resp.sendRedirect("list");
    }
}
