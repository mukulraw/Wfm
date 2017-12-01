package com.example.user.wfm;

import com.example.user.wfm.ActiveOrdersPOJO.ActiveBean;
import com.example.user.wfm.LoginPOJO.LoginBean;
import com.example.user.wfm.OrderUpdatePOJO.OrderUpdateBean;
import com.example.user.wfm.PreviousPOJO.PreviousBean;
import com.example.user.wfm.UndeliveredStatusPOJO.UndeliveredBean;
import com.example.user.wfm.UpdatePOJO.UpdateBean;
import com.example.user.wfm.UpdateTrack.UpdateTrackBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by USER on 11/30/2017.
 */

public interface Allapi {

    @Multipart
    @POST("courier/apiapp/login.php")
    Call<LoginBean> login (
            @Part("email") String m ,
            @Part("password") String P ,
            @Part("IMEI") String I,
            @Part("deviceId") String D ,
            @Part("latitude") String L ,
            @Part("longitude") String lon ,
            @Part("battery") String b
    );


    @Multipart
    @POST("courier/apiapp/activeorders.php")
    Call<ActiveBean> active (
            @Part("username") String m


    );



    @Multipart
    @POST("courier/apiapp/previousorder.php")
    Call<PreviousBean> previous (
            @Part("username") String m


    );



    @Multipart
    @POST("courier/apiapp/updateorder.php")
    Call<UpdateBean> order (
            @Part("username") String m ,
            @Part("awbNo") String a,
            @Part("paymentMode") String p,
            @Part("status") String s,
            @Part("value") String v,
            @Part("IMEI") String i,
            @Part("deviceId") String d,
            @Part("latitude") String l,
            @Part("longitude") String lo,
            @Part("battery") String b,
            @Part("undeliveredStatus") String under

    );


    @GET("courier/apiapp/statusList.php")
    Call<UndeliveredBean> undelivered (

    );

    @Multipart
    @POST("courier/apiapp/updatetrack.php")
    Call<UpdateTrackBean> track (
            @Part("username") String m ,
            @Part("latitude") String a,
            @Part("longitude") String p,
            @Part("battery") String s,
            @Part("IMEI") String v,
            @Part("deviceId") String i


    );





}
