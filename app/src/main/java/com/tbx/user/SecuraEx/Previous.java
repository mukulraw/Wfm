package com.tbx.user.SecuraEx;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tbx.user.SecuraEx.PreviousPOJO.Datum;
import com.tbx.user.SecuraEx.PreviousPOJO.PreviousBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by USER on 11/27/2017.
 */

public class Previous extends Fragment {

    RecyclerView grid;

    GridLayoutManager manager;

    PreviousAdapter adapter;

    ProgressBar bar;

    List<Datum> list;
    SwipeRefreshLayout swipeRefreshLayout;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.previous , container , false);

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                load();
            }
        });



        grid = (RecyclerView)view.findViewById(R.id.grid);

        bar = (ProgressBar)view.findViewById(R.id.progress);

        manager = new GridLayoutManager(getContext() , 1);

        list = new ArrayList<>();

        adapter = new PreviousAdapter(getContext(), list);

        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);

        load();



        return view;
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


    private void load()
    {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

        if (isDeviceLocationEnabled(getContext())) {


            bar.setVisibility(View.VISIBLE);

            Bean b = (Bean) getContext().getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseURL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Allapi cr = retrofit.create(Allapi.class);
            Call<PreviousBean> call = cr.previous(b.username);

            call.enqueue(new Callback<PreviousBean>() {
                @Override
                public void onResponse(Call<PreviousBean> call, Response<PreviousBean> response) {


                    if (response.body().getData().size() > 0) {

                        grid.setVisibility(View.VISIBLE);


                    } else {


                        Toast.makeText(getContext(), "No Previous order found", Toast.LENGTH_LONG).show();

                        grid.setVisibility(View.GONE);
                    }


                    adapter.setgrid(response.body().getData());

                    bar.setVisibility(View.GONE);

                    swipeRefreshLayout.setRefreshing(false);


                }

                @Override
                public void onFailure(Call<PreviousBean> call, Throwable t) {

                    bar.setVisibility(View.GONE);

                    swipeRefreshLayout.setRefreshing(false);

                }
            });

        }
        else {

            showGPSDisabledAlertToUser();
        }


    }




    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(callGPSSettingIntent , 12);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        Toast.makeText(getContext() , "GPS is required for this app", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        getActivity().finish();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

}


