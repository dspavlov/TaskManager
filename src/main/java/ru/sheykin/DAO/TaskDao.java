package ru.sheykin.DAO;

import java.util.List;
import java.util.Optional;

public interface TaskDao<T> {

    int add(T t);

    int update(T t);

    Optional<T> get(int id);

    List<T> getAll(int userId);

    int delete(int id);
}