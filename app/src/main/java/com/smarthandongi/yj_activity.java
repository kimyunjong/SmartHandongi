package com.smarthandongi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by Joel on 2015-01-21.
 */
public class yj_activity extends Activity implements View.OnTouchListener {
    Button all_btn,notice_btn, outer_btn,seminar_btn, recruit_btn, agora_btn, board_btn, timeline_btn, search_btn,menu_btn,write_btn;
    ImageView all_img,notice_img, outer_img,seminar_img, recruit_img,agora_img, board_img, timeline_img,search_img,menu_img,write_img;

    Carrier carrier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        carrier = (Carrier) intent.getSerializableExtra("carrier");
        setContentView(R.layout.dashboard);

        all_btn = (Button) findViewById(R.id.all_btn);
        notice_btn = (Button) findViewById(R.id.notice_btn);
        outer_btn = (Button) findViewById(R.id.outer_btn);
        seminar_btn = (Button) findViewById(R.id.seminar_btn);
        recruit_btn = (Button) findViewById(R.id.recruit_btn);
        agora_btn = (Button) findViewById(R.id.agora_btn);
        board_btn = (Button) findViewById(R.id.board_btn);
        timeline_btn = (Button) findViewById(R.id.timeline_btn);
        search_btn = (Button) findViewById(R.id.search_btn);
        menu_btn = (Button) findViewById(R.id.menu_btn);
        write_btn = (Button) findViewById(R.id.write_btn);

        all_img = (ImageView) findViewById(R.id.all_img);
        notice_img = (ImageView) findViewById(R.id.notice_img);
        outer_img = (ImageView) findViewById(R.id.outer_img);
        seminar_img = (ImageView) findViewById(R.id.seminar_img);
        recruit_img = (ImageView) findViewById(R.id.recruit_img);
        agora_img = (ImageView) findViewById(R.id.agora_img);
        board_img = (ImageView) findViewById(R.id.board_img);
        timeline_img = (ImageView) findViewById(R.id.timeline_img);
        search_img = (ImageView) findViewById(R.id.search_img);
        menu_img = (ImageView) findViewById(R.id.menu_img);
        write_img = (ImageView) findViewById(R.id.write_img);

        all_btn.setOnTouchListener(this);
        notice_btn.setOnTouchListener(this);
        outer_btn.setOnTouchListener(this);
        seminar_btn.setOnTouchListener(this);
        recruit_btn.setOnTouchListener(this);
        agora_btn.setOnTouchListener(this);
        board_btn.setOnTouchListener(this);
        timeline_btn.setOnTouchListener(this);
        search_btn.setOnTouchListener(this);
        menu_btn.setOnTouchListener(this);
        write_btn.setOnTouchListener(this);

    }
    @Override
    public boolean onTouch(View v,
                           MotionEvent event) {
        switch (v.getId()) {
            case R.id.all_btn: {
                if (event.getAction() == 0) {
                    all_img.setImageResource(R.drawable.all_btn_on);
                }
                else if(event.getAction()==1){
                    all_img.setImageResource(R.drawable.all_btn);
                }

                break;
            }

            case R.id.notice_btn: {
                if (event.getAction() == 0) {
                    notice_img.setImageResource(R.drawable.notice_btn_on);
                }
                else if (event.getAction() == 1) {
                    notice_img.setImageResource(R.drawable.notice_btn);
                }

                break;
            }
            case R.id.outer_btn: {
                if (event.getAction() == 0) {
                    outer_img.setImageResource(R.drawable.outer_btn_on);
                }

                else if (event.getAction() == 1) {
                    outer_img.setImageResource(R.drawable.outer_btn);
                }
                break;
            }

            case R.id.seminar_btn: {
                if (event.getAction() == 0) {
                    seminar_img.setImageResource(R.drawable.seminar_btn_on);
                }
                else if (event.getAction() == 1) {
                    seminar_img.setImageResource(R.drawable.seminar_btn);
                }
                break;
            }
            case R.id.recruit_btn: {
                if (event.getAction() == 0) {
                    recruit_img.setImageResource(R.drawable.recruit_btn_on);
                }
                else if (event.getAction() == 1) {
                    recruit_img.setImageResource(R.drawable.recruit_btn);
                }
                break;
            }
            case R.id.agora_btn: {
                if (event.getAction() == 0) {
                    agora_img.setImageResource(R.drawable.agora_btn_on);
                }

                else if (event.getAction() == 1) {
                    agora_img.setImageResource(R.drawable.agora_btn);
                }
                break;
            }
            case R.id.board_btn: {
                if (event.getAction() == 0) {
                    board_img.setImageResource(R.drawable.board_btn_on);
                }
                else if (event.getAction() == 1) {
                    board_img.setImageResource(R.drawable.board_btn);
                }
                break;
            }

            case R.id.timeline_btn: {
                if (event.getAction() == 0) {
                    timeline_img.setImageResource(R.drawable.timeline_btn_on);
                }
                else if (event.getAction() == 1) {
                    timeline_img.setImageResource(R.drawable.timeline_btn);
                }
                break;
            }
            case R.id.search_btn: {
                if (event.getAction() == 0) {
                    search_img.setImageResource(R.drawable.search_btn);
                }
                else if (event.getAction() == 1) {
                    search_img.setImageResource(R.drawable.search_btn);
                }
                break;
            }

        case R.id.menu_btn: {
            if (event.getAction() == 0) {
                menu_img.setImageResource(R.drawable.menu_btn);
            }
            else if (event.getAction() == 1) {
                menu_img.setImageResource(R.drawable.menu_btn);
            }
            break;
        }
            case R.id.write_btn: {
                if (event.getAction() == 0) {
                    write_img.setImageResource(R.drawable.write_btn);
                }
                else if (event.getAction() == 1) {
                    write_img.setImageResource(R.drawable.write_btn);
                }
                break;
            }
    }

        return false;
    }
}
