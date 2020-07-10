package ru.sheykin.DAO;

import ru.sheykin.model.User;
import java.util.Optional;

public interface UserDao<T> {

    int add(T t);

    Optional<User> get(String userName);

    boolean isExist(String userName);
}
