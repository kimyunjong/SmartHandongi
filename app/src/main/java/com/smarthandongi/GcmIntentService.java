package com.smarthandongi;

/**
 * Created by Administrator on 2015-01-22.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

/**
 * Created by Administrator on 2015-01-22.
 */
public class GcmIntentService extends GCMBaseIntentService {

    static String re_message = null;
    static Carrier carrier = new Carrier();

    private  void generateNotifiaction(Context context, String message) {
        int icon = R.drawable.push_handongi;
        long when = System.currentTimeMillis();
        String title = "모여라 한동이";
        carrier.setBy_GCM(true);

//        carrier.setBy_GCM(true);
        //passed.setPic_id(pic_id.replace("'", ""));
        Intent intent = new Intent(this, Intro.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("carrier", carrier);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new Notification(icon, message, when);


        notification.largeIcon=(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.push_handongi), 250, 250, false));


        PendingIntent pintent = PendingIntent.getActivity(context, 0, intent, 0);


        notification.setLatestEventInfo(context, title, message, pintent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);

        // Play default notification sound
        notification.sound = Uri.parse("android.resource://com.smarthandongi/" + R.raw.wal);
        //notification.defaults |= Notification.DEFAULT_SOUND;

        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);
    }
    protected void onError (Context arg0, String arg1){

    }
    @Override
    protected void onMessage(Context context, Intent intent){
        String msg = intent.getStringExtra("msg");
        Log.e("getmessage", "getmessage:"+ msg);
        generateNotifiaction(context,msg);
    }
    @Override
    protected void onRegistered(Context context, String reg_id ){
        Log.e("키를 등록합니다.(GCM INTENTSERVICE)",reg_id);
    }
    @Override
    protected void onUnregistered(Context arg0, String arg1){
        GCMRegistrar.unregister(arg0);
        Log.e("키를 제거합니다.(GCM INTENTSERVICE", "제거되었습니다.");

    }
}