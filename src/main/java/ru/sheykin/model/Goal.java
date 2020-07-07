package ru.sheykin.model;

public class Goal {

    private int id;
    private String goalName;
    private int userId;

    public Goal(int id, String goalName) {
        this.id = id;
        this.goalName = goalName;
    }

    public Goal(String goalName, int userId) {
        this.goalName = goalName;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public String getGoalName() {
        return goalName;
    }

    public int getUserId() {
        return userId;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
