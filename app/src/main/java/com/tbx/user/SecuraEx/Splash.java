package com.tbx.user.SecuraEx;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tbx.user.SecuraEx.LoginPOJO.LoginBean;

import java.util.Objects;
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

public class Splash extends AppCompatActivity implements LocationListener {


    Timer time;

    SharedPreferences pref;

    ProgressBar bar;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    String[] PERMISSIONS = {android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE};


    Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        Criteria criteria = new Criteria();


        String mprovider = locationManager.getBestProvider(criteria, false);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(mprovider);
        locationManager.requestLocationUpdates(mprovider, 15000, 1, this);



        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);

        bar = (ProgressBar) findViewById(R.id.progress);

        if (hasPermissions(this, PERMISSIONS)) {

            startApp();


        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
        }


    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {

                startApp();

            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {

                    Toast.makeText(getApplicationContext(), "Permissions are required for this app", Toast.LENGTH_SHORT).show();
                    finish();

                }
                //permission is denied (and never ask again is  checked)
                //shouldShowRequestPermissionRationale will return false
                else {
                    Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                            .show();
                    finish();
                    //                            //proceed with logic by disabling the related features or quit the app.
                }
            }

        }

    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(callGPSSettingIntent, 12);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "GPS is required for this app", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    public static boolean isDeviceLocationEnabled(Context mContext) {
        int locMode = 0;
        String locProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locMode = Settings.Secure.getInt(mContext.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locProviders = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locProviders);
        }
    }


    public void startApp() {




        if (location != null) {


            Log.d("asdasd" , "Asdad");

            Log.d("lat1" , String.valueOf(location.getLatitude()));
            Log.d("lng1" , String.valueOf(location.getLongitude()));

            String lat1 = String.valueOf(location.getLatitude());
            String lng1 = String.valueOf(location.getLongitude());

            String e = pref.getString("email", "");
            String p = pref.getString("pass", "");


            if (e.length() > 0 && p.length() > 0) {


                EasyDeviceMod easyDeviceMod = new EasyDeviceMod(Splash.this);

                EasyLocationMod easyLocationMod = new EasyLocationMod(Splash.this);

                EasyBatteryMod easyBatteryMod = new EasyBatteryMod(Splash.this);

                if (ActivityCompat.checkSelfPermission(Splash.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
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

                bar.setVisibility(View.VISIBLE);

                Log.d("lkghb", "gfjkl");

                Bean b = (Bean) getApplicationContext();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseURL)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Allapi cr = retrofit.create(Allapi.class);
                Call<LoginBean> call = cr.login(e , p , imei , device , lat1 , lng1 , String.valueOf(battery));

                call.enqueue(new Callback<LoginBean>() {
                    @Override
                    public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {

                        Log.d("lkjfdgh", "response");

                        if (Objects.equals(response.body().getStatus(), "1"))

                        {

                            Toast.makeText(Splash.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            Bean b = (Bean) getApplicationContext();
                            b.username = response.body().getData().getUsername();

                            Intent i = new Intent(Splash.this, Home.class);
                            startActivity(i);
                            finish();

                            bar.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginBean> call, Throwable t) {

                        bar.setVisibility(View.GONE);

                        Log.d("hgf", t.toString());

                    }
                });

            } else {


                time = new Timer();
                time.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        Intent i = new Intent(Splash.this, MainActivity.class);
                        startActivity(i);
                        finish();

                    }
                }, 1500);

            }

        } else {
            showGPSDisabledAlertToUser();
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
