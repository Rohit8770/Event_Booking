 package com.example.aestheticaevent.MoreSettings.Ticket.TicketRespomse;

import java.io.Serializable;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteListResponse implements Serializable, Parcelable
{

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<DeleteListResponse> CREATOR = new Creator<DeleteListResponse>() {


        public DeleteListResponse createFromParcel(android.os.Parcel in) {
            return new DeleteListResponse(in);
        }

        public DeleteListResponse[] newArray(int size) {
            return (new DeleteListResponse[size]);
        }

    }
            ;
    private final static long serialVersionUID = 580199562988255802L;

    @SuppressWarnings({
            "unchecked"
    })
    protected DeleteListResponse(android.os.Parcel in) {
        this.userId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public DeleteListResponse() {
    }

    public DeleteListResponse(Integer userId, String message, String status) {
        this.userId = userId;
        this.message = message;
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
        dest.writeValue(userId);
        dest.writeValue(message);
        dest.writeValue(status);
    }

    public int describeContents() {
        return 0;
    }

}