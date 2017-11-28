package com.example.user.wfm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by USER on 11/27/2017.
 */

public class PreviousAdapter extends RecyclerView.Adapter<PreviousAdapter.MyViewHolder> {


    Context context;

    public PreviousAdapter(Context context){


        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.previous_list_model , parent , false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {




        holder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (holder.linear.getVisibility() == View.VISIBLE)
                {
                    holder.linear.setVisibility(View.GONE);
                }
                else if (holder.linear.getVisibility() == View.GONE)
                {
                    holder.linear.setVisibility(View.VISIBLE);
                }


            }
        });



    }

    @Override
    public int getItemCount() {
        return 12;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView name  , contact , address , date , number;

        ImageView down;

        LinearLayout linear;


        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.name);

            contact = (TextView)itemView.findViewById(R.id.contact);

            address = (TextView)itemView.findViewById(R.id.address);

            number = (TextView)itemView.findViewById(R.id.number);

            date = (TextView)itemView.findViewById(R.id.date);

            down = (ImageView) itemView.findViewById(R.id.down);

            linear = (LinearLayout) itemView.findViewById(R.id.linear);

        }
    }
}
