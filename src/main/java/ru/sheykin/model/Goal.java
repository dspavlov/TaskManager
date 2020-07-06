package ru.sheykin.model;

public class Goal {

    private int id;
    private String goalName;

    public Goal(int id, String goalName) {
        this.id = id;
        this.goalName = goalName;
    }

    public int getId() {
        return id;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }
}
