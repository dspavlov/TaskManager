package ru.sheyk.DAO;

import ru.sheyk.model.Task;

import java.util.List;

public interface TaskDataManipulation {

    void insertTask(Task task);

    void updateTask(Task task);

    Task selectTask(int id);

    List<Task> selectAllTasks(Task task);

    boolean deleteTask(int id);

}
