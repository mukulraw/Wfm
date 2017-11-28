package com.example.user.wfm;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by USER on 11/27/2017.
 */

public class Active extends Fragment {

    TextView date , number , contact , address , paymenttype , name  , notdelivery , delivery;

    RadioGroup radioGroup;

    EditText bankname;

    LinearLayout bank , paytm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.active , container , false);

        date = (TextView)view.findViewById(R.id.date);

        number = (TextView)view.findViewById(R.id.number);

        contact = (TextView)view.findViewById(R.id.contact);

        address = (TextView)view.findViewById(R.id.address);

        paymenttype = (TextView)view.findViewById(R.id.paymenttype);

        name = (TextView)view.findViewById(R.id.name);

        notdelivery = (TextView)view.findViewById(R.id.notdelivery);

        delivery = (TextView)view.findViewById(R.id.delivery);

        bankname = (EditText) view.findViewById(R.id.bankname);

        radioGroup = (RadioGroup)view.findViewById(R.id.radiogroup);

        bank = (LinearLayout) view.findViewById(R.id.linearbank);
        paytm = (LinearLayout) view.findViewById(R.id.linearpaytm);



        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog);
                dialog.setCancelable(true);
                dialog.show();

                TextView delivery = (TextView)dialog.findViewById(R.id.deliveryconfirm);
                TextView go = (TextView)dialog.findViewById(R.id.go);


                go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });


                delivery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });


            }
        });


        notdelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.failed_delivery);
                dialog.setCancelable(true);
                dialog.show();


                EditText search  = (EditText)dialog.findViewById(R.id.selectdelivery);
                TextView delivery = (TextView)dialog.findViewById(R.id.confirm);
                TextView go = (TextView)dialog.findViewById(R.id.go);


                go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });



                delivery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });

            }


        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId == R.id.credit){

                    Log.d("ytryr" , "dyhgfj");

                    bank.setVisibility(View.VISIBLE);
                    paytm.setVisibility(View.GONE);
                }
                else if (selectedId == R.id.paytmmm){

                    paytm.setVisibility(View .VISIBLE);
                    bank.setVisibility(View.GONE);
                }

                else if (selectedId == R.id.cash){

                    bank.setVisibility(View.GONE);
                    paytm.setVisibility(View.GONE);
                }



            }
        });


        return view;
    }
}
