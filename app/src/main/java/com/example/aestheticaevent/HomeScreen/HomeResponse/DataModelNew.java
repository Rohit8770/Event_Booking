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
         String sub_category_id;
         @SerializedName("category_id")
         @Expose
         String category_id;
         @SerializedName("sub_category_name")
         @Expose
         String sub_category_name;
         @SerializedName("location")
         @Expose
         String location;
         @SerializedName("start_time")
         @Expose
         String start_time;
         @SerializedName("end_time")
         @Expose
         String end_time;

         @SerializedName("date")
         @Expose
         String date;
         @SerializedName("picture")
         @Expose
         String picture;
         @SerializedName("organizer")
         @Expose
         String organizer;
         @SerializedName("event_id")
         @Expose
         String event_id;
         @SerializedName("desciprtion")
         @Expose
         String desciprtion;
         @SerializedName("price")
         @Expose
         String price;
         @SerializedName("venue")
         @Expose
         String venue;


         public newList(String sub_category_id, String category_id, String sub_category_name, String location, String start_time, String end_time, String date, String picture, String organizer, String event_id, String desciprtion, String price, String venue) {
             this.sub_category_id = sub_category_id;
             this.category_id = category_id;
             this.sub_category_name = sub_category_name;
             this.location = location;
             this.start_time = start_time;
             this.end_time = end_time;
             this.date = date;
             this.picture = picture;
             this.organizer = organizer;
             this.event_id = event_id;
             this.desciprtion = desciprtion;
             this.price = price;
             this.venue = venue;
         }

         public String getSub_category_id() {
             return sub_category_id;
         }

         public void setSub_category_id(String sub_category_id) {
             this.sub_category_id = sub_category_id;
         }

         public String getCategory_id() {
             return category_id;
         }

         public void setCategory_id(String category_id) {
             this.category_id = category_id;
         }

         public String getSub_category_name() {
             return sub_category_name;
         }

         public void setSub_category_name(String sub_category_name) {
             this.sub_category_name = sub_category_name;
         }

         public String getLocation() {
             return location;
         }

         public void setLocation(String location) {
             this.location = location;
         }

         public String getStart_time() {
             return start_time;
         }

         public void setStart_time(String start_time) {
             this.start_time = start_time;
         }

         public String getEnd_time() {
             return end_time;
         }

         public void setEnd_time(String end_time) {
             this.end_time = end_time;
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

         public String getEvent_id() {
             return event_id;
         }

         public void setEvent_id(String event_id) {
             this.event_id = event_id;
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

         public String getVenue() {
             return venue;
         }

         public void setVenue(String venue) {
             this.venue = venue;
         }
     }
 }
