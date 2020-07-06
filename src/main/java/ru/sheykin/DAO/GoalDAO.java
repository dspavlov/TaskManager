package ru.sheykin.DAO;

import ru.sheykin.model.Goal;
import ru.sheykin.util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GoalDAO implements GoalDataManipulation {

    private static final String GET_GOAL_BY_ID = "SELECT goalName FROM goals WHERE goalId = ?;";

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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return goal;
    }
}
