package ru.sheykin.DAO.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sheykin.DAO.GoalDataManipulation;
import ru.sheykin.model.Goal;
import ru.sheykin.util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoalDAO implements GoalDataManipulation {

    private static final Logger LOG = LoggerFactory.getLogger(GoalDAO.class);
    private static final String GET_GOAL_BY_ID = "SELECT goalName FROM goals WHERE goalId = ?;";
    private static final String INSERT_GOAL = "INSERT INTO goals (goalName, userId) VALUES (?, ?);";
    private static final String SELECT_ALL_GOALS = "SELECT * FROM goals WHERE userId = ?;";

    @Override
    public Goal getGoal(int id) {
        Goal goal = null;

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_GOAL_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String goalName = rs.getString("goalName");
                goal = new Goal(id, goalName);
            }
            if (goal != null) {
                LOG.debug("getGoal : The goal has been selected: {}", goal.getGoalName());
            }
        } catch (SQLException throwables) {
            LOG.error("Failed to add new goal");
            LOG.error("Exception: ", throwables);
        }
        return goal;
    }

    @Override
    public int addGoal(Goal goal) {
        int status = 0;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GOAL)) {
            preparedStatement.setString(1, goal.getGoalName());
            preparedStatement.setInt(2, goal.getUserId());
            status = preparedStatement.executeUpdate();
            LOG.debug("addGoal : The goal has been added, name: {}", goal.getGoalName());
        } catch (SQLException throwables) {
            LOG.error("addGoal : Failed to add new task: {}", goal.getGoalName());
            LOG.error("Exception: ", throwables);
        }
        return status;
    }

    public List<Goal> selectAllGoals(int userId) {
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
            LOG.debug("selectAllGoals : All the goals have been selected for user id: {}", userId);
        } catch (SQLException throwables) {
            LOG.error("selectAllGoals : Failed to select all the goal for current user with id: {}", userId);
            LOG.error("Exception: ", throwables);
        }
        return goals;
    }
}
