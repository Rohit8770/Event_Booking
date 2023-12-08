package com.example.aestheticaevent.HomeScreen.HomeResponse;

import java.io.Serializable;
import java.util.List;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class MobileListResponse implements Serializable, Parcelable
{

    @SerializedName("usersList")
    @Expose
    private List<Users> usersList;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    private final static long serialVersionUID = -213955001370289544L;

    @SuppressWarnings({
            "unchecked"
    })

    public MobileListResponse() {
    }

    public MobileListResponse(List<Users> usersList, String message, String status) {
        super();
        this.usersList = usersList;
        this.message = message;
        this.status = status;
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
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
        dest.writeList(usersList);
        dest.writeValue(message);
        dest.writeValue(status);
    }

    public int describeContents() {
        return 0;
    }

@Generated("jsonschema2pojo")
public class Users implements Serializable, Parcelable {

    @SerializedName("user_id")
    @Expose
    private String userId;
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

    private final static long serialVersionUID = -1507727439617958955L;

    @SuppressWarnings({
            "unchecked"
    })
    protected Users(android.os.Parcel in) {
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.username = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.mobile = ((String) in.readValue((String.class.getClassLoader())));
        this.userImage = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public Users() {
    }

    /**
     * @param userImage
     * @param mobile
     * @param userId
     * @param email
     * @param username
     */
    public Users(String userId, String username, String email, String mobile, String userImage) {
        super();
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.userImage = userImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(userId);
        dest.writeValue(username);
        dest.writeValue(email);
        dest.writeValue(mobile);
        dest.writeValue(userImage);
    }

    public int describeContents() {
        return 0;
    }

    }
}