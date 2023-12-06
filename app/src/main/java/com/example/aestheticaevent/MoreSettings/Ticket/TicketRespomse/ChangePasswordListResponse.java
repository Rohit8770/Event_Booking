package com.example.aestheticaevent.MoreSettings.Ticket.TicketRespomse;

import java.io.Serializable;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class ChangePasswordListResponse implements Serializable, Parcelable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<ChangePasswordListResponse> CREATOR = new Creator<ChangePasswordListResponse>() {


        public ChangePasswordListResponse createFromParcel(android.os.Parcel in) {
            return new ChangePasswordListResponse(in);
        }

        public ChangePasswordListResponse[] newArray(int size) {
            return (new ChangePasswordListResponse[size]);
        }

    }
            ;
    private final static long serialVersionUID = 7438243934758704177L;

    @SuppressWarnings({
            "unchecked"
    })
    protected ChangePasswordListResponse(android.os.Parcel in) {
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public ChangePasswordListResponse() {
    }

    /**
     *
     * @param message
     * @param status
     */
    public ChangePasswordListResponse(String message, String status) {
        super();
        this.message = message;
        this.status = status;
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
        dest.writeValue(message);
        dest.writeValue(status);
    }

    public int describeContents() {
        return 0;
    }

}