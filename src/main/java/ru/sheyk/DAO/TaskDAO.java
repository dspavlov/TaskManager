package ru.sheyk.DAO;

import ru.sheyk.model.Task;
import ru.sheyk.model.User;
import ru.sheyk.util.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    private static final String INSERT_TASK = "INSERT INTO tasks (name, details) VALUES (?, ?);";
    private static final String SELECT_TASK_BY_ID = "SELECT id, name, details FROM tasks WHERE id = ?;";
    private static final String SELECT_ALL_TASKS = "SELECT * FROM tasks;";
    private static final String DELETE_TASK_BY_ID = "DELETE FROM tasks WHERE id = ?;";
    private static final String UPDATE_TASK_BY_ID = "UPDATE tasks SET name = ?, details = ? WHERE id = ?;";
    private static final String GET_USER_BY_ID = "SELECT userId FROM users WHERE userName = ?;";

    public void insertTask(Task task) {
        try (PreparedStatement preparedStatement = DataSource.getConnection().prepareStatement(INSERT_TASK)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDetails());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateTask(Task task) {
        try (PreparedStatement preparedStatement = DataSource.getConnection().prepareStatement(UPDATE_TASK_BY_ID)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Task selectTask(int id) {
        Task task = null;

        try (PreparedStatement preparedStatement = DataSource.getConnection().prepareStatement(SELECT_TASK_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String details = rs.getString("details");
                task = new Task(id, name, details);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return task;
    }

    public List<Task> selectAllTasks() {
        List<Task> tasks = new ArrayList<>();

        try (PreparedStatement preparedStatement = DataSource.getConnection().prepareStatement(SELECT_ALL_TASKS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String details = rs.getString("details");
                tasks.add(new Task(id, name, details));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tasks;
    }

    public boolean deleteTask(int id) {
        boolean rowDeleted = false;
        try (PreparedStatement preparedStatement = DataSource.getConnection().prepareStatement(DELETE_TASK_BY_ID)) {
            preparedStatement.setInt(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowDeleted;
    }

    public int getUserId(User user) {
        int userId = 0;
        try (PreparedStatement preparedStatement = DataSource.getConnection().prepareStatement(GET_USER_BY_ID)) {
            preparedStatement.setString(1, user.getUserName());
            ResultSet rs = preparedStatement.executeQuery();
            userId = rs.getInt("userId");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userId;
    }
}