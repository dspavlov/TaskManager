package ru.sheykin.DAO;

import ru.sheykin.model.User;

public interface UserDataManipulation {

    int add(User user);

    User get(String userName);

    boolean isUserExists(String userName);
}
