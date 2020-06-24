package ru.sheykin.DAO;

import ru.sheykin.model.User;

public interface UserDataManipulation {

    int getUserId(User user);

    User getUser(String userName);

    void addUser(User user);
}
