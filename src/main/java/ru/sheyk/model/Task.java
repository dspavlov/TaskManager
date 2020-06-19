package ru.sheyk.model;

public class Task {

    private int id;
    private String name;
    private String details;

    public Task() {
    }

    public Task(String name, String details) {
        this.name = name;
        this.details = details;
    }

    public Task(int id, String name, String details) {
        this.id = id;
        this.name = name;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}