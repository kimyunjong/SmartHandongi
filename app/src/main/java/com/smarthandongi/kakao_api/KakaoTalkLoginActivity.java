/**
 * Copyright 2014 Daum Kakao Corp.
 *
 * Redistribution and modification in source or binary forms are not permitted without specific prior written permission. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smarthandongi.kakao_api;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.kakao.UserProfile;
import com.smarthandongi.Carrier;
import com.smarthandongi.MainActivity2;
import com.smarthandongi.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.concurrent.atomic.AtomicInteger;




///**
// * 카카오톡 API는 로그인 기반이다.
// * 세션을 오픈한 후에 {@link KakaoTalkMainActivity}로 넘긴다.
// * (이 샘플은 가입 절차가 따로 필요 없는 자동가입 앱이다.)
// * @author MJ
// */


public class KakaoTalkLoginActivity extends SampleLoginActivity {

    private Carrier carrier;
    private UserProfile userProfile;
   //수영 추가
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

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
    //수영 추가 끝
    private void getAppkeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.d("Hash key", something);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    } @Override
    protected void onCreate(Bundle savedInstanceState) {
        carrier = (Carrier)getIntent().getSerializableExtra("carrier");
        super.onCreate(savedInstanceState);
        setBackground(getResources().getDrawable(R.drawable.kakaotalk_sample_login_background));
        Log.d("kakaoTalkLogin activity","Oncreate");
        getAppkeyHash();


        mDisplay = (TextView) findViewById(R.id.display);

        carrier = (Carrier)getIntent().getSerializableExtra("carrier");
        context = getApplicationContext();

        // Check device for Play Services APK. If check succeeds, proceed with
        //  GCM registration.
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);

            Log.i(TAG, "No valid Google Play Services APK found.");
            if (regid.isEmpty()) {
                registerInBackground();
            }
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");

        }
        carrier.setRegid(regid);
        RegIDInsertTask regIDInsertTask  = new RegIDInsertTask();
        regIDInsertTask.execute(regid);
        // regIDInsertTaske= new regIDInsertTask.execute(regid);
        // sendAPIkey();
    }


    /**
     * 세션이 오픈되었으면 가입 페이지로 이동
     */
    @Override
    protected void onSessionOpened() {

        userProfile = UserProfile.loadFromCache();
        Intent intent = new Intent(KakaoTalkLoginActivity.this, KakaoTalkSignupActivity.class);
        carrier.setLogged_in(true);
        carrier.setId(String.valueOf(userProfile.getId()));
        carrier.setNickname(userProfile.getNickname());

        intent.putExtra("carrier", carrier);
        startActivity(intent);

        overridePendingTransition(0,0);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent;
        intent = new Intent(KakaoTalkLoginActivity.this, MainActivity2.class);
        intent.putExtra("carrier", carrier);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
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
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" +regid;
                    sendRegistrationIdToBackend();


                    storeRegistrationId(context,regid);

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
               // mDisplay.append(msg);
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
       /* if (view == findViewById(R.id.button_registId)) {
            new AsyncTask() {
                @Override
                protected String doInBackground(Object... params) {
                    String msg = "";
                    try {
                        Bundle data = new Bundle();
                        data.putString("my_message", "Hello World");
                        data.putString("my_action",
                                "com.google.android.gcm.demo.app.ECHO_NOW");
                        String id = Integer.toString(msgId.incrementAndGet());
                        gcm.send(SENDER_ID + "@gcm.googleapis.com", id, data);
                        msg = "Sent message";
                    } catch (IOException ex) {
                        msg = "Error :" + ex.getMessage();
                    }
                    return msg;
                }

                @Override
                protected void onPostExecute(Object msg) {
                    //super.onPostExecute(msg);
                    mDisplay.append(msg + "\n");
                }
            }.execute(null, null, null);
        } else if (view == findViewById(R.id.clear)) {
            mDisplay.setText("");
        }/*
        else if (view==findViewById(R.id.group_info)){
            Intent intent = new Intent(ds_activity.this, group_info.class).putExtra("carrier", carrier);
            startActivity(intent);
        }
*/

    }
    private class RegIDInsertTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loagindDialog = ProgressDialog.show(KakaoTalkLoginActivity.this, "키 등록 중입니다..",
                    "Please wait..", true, false);
        }

        @Override
        protected Void doInBackground(String... params) {
            HttpPostData(regid );
            return null;
        }

        protected void onPostExecute(Void result) {
  //          loagindDialog.dismiss();
        }
    }

    public void HttpPostData(String reg_id ) {
        try {
            URL url = new URL("http://hungry.portfolio1000.com/smarthandongi/gcm_reg_insert.php?reg_id="+regid);       // URL 설정
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
/*
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(KakaoTalkLoginActivity.this, MainActivity2.class).putExtra("carrier", carrier);
        startActivity(intent);
        finish();
    }
}
*/

}



