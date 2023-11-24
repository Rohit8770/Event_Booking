package com.example.aestheticaevent.network;

import com.example.aestheticaevent.HomeScreen.HomeResponse.CategoryListResponse;
import com.example.aestheticaevent.HomeScreen.HomeResponse.DataModelNew;
import com.example.aestheticaevent.HomeScreen.HomeResponse.SubCategoryListResponse;
import com.example.aestheticaevent.HomeScreen.HomeResponse.TicketListResponse;
import com.example.aestheticaevent.MoreSettings.TicketRespomse.ButTicketListResponse;
import com.example.aestheticaevent.MoreSettings.TicketRespomse.PassListResponse;
import com.example.aestheticaevent.User.UserResponse.LoginResponse;
import com.example.aestheticaevent.User.UserResponse.RegisterResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Single;

public interface Restcall {

    @FormUrlEncoded
    @POST("controller/registercontroller.php")
    Single<RegisterResponse> RegisterUser(
            @Field("tag") String tag,
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("confirm_password") String confirm_password);

    @FormUrlEncoded
    @POST("controller/registercontroller.php")
    Single<LoginResponse> LoginUser(
            @Field("tag") String tag,
            @Field("email") String email,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("controller/categorycontroller.php")
    Single<CategoryListResponse> getcategory(
            @Field("tag") String tag);

    @FormUrlEncoded
    @POST("controller/subcategorycontroller.php")
    Single<SubCategoryListResponse> Getsubcategory(
            @Field("tag") String tag,
            @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST("controller/eventinfoconteroller.php")
    Single<DataModelNew> Geteventinfo(
            @Field("tag") String tag,
            @Field("sub_category_id") String sub_category_id);

    @FormUrlEncoded
    @POST("controller/passcontrollerapi.php")
    Single<TicketListResponse> Getpassdetails(
            @Field("tag") String tag,
            @Field("event_id") String event_id,
            @Field("user_id") String user_id,
            @Field("sub_category_id") String sub_category_id);

    @FormUrlEncoded
    @POST("controller/passcontrollerapi.php")
    Single<ButTicketListResponse> AddTicketDetails(
            @Field("tag") String tag,
            @Field("event_id") String event_id,
            @Field("user_id") String user_id,
            @Field("sub_category_id") String sub_category_id,
            @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST("controller/passcontrollerapi.php")
    Single<PassListResponse> GetTicketDetails(
            @Field("tag") String tag,
            @Field("event_id") String event_id,
            @Field("user_id") String user_id,
            @Field("sub_category_id") String sub_category_id,
            @Field("ticket_id") String ticket_id);

}


