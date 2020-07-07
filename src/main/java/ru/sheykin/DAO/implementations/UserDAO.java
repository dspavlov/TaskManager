package ru.sheykin.DAO.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sheykin.DAO.UserDataManipulation;
import ru.sheykin.model.User;
import ru.sheykin.util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements UserDataManipulation {

    private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class);
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
            LOG.debug("addUser : The user has been added, name: {}", user.getUserName());
        } catch (SQLException throwables) {
            LOG.error("addUser : Failed to add new user: {}", user.getUserName());
            LOG.error("Exception: ", throwables);
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
            LOG.debug("getUser : The user has been found, userName: {}", userName);
        } catch (SQLException throwables) {
            LOG.error("getUser : Failed to find the user with name: {}", userName);
            LOG.error("Exception: ", throwables);
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
            LOG.debug("getUserId : The user has been found, userName: {}", user.getUserName());
        } catch (SQLException throwables) {
            LOG.error("getUserId : Failed to find the user with name: {}", user.getUserName());
            LOG.error("Exception: ", throwables);
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
            LOG.debug("isUserExist : The user has been found, userName: {}", userName);
        } catch (SQLException throwables) {
            LOG.error("isUserExist : Failed to find the user with name: {}", userName);
            LOG.error("Exception: ", throwables);
        }
        return flag;
    }
}