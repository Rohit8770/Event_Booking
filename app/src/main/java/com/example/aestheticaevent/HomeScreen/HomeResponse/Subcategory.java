package com.example.aestheticaevent.HomeScreen.HomeResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subcategory {

    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("sub_category_name")
    @Expose
    private String subCategoryName;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("timing")
    @Expose
    private String timing;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("status")
    @Expose
    private String status;


    public final static Parcelable.Creator<Subcategory> CREATOR = new Parcelable.Creator<Subcategory>() {


        @Override
        public Subcategory createFromParcel(Parcel source) {
            return null;
        }

        public Subcategory[] newArray(int size) {
            return (new Subcategory[size]);
        }

    };
    private final static long serialVersionUID = -5235805594277337686L;

    public Subcategory(String categoryId, String subCategoryId, String subCategoryName, String location, String timing, String date, String picture, String price, String status) {
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.location = location;
        this.timing = timing;
        this.date = date;
        this.picture = picture;
        this.price = price;
        this.status = status;
    }

    @SuppressWarnings({
            "unchecked"
    })


    public Subcategory() {
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTiming() {
        return timing;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(categoryId);
        dest.writeValue(subCategoryId);
        dest.writeValue(subCategoryName);
        dest.writeValue(location);
        dest.writeValue(timing);
        dest.writeValue(date);
        dest.writeValue(picture);
    }

    public int describeContents() {
        return 0;
    }

}
