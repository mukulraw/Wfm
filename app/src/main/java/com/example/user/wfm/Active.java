package com.example.user.wfm;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.wfm.ActiveOrdersPOJO.ActiveBean;
import com.example.user.wfm.LoginPOJO.LoginBean;
import com.example.user.wfm.UndeliveredStatusPOJO.UndeliveredBean;
import com.example.user.wfm.UpdatePOJO.UpdateBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import github.nisrulz.easydeviceinfo.base.EasyBatteryMod;
import github.nisrulz.easydeviceinfo.base.EasyDeviceMod;
import github.nisrulz.easydeviceinfo.base.EasyLocationMod;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by USER on 11/27/2017.
 */

public class Active extends Fragment {

    TextView date , number , contact , address , paymenttype , name  , notdelivery , delivery , amount  , mode;

    RadioGroup radioGroup;

    EditText bankname , paytmno;

    LinearLayout bank , paytm;

    CardView card;

    ProgressBar bar;




    String payMode;
    String value;

    List<String> list;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.active , container , false);


        list = new ArrayList<>();

        date = (TextView)view.findViewById(R.id.date);

        number = (TextView)view.findViewById(R.id.number);

        contact = (TextView)view.findViewById(R.id.contact);

        address = (TextView)view.findViewById(R.id.address);

        card = (CardView) view.findViewById(R.id.card);

        mode = (TextView)view.findViewById(R.id.mode);



        paymenttype = (TextView)view.findViewById(R.id.paymenttype);

        name = (TextView)view.findViewById(R.id.name);

        notdelivery = (TextView)view.findViewById(R.id.notdelivery);

        amount = (TextView)view.findViewById(R.id.amount);

        delivery = (TextView)view.findViewById(R.id.delivery);

        bankname = (EditText) view.findViewById(R.id.bankname);

        paytmno = (EditText) view.findViewById(R.id.paytmno);

        radioGroup = (RadioGroup)view.findViewById(R.id.radiogroup);

        bank = (LinearLayout) view.findViewById(R.id.linearbank);

        paytm = (LinearLayout) view.findViewById(R.id.linearpaytm);

        bar = (ProgressBar) view.findViewById(R.id.progress);


        bar.setVisibility(View.VISIBLE);


        Log.d("dfklgd" , "flkjh;fg");

        Bean b = (Bean)getContext().getApplicationContext();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Allapi cr = retrofit.create(Allapi.class);
        Call<ActiveBean> call = cr.active(b.username);
        Log.d("hghdf" , b.username);

        call.enqueue(new Callback<ActiveBean>() {
            @Override
            public void onResponse(Call<ActiveBean> call, Response<ActiveBean> response) {


                Log.d("flfkjslk" , "response");

                Log.d("bgjkhl" , response.body().getStatus());

             date.setText(response.body().getData().getDate());
             number.setText(response.body().getData().getAWBNo());
             contact.setText(response.body().getData().getContactNo());
             address.setText(response.body().getData().getAdddress());
             name.setText(response.body().getData().getCustomerName());
             paymenttype.setText(response.body().getData().getPaymenttype());
             amount.setText("Rs. " + response.body().getData().getAmount());

             if (Objects.equals(response.body().getData().getPaymenttype(), "COD"))
             {

                 card.setVisibility(View.VISIBLE);
                 mode.setVisibility(View.VISIBLE);

             }
             else
             {
                 card.setVisibility(View.GONE);
                 mode.setVisibility(View.GONE);

             }

             bar.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<ActiveBean> call, Throwable t) {

                Log.d("klgklfd" , t.toString());

                bar.setVisibility(View.GONE);

            }
        });



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

                        EasyDeviceMod easyDeviceMod = new EasyDeviceMod(getContext());

                        EasyLocationMod easyLocationMod = new EasyLocationMod(getContext());

                        EasyBatteryMod easyBatteryMod = new EasyBatteryMod(getContext());

                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        String imei = easyDeviceMod.getIMEI();

                        String device = easyDeviceMod.getDevice();

                        //Get Lat-Long
                        double[] l = easyLocationMod.getLatLong();
                        String lat = String.valueOf(l[0]);
                        String lon = String.valueOf(l[1]);



                        if (paytmno.getText().toString().length()>0)
                        {
                            value = paytmno.getText().toString();
                        }
                        else
                        {
                            value = bankname.getText().toString();
                        }



                        final int battery = easyBatteryMod.getBatteryPercentage();



                        bar.setVisibility(View.VISIBLE);

                        Bean b = (Bean)getContext().getApplicationContext();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseURL)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        Allapi cr = retrofit.create(Allapi.class);
                        Call<UpdateBean> call = cr.order(b.username , number.getText().toString() , payMode, "delivered" , value , imei , device , lat , lon , String.valueOf(battery) , "");

                        call.enqueue(new Callback<UpdateBean>() {
                            @Override
                            public void onResponse(Call<UpdateBean> call, Response<UpdateBean> response) {


                                Toast.makeText(getContext(),response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                                bar.setVisibility(View.GONE);


                            }

                            @Override
                            public void onFailure(Call<UpdateBean> call, Throwable t) {

                                bar.setVisibility(View.GONE);

                            }
                        });
                    }
                });



            }
        });


        notdelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("gkjg" , "fhjg");

                final String[] status = {""};

                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.failed_delivery);
                dialog.setCancelable(true);
                dialog.show();


                final Spinner search  = (Spinner) dialog.findViewById(R.id.selectdelivery);
                TextView delivery = (TextView)dialog.findViewById(R.id.confirm);
                TextView go = (TextView)dialog.findViewById(R.id.go);

                Bean b = (Bean)getContext().getApplicationContext();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseURL)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Allapi cr = retrofit.create(Allapi.class);
                Call<UndeliveredBean> call = cr.undelivered();

                call.enqueue(new Callback<UndeliveredBean>() {
                    @Override
                    public void onResponse(Call<UndeliveredBean> call, Response<UndeliveredBean> response) {


                         Log.d("hjh" , "response");
                        list.clear();

                        for (int i = 0 ; i < response.body().getData().size() ; i++)
                        {

                            list.add(response.body().getData().get(i).getStatus());

                        }


                        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,list);

                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Setting the ArrayAdapter data on the Spinner
                        search.setAdapter(aa);


                    }

                    @Override
                    public void onFailure(Call<UndeliveredBean> call, Throwable t) {


                        Log.d("jhfjh" , t.toString());


                    }
                });





                search.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        status[0] = list.get(i);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {



                    }
                });









                go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });



                delivery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        EasyDeviceMod easyDeviceMod = new EasyDeviceMod(getContext());

                        EasyLocationMod easyLocationMod = new EasyLocationMod(getContext());

                        EasyBatteryMod easyBatteryMod = new EasyBatteryMod(getContext());

                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        String imei = easyDeviceMod.getIMEI();

                        String device = easyDeviceMod.getDevice();

                        //Get Lat-Long
                        double[] l = easyLocationMod.getLatLong();
                        String lat = String.valueOf(l[0]);
                        String lon = String.valueOf(l[1]);



                        if (paytmno.getText().toString().length()>0)
                        {
                            value = paytmno.getText().toString();
                        }
                        else
                        {
                            value = bankname.getText().toString();
                        }



                        final int battery = easyBatteryMod.getBatteryPercentage();



                        bar.setVisibility(View.VISIBLE);

                        Bean b = (Bean)getContext().getApplicationContext();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseURL)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        Allapi cr = retrofit.create(Allapi.class);
                        Call<UpdateBean> call = cr.order(b.username , number.getText().toString() , payMode, "undelivered" , value , imei , device , lat , lon , String.valueOf(battery) , status[0]);

                        call.enqueue(new Callback<UpdateBean>() {
                            @Override
                            public void onResponse(Call<UpdateBean> call, Response<UpdateBean> response) {


                                Toast.makeText(getContext(),response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                dialog.dismiss();

                                bar.setVisibility(View.GONE);


                            }

                            @Override
                            public void onFailure(Call<UpdateBean> call, Throwable t) {

                                bar.setVisibility(View.GONE);

                            }
                        });

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

                    payMode = "card";




                    bank.setVisibility(View.VISIBLE);
                    paytm.setVisibility(View.GONE);
                }
                else if (selectedId == R.id.paytmmm){

                    paytm.setVisibility(View .VISIBLE);
                    bank.setVisibility(View.GONE);

                    payMode = "paytm";

                }

                else if (selectedId == R.id.cash){

                    payMode = "cash";

                    bank.setVisibility(View.GONE);
                    paytm.setVisibility(View.GONE);
                }



            }
        });


        return view;
    }
}
