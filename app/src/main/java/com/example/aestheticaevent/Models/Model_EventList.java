package com.example.aestheticaevent.Models;
public class Model_EventList {
    private String date;
    private String name;
    private String location;

    public Model_EventList(String date, String name, String location) {
        this.date = date;
        this.name = name;
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
