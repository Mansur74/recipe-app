package com.example.recipeapp.Models;

public class NotificationsModel {

    private String message, date;

    public NotificationsModel(String message, String date) {
        this.message = message;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
