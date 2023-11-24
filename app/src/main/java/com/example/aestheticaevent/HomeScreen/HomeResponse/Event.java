package com.example.aestheticaevent.HomeScreen.HomeResponse;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("sub_category_name")
    @Expose
    private String subCategoryName;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("timing")
    @Expose
    private String timing;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("organizer")
    @Expose
    private String organizer;
    @SerializedName("event_id")
    @Expose
    private String eventId;
    @SerializedName("desciprtion")
    @Expose
    private String desciprtion;
    public final static Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {


        public Event createFromParcel(android.os.Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return (new Event[size]);
        }

    };
    private final static long serialVersionUID = -6453554019198855564L;

    @SuppressWarnings({
            "unchecked"
    })
    protected Event(android.os.Parcel in) {
        this.subCategoryId = ((String) in.readValue((String.class.getClassLoader())));
        this.categoryId = ((String) in.readValue((String.class.getClassLoader())));
        this.subCategoryName = ((String) in.readValue((String.class.getClassLoader())));
        this.location = ((String) in.readValue((String.class.getClassLoader())));
        this.timing = ((String) in.readValue((String.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.picture = ((String) in.readValue((String.class.getClassLoader())));
        this.organizer = ((String) in.readValue((String.class.getClassLoader())));
        this.eventId = ((String) in.readValue((String.class.getClassLoader())));
        this.desciprtion = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Event() {
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getDesciprtion() {
        return desciprtion;
    }

    public void setDesciprtion(String desciprtion) {
        this.desciprtion = desciprtion;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(subCategoryId);
        dest.writeValue(categoryId);
        dest.writeValue(subCategoryName);
        dest.writeValue(location);
        dest.writeValue(timing);
        dest.writeValue(date);
        dest.writeValue(picture);
        dest.writeValue(organizer);
        dest.writeValue(eventId);
        dest.writeValue(desciprtion);
    }

    public int describeContents() {
        return 0;
    }

}
