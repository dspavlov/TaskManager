package ru.sheykin.DAO;

import ru.sheykin.model.Task;

import java.util.List;

public interface TaskDataManipulation {

    void insertTask(Task task);

    void updateTask(Task task);

    Task selectTask(int id);

    List<Task> selectAllTasks(Task task);

    boolean deleteTask(int id);
}
