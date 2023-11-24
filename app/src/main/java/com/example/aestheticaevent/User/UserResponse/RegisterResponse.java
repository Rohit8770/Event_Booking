package com.example.aestheticaevent.User.UserResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("status")
        @Expose
        private String status;

    public RegisterResponse(String message, String status) {
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

    }

