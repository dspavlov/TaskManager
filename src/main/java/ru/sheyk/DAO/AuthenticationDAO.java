package ru.sheyk.DAO;

import ru.sheyk.model.User;
import ru.sheyk.util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationDAO {

    public void addUser(User user) {
        String USERNAME_SQL = "INSERT INTO users" +
                " (userName, password) VALUES " +
                " (?, ?)";

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
        String VALIDATION_SQL = "SELECT * FROM users " +
                " WHERE userName = ? AND password = ?";
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

}
