package com.example.aestheticaevent.MoreSettings.Ticket.TicketRespomse;

import java.io.Serializable;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class EditListResponse implements Serializable, Parcelable
{

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("user_image")
    @Expose
    private String userImage;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<EditListResponse> CREATOR = new Creator<EditListResponse>() {


        public EditListResponse createFromParcel(android.os.Parcel in) {
            return new EditListResponse(in);
        }

        public EditListResponse[] newArray(int size) {
            return (new EditListResponse[size]);
        }

    }
            ;
    private final static long serialVersionUID = -2394139186975139607L;

    @SuppressWarnings({
            "unchecked"
    })
    protected EditListResponse(android.os.Parcel in) {
        this.userId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.username = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.mobile = ((String) in.readValue((String.class.getClassLoader())));
        this.userImage = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public EditListResponse() {
    }

    /**
     *
     * @param userImage
     * @param mobile
     * @param message
     * @param userId
     * @param email
     * @param username
     * @param status
     */
    public EditListResponse(Integer userId, String username, String email, String mobile, String userImage, String message, String status) {
        super();
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.userImage = userImage;
        this.message = message;
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
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
        dest.writeValue(username);
        dest.writeValue(email);
        dest.writeValue(mobile);
        dest.writeValue(userImage);
        dest.writeValue(message);
        dest.writeValue(status);
    }

    public int describeContents() {
        return 0;
    }

}