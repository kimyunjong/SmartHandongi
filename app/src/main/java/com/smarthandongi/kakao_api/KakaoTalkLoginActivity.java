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

;
import android.content.Intent;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.kakao.UserProfile;
import com.smarthandongi.Carrier;
import com.smarthandongi.MainActivity2;
import com.smarthandongi.R;
import com.smarthandongi.yj_activity;


import java.security.MessageDigest;





///**
// * 카카오톡 API는 로그인 기반이다.
// * 세션을 오픈한 후에 {@link KakaoTalkMainActivity}로 넘긴다.
// * (이 샘플은 가입 절차가 따로 필요 없는 자동가입 앱이다.)
// * @author MJ
// */


public class KakaoTalkLoginActivity extends SampleLoginActivity {

    private Carrier carrier;
    private UserProfile userProfile;

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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        carrier = (Carrier) getIntent().getSerializableExtra("carrier");
        super.onCreate(savedInstanceState);
        setBackground(getResources().getDrawable(R.drawable.kakaotalk_sample_login_background)); //image 바꿀수 있다
        Log.d("kakaoTalkLogin activity", "Oncreate");
        getAppkeyHash();


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

        overridePendingTransition(0, 0);
        finish();

        if(carrier.isBy_GCM()){
            Log.d("Kakaotalk 로그인 액티비티","intro화면을 지나서왔어요");
            Log.d("Kakaotalk 로그인 액티비티",String.valueOf(carrier.getPost_id()));
            carrier = (Carrier) getIntent().getSerializableExtra("carrier");
            intent = new Intent(KakaoTalkLoginActivity.this, KakaoTalkSignupActivity.class);
            carrier.setLogged_in(true);
            carrier.setId(String.valueOf(userProfile.getId()));
            carrier.setNickname(userProfile.getNickname());

            intent.putExtra("carrier", carrier);
            startActivity(intent);

            overridePendingTransition(0, 0);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent;
        intent = new Intent(KakaoTalkLoginActivity.this, yj_activity.class);
        intent.putExtra("carrier", carrier);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

}



