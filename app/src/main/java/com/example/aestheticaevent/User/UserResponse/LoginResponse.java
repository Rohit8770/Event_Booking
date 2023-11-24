package com.example.aestheticaevent.User.UserResponse;

import java.io.Serializable;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse implements Serializable, Parcelable {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<LoginResponse> CREATOR = new Creator<LoginResponse>() {


        public LoginResponse createFromParcel(android.os.Parcel in) {
            return new LoginResponse(in);
        }

        public LoginResponse[] newArray(int size) {
            return (new LoginResponse[size]);
        }

    }
            ;
    private final static long serialVersionUID = -1357585573421634550L;


    protected LoginResponse(android.os.Parcel in) {
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.username = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public LoginResponse() {
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


    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(userId);
        dest.writeValue(username);
        dest.writeValue(email);
        dest.writeValue(message);
        dest.writeValue(status);
    }

    public int describeContents() {
        return 0;
    }

}



