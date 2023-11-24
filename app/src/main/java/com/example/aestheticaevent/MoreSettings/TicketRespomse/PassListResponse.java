package com.example.aestheticaevent.MoreSettings.TicketRespomse;

import java.io.Serializable;
import java.util.List;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PassListResponse implements Serializable, Parcelable
{

    @SerializedName("ticketdetailsList")
    @Expose
    private List<Ticketdetails> ticketdetailsList;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<PassListResponse> CREATOR = new Creator<PassListResponse>() {


        public PassListResponse createFromParcel(android.os.Parcel in) {
            return new PassListResponse(in);
        }

        public PassListResponse[] newArray(int size) {
            return (new PassListResponse[size]);
        }

    }
            ;
    private final static long serialVersionUID = 3295262138739932826L;

    @SuppressWarnings({
            "unchecked"
    })
    protected PassListResponse(android.os.Parcel in) {
        in.readList(this.ticketdetailsList, (Ticketdetails.class.getClassLoader()));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public PassListResponse() {
    }

    public List<Ticketdetails> getTicketdetailsList() {
        return ticketdetailsList;
    }

    public void setTicketdetailsList(List<Ticketdetails> ticketdetailsList) {
        this.ticketdetailsList = ticketdetailsList;
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
        dest.writeList(ticketdetailsList);
        dest.writeValue(message);
        dest.writeValue(status);
    }

    public int describeContents() {
        return 0;
    }

}
