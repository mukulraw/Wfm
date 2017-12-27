package com.tbx.user.SecuraEx;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.tbx.user.SecuraEx.UpdateTrack.UpdateTrackBean;

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

public class NotifyService extends Service implements LocationListener {

    Timer timer;
    ConnectionDetector cd;
    Location location;

    private void doSomethingRepeatedly() {
        timer = new Timer();
        timer.scheduleAtFixedRate( new TimerTask() {
            public void run() {


                //Log.d("asdasd" , "asdasdasd");

                try{

                    if (cd.isConnectingToInternet())
                    {

                        final LocationManager locationManager = (LocationManager)getApplicationContext() .getSystemService(LOCATION_SERVICE);


                        Criteria criteria = new Criteria();


                        String mprovider = locationManager.getBestProvider(criteria, false);


                        if (ActivityCompat.checkSelfPermission(NotifyService.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NotifyService.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, NotifyService.this , Looper.getMainLooper());


                        final String lat1 = String.valueOf(location.getLatitude());
                        final String lng1 = String.valueOf(location.getLongitude());


                        Log.d("asda" , lat1);
                        Log.d("asda" , lng1);
                        Log.d("asda" , "asdasd");


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
                        Call<UpdateTrackBean> call = cr.track(b.username , lat1 , lng1 , String.valueOf(battery) , imei , device);

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
                    e.printStackTrace();
                }

            }
        }, 0 ,1000 * 60 * 15);



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


    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
