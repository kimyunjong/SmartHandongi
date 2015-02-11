package com.smarthandongi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.smarthandongi.adapter.GroupListAdapter;
import com.smarthandongi.adapter.SMP_PostAdapter;
import com.smarthandongi.database.PostDatabase;

import java.util.ArrayList;

/**
 * Created by user on 2015-02-11.
 */
public class SeeMyPost extends Activity implements View.OnClickListener{
    Carrier carrier;
    ImageButton post_btn,comment_btn;
    ArrayList<PostDatabase> posting_list,myPost_list;
    SMP_PostAdapter post_adapter;
    private ListView post_listview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_my_post);
        carrier = (Carrier) getIntent().getSerializableExtra("carrier");
        posting_list=(ArrayList)getIntent().getSerializableExtra("post_list");

        Log.d("글은제대로받아온것인가?",posting_list.get(3).getTitle());
        post_btn=(ImageButton)findViewById(R.id.smp_post);
        comment_btn=(ImageButton)findViewById(R.id.smp_comment);

        post_listview=(ListView)findViewById(R.id.smp_listview);

        post_btn.setOnClickListener(this);
        comment_btn.setOnClickListener(this);

        myPostConstruction();
        myCommentConstruction();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.smp_post : {
                post_listview.setVisibility(View.VISIBLE);
                comment_btn.setBackgroundResource(R.drawable.smp_comment);
                post_btn.setBackgroundResource(R.drawable.smp_post_selected);
                break;
            }
            case R.id.smp_comment : {
                post_listview.setVisibility(View.INVISIBLE);
                comment_btn.setBackgroundResource(R.drawable.smp_comment_selected);
                post_btn.setBackgroundResource(R.drawable.smp_post);

                break;
            }
        }
    }

    public void myPostConstruction() {

        myPost_list=new ArrayList<PostDatabase>();

        Log.d("myPostLIst Size",String.valueOf(myPost_list.size()));
        Log.d("myPostLIst Size",String.valueOf(posting_list.size()));
        for(int i=0; i<posting_list.size();i++) {
            if (posting_list.get(i).getKakao_id().compareTo(carrier.getId())==0) {
                System.out.println("내가쓴글이있다고욧ㅇ");
                myPost_list.add(new PostDatabase  ( posting_list.get(i).getTitle(),posting_list.get(i).getId(), posting_list.get(i).getKakao_id(), posting_list.get(i).getBig_category(),
                        posting_list.get(i).getCategory(), posting_list.get(i).getGroup(),posting_list.get(i).getContent(), posting_list.get(i).getPosting_date(), posting_list.get(i).getlink(),
                        posting_list.get(i).getStart_date(), posting_list.get(i).getEnd_date(), posting_list.get(i).getHas_pic(),
                        posting_list.get(i).getLike(), posting_list.get(i).getView_num(),posting_list.get(i).getGroup_name(),posting_list.get(i).getKakao_nic()));

                post_adapter = new SMP_PostAdapter(SeeMyPost.this,R.layout.my_post_listview,myPost_list);
                post_listview.setAdapter(post_adapter);
                post_listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            }
        }
        Log.d("myPostLIst Size",String.valueOf(myPost_list.size()));

    }

    public void myCommentConstruction() {

    }
}
