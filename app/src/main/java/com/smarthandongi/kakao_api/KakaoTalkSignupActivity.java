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

import android.content.Intent;
import android.os.Bundle;

import com.kakao.UserProfile;
import com.smarthandongi.Carrier;
import com.smarthandongi.MainActivity2;
import com.smarthandongi.yj_activity;

/**
 * 유효한 세션이 있다는 검증 후
 * me를 호출하여 가입 여부에 따라 가입 페이지를 그리던지 Main 페이지로 이동 시킨다.
 */
public class KakaoTalkSignupActivity extends SampleSignupActivity {


    private UserProfile userProfile;
    private Carrier carrier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carrier = (Carrier)getIntent().getSerializableExtra("carrier");



    }

    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, KakaoTalkLoginActivity.class);

        startActivity(intent);

        overridePendingTransition(0, 0);
        finish();
    }

    protected void redirectMainActivity() {

        userProfile = UserProfile.loadFromCache();
        carrier.setLogged_in(true);
        carrier.setId(String.valueOf(userProfile.getId()));
        carrier.setNickname(userProfile.getNickname());


        final Intent intent = new Intent(this, yj_activity.class);
        intent.putExtra("carrier", carrier);

        startActivity(intent);

        finish();
    }
}
