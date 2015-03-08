package com.togetherhandongi;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 *
 * Created by Administrator on 2015-01-22.
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent){
        completeWakefulIntent(intent);
        //Explicitly specify that GcmIntentService will handel the intent/
        ComponentName comp= new ComponentName(context.getPackageName(), GcmIntentService.class.getName());
        //Start the service, keeping the device awkae while it is launching
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);


    }
}
