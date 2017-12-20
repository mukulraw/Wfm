package com.tbx.user.wfm;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tbx.user.wfm.ProgressBarPOJO.ProgressbarBean;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

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
 * Created by USER on 11/28/2017.
 */

public class Weekly extends Fragment {

    PieChart pieChart;
    BarChart chart;

    ProgressBar progress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.daily , container , false);
        progress = (ProgressBar)view.findViewById(R.id.progress);

        pieChart = (PieChart)view.findViewById(R.id.piechart);
        chart = (BarChart)view.findViewById(R.id.barchart);


        LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {


            progress.setVisibility(View.VISIBLE);

            Bean b = (Bean) getContext().getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseURL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Allapi cr = retrofit.create(Allapi.class);


            Call<ProgressbarBean> call = cr.getProgress(b.username, "weekly");

            call.enqueue(new Callback<ProgressbarBean>() {
                @Override
                public void onResponse(Call<ProgressbarBean> call, Response<ProgressbarBean> response) {


                    float total = Float.parseFloat(response.body().getData().getTotalOrder());
                    float delivered = Float.parseFloat(response.body().getData().getTotalDeliveredOrder());
                    float undelivered = Float.parseFloat(response.body().getData().getTotalUndeliveredOrder());


                    Log.d("del", String.valueOf(delivered));
                    Log.d("undel", String.valueOf(undelivered));


                    float delPer = (delivered / total) * 100;
                    float undelPer = (undelivered / total) * 100;


                    Log.d("asdasd", String.valueOf(delPer));
                    Log.d("asdasd", String.valueOf(undelPer));

                    float amount = Float.parseFloat(response.body().getData().getTotalCollectableAmount());


                    if (total > 0) {

                        List<Entry> pieEntries = new ArrayList<>();

                        pieEntries.add(new Entry(delPer, 0));
                        pieEntries.add(new Entry(undelPer, 1));

                        final List<String> labels = new ArrayList<>();

                        labels.add("Delivered");
                        labels.add("Undelivered");


                        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
                        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                        PieData pd = new PieData(labels, pieDataSet);

                        pieChart.setData(pd);

                        ArrayList<BarEntry> bargroup1 = new ArrayList<>();
                        bargroup1.add(new BarEntry(amount, 0));


                        BarDataSet barDataSet1 = new BarDataSet(bargroup1, "Collected Amount");
                        barDataSet1.setColors(ColorTemplate.JOYFUL_COLORS);

                        ArrayList<BarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist
                        dataSets.add(barDataSet1);

                        final List<String> labels2 = new ArrayList<>();

                        labels2.add("Week's amount");

                        BarData data = new BarData(labels2, dataSets);
                        chart.setData(data);

                    }


                    progress.setVisibility(View.GONE);


                }

                @Override
                public void onFailure(Call<ProgressbarBean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });

        }

        else {

            showGPSDisabledAlertToUser();
        }

        return view;
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
