package com.tbx.user.SecuraEx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by TBX on 12/1/2017.
 */

public class receive extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("asdReceiver" , "Received");

        Intent pushIntent = new Intent(context, NotifyService.class);
        context.startService(pushIntent);
    }

}
