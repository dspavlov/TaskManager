package ru.sheyk.DAO;

import ru.sheyk.model.User;
import ru.sheyk.util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationDAO implements UserDataManipulation {

    private static final String GET_USER_BY_ID = "SELECT userId FROM users WHERE userName = ?;";
    private static final String USERNAME_SQL = "INSERT INTO users (userName, password) VALUES (?, ?);";
    private static final String VALIDATION_SQL = "SELECT * FROM users WHERE userName = ? AND password = ?;";

    public void addUser(User user) {

        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(USERNAME_SQL);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean validateUser(User user) {

        boolean status = false;

        try (PreparedStatement preparedStatement = DataSource.getConnection().prepareStatement(VALIDATION_SQL)) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            status = rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }

    public int getUserId(User user) {
        int userId = 0;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID)) {
            preparedStatement.setString(1, user.getUserName());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                userId = rs.getInt("userId");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userId;
    }

}
