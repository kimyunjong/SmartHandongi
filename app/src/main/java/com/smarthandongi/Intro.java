package com.smarthandongi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.smarthandongi.kakao_api.KakaoTalkLoginActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by Joel on 2015-01-20.
 */
public class Intro extends Activity {
    Carrier carrier;
    Intent get_intent;
    Intent put_intent;
    ImageView background_intro;

    RelativeLayout touch1;
    ImageView pic;

    Handler handler;
    TextView blink;

    //수영 추가
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private int isLogout_regid=1;
    int screen_height = 0, screen_width = 0;

    String SENDER_ID = "651406894161";
    String myResult;
    ProgressDialog loagindDialog;

    static final String TAG = "GCMDemo";

    TextView mDisplay;
    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    SharedPreferences prefs;
    Context context;
    String regid;
    Typeface typeface;

    //수영 추가 끝

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Intent intent = getIntent();
        if (intent.getSerializableExtra("carrier") != null)
        {carrier = (Carrier)intent.getSerializableExtra("carrier");

        //    Log.d("처음에 들어온 posting_id",String.valueOf(carrier.getPost_id()));
        }
        else
            carrier = new Carrier();
        setContentView(R.layout.intro);
        typeface = Typeface.createFromAsset(getAssets(), "KOPUBDOTUM_PRO_LIGHT.OTF");

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screen_height = metrics.heightPixels;
        screen_width = metrics.widthPixels;


        //수영추가
        mDisplay = (TextView) findViewById(R.id.display);




        context = getApplicationContext();

        // Check device for Play Services APK. If check succeeds, proceed with
        //  GCM registration.
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(context);
            carrier.setRegid(getRegistrationId(context));
    //        Log.d("여기입니다","여기가 안된다구요");
            Log.d("regid는요 ", carrier.getRegid());

            if (carrier.getRegid().isEmpty()) {
                registerInBackground();
            }
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");

        }
        System.out.println(carrier.getRegid()+"제발 확인 ");
       // String temp=regid;

        //carrier.setRegid(regid);
      //  System.out.println(regid+"제발 확인되어라");


        RegIDInsertTask regIDInsertTask = new RegIDInsertTask();
        regIDInsertTask.execute(carrier.getRegid());

       if (carrier.isBy_GCM())
            enter();
        //수영추가

        blink = (TextView) findViewById(R.id.blink);
        blink.setTypeface(typeface);
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(300); //You can manage the blinking time with this parameter
        anim.setStartOffset(0);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        blink.startAnimation(anim);
        pic=(ImageView)findViewById(R.id.Intro_background);
        pic.getLayoutParams().width = ((int)(screen_width*0.9));
        pic.getLayoutParams().height = ((int)(screen_height*0.9));
        pic.requestLayout();


        ImageTask it = new ImageTask(pic);
        it.execute("http://hungry.portfolio1000.com/smarthandongi/photo/infomation" + String.valueOf((int)(Math.random()*100)%3+1) + ".png");


        touch1 = (RelativeLayout) findViewById(R.id.touch1);
        touch1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    Intent intent = new Intent(Intro.this, KakaoTalkLoginActivity.class);

                    intent.putExtra("carrier", carrier);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
                return false;
            }
        });



    }
    //수영추가 GCM 화면 들어가기
    public void enter() {
        Intent intent = new Intent(Intro.this, KakaoTalkLoginActivity.class);
        intent.putExtra("carrier", carrier);
        startActivity(intent);
        overridePendingTransition(0,0);

        Log.d("enter화면 푸시들어왔어요","푸쉬화면으로 전환");
        Log.e("enter", "carrier.getPost_id"+carrier.getPost_id());

        finish();
    }
    //수영 추가 끝

    public void onBackPressed(){
        finish();
        System.exit(0);
    }

    private class ImageTask extends AsyncTask<String, Integer, Bitmap> {

        Bitmap picture;
        ImageView picture_view;

        public ImageTask(ImageView picture_view){
            this.picture_view = picture_view;
        }

        protected Bitmap doInBackground(String... url) {
            try{
                URL myFileUrl = new URL(url[0]);
                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();

                picture = BitmapFactory.decodeStream(is);
            }catch(IOException e){
                e.printStackTrace();
            }
            return picture;
        }
        protected void onPostExecute(Bitmap picture){
            picture_view.setImageBitmap(picture);
        }
    }
    //수영 추가
    @Override
    protected void onResume(){
        super.onResume();
        checkPlayServices();

    }

    //google play service 가 사용 가능한가
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    // registration id를 가져온다.
    private String getRegistrationId(Context context) {

        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }

        return registrationId;
    }
    private SharedPreferences getGCMPreferences(Context context){
        return getSharedPreferences(Activity.class.getSimpleName(), Context.MODE_PRIVATE);
    }/**
     * @return Application's version code from the {@code PackageManager}.
     */
    private int getAppVersion(Context context){
        try{
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),0);
            return packageInfo.versionCode;
        }catch (PackageManager.NameNotFoundException e){
            throw new RuntimeException("Could not get package nane:"+e);
        }
    }
    // gcm 서버에 접속해서 registration id를 발급받는다.
    private void registerInBackground()
    {
        new AsyncTask<Void, Void, String>()
        {
            @Override
            protected String doInBackground(Void... params)
            {
                String msg = "";
                try
                {
                    if (gcm == null)
                    {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    carrier.setRegid(gcm.register(SENDER_ID));
                    msg = "Device registered, registration ID=" +carrier.getRegid();
                    sendRegistrationIdToBackend();


                    storeRegistrationId(context,carrier.getRegid());

                }
                catch (IOException ex)
                {
                    msg = "Error :" + ex.getMessage();

                }

                return msg;
            }


            @Override
            protected void onPostExecute(String msg)
            {

                Log.i("ds_activity.java | onPostExecute", "|" + msg + "|");
              //  mDisplay.append(msg);
            }
        }.execute(null, null, null);
    }
    private void sendRegistrationIdToBackend() {
        // Your implementation here.
    }
    // registraion id를 preference에 저장한다.
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    public void onClick(final View view) {


    }
    private class RegIDInsertTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  loagindDialog = ProgressDialog.show(KakaoTalkLoginActivity.this, "키 등록 중입니다..",
                    "Please wait..", true, false);*/
        }

        @Override
        protected Void doInBackground(String... params) {
            HttpPostData(regid );
            return null;
        }

        protected void onPostExecute(Void result) {
            // loagindDialog.dismiss();
        }
    }

    public void HttpPostData(String reg_id ) {
        try {
            URL url = new URL("http://hungry.portfolio1000.com/smarthandongi/gcm_reg_insert.php?reg_id="+carrier.getRegid());       // URL 설정
            Log.d("regid등록","등록되었습니다.");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속
            //--------------------------
            //   전송 모드 설정 - 기본적인 설정이다
            //--------------------------
            http.setDefaultUseCaches(false);
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");

            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            StringBuffer buffer = new StringBuffer();
            buffer.append("reg_id").append("=").append(reg_id);                 // php 변수에 값 대입


            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "EUC-KR");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            writer.flush();
            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                builder.append(str + "\n");
            }

            myResult = builder.toString();

        } catch (MalformedURLException e) {
            //
        } catch (IOException e) {
            //
        } // try
    } // HttpPostData
}
