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

    public int add(User user) {
        int status = 0;
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
        return status;
    }

    public User get(String userName) {
        User user = new User();
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_USERNAME_SQL);
            preparedStatement.setString(1, userName);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user.setUserId(rs.getInt("userId"));
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