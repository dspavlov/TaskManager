package ru.sheykin.servlet;

import ru.sheykin.DAO.AuthenticationDAO;
import ru.sheykin.DAO.TaskDAO;
import ru.sheykin.DAO.TaskDataManipulation;
import ru.sheykin.DAO.UserDataManipulation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete")
public class DeleteTaskServlet extends HttpServlet {

    private TaskDataManipulation taskDataManipulation;

    @Override
    public void init() throws ServletException {
        taskDataManipulation = new TaskDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        taskDataManipulation.deleteTask(id);
        resp.sendRedirect("list");
    }
}
