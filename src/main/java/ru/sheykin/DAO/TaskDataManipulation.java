package ru.sheykin.DAO;

import ru.sheykin.model.Task;

import java.util.List;

public interface TaskDataManipulation {

    int addTask(Task task);

    int updateTask(Task task);

    Task selectTask(int id);

    List<Task> selectAllTasks(int userId);

    int deleteTask(int id);
}
