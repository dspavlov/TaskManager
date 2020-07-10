package ru.sheykin.controller.servlet;

import ru.sheykin.DAO.implementations.DAOFactory;
import ru.sheykin.DAO.implementations.DAOTypes;
import ru.sheykin.DAO.TaskDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.*;

/**
 * Deletes selected task
 */
@WebServlet("/delete")
public class DeleteTaskServlet extends HttpServlet {

    private TaskDao taskDao;

    @Override
    public void init() throws ServletException {
        taskDao = DAOFactory.getDaoFactory().getTaskDataManipulationInstance(DAOTypes.SQL);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDelete(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        if (taskDao.delete(id) == 1) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("list");
            dispatcher.forward(req, resp);
        } else
            resp.setStatus(SC_NO_CONTENT);
    }
}
