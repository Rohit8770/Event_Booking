package com.example.aestheticaevent.Models;
public class Model_EventList {
    private String eventName;
    private int eventImageResource;

    public Model_EventList(String eventName, int eventImageResource) {
        this.eventName = eventName;
        this.eventImageResource = eventImageResource;
    }

    public String getEventName() {
        return eventName;
    }

    public int getEventImageResource() {
        return eventImageResource;
    }
}
