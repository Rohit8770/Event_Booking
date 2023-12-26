package com.example.aestheticaevent.HomeScreen.HomeResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class CategoryListResponse {

    @SerializedName("GetcategoryList")
    @Expose
    public List<Getcategory> getcategoryList;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("status")
    @Expose
    public String status;

    public CategoryListResponse(List<Getcategory> getcategoryList, String message, String status) {
        this.getcategoryList = getcategoryList;
        this.message = message;
        this.status = status;
    }

    public List<Getcategory> getGetcategoryList() {
        return getcategoryList;
    }

    public void setGetcategoryList(List<Getcategory> getcategoryList) {
        this.getcategoryList = getcategoryList;
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


    public class Getcategory {

        @SerializedName("category_name")
        @Expose
        public String categoryName;
        @SerializedName("category_image")
        @Expose
        public String categoryImage;

        public Getcategory(String categoryName, String categoryImage) {
            this.categoryName = categoryName;
            this.categoryImage = categoryImage;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryImage() {
            return categoryImage;
        }

        public void setCategoryImage(String categoryImage) {
            this.categoryImage = categoryImage;
        }
    }
}