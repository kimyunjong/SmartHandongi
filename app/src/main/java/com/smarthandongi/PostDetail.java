package com.smarthandongi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarthandongi.database.PostDatabase;

/**
 * Created by Joel on 2015-01-25.
 */
public class PostDetail extends Activity{

    private Carrier carrier;
    private PostDatabase post;
    private TextView title, post_day, start_day, end_day, content, review_num,img;

    private ImageView background,post_img;
    private Button scrap_btn, del_btn, review_show_btn, edit_btn;
    Button writer_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        carrier=(Carrier)intent.getSerializableExtra("carrier");
        post=(PostDatabase)intent.getSerializableExtra("post");
        setContentView(R.layout.post_detail);
        writer_btn=(Button)findViewById(R.id.writer_name);
        writer_btn.setShadowLayer(0,0,0,0);

    }

}
