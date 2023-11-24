package com.example.aestheticaevent.HomeScreen.HomeResponse;

import java.io.Serializable;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class EventinfoListResponse implements Serializable, Parcelable {

    @SerializedName("eventList")
    @Expose
    private List<Event> eventList;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<EventinfoListResponse> CREATOR = new Creator<EventinfoListResponse>() {


        public EventinfoListResponse createFromParcel(android.os.Parcel in) {
            return new EventinfoListResponse(in);
        }

        public EventinfoListResponse[] newArray(int size) {
            return (new EventinfoListResponse[size]);
        }

    }
            ;
    private final static long serialVersionUID = -7491891399152125334L;

    @SuppressWarnings({
            "unchecked"
    })
    protected EventinfoListResponse(android.os.Parcel in) {
        in.readList(this.eventList, (com.example.aestheticaevent.HomeScreen.HomeResponse.Event.class.getClassLoader()));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public EventinfoListResponse() {
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeList(eventList);
        dest.writeValue(message);
        dest.writeValue(status);
    }

    public int describeContents() {
        return 0;
    }

}



