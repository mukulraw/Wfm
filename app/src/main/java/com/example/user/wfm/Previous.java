package com.example.user.wfm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.wfm.LoginPOJO.LoginBean;
import com.example.user.wfm.PreviousPOJO.Datum;
import com.example.user.wfm.PreviousPOJO.PreviousBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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

    private void load()
    {


        bar.setVisibility(View.VISIBLE);

        Bean b = (Bean)getContext().getApplicationContext();

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


                if (response.body().getData().size()>0){

                    grid.setVisibility(View.VISIBLE);




                }
                else {


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

}


