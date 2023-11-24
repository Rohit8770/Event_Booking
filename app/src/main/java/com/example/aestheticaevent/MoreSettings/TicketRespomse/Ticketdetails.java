package com.example.aestheticaevent.MoreSettings.TicketRespomse;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ticketdetails implements Serializable, Parcelable {

    @SerializedName("ticket_id")
    @Expose
    private String ticketId;
    @SerializedName("user_id")
    @Expose
    private String userId;
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
    @SerializedName("current_time")
    @Expose
    private String currentTime;
    @SerializedName("organizer")
    @Expose
    private String organizer;
    public final static Creator<Ticketdetails> CREATOR = new Creator<Ticketdetails>() {


        public Ticketdetails createFromParcel(android.os.Parcel in) {
            return new Ticketdetails(in);
        }

        public Ticketdetails[] newArray(int size) {
            return (new Ticketdetails[size]);
        }

    };
    private final static long serialVersionUID = 7913577259919905328L;

    @SuppressWarnings({
            "unchecked"
    })
    protected Ticketdetails(android.os.Parcel in) {
        this.ticketId = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.username = ((String) in.readValue((String.class.getClassLoader())));
        this.subCategoryName = ((String) in.readValue((String.class.getClassLoader())));
        this.timing = ((String) in.readValue((String.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.location = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.currentTime = ((String) in.readValue((String.class.getClassLoader())));
        this.organizer = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Ticketdetails() {
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

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(ticketId);
        dest.writeValue(userId);
        dest.writeValue(username);
        dest.writeValue(subCategoryName);
        dest.writeValue(timing);
        dest.writeValue(date);
        dest.writeValue(location);
        dest.writeValue(price);
        dest.writeValue(currentTime);
        dest.writeValue(organizer);
    }

    public int describeContents() {
        return 0;
    }

}
