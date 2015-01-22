package com.smarthandongi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smart_handongi.kakao_api.KakaoTalkLoginActivity;

/**
 * Created by Joel on 2015-01-20.
 */
public class Intro extends Activity {
    Carrier carrier;
    Intent get_intent;
    RelativeLayout touch1, touch2;
    ImageView pic;
    TextView blink;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        get_intent = getIntent();
        carrier = new Carrier();
        setContentView(R.layout.intro);

        blink = (TextView) findViewById(R.id.blink);
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(300); //You can manage the blinking time with this parameter
        anim.setStartOffset(0);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        blink.startAnimation(anim);

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
        public void onBackPressed(){
            finish();
            System.exit(0);
    }
}
