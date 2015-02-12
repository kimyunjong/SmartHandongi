package com.smarthandongi;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.smarthandongi.adapter.GroupListAdapter;
import com.smarthandongi.adapter.SMP_CommentAdapter;
import com.smarthandongi.adapter.SMP_PostAdapter;
import com.smarthandongi.database.PostDatabase;
import com.smarthandongi.database.ReviewDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by user on 2015-02-11.
 */
public class SeeMyPost extends Activity implements View.OnClickListener{
    Carrier carrier;
    ImageButton post_btn,comment_btn;
    ArrayList<PostDatabase> posting_list,myPost_list;
    ArrayList<ReviewDatabase> comment_list;
    SMP_PostAdapter post_adapter;
    SMP_CommentAdapter comment_adapter;
    private ListView post_listview,comment_listview;

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
        comment_listview=(ListView)findViewById(R.id.smp_comment_listview);

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
                comment_listview.setVisibility(View.INVISIBLE);
                comment_btn.setBackgroundResource(R.drawable.smp_comment);
                post_btn.setBackgroundResource(R.drawable.smp_post_selected);
                break;
            }
            case R.id.smp_comment : {
                post_listview.setVisibility(View.INVISIBLE);
                comment_listview.setVisibility(View.VISIBLE);
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

                post_adapter = new SMP_PostAdapter(SeeMyPost.this,R.layout.my_post_listview,myPost_list,posting_list,carrier);
                post_listview.setAdapter(post_adapter);
                post_listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

            }
        }
        Log.d("myPostLIst Size",String.valueOf(myPost_list.size()));

    }

    public void myCommentConstruction() {
        comment_list=new ArrayList<ReviewDatabase>();
        phpCreate();

    }

    public void phpCreate() {
        CommentDownloadPhp download = new CommentDownloadPhp(comment_list,this);
        download.execute("http://hungry.portfolio1000.com/smarthandongi/comment.php");
    }
    public class CommentDownloadPhp extends AsyncTask<String, android.R.integer, String> {

        private ArrayList<ReviewDatabase> comment_list;
        private Activity activity;

        public CommentDownloadPhp(ArrayList<ReviewDatabase> comment_list, Activity activity) {
            super();
            this.comment_list = comment_list;
            this.activity = activity;
        }

        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            String return_str = "";

            while (return_str.equalsIgnoreCase("")) {
                try {
                    URL data_url = new URL(urls[0]);
                    HttpURLConnection conn = (HttpURLConnection) data_url.openConnection();
                    if (conn != null) {
                        conn.setConnectTimeout(10000);
                        conn.setUseCaches(false);
                        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                            for (; ; ) {
                                String line = br.readLine();
                                if (line == null) break;
                                jsonHtml.append(line + "\n");
                            }
                            br.close();
                        }
                        conn.disconnect();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return_str = jsonHtml.toString();
            }
            Log.v("연결 시도", "연결되어라doinbackground$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$4");
            return jsonHtml.toString();
        }

        protected void onPostExecute(String str) {

            try {
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("results");

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    if(carrier.getId().compareTo(jo.getString("kakao_id"))==0) {
                        comment_list.add(new ReviewDatabase(jo.getInt("posting_id"),
                                jo.getInt("review_id"), jo.getString("kakao_id"), jo.getString("kakao_nick"),
                                jo.getString("reply_date"), jo.getString("content")
                        ));
                        comment_adapter = new SMP_CommentAdapter(SeeMyPost.this,R.layout.my_comment_listview,comment_list,carrier,posting_list);
                        comment_listview.setAdapter(comment_adapter);
                        comment_listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

                    }



                }



            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }



    public void onBackPressed() {
        carrier.setFromSMP(0);
        Intent intent = new Intent(SeeMyPost.this,yj_activity.class).putExtra("carrier",carrier);
        startActivity(intent);
        finish();
    }
}
