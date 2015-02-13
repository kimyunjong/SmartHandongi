package com.smarthandongi;

/**
 * Created by Administrator on 2015-01-22.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

/**
 * Created by Administrator on 2015-01-22.
 */
public class GcmIntentService extends GCMBaseIntentService {

    static String re_message = null;
    static Carrier carrier;
    private static void generateNotifiaction(Context context, String message){
        int icon =R.drawable.push_image_origin;
        long when = System.currentTimeMillis();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);

        String title = "모여라 한동이";
            Intent notificationIntent = new Intent(context, PostDetail.class).putExtra("carrier",carrier);

           // notificationIntent.setAction(PostDetail.CustomInternalMessageAction);
           // notificationIntent.putExtra(PostDetail.ReceiveTestMessage, re_message);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent intent = PendingIntent.getActivity(context,0,notificationIntent,0);
        /*getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | Windowanager.LayoutParams.FLAG_TURN_SCREEN_ON);*/
        notification.setLatestEventInfo(context,title,message,intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0,notification);

        // Play default notification sound
        notification.sound = Uri.parse("android.resource://com.smarthandongi/" + R.raw.wal);
        //notification.defaults |= Notification.DEFAULT_SOUND;

        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);
    }
    @Override
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
