package com.example.aestheticaevent.network;

import com.example.aestheticaevent.HomeScreen.HomeResponse.CategoryListResponse;
import com.example.aestheticaevent.HomeScreen.HomeResponse.CompleteResponse;
import com.example.aestheticaevent.HomeScreen.HomeResponse.DataModelNew;
import com.example.aestheticaevent.HomeScreen.HomeResponse.ForgetPasswordListResponse;
import com.example.aestheticaevent.HomeScreen.HomeResponse.LocationLisResponse;
import com.example.aestheticaevent.HomeScreen.HomeResponse.MobileListResponse;
import com.example.aestheticaevent.HomeScreen.HomeResponse.SubCategoryListResponse;
import com.example.aestheticaevent.HomeScreen.HomeResponse.TicketListResponse;
import com.example.aestheticaevent.HomeScreen.HomeResponse.ButTicketListResponse;
import com.example.aestheticaevent.MoreSettings.Ticket.TicketRespomse.ChangePasswordListResponse;
import com.example.aestheticaevent.MoreSettings.Ticket.TicketRespomse.DeleteListResponse;
import com.example.aestheticaevent.MoreSettings.Ticket.TicketRespomse.EditListResponse;
import com.example.aestheticaevent.MoreSettings.Ticket.TicketRespomse.PassListResponse;
import com.example.aestheticaevent.MoreSettings.Ticket.TicketRespomse.QrListResponse;
import com.example.aestheticaevent.User.UserResponse.LoginResponse;
import com.example.aestheticaevent.User.UserResponse.RegisterResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Single;

public interface Restcall {

    @Multipart
    @POST("controller/registercontroller.php")
    Single<RegisterResponse> RegisterUser(
            @Part("tag") RequestBody tag,
            @Part("username") RequestBody username,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part MultipartBody.Part user_image,
            @Part("mobile") RequestBody mobile,
            @Part("device_token") RequestBody device_token);

    @FormUrlEncoded
    @POST("controller/registercontroller.php")
    Single<LoginResponse> LoginUser(
            @Field("tag") String tag,
            @Field("email") String email,
            @Field("password") String password);


   /* @FormUrlEncoded
    @POST("controller/categorycontroller.php")
    Single<CategoryListResponse> getcategory(
            @Field("tag") String tag);*/

    @FormUrlEncoded
    @POST("Controller/EventBooking.php")
    Single<CategoryListResponse> getcategory(
            @Field("tag") String tag);


    @FormUrlEncoded
    @POST("controller/upcomingevent.php")
    Single<CompleteResponse> Getclosedevents(
            @Field("tag") String tag,
            @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST("controller/upcomingevent.php")
    Single<SubCategoryListResponse> Getupcomingevents(
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
            @Field("username") String username,
            @Field("sub_category_id") String sub_category_id,
            @Field("qty_member") String qty_member,
            @Field("category_id") String category_id,
            @Field("user_id") String user_id);

    @FormUrlEncoded 
    @POST("controller/passcontrollerapi.php")
    Single<PassListResponse> GetTicketDetails(
            @Field("tag") String tag,
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("controller/subcategorycontroller.php")
    Single<LocationLisResponse> GetsubcategoryCall(
            @Field("tag") String tag,
            @Field("category_id") String category_id);


    @FormUrlEncoded
    @POST("controller/registercontroller.php")
    Single<DeleteListResponse> Deleteuser(
            @Field("tag") String tag,
            @Field("user_id") String user_id);

    @Multipart
    @POST("controller/passwordcontroller.php")
    Single<EditListResponse> Editprofile(
            @Part("tag") RequestBody tag,
            @Part("user_id") RequestBody user_id,
            @Part("username") RequestBody username,
            @Part("email") RequestBody email,
            @Part MultipartBody.Part user_image,
            @Part("mobile") RequestBody mobile);

    @FormUrlEncoded
    @POST("controller/passwordcontroller.php")
    Single<ChangePasswordListResponse> ChangePassword(
            @Field("tag") String tag,
            @Field("user_id") String user_id,
            @Field("password") String password,
            @Field("newPass") String newPass,
            @Field("conPass") String conPass);

    @FormUrlEncoded
    @POST("controller/passwordcontroller.php")
    Single<ForgetPasswordListResponse> AddForgetNewPassword(
            @Field("tag") String tag,
            @Field("user_id") String user_id,
            @Field("newPass") String newPass,
            @Field("conPass") String conPass,
            @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("controller/passwordcontroller.php")
    Single<MobileListResponse> GetAllData(
            @Field("tag") String tag);


/*    @FormUrlEncoded
    @POST("controller/qrcodecontroller.php")
    Single<QrListResponse> AddticketdetailCall(
            @Field("tag") String tag,
            @Field("category_id") String category_id,
            @Field("event_id") String event_id,
            @Field("user_id") String user_id,
            @Field("qty_member") String qty_member,
            @Field("sub_category_id") String sub_category_id);*/

    @FormUrlEncoded
    @POST("controller/qrApicontroller.php")
    Single<QrListResponse> Qrcodegenerate(
            @Field("tag") String tag,
            @Field("event_id") String event_id,
            @Field("username") String username,
            @Field("date") String date,
            @Field("timing") String timing,
            @Field("qty_member") String qty_member,
            @Field("user_id") String user_id);
}


