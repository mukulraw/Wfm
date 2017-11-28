package com.example.user.wfm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

/**
 * Created by USER on 11/27/2017.
 */

public class Previous extends Fragment {

    RecyclerView grid;
    GridLayoutManager manager;
    PreviousAdapter adapter;
    ProgressBar bar;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.previous , container , false);

        grid = (RecyclerView)view.findViewById(R.id.grid);

        bar = (ProgressBar)view.findViewById(R.id.progress);

        manager = new GridLayoutManager(getContext() , 1);

        adapter = new PreviousAdapter(getContext());

        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);

        return view;
    }
}
