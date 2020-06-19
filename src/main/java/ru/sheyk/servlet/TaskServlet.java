package ru.sheyk.servlet;

import ru.sheyk.DAO.TaskDAO;
import ru.sheyk.model.Task;
import ru.sheyk.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class TaskServlet extends HttpServlet {
    private TaskDAO taskDAO;

    @Override
    public void init() throws ServletException {
        taskDAO = new TaskDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();

        switch (action) {
            case "/new":
                showNewForm(req, resp);
                break;
            case "/insert":
                insertTask(req, resp);
                break;
            case "/delete":
                deleteTask(req, resp);
                break;
            case "/edit":
                showEditForm(req, resp);
                break;
            case "/update":
                updateTask(req, resp);
                break;
            default:
                showTaskList(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("taskForm.jsp");
        dispatcher.forward(req, resp);
    }

    private void insertTask(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int userId = taskDAO.getUserId((User)(req.getSession().getAttribute("userName")));
        String name = req.getParameter("name");
        String details = req.getParameter("details");
        Task task = new Task(name, details, userId);
        taskDAO.insertTask(task);
        resp.sendRedirect("list");
    }

    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        taskDAO.deleteTask(id);
        resp.sendRedirect("list");
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Task task = taskDAO.selectTask(id);
        RequestDispatcher dispatcher = req.getRequestDispatcher("taskForm.jsp");
        req.setAttribute("task", task);
        dispatcher.forward(req, resp);
    }

    private void updateTask(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String details = req.getParameter("details");

        Task task = new Task(id, name, details);
        taskDAO.updateTask(task);
        resp.sendRedirect("list");
    }

    private void showTaskList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Task> showTaskList = taskDAO.selectAllTasks();
        req.setAttribute("showTaskList", showTaskList);

        RequestDispatcher dispatcher = req.getRequestDispatcher("taskList.jsp");
        dispatcher.forward(req, resp);
    }
}
