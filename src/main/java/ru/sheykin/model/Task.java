package ru.sheykin.model;

public class Task {

    private int id;
    private String name;
    private String details;
    private int userId;

    public Task() {
    }

    public Task(int id, String name, String details) {
        this.id = id;
        this.name = name;
        this.details = details;
    }

    public Task(String name, String details, int userId) {
        this.name = name;
        this.details = details;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public int getUserId() {
        return userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}