package com.smarthandongi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

        img = (ImageView) findViewById(R.id.settingimg);
        mainimg = (ImageView) findViewById(R.id.mainimg);
        twelveimg = (ImageView) findViewById(R.id.twelveimg);
        momsimg = (ImageView) findViewById(R.id.momsimg);
        ddorangimg = (ImageView) findViewById(R.id.ddorangimg);
        cafeimg = (ImageView) findViewById(R.id.cafeimg);
        deliveryimg = (ImageView) findViewById(R.id.deliveryimg);

        bogibtn.setOnTouchListener(this);
        bustimetablebtn.setOnTouchListener(this);
        settingbtn.setOnTouchListener(this);
        mainbtn.setOnTouchListener(this);
        twelvebtn.setOnTouchListener(this);
        momsbtn.setOnTouchListener(this);
        ddorangbtn.setOnTouchListener(this);
        cafebtn.setOnTouchListener(this);
        deliverybtn.setOnTouchListener(this);

    }
}
