package com.smarthandongi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by LEWIS on 2015-01-23.
 */

public class SelectGroupOrNot extends Activity implements View.OnTouchListener, View.OnClickListener {
    Carrier carrier;

    Button individual_btn, group_btn, sel_back_btn;
    ImageView individual_img, group_img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_group_or_not);
        carrier = (Carrier) getIntent().getSerializableExtra("carrier");

        individual_btn  = (Button)findViewById(R.id.individual_btn);
        group_btn       = (Button)findViewById(R.id.group_btn);
        sel_back_btn    = (Button)findViewById(R.id.sel_back_btn);

        individual_img  = (ImageView)findViewById(R.id.individual_img);
        group_img       = (ImageView)findViewById(R.id.group_img);

        individual_btn.setOnTouchListener(this);
        group_btn.setOnTouchListener(this);

        individual_btn.setOnClickListener(this);
        group_btn.setOnClickListener(this);
        sel_back_btn.setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(v.getId()){
            case R.id.individual_btn : {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    individual_img.setBackgroundResource(R.drawable.writing_individual_on);
                }
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    individual_img.setBackgroundResource(R.drawable.writing_individual);
                }
                break;
            }
            case R.id.group_btn : {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    group_img.setBackgroundResource(0);
                    group_img.invalidate();
                    group_img.setBackgroundResource(R.drawable.writing_group_on);
                    group_img.refreshDrawableState();
                    group_img.invalidate();
                }
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    group_img.setBackgroundResource(0);
                    group_img.invalidate();
                    group_img.setBackgroundResource(R.drawable.writing_group);
                    group_img.refreshDrawableState();
                    group_img.invalidate();
                }
                break;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.individual_btn : {
                carrier.setSelector(0);
                Intent intent = new Intent(SelectGroupOrNot.this, Writing.class).putExtra("carrier", carrier);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.group_btn : {
                carrier.setSelector(1);
                Intent intent = new Intent(SelectGroupOrNot.this,GroupSearch.class).putExtra("carrier",carrier);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.sel_back_btn : {
                Intent intent = new Intent(SelectGroupOrNot.this, yy_activity.class).putExtra("carrier", carrier);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            }
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(SelectGroupOrNot.this, yy_activity.class).putExtra("carrier", carrier);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}
