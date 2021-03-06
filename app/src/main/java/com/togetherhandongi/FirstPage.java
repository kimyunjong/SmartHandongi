package com.togetherhandongi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by Joel on 2015-02-13.
 */
public class FirstPage extends Activity {
    private Thread thread;
    private Boolean thread_running = true;
    int timer = 0;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.first_page);

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (true) {
                    timer++;
                    if (timer == 100) {
                        Intent intent = new Intent(FirstPage.this, Intro.class);

                        startActivityForResult(intent, 0);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    }


                    System.out.println(timer);
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();


    }


}
