package ru.sheykin.DAO;

import ru.sheykin.model.Goal;

import java.util.List;

public interface GoalDataManipulation {

    Goal get(int id);

    int add(Goal goal);

    List<Goal> getAll(int userId);
}
