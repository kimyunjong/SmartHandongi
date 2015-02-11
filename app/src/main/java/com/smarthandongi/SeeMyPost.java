package com.smarthandongi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by user on 2015-02-11.
 */
public class SeeMyPost extends Activity implements View.OnClickListener{
    Carrier carrier;
    ImageButton post_btn,comment_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_my_post);
        carrier = (Carrier) getIntent().getSerializableExtra("carrier");

        post_btn=(ImageButton)findViewById(R.id.smp_post);
        comment_btn=(ImageButton)findViewById(R.id.smp_comment);

        post_btn.setOnClickListener(this);
        comment_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.smp_post : {

                break;
            }
            case R.id.smp_comment : {

                break;
            }
        }
    }
}
