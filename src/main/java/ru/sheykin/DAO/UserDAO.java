package ru.sheykin.DAO;

import ru.sheykin.model.User;
import ru.sheykin.util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements UserDataManipulation {

    private static final String GET_USER_BY_USERNAME = "SELECT * FROM users WHERE userName = ?;";
    private static final String ADD_USER = "INSERT INTO users (userName, password) VALUES (?, ?);";
    private static final String GET_USER_BY_USERNAME_SQL = "SELECT * FROM users WHERE userName = ?;";

    UserDAO() {
    }

    public void addUser(User user) {

        try (Connection connection = ru.sheykin.util.DataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public User getUser(String userName) {
        User user = new User();
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_USERNAME_SQL);
            preparedStatement.setString(1, userName);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    public int getUserId(User user) {
        int userId = 0;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_USERNAME)) {
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

    public boolean isUserExists(String userName) {
        boolean flag = false;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_USERNAME)) {
            preparedStatement.setString(1, userName);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String userNameFromDB = rs.getString("userName");
                if (userNameFromDB.equals(userName)) {
                    flag = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flag;
    }
}