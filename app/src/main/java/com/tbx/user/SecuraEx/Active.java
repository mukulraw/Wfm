package com.tbx.user.SecuraEx;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tbx.user.SecuraEx.ActiveOrdersPOJO.ActiveBean;
import com.tbx.user.SecuraEx.UndeliveredStatusPOJO.UndeliveredBean;
import com.tbx.user.SecuraEx.UpdatePOJO.UpdateBean;

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

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by USER on 11/27/2017.
 */

public class Active extends Fragment implements LocationListener{

    TextView date, number, contact, address, paymenttype, name, notdelivery, delivery, amount, mode;

    RadioGroup radioGroup;

    EditText bankname, paytmno;

    LinearLayout bank, paytm;

    RelativeLayout relativeLayout;

    CardView card;

    ProgressBar bar;

    String payMode = "";

    Location location;

    String value;

    List<String> list;

    SwipeRefreshLayout swipeRefreshLayout;

    ConnectionDetector cd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.active, container, false);

        cd = new ConnectionDetector(getContext());

        /*inal LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);


        Criteria criteria = new Criteria();


        String mprovider = locationManager.getBestProvider(criteria, false);


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

*/



        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                load();
            }
        });


        list = new ArrayList<>();

        date = (TextView) view.findViewById(R.id.date);

        number = (TextView) view.findViewById(R.id.number);

        contact = (TextView) view.findViewById(R.id.contact);

        address = (TextView) view.findViewById(R.id.address);

        card = (CardView) view.findViewById(R.id.card);

        mode = (TextView) view.findViewById(R.id.mode);

        paymenttype = (TextView) view.findViewById(R.id.paymenttype);

        name = (TextView) view.findViewById(R.id.name);

        notdelivery = (TextView) view.findViewById(R.id.notdelivery);

        amount = (TextView) view.findViewById(R.id.amount);

        delivery = (TextView) view.findViewById(R.id.delivery);

        bankname = (EditText) view.findViewById(R.id.bankname);

        paytmno = (EditText) view.findViewById(R.id.paytmno);

        radioGroup = (RadioGroup) view.findViewById(R.id.radiogroup);

        bank = (LinearLayout) view.findViewById(R.id.linearbank);

        paytm = (LinearLayout) view.findViewById(R.id.linearpaytm);

        bar = (ProgressBar) view.findViewById(R.id.progress);

        relativeLayout = (RelativeLayout) view.findViewById(R.id.relative);

        load();

        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cd.isConnectingToInternet())
                {
                    if (isDeviceLocationEnabled(getContext())) {


                        //final String lat1 = String.valueOf(location.getLatitude());
                        //final String lng1 = String.valueOf(location.getLongitude());



                        final Dialog dialog = new Dialog(getActivity());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog);
                        dialog.setCancelable(true);
                        dialog.show();

                        TextView delivery = (TextView) dialog.findViewById(R.id.deliveryconfirm);
                        TextView go = (TextView) dialog.findViewById(R.id.go);
                        final ProgressBar progress = (ProgressBar)dialog.findViewById(R.id.progress);


                        go.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog.dismiss();

                            }
                        });


                        delivery.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                if (payMode.length() > 0) {


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


                                    if (paytmno.getText().toString().length() > 0) {
                                        value = paytmno.getText().toString();
                                    } else {
                                        value = bankname.getText().toString();
                                    }



                                    if (Objects.equals(payMode, "paytm") || Objects.equals(payMode, "card"))
                                    {

                                        if (value.length() > 0)
                                        {
                                            final int battery = easyBatteryMod.getBatteryPercentage();

                                            progress.setVisibility(View.VISIBLE);

                                            Bean b = (Bean) getContext().getApplicationContext();
                                            Retrofit retrofit = new Retrofit.Builder()
                                                    .baseUrl(b.baseURL)
                                                    .addConverterFactory(ScalarsConverterFactory.create())
                                                    .addConverterFactory(GsonConverterFactory.create())
                                                    .build();
                                            Allapi cr = retrofit.create(Allapi.class);
                                            Call<UpdateBean> call = cr.order(b.username, number.getText().toString(), payMode, "delivered", value, imei, device, lat, lon, String.valueOf(battery), "");

                                            call.enqueue(new Callback<UpdateBean>() {
                                                @Override
                                                public void onResponse(Call<UpdateBean> call, Response<UpdateBean> response) {

                                                    radioGroup.clearCheck();


                                                    payMode = "";
                                                    value = "";

                                                    bankname.setText("");
                                                    paytmno.setText("");



                                                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();

                                                    progress.setVisibility(View.GONE);
                                                    load();

                                                }

                                                @Override
                                                public void onFailure(Call<UpdateBean> call, Throwable t) {

                                                    progress.setVisibility(View.GONE);

                                                }
                                            });
                                        }
                                        else
                                        {
                                            Toast.makeText(getContext() , "Please Enter a Value" , Toast.LENGTH_SHORT).show();
                                        }




                                    }
                                    else
                                    {
                                        final int battery = easyBatteryMod.getBatteryPercentage();

                                        progress.setVisibility(View.VISIBLE);

                                        Bean b = (Bean) getContext().getApplicationContext();
                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(b.baseURL)
                                                .addConverterFactory(ScalarsConverterFactory.create())
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();
                                        Allapi cr = retrofit.create(Allapi.class);
                                        Call<UpdateBean> call = cr.order(b.username, number.getText().toString(), payMode, "delivered", value, imei, device, lat, lon, String.valueOf(battery), "");

                                        call.enqueue(new Callback<UpdateBean>() {
                                            @Override
                                            public void onResponse(Call<UpdateBean> call, Response<UpdateBean> response) {

                                                radioGroup.clearCheck();


                                                payMode = "";
                                                value = "";

                                                bankname.setText("");
                                                paytmno.setText("");



                                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();

                                                progress.setVisibility(View.GONE);
                                                load();

                                            }

                                            @Override
                                            public void onFailure(Call<UpdateBean> call, Throwable t) {

                                                progress.setVisibility(View.GONE);

                                            }
                                        });
                                    }




                                } else {

                                    Toast.makeText(getContext(), "Please select the payment mode", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
                    } else {
                        showGPSDisabledAlertToUser();
                    }
                }else
                {
                    Toast.makeText(getContext() , "No Internet Connection" , Toast.LENGTH_SHORT).show();

                }




            }
        });


        notdelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cd.isConnectingToInternet())
                {
                    if (isDeviceLocationEnabled(getContext())) {
                        Log.d("gkjg", "fhjg");

                        //final String lat1 = String.valueOf(location.getLatitude());
                        //final String lng1 = String.valueOf(location.getLongitude());


                        final String[] status = {""};

                        final Dialog dialog = new Dialog(getActivity());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.failed_delivery);
                        dialog.setCancelable(true);
                        dialog.show();


                        final Spinner search = (Spinner) dialog.findViewById(R.id.selectdelivery);
                        TextView delivery = (TextView) dialog.findViewById(R.id.confirm);
                        TextView go = (TextView) dialog.findViewById(R.id.go);

                        final ProgressBar progress = (ProgressBar)dialog.findViewById(R.id.progress);


                        Bean b = (Bean) getContext().getApplicationContext();
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


                                Log.d("hjh", "response");
                                list.clear();

                                list.add("Select Status");

                                for (int i = 0; i < response.body().getData().size(); i++) {

                                    list.add(response.body().getData().get(i).getStatus());

                                }


                                ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, list);

                                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //Setting the ArrayAdapter data on the Spinner
                                search.setAdapter(aa);


                            }

                            @Override
                            public void onFailure(Call<UndeliveredBean> call, Throwable t) {


                                Log.d("jhfjh", t.toString());


                            }
                        });


                        search.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                if (i > 0) {

                                    status[0] = list.get(i);

                                }


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

                                if (status[0].length() > 0) {

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


                                    if (paytmno.getText().toString().length() > 0) {
                                        value = paytmno.getText().toString();
                                    } else {
                                        value = bankname.getText().toString();
                                    }


                                    final int battery = easyBatteryMod.getBatteryPercentage();


                                    progress.setVisibility(View.VISIBLE);

                                    Bean b = (Bean) getContext().getApplicationContext();
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(b.baseURL)
                                            .addConverterFactory(ScalarsConverterFactory.create())
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                                    Allapi cr = retrofit.create(Allapi.class);
                                    Call<UpdateBean> call = cr.order(b.username, number.getText().toString(), "", "undelivered", "", imei, device, lat, lon, String.valueOf(battery), status[0]);

                                    call.enqueue(new Callback<UpdateBean>() {
                                        @Override
                                        public void onResponse(Call<UpdateBean> call, Response<UpdateBean> response) {

                                            radioGroup.clearCheck();

                                            payMode = "";
                                            value = "";


                                            bankname.setText("");
                                            paytmno.setText("");



                                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                            dialog.dismiss();

                                            progress.setVisibility(View.GONE);

                                            load();
                                        }

                                        @Override
                                        public void onFailure(Call<UpdateBean> call, Throwable t) {

                                            progress.setVisibility(View.GONE);

                                        }
                                    });

                                } else {

                                    Toast.makeText(getContext(), "Please Select a Status", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    } else {
                        showGPSDisabledAlertToUser();
                    }
                }
                else
                {
                    Toast.makeText(getContext() , "No Internet Connection" , Toast.LENGTH_SHORT).show();

                }




            }

        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId == R.id.credit) {

                    Log.d("ytryr", "dyhgfj");

                    payMode = "card";


                    bank.setVisibility(View.VISIBLE);
                    paytm.setVisibility(View.GONE);
                } else if (selectedId == R.id.paytmmm) {

                    paytm.setVisibility(View.VISIBLE);
                    bank.setVisibility(View.GONE);

                    payMode = "paytm";

                } else if (selectedId == R.id.cash) {

                    payMode = "cash";

                    bank.setVisibility(View.GONE);
                    paytm.setVisibility(View.GONE);
                }

            }
        });


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


    private void load() {


        if (cd.isConnectingToInternet())
        {
            LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

            if (isDeviceLocationEnabled(getContext())) {


                //String lat1 = String.valueOf(location.getLatitude());
                //String lng1 = String.valueOf(location.getLongitude());


                bar.setVisibility(View.VISIBLE);


                Log.d("dfklgd", "flkjh;fg");

                Bean b = (Bean) getContext().getApplicationContext();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseURL)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Allapi cr = retrofit.create(Allapi.class);
                Call<ActiveBean> call = cr.active(b.username);
                Log.d("hghdf", b.username);

                call.enqueue(new Callback<ActiveBean>() {
                    @Override
                    public void onResponse(Call<ActiveBean> call, Response<ActiveBean> response) {


                        if (response.body().getData().getAWBNo().length() > 0) {

                            relativeLayout.setVisibility(View.VISIBLE);

                        } else {

                            Toast.makeText(getContext(), "No active order found", Toast.LENGTH_LONG).show();

                            relativeLayout.setVisibility(View.GONE);
                        }

                        Log.d("flfkjslk", "response");

                        Log.d("bgjkhl", response.body().getStatus());

                        date.setText(response.body().getData().getDate());
                        number.setText(response.body().getData().getAWBNo());
                        contact.setText(response.body().getData().getContactNo());
                        address.setText(response.body().getData().getAdddress());
                        name.setText(response.body().getData().getCustomerName());
                        paymenttype.setText(response.body().getData().getPaymenttype());
                        amount.setText("Rs. " + response.body().getData().getAmount());

                        if (Objects.equals(response.body().getData().getPaymenttype(), "COD")) {

                            card.setVisibility(View.VISIBLE);
                            mode.setVisibility(View.VISIBLE);

                        } else {
                            card.setVisibility(View.GONE);
                            mode.setVisibility(View.GONE);

                        }

                        bar.setVisibility(View.GONE);

                        swipeRefreshLayout.setRefreshing(false);


                    }

                    @Override
                    public void onFailure(Call<ActiveBean> call, Throwable t) {

                        Log.d("klgklfd", t.toString());

                        bar.setVisibility(View.GONE);

                        swipeRefreshLayout.setRefreshing(false);

                    }
                });
            } else {
                showGPSDisabledAlertToUser();
            }
        }
        else
        {
            Toast.makeText(getContext() , "No Internet Connection" , Toast.LENGTH_SHORT).show();
        }






    }


    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(callGPSSettingIntent, 12);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getContext(), "GPS is required for this app", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        getActivity().finish();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    @Override
    public void onLocationChanged(Location location) {

        this.location = location;

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
