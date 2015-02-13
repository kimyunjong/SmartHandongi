package com.smarthandongi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smarthandongi.kakao_api.KakaoTalkLoginActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
        pic=(ImageView)findViewById(R.id.Intro_background);
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

}
