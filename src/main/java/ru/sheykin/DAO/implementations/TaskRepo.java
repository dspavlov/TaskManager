package ru.sheykin.DAO.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sheykin.DAO.TaskDao;
import ru.sheykin.model.Task;
import ru.sheykin.util.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskRepo implements TaskDao<Task> {

    private static final Logger LOG = LoggerFactory.getLogger(TaskRepo.class);
    private static final String INSERT_TASK = "INSERT INTO tasks (name, details, userId, date, goalId) VALUES (?, ?, ?, ?, ?);";
    private static final String SELECT_TASK_BY_ID = "SELECT id, name, details, date FROM tasks WHERE id = ?;";
    private static final String SELECT_ALL_TASKS = "SELECT * FROM tasks WHERE userId = ?;";
    private static final String DELETE_TASK_BY_ID = "DELETE FROM tasks WHERE id = ?;";
    private static final String UPDATE_TASK_BY_ID = "UPDATE tasks SET name = ?, details = ?, date = ?, goalId = ? WHERE id = ?;";

    TaskRepo() {
    }

    public int add(Task task) {
        int status = 0;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TASK)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDetails());
            preparedStatement.setInt(3, task.getUserId());
            preparedStatement.setObject(4, task.getDate());
            preparedStatement.setInt(5, task.getGoalId());
            status = preparedStatement.executeUpdate();
            LOG.debug("Task add: The task has been added, name: {}", task.getName());
        } catch (SQLException throwables) {
            LOG.error("Task add: Failed to add new task: {}", task.getName());
            LOG.error("Exception: ", throwables);
        }
        return status;
    }

    public int update(Task task) {
        int status = 0;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TASK_BY_ID)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDetails());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(task.getDate()));
            preparedStatement.setInt(4, task.getId());
            preparedStatement.setInt(5, task.getGoalId());
            status = preparedStatement.executeUpdate();
            LOG.debug("Task update: The task has been updated, name: {}", task.getName());
        } catch (SQLException throwables) {
            LOG.error("Task update: Failed to update the task: {}", task.getName());
            LOG.error("Exception: ", throwables);
        }
        return status;
    }

    public Optional<Task> get(int id) {
        Task task = null;

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TASK_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String details = rs.getString("details");
                LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
                int goalId = rs.getInt("goalId");
                task = new Task(id, name, details, date, goalId);
            }
        } catch (SQLException throwables) {
            LOG.error("Task get: Failed to select the task with id: {}", id);
            LOG.error("Exception: ", throwables);
        }
        return Optional.ofNullable(task);
    }

    public List<Task> getAll(int userId) {
        List<Task> tasks = new ArrayList<>();

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TASKS)) {
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String details = rs.getString("details");
                LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
                int goalId = rs.getInt("goalId");
                tasks.add(new Task(id, name, details, date, goalId));
            }
        } catch (SQLException throwables) {
            LOG.error("Task selectAll: Failed to select all the tasks for current user with id: {}", userId);
            LOG.error("Exception: ", throwables);
        }
        return tasks;
    }

    public int delete(int id) {
        int status = 0;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TASK_BY_ID)) {
            preparedStatement.setInt(1, id);
            status = preparedStatement.executeUpdate();
            LOG.debug("Task delete: Task deleted, id: {}", id);
        } catch (SQLException throwables) {
            LOG.error("Task delete: Failed to delete the task with id: {}", id);
            LOG.error("Exception: ", throwables);
        }
        return status;
    }
}