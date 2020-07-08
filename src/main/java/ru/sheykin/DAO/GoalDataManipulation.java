package ru.sheykin.DAO;

import ru.sheykin.model.Goal;

import java.util.List;

public interface GoalDataManipulation {

    Goal getGoal(int id);

    int addGoal(Goal goal);

    List<Goal> selectAllGoals(int userId);
}
