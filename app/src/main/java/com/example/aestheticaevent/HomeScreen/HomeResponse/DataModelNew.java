package com.example.aestheticaevent.HomeScreen.HomeResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

 public class DataModelNew {

     @SerializedName("eventList")
     @Expose
     List<newList> eventList;
     @SerializedName("message")
     @Expose
     String message;
     @SerializedName("status")
     @Expose
     String status;

     public DataModelNew(List<newList> eventList, String message, String status) {
         this.eventList = eventList;
         this.message = message;
         this.status = status;
     }

     public List<newList> getEventList() {
         return eventList;
     }

     public void setEventList(List<newList> eventList) {
         this.eventList = eventList;
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

     public class newList {

         @SerializedName("sub_category_id")
         @Expose
         private String subCategoryId;
         @SerializedName("event_id")
         @Expose
         private String eventId;
         @SerializedName("category_id")
         @Expose
         private String categoryId;
         @SerializedName("sub_category_name")
         @Expose
         private String subCategoryName;
         @SerializedName("venue")
         @Expose
         private String venue;
         @SerializedName("location")
         @Expose
         private String location;
         @SerializedName("timing")
         @Expose
         private String timing;
         @SerializedName("end_time")
         @Expose
         private String endTime;
         @SerializedName("date")
         @Expose
         private String date;
         @SerializedName("picture")
         @Expose
         private String picture;
         @SerializedName("organizer")
         @Expose
         private String organizer;
         @SerializedName("desciprtion")
         @Expose
         private String desciprtion;
         @SerializedName("price")
         @Expose
         private String price;


         public newList(String subCategoryId, String categoryId, String subCategoryName, String venue, String location, String timing, String endTime, String date, String picture,String organizer, String eventId, String desciprtion, String price) {
             this.subCategoryId = subCategoryId;
             this.categoryId = categoryId;
             this.subCategoryName = subCategoryName;
             this.venue = venue;
             this.location = location;
             this.timing = timing;
             this.endTime = endTime;
             this.date = date;
             this.picture = picture;
             this.organizer = organizer;
             this.eventId = eventId;
             this.desciprtion = desciprtion;
             this.price = price;
         }

         public String getSubCategoryId() {
             return subCategoryId;
         }

         public void setSubCategoryId(String subCategoryId) {
             this.subCategoryId = subCategoryId;
         }

         public String getCategoryId() {
             return categoryId;
         }

         public void setCategoryId(String categoryId) {
             this.categoryId = categoryId;
         }

         public String getSubCategoryName() {
             return subCategoryName;
         }

         public void setSubCategoryName(String subCategoryName) {
             this.subCategoryName = subCategoryName;
         }

         public String getVenue() {
             return venue;
         }

         public void setVenue(String venue) {
             this.venue = venue;
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

         public String getEndTime() {
             return endTime;
         }

         public void setEndTime(String endTime) {
             this.endTime = endTime;
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


         public String getOrganizer() {
             return organizer;
         }

         public void setOrganizer(String organizer) {
             this.organizer = organizer;
         }

         public String getEventId() {
             return eventId;
         }

         public void setEventId(String eventId) {
             this.eventId = eventId;
         }

         public String getDesciprtion() {
             return desciprtion;
         }

         public void setDesciprtion(String desciprtion) {
             this.desciprtion = desciprtion;
         }

         public String getPrice() {
             return price;
         }

         public void setPrice(String price) {
             this.price = price;
         }
     }
 }
