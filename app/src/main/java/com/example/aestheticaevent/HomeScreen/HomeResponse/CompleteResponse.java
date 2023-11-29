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


public class CompleteResponse implements Serializable, Parcelable
{

    @SerializedName("closeeventList")
    @Expose
    private List<Closeevent> closeeventList;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<CompleteResponse> CREATOR = new Creator<CompleteResponse>() {


        public CompleteResponse createFromParcel(android.os.Parcel in) {
            return new CompleteResponse(in);
        }

        public CompleteResponse[] newArray(int size) {
            return (new CompleteResponse[size]);
        }

    }
            ;
    private final static long serialVersionUID = -4462266916500516363L;

    @SuppressWarnings({
            "unchecked"
    })
    protected CompleteResponse(android.os.Parcel in) {
        in.readList(this.closeeventList, (com.example.aestheticaevent.HomeScreen.HomeResponse.Closeevent.class.getClassLoader()));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public CompleteResponse() {
    }

    public List<Closeevent> getCloseeventList() {
        return closeeventList;
    }

    public void setCloseeventList(List<Closeevent> closeeventList) {
        this.closeeventList = closeeventList;
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
        dest.writeList(closeeventList);
        dest.writeValue(message);
        dest.writeValue(status);
    }

    public int describeContents() {
        return 0;
    }

}