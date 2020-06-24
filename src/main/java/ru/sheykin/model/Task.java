package ru.sheykin.model;

import java.time.LocalDateTime;

public class Task {

    private int id;
    private String name;
    private String details;
    private int userId;
    private LocalDateTime date;

    public Task(int id, String name, String details, int userId, LocalDateTime date) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.userId = userId;
        this.date = date;
    }

    public Task(int id, String name, String details, LocalDateTime date) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.date = date;
    }

    public Task(String name, String details, int userId, LocalDateTime date) {
        this.name = name;
        this.details = details;
        this.userId = userId;
        this.date = date;
    }

    public Task() {
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

    public LocalDateTime getDate() {
        return date;
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