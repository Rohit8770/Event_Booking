package com.example.aestheticaevent.HomeScreen.HomeResponse;

import java.io.Serializable;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgetPasswordListResponse implements Serializable, Parcelable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    public final static Creator<ForgetPasswordListResponse> CREATOR = new Creator<ForgetPasswordListResponse>() {


        public ForgetPasswordListResponse createFromParcel(android.os.Parcel in) {
            return new ForgetPasswordListResponse(in);
        }

        public ForgetPasswordListResponse[] newArray(int size) {
            return (new ForgetPasswordListResponse[size]);
        }

    }
            ;
    private final static long serialVersionUID = 1893909314536427182L;

    @SuppressWarnings({
            "unchecked"
    })
    protected ForgetPasswordListResponse(android.os.Parcel in) {
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public ForgetPasswordListResponse() {
    }

    /**
     *
     * @param message
     * @param status
     */
    public ForgetPasswordListResponse(String message, Integer status) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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