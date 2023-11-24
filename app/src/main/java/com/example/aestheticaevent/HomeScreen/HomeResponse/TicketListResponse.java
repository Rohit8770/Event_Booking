package com.example.aestheticaevent.HomeScreen.HomeResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TicketListResponse {

    @SerializedName("userdetailsList")
    @Expose
    private List<Userdetails> userdetailsList;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public TicketListResponse(List<Userdetails> userdetailsList, String message, String status) {
        this.userdetailsList = userdetailsList;
        this.message = message;
        this.status = status;
    }

    public List<Userdetails> getUserdetailsList() {
        return userdetailsList;
    }

    public void setUserdetailsList(List<Userdetails> userdetailsList) {
        this.userdetailsList = userdetailsList;
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

    public class Userdetails{
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("sub_category_name")
        @Expose
        private String subCategoryName;
        @SerializedName("timing")
        @Expose
        private String timing;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("current_time")
        @Expose
        private String currentTime;
        @SerializedName("organizer")
        @Expose
        private String organizer;

        public Userdetails(String userId, String username, String subCategoryName, String timing, String date, String location, String price, String currentTime, String organizer) {
            this.userId = userId;
            this.username = username;
            this.subCategoryName = subCategoryName;
            this.timing = timing;
            this.date = date;
            this.location = location;
            this.price = price;
            this.currentTime = currentTime;
            this.organizer = organizer;
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

        public String getSubCategoryName() {
            return subCategoryName;
        }

        public void setSubCategoryName(String subCategoryName) {
            this.subCategoryName = subCategoryName;
        }

        public String getTiming() {
            return timing;
        }

        public void setTiming(String timing) {
            this.timing = timing;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(String currentTime) {
            this.currentTime = currentTime;
        }

        public String getOrganizer() {
            return organizer;
        }

        public void setOrganizer(String organizer) {
            this.organizer = organizer;
        }
    }

}
