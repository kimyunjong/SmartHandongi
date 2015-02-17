package com.smarthandongi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
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
    int screen_width,temp=2,screen_height;
    Picture small_image = new Picture();
    GroupDatabase1 group;
    Button back_btn,register_group;
    Carrier carrier;
    Typeface typeface;
    Context context = this;


    public void construction(int group_id){

        group_image.setVisibility(View.VISIBLE);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screen_width = metrics.widthPixels;

        screen_height = metrics.heightPixels;

        PostImageTask postImageTask = new PostImageTask(small_image,group_id,group_image,screen_width,temp);
        postImageTask.execute(0);


    }


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_info);
        typeface = Typeface.createFromAsset(getAssets(), "KOPUBDOTUM_PRO_LIGHT.OTF");
        Intent intent=getIntent();
        int group_id1= intent.getIntExtra("group_id",0);
        String group_name1 =intent.getStringExtra("group_name");
        String group_category1 = intent.getStringExtra("group_category");
        String introduce1 = intent.getStringExtra("introduce");
        String regid=null;
        carrier = (Carrier)getIntent().getSerializableExtra("carrier");
        regid=carrier.getRegid();
        Log.d("regid g infolist", carrier.getRegid());
        carrier.setRegid(regid);
        back_btn=(Button)findViewById(R.id.back);// 뒤로가기 버튼
        back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(group_info.this, group_infoList.class).putExtra("carrier",carrier);
                startActivity(intent);
            }
        });

        group_name=(TextView) findViewById(R.id.group_name);
        group_name.setTypeface(typeface);
        group_name.setText(group_name1);
        group_category=(TextView) findViewById(R.id.group_category);
        group_category.setTypeface(typeface);
        group_category.setText(group_category1);

        introduce=(TextView) findViewById(R.id.intro);
        introduce.setTypeface(typeface);
        introduce.setText(introduce1);

        group_image=(ImageView)findViewById(R.id.small_image);
        register_group = (Button)findViewById(R.id.writing_confirm_btn);

        register_group.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Uri uri = Uri.parse("http://me2.do/FY0wtxRF");

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        construction(group_id1);
        StringBuffer buf = null;
        WindowManager wm = null;
        Display display = null;
        wm = getWindowManager();
        display = wm.getDefaultDisplay();
        buf = new StringBuffer();
        buf.append("Window height: " + display.getHeight() + "\n");
    }


}
