 package com.example.aestheticaevent.HomeScreen.HomeResponse;

import java.io.Serializable;
import java.util.List;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationLisResponse  {


    @SerializedName("subList")
    @Expose
    private List<Subcategory> subList;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public LocationLisResponse(List<Subcategory> subList, String message, String status) {
        this.subList = subList;
        this.message = message;
        this.status = status;
    }

    public List<Subcategory> getSubList() {
        return subList;
    }

    public void setSubList(List<Subcategory> subList) {
        this.subList = subList;
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

    public class Subcategory {

        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("latitude")
        @Expose
        private String latitude;

        public Subcategory(String longitude, String latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }
    }
}