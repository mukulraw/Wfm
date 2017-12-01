package com.example.user.wfm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.wfm.LoginPOJO.LoginBean;

import java.util.Objects;

import github.nisrulz.easydeviceinfo.base.EasyBatteryMod;
import github.nisrulz.easydeviceinfo.base.EasyConfigMod;
import github.nisrulz.easydeviceinfo.base.EasyDeviceMod;
import github.nisrulz.easydeviceinfo.base.EasyLocationMod;
import github.nisrulz.easydeviceinfo.base.EasySimMod;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText email, pass;

    Button sign;

    ProgressBar bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        bar = (ProgressBar) findViewById(R.id.progress);


        sign = (Button) findViewById(R.id.sign);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String e = email.getText().toString();
                String p = pass.getText().toString();


                if (e.length() > 0)

                {
                    if (p.length() > 0) {

                        Log.d("flkdb" ,"response");


                        EasyDeviceMod easyDeviceMod = new EasyDeviceMod(MainActivity.this);

                        EasyLocationMod easyLocationMod = new EasyLocationMod(MainActivity.this);

                        EasyBatteryMod easyBatteryMod = new EasyBatteryMod(MainActivity.this);

                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
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

                        Bean b = (Bean)getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseURL)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        Allapi cr = retrofit.create(Allapi.class);
                        Call<LoginBean> call = cr.login(e , p , imei ,device ,  lat , lon ,String.valueOf( battery) );
                        call.enqueue(new Callback<LoginBean>() {
                            @Override
                            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {


                                if (Objects.equals(response.body().getStatus() , "1")){

                                    Intent i = new Intent(MainActivity .this , Home.class);
                                    startActivity(i);


                                    Log.d("fgblkdfh" , "fdhbgf");
                                }

                                else {

                                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                bar.setVisibility(View.GONE);





                            }

                            @Override
                            public void onFailure(Call<LoginBean> call, Throwable t) {

                                bar.setVisibility(View.GONE);

                                Log.d("hgdlh" , t.toString());



                            }
                        });



                    }

                    else {

                        Toast.makeText(MainActivity.this, "Please enter a valid password", Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Toast.makeText(MainActivity.this, "Please enter a valid Email", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
