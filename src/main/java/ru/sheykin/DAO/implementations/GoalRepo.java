package ru.sheykin.DAO.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sheykin.DAO.GoalDao;
import ru.sheykin.model.Goal;
import ru.sheykin.util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GoalRepo implements GoalDao<Goal> {

    private static final Logger LOG = LoggerFactory.getLogger(GoalRepo.class);
    private static final String GET_GOAL_BY_ID = "SELECT goalName FROM goals WHERE goalId = ?;";
    private static final String INSERT_GOAL = "INSERT INTO goals (goalName, userId) VALUES (?, ?);";
    private static final String SELECT_ALL_GOALS = "SELECT * FROM goals WHERE userId = ?;";
    private static final String DELETE_GOAL_BY_ID = "DELETE FROM goals WHERE goalId = ?;";

    @Override
    public Optional<Goal> get(int id) {
        Goal goal = null;

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_GOAL_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String goalName = rs.getString("goalName");
                goal = new Goal(id, goalName);
            }
        } catch (SQLException throwables) {
            LOG.error("Goal get: Failed to select the goal");
            LOG.error("Exception: ", throwables);
        }
        return Optional.ofNullable(goal);
    }

    public List<Goal> getAll(int userId) {
        List<Goal> goals = new ArrayList<>();

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_GOALS)) {
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int goalId = rs.getInt("goalId");
                String goalName = rs.getString("goalName");
                goals.add(new Goal(goalId, goalName));
            }
        } catch (SQLException throwables) {
            LOG.error("Goal selectAll : Failed to select all the goal for current user with id: {}", userId);
            LOG.error("Exception: ", throwables);
        }
        return goals;
    }

    @Override
    public int delete(int id) {
        int status = 0;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_GOAL_BY_ID)) {
            preparedStatement.setInt(1, id);
            status = preparedStatement.executeUpdate();
            LOG.debug("Goal delete: Goal deleted, id: {}", id);
        } catch (SQLException throwables) {
            LOG.error("Goal delete: Failed to delete the goal with id: {}", id);
            LOG.error("Exception: ", throwables);
        }
        return status;
    }

    @Override
    public int add(Goal goal) {
        int status = 0;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GOAL)) {
            preparedStatement.setString(1, goal.getName());
            preparedStatement.setInt(2, goal.getUserId());
            status = preparedStatement.executeUpdate();
            LOG.debug("Goal add: The goal has been added, name: {}", goal.getName());
        } catch (SQLException throwables) {
            LOG.error("Goal add: Failed to add new task: {}", goal.getName());
            LOG.error("Exception: ", throwables);
        }
        return status;
    }
}
