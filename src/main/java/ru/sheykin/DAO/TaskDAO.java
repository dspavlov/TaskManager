package ru.sheykin.DAO;

import ru.sheykin.model.Task;
import ru.sheykin.util.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO implements TaskDataManipulation {

    private static final String INSERT_TASK = "INSERT INTO tasks (name, details, userId, date) VALUES (?, ?, ?, ?);";
    private static final String SELECT_TASK_BY_ID = "SELECT id, name, details FROM tasks WHERE id = ?;";
    private static final String SELECT_ALL_TASKS = "SELECT * FROM tasks WHERE userId = ?;";
    private static final String DELETE_TASK_BY_ID = "DELETE FROM tasks WHERE id = ?;";
    private static final String UPDATE_TASK_BY_ID = "UPDATE tasks SET name = ?, details = ?, date= ? WHERE id = ?;";

    TaskDAO() {
    }

    public void insertTask(Task task) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TASK)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDetails());
            preparedStatement.setInt(3, task.getUserId());
            preparedStatement.setObject(4, task.getDate());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateTask(Task task) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TASK_BY_ID)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDetails());
            preparedStatement.setInt(3, task.getId());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(task.getDate()));
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Task selectTask(int id) {
        Task task = null;

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TASK_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String details = rs.getString("details");
                LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
                task = new Task(id, name, details, date);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return task;
    }

    public List<Task> selectAllTasks(Task task) {
        List<Task> tasks = new ArrayList<>();

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TASKS)) {
            preparedStatement.setInt(1, task.getUserId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String details = rs.getString("details");
                LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
                tasks.add(new Task(id, name, details, date));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tasks;
    }

    public boolean deleteTask(int id) {
        boolean rowDeleted = false;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TASK_BY_ID)) {
            preparedStatement.setInt(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowDeleted;
    }
}