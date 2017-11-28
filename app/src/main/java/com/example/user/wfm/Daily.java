package com.example.user.wfm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

/**
 * Created by USER on 11/28/2017.
 */

public class Daily extends Fragment {

    PieChart pieChart;
    BarChart chart;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.daily , container , false);

        pieChart = (PieChart)view.findViewById(R.id.piechart);
        chart = (BarChart)view.findViewById(R.id.barchart);

        List<Entry> pieEntries = new ArrayList<>();

        pieEntries.add(new Entry(20f , 0));
        pieEntries.add(new Entry(20f,1));
        pieEntries.add(new Entry(20f,2));
        pieEntries.add(new Entry(20f,3));
        pieEntries.add(new Entry(20f,4));

        final List<String> labels = new ArrayList<>();

        labels.add("1");
        labels.add("2");
        labels.add("3");
        labels.add("4");
        labels.add("5");
        labels.add("6");
        PieDataSet pieDataSet = new PieDataSet(pieEntries , "Label");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pd = new PieData( labels, pieDataSet);

        pieChart.setData(pd);




      /*  ArrayList<String> labels = new ArrayList<String>();
        labels.add("2016");
        labels.add("2015");
        labels.add("2014");
        labels.add("2013");
        labels.add("2012");
*/

        ArrayList<BarEntry> bargroup1 = new ArrayList<>();
        bargroup1.add(new BarEntry(8f, 0));
        bargroup1.add(new BarEntry(2f, 1));
        bargroup1.add(new BarEntry(5f, 2));
        bargroup1.add(new BarEntry(20f, 3));
        bargroup1.add(new BarEntry(15f, 4));
        bargroup1.add(new BarEntry(19f, 5));

        BarDataSet barDataSet1 = new BarDataSet(bargroup1, "Bar Group 1");
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        ArrayList<BarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist
        dataSets.add(barDataSet1);


        BarData data = new BarData(labels, dataSets);
        chart.setData(data);
        return view;
    }
}
