package com.example.aestheticaevent.HomeScreen.HomeResponse;

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
    public final static Parcelable.Creator<Subcategory> CREATOR = new Parcelable.Creator<Subcategory>() {


        public Subcategory createFromParcel(android.os.Parcel in) {
            return new Subcategory(in);
        }

        public Subcategory[] newArray(int size) {
            return (new Subcategory[size]);
        }

    };
    private final static long serialVersionUID = -5235805594277337686L;

    @SuppressWarnings({
            "unchecked"
    })
    protected Subcategory(android.os.Parcel in) {
        this.categoryId = ((String) in.readValue((String.class.getClassLoader())));
        this.subCategoryId = ((String) in.readValue((String.class.getClassLoader())));
        this.subCategoryName = ((String) in.readValue((String.class.getClassLoader())));
        this.location = ((String) in.readValue((String.class.getClassLoader())));
        this.timing = ((String) in.readValue((String.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.picture = ((String) in.readValue((String.class.getClassLoader())));
    }

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
