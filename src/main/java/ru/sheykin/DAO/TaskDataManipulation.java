package ru.sheykin.DAO;

import ru.sheykin.model.Task;

import java.util.List;

public interface TaskDataManipulation {

    int add(Task task);

    int update(Task task);

    Task get(int id);

    List<Task> getAll(int userId);

    int delete(int id);
}
