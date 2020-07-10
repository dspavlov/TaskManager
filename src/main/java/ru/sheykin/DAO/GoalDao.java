package ru.sheykin.DAO;

import java.util.List;
import java.util.Optional;

public interface GoalDao<T> {

    int add(T t);

    Optional<T> get(int id);

    List<T> getAll(int userId);

    int delete(int id);
}