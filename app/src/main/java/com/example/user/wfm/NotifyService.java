package com.example.user.wfm;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.util.Log;

import com.example.user.wfm.LoginPOJO.LoginBean;
import com.example.user.wfm.UpdateTrack.UpdateTrackBean;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import github.nisrulz.easydeviceinfo.base.EasyBatteryMod;
import github.nisrulz.easydeviceinfo.base.EasyDeviceMod;
import github.nisrulz.easydeviceinfo.base.EasyLocationMod;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by TBX on 12/1/2017.
 */

public class NotifyService extends Service {

    Timer timer;
    ConnectionDetector cd;

    private void doSomethingRepeatedly() {
        timer = new Timer();
        timer.scheduleAtFixedRate( new TimerTask() {
            public void run() {



                try{

                    if (cd.isConnectingToInternet())
                    {



                        EasyDeviceMod easyDeviceMod = new EasyDeviceMod(NotifyService.this);

                        EasyLocationMod easyLocationMod = new EasyLocationMod(NotifyService.this);

                        EasyBatteryMod easyBatteryMod = new EasyBatteryMod(NotifyService.this);

                        if (ActivityCompat.checkSelfPermission(NotifyService.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        String imei = easyDeviceMod.getIMEI();

                        String device = easyDeviceMod.getDevice();

                        //Get Lat-Long
                        double[] l = easyLocationMod.getLatLong();
                        String lat = String.valueOf(l[0]);
                        String lon = String.valueOf(l[1]);

                        int battery = easyBatteryMod.getBatteryPercentage();

                        Bean b = (Bean) getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseURL)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        Allapi cr = retrofit.create(Allapi.class);
                        Call<UpdateTrackBean> call = cr.track(b.username , lat , lon , String.valueOf(battery) , imei , device);

                        call.enqueue(new Callback<UpdateTrackBean>() {
                            @Override
                            public void onResponse(Call<UpdateTrackBean> call, Response<UpdateTrackBean> response) {


                            }

                            @Override
                            public void onFailure(Call<UpdateTrackBean> call, Throwable t) {


                            }
                        });

                    }


                }
                catch (Exception e) {
                    // TODO: handle exception
                }

            }
        }, 0, 1000* 60*15);



    }

    SharedPreferences pref;
    SharedPreferences.Editor edit;


    @Override
    public void onCreate() {



        pref = getApplicationContext().getSharedPreferences("myPref" , Activity.MODE_PRIVATE);
        edit = pref.edit();
        cd = new ConnectionDetector(getApplicationContext());


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        doSomethingRepeatedly();

        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        receive r = new receive();

        if (r!=null) {
            unregisterReceiver(r);
            r=null;
        }

    }


}
