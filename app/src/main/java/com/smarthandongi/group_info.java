package com.smarthandongi;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;
import android.util.DisplayMetrics;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarthandongi.database.Picture;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015-02-03.
 */
public class group_info extends Activity {
    //ListView group_list_view;
    ArrayList<GroupDatabase1> group_list = new ArrayList<GroupDatabase1>();



    ImageView group_image;
    TextView group_name,group_category, introduce, slash;
    int screen_width,temp=2;
    Picture small_image = new Picture();
    GroupDatabase1 group;
    Button back_btn;



    public void construction(int group_id){

        group_image.setVisibility(View.VISIBLE);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screen_width = metrics.widthPixels;

        PostImageTask postImageTask = new PostImageTask(small_image,group_id,group_image,screen_width,temp);
        postImageTask.execute(0);


    }


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_info);
        Intent intent=getIntent();
        int group_id1= intent.getIntExtra("group_id",0);
        String group_name1 =intent.getStringExtra("group_name");
        String group_category1 = intent.getStringExtra("group_category");
        String introduce1 = intent.getStringExtra("introduce");

        back_btn=(Button)findViewById(R.id.back);// 뒤로가기 버튼
        back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(group_info.this, group_infoList.class);
                startActivity(intent);
            }
        });

        group_name=(TextView) findViewById(R.id.group_name);
        group_name.setText(group_name1);
        group_category=(TextView) findViewById(R.id.group_category);
        group_category.setText(group_category1);

        introduce=(TextView) findViewById(R.id.intro);
        introduce.setText(introduce1);


        slash=(TextView)findViewById(R.id.slash);
        slash.setText("  |  ");

        group_image=(ImageView)findViewById(R.id.small_image);

        construction(group_id1);

    }


}
