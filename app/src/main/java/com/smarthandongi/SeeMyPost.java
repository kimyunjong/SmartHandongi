package com.smarthandongi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    ArrayList<PostDatabase> posting_list=new ArrayList<PostDatabase>(),myPost_list=new ArrayList<PostDatabase>();
    ArrayList<ReviewDatabase> comment_list=new ArrayList<ReviewDatabase>();
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

        post_btn.setOnClickListener(this);
        comment_btn.setOnClickListener(this);

        construction();

        comment_listview.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.smp_post : {
                post_listview.setVisibility(View.VISIBLE);
                comment_listview.setVisibility(View.GONE);
                comment_btn.setBackgroundResource(R.drawable.smp_comment);
                post_btn.setBackgroundResource(R.drawable.smp_post_selected);
                break;
            }
            case R.id.smp_comment : {
                Log.d("내가눌렸다", "내가눌림");
                carrier.setFromSMP(0);
                post_listview.setVisibility(View.GONE);
                post_listview.setFocusable(false);
                comment_listview.setVisibility(View.VISIBLE);

                comment_btn.setBackgroundResource(R.drawable.smp_comment_selected);
                post_btn.setBackgroundResource(R.drawable.smp_post);

                break;
            }
        }
    }

    public void construction() {
        post_listview=(ListView)findViewById(R.id.smp_listview);
        comment_listview=(ListView)findViewById(R.id.smp_comment_listview);
        phpCreate();
    }
    public void phpCreate() {
        CommentDownloadPhp download = new CommentDownloadPhp(comment_list,this);
        download.execute("http://hungry.portfolio1000.com/smarthandongi/comment.php");
    }

    public void postconstruction() {

        for(int i=0; i<posting_list.size();i++) {
            if (posting_list.get(i).getKakao_id().compareTo(carrier.getId())==0) {
                System.out.println("내가쓴글이있다고욧ㅇ");
                myPost_list.add(new PostDatabase  ( posting_list.get(i).getTitle(),posting_list.get(i).getId(), posting_list.get(i).getKakao_id(), posting_list.get(i).getBig_category(),
                        posting_list.get(i).getCategory(), posting_list.get(i).getGroup(),posting_list.get(i).getContent(), posting_list.get(i).getPosting_date(), posting_list.get(i).getlink(),
                        posting_list.get(i).getStart_date(), posting_list.get(i).getEnd_date(), posting_list.get(i).getHas_pic(),
                        posting_list.get(i).getLike(), posting_list.get(i).getView_num(),posting_list.get(i).getGroup_name(),posting_list.get(i).getKakao_nic()));
            }
        }
        post_adapter = new SMP_PostAdapter(SeeMyPost.this,R.layout.my_post_listview,myPost_list,posting_list,carrier);
        post_listview.setAdapter(post_adapter);

        Log.d("myPostLIst Size",String.valueOf(myPost_list.size()));

    }


    public class CommentDownloadPhp extends AsyncTask<String, android.R.integer, String> {

        private ArrayList<ReviewDatabase> comment_list;
        private Context context;

        public CommentDownloadPhp(ArrayList<ReviewDatabase> comment_list, Context context) {
            super();
            this.comment_list = comment_list;
            this.context = context;
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

                        Log.d("이거되야하는데","왜안되는거지?");

                    }
                }
                comment_adapter = new SMP_CommentAdapter(context,R.layout.my_comment_listview,comment_list,carrier,posting_list);
                comment_listview.setAdapter(comment_adapter);
                comment_listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

                comment_listview.setOnItemClickListener(commentClickListener);
                Log.d("니가먼저지?","내가멈ㄴ저야");


                postconstruction();

                post_adapter = new SMP_PostAdapter(SeeMyPost.this,R.layout.my_post_listview,myPost_list,posting_list,carrier);
                post_listview.setAdapter(post_adapter);
                post_listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                post_listview.setOnItemClickListener(postClickListener);


                Log.d("너되야해", "너될거야??");

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    AdapterView.OnItemClickListener postClickListener = new AdapterView.OnItemClickListener() {

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int pos=0;
            for(int i=0; i<posting_list.size(); i++) {
                if(myPost_list.get(position).getId()==posting_list.get(i).getId()) {
                    pos=i;
                    break;
                }
            }
            carrier.setFromSMP(1);
            Intent intent = new Intent(SeeMyPost.this,PostDetail.class);
            intent.putExtra("post_list",posting_list);
            intent.putExtra("position",pos);
            intent.putExtra("post",myPost_list.get(position));
            intent.putExtra("carrier",carrier);
            startActivity(intent);
            finish();
        }
    };

    AdapterView.OnItemClickListener commentClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int pos=0;
            Intent intent = new Intent (SeeMyPost.this,Review.class);
            carrier.setFromSMP(1);
            intent.putExtra("carrier",carrier);
            intent.putExtra("posting_id",comment_list.get(position).getPosting_id());
            intent.putExtra("post_list",posting_list);
            System.out.println("너는되야지");
            startActivity(intent);

            finish();
        }
    };

    public void onBackPressed() {
        carrier.setFromSMP(0);
        Intent intent = new Intent(SeeMyPost.this,yj_activity.class).putExtra("carrier",carrier);
        startActivity(intent);
        finish();
    }
}
