package com.example.user.wfm;

import com.example.user.wfm.ActiveOrdersPOJO.ActiveBean;
import com.example.user.wfm.LoginPOJO.LoginBean;
import com.example.user.wfm.OrderUpdatePOJO.OrderUpdateBean;
import com.example.user.wfm.PreviousPOJO.PreviousBean;
import com.example.user.wfm.UndeliveredStatusPOJO.UndeliveredBean;
import com.example.user.wfm.UpdateTrack.UpdateTrackBean;

import retrofit2.Call;
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
    @POST("waschen_api/sign_up.php")
    Call<ActiveBean> active (
            @Part("email") String m


    );




    @Multipart
    @POST("waschen_api/sign_up.php")
    Call<PreviousBean> previous (
            @Part("email") String m ,
            @Part("password") String c

    );




    @Multipart
    @POST("waschen_api/sign_up.php")
    Call<OrderUpdateBean> order (
            @Part("email") String m ,
            @Part("password") String c

    );



    @Multipart
    @POST("waschen_api/sign_up.php")
    Call<UndeliveredBean> undelivered (
            @Part("email") String m ,
            @Part("password") String c

    );



    @Multipart
    @POST("waschen_api/sign_up.php")
    Call<UpdateTrackBean> update (
            @Part("email") String m ,
            @Part("password") String c

    );






}
