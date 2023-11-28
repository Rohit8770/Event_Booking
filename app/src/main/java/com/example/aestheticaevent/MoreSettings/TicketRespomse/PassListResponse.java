package com.example.aestheticaevent.MoreSettings.TicketRespomse;

import java.io.Serializable;
import java.util.List;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PassListResponse {

    @SerializedName("ticketdetailsList")
    @Expose
    private List<Ticketdetails> ticketdetailsList;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    private String status;

    public PassListResponse(List<Ticketdetails> ticketdetailsList, String message, String status) {
        this.ticketdetailsList = ticketdetailsList;
        this.message = message;
        this.status = status;
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
}
