package com.example.aestheticaevent.MoreSettings.Ticket.TicketRespomse;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ticketdetails {

    @SerializedName("ticket_id")
    @Expose
    private String ticketId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("event_id")
    @Expose
    private String eventId;
    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("sub_category_name")
    @Expose
    private String subCategoryName;
    @SerializedName("timing")
    @Expose
    private String timing;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("ticket_time")
    @Expose
    private String ticketTime;
    @SerializedName("organizer")
    @Expose
    private String organizer;

    public Ticketdetails(String ticketId, String userId, String eventId, String subCategoryId, String username, String subCategoryName, String timing, String date, String location, String price, String ticketTime, String organizer) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.eventId = eventId;
        this.subCategoryId = subCategoryId;
        this.username = username;
        this.subCategoryName = subCategoryName;
        this.timing = timing;
        this.date = date;
        this.location = location;
        this.price = price;
        this.ticketTime = ticketTime;
        this.organizer = organizer;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTicketTime() {
        return ticketTime;
    }

    public void setTicketTime(String ticketTime) {
        this.ticketTime = ticketTime;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }
}