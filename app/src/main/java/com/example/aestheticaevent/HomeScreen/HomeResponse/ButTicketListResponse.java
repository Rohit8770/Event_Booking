 package com.example.aestheticaevent.HomeScreen.HomeResponse;

import java.io.Serializable;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ButTicketListResponse implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<ButTicketListResponse> CREATOR = new Creator<ButTicketListResponse>() {


        public ButTicketListResponse createFromParcel(android.os.Parcel in) {
            return new ButTicketListResponse(in);
        }

        public ButTicketListResponse[] newArray(int size) {
            return (new ButTicketListResponse[size]);
        }

    }
            ;
    private final static long serialVersionUID = -1775840735720283296L;

    @SuppressWarnings({
            "unchecked"
    })
    protected ButTicketListResponse(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ButTicketListResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(message);
        dest.writeValue(status);
    }

    public int describeContents() {
        return 0;
    }

}