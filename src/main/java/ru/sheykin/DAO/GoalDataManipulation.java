package ru.sheykin.DAO;

import ru.sheykin.model.Goal;

public interface GoalDataManipulation {

    Goal getGoal(int id);

    int addGoal(Goal goal);
}
