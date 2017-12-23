package com.tbx.user.SecuraEx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Home extends AppCompatActivity {


    SharedPreferences pref;

    SharedPreferences.Editor edit;

    ImageView home , logout , bar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        pref = getSharedPreferences("hjdf" , MODE_PRIVATE);
        edit = pref.edit();

        bar = (ImageView) findViewById(R.id.bar);
        home = (ImageView) findViewById(R.id.home);
        logout = (ImageView) findViewById(R.id.logout);



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Home.this , MainActivity.class);
                startActivity(i);
                finish();



            }
        });

        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Progress fragment = new Progress();
                ft.replace(R.id.replace , fragment);
                ft.commit();

            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Orders fragment = new Orders();
                ft.replace(R.id.replace , fragment);
                ft.commit();

            }
        });


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Orders fragment = new Orders();
        ft.replace(R.id.replace , fragment);
        ft.commit();



        Intent intent = new Intent(Home.this, NotifyService.class);

        Home.this.startService(intent);

    }









}
