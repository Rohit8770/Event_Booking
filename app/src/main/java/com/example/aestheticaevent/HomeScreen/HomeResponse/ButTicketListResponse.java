 package com.example.aestheticaevent.HomeScreen.HomeResponse;

import java.io.Serializable;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

 @Generated("jsonschema2pojo")
public class ButTicketListResponse implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;


    /**
     * No args constructor for use in serialization
     *
     */
    public ButTicketListResponse() {
    }

    /**
     *
     * @param id
     * @param message
     * @param status
     */
    public ButTicketListResponse(String id, String message, String status) {
        super();
        this.id = id;
        this.message = message;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.message == null)? 0 :this.message.hashCode()));
        result = ((result* 31)+((this.status == null)? 0 :this.status.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ButTicketListResponse) == false) {
            return false;
        }
        ButTicketListResponse rhs = ((ButTicketListResponse) other);
        return ((((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id)))&&((this.message == rhs.message)||((this.message!= null)&&this.message.equals(rhs.message))))&&((this.status == rhs.status)||((this.status!= null)&&this.status.equals(rhs.status))));
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(message);
        dest.writeValue(status);
    }

    public int describeContents() {
        return 0;
    }

}