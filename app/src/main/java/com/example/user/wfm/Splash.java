package com.example.user.wfm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {



    Timer time;

    SharedPreferences pref;

    ProgressBar bar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        pref  = getSharedPreferences("pref" , Context.MODE_PRIVATE);

        bar = (ProgressBar) findViewById(R.id.progress);


        time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {

                Intent i = new Intent(Splash.this ,MainActivity .class);
                startActivity(i);
                finish();

            }
        } , 1500);



    }


}
