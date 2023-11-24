package com.example.aestheticaevent.HomeScreen.HomeResponse;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SubCategoryListResponse implements Serializable {

    @SerializedName("subcategoryList")
    @Expose
    private List<Subcategory> subcategoryList;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Parcelable.Creator<SubCategoryListResponse> CREATOR = new Parcelable.Creator<SubCategoryListResponse>() {


        public SubCategoryListResponse createFromParcel(android.os.Parcel in) {
            return new SubCategoryListResponse(in);
        }

        public SubCategoryListResponse[] newArray(int size) {
            return (new SubCategoryListResponse[size]);
        }

    }
            ;
    private final static long serialVersionUID = 8606122694835143423L;

    @SuppressWarnings({
            "unchecked"
    })
    protected SubCategoryListResponse(android.os.Parcel in) {
        in.readList(this.subcategoryList, (com.example.aestheticaevent.HomeScreen.HomeResponse.Subcategory.class.getClassLoader()));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public SubCategoryListResponse() {
    }

    public List<Subcategory> getSubcategoryList() {
        return subcategoryList;
    }

    public void setSubcategoryList(List<Subcategory> subcategoryList) {
        this.subcategoryList = subcategoryList;
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
        dest.writeList(subcategoryList);
        dest.writeValue(message);
        dest.writeValue(status);
    }

    public int describeContents() {
        return 0;
    }
}

