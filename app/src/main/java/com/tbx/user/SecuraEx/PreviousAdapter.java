package com.tbx.user.SecuraEx;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbx.user.SecuraEx.PreviousPOJO.Datum;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by USER on 11/27/2017.
 */

public class PreviousAdapter extends RecyclerView.Adapter<PreviousAdapter.MyViewHolder> {


    Context context;

    List<Datum>list = new ArrayList<>();

    public PreviousAdapter(Context context ,  List<Datum>list){


        this.context = context;
        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.previous_list_model , parent , false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Datum item = list.get(position);
        holder.name.setText(item.getCustomerName());
        holder.contact.setText(item.getContactNo());
        holder.address.setText(item.getAdddress());
        holder.date.setText(item.getDate());
        holder.number.setText(item.getAWBNo());
        holder.type.setText(item.getPaymenttype());
        holder.mode.setText(item.getPaymentMode());
        holder.bankname.setText(item.getValue());
        holder.date1.setText(item.getDate());

        if (Objects.equals(item.getStatus(), "undelivered"))
        {
            holder.success.setText(item.getUndeliveredRemark());
        }
        else
        {
            holder.success.setText(item.getStatus());
        }





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

    public void setgrid( List<Datum>list){


        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView name  , contact , address , date , number  , bankname , type , mode , pm , date1 , success;

        ImageView down;

        LinearLayout linear;


        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.name);

            contact = (TextView)itemView.findViewById(R.id.contact);

            address = (TextView)itemView.findViewById(R.id.address);

            number = (TextView)itemView.findViewById(R.id.number);

            date = (TextView)itemView.findViewById(R.id.date);

            type = (TextView)itemView.findViewById(R.id.type);

            mode = (TextView)itemView.findViewById(R.id.mode);

            pm = (TextView)itemView.findViewById(R.id.pm);

            date1 = (TextView)itemView.findViewById(R.id.date1);

            success = (TextView)itemView.findViewById(R.id.success);

            bankname = (TextView)itemView.findViewById(R.id.bankname);

            down = (ImageView) itemView.findViewById(R.id.down);

            linear = (LinearLayout) itemView.findViewById(R.id.linear);

        }
    }
}
