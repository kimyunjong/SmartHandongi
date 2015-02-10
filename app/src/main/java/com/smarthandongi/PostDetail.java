package com.smarthandongi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarthandongi.database.Picture;
import com.smarthandongi.database.PostDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Joel on 2015-01-25.
 */
public class PostDetail extends Activity implements View.OnClickListener{

    private Carrier carrier;
    private PostDatabase post;
    private PostDB_Php postdbphp;
    private DeletePhp del_php;
    private TextView title, post_day, start_day, end_day, content, view_num,review_num,img,link,
                     type;


    ImageView post_img;
    private Button scrap_btn, del_btn, review_show_btn, edit_btn,writer_btn,report_btn,group_btn,home_btn,backward_btn,forward_btn;

    ArrayList<PostDatabase> post_list = new ArrayList<PostDatabase>();
    ArrayList<PostDatabase> posting_list;

    private int posting_id,position;
    int screen_width;
    Picture poster = new Picture();


   //수영 추가
    String myResult;
    ProgressDialog loagindDialog;
    int temp=1;
    //수영 추가 끝


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        carrier=(Carrier)intent.getSerializableExtra("carrier");
        post=(PostDatabase)intent.getSerializableExtra("post");
        posting_list=(ArrayList)intent.getSerializableExtra("post_list");
        position=(int)intent.getSerializableExtra("position");

        setContentView(R.layout.post_detail);

        //버튼
        writer_btn=(Button)findViewById(R.id.writer_name);
        writer_btn.setShadowLayer(0,0,0,0);
        scrap_btn=(Button)findViewById(R.id.pos_scrap_btn);
        report_btn=(Button)findViewById(R.id.pos_report_btn);
        edit_btn=(Button)findViewById(R.id.pos_edit_btn);
        del_btn=(Button)findViewById(R.id.pos_del_btn);
        review_show_btn=(Button)findViewById(R.id.pos_review_show_btn);
        group_btn=(Button)findViewById(R.id.writer_name);
        forward_btn=(Button)findViewById(R.id.post_forward_btn);
        backward_btn=(Button)findViewById(R.id.post_backward_btn);
        home_btn=(Button)findViewById(R.id.post_detail_home);

        review_show_btn.setOnClickListener(this);
        edit_btn.setOnClickListener(this);
        del_btn.setOnClickListener(this);
        scrap_btn.setOnClickListener(this);
        report_btn.setOnClickListener(this);
        group_btn.setOnClickListener(this);
        forward_btn.setOnClickListener(this);
        backward_btn.setOnClickListener(this);
        home_btn.setOnClickListener(this);

        //텍스트뷰
        start_day=(TextView)findViewById(R.id.pos_start_day);
        end_day=(TextView)findViewById(R.id.pos_end_day);
        link=(TextView)findViewById(R.id.pos_link);
        type=(TextView)findViewById(R.id.pos_type);
        post_day=(TextView)findViewById(R.id.posting_date);
        title=(TextView)findViewById(R.id.pos_title);
        content=(TextView)findViewById(R.id.pos_content);
        view_num=(TextView)findViewById(R.id.pos_view_num);
        review_num=(TextView)findViewById(R.id.pos_review_num);

        //이미지뷰
        post_img=(ImageView)findViewById(R.id.poster);




        if(carrier.getFromWriting()==1) {

            phpCreate();

            new AlertDialog.Builder(this)
                    .setTitle("푸시알람설정")
                    .setMessage("바로 푸시를 보내시겠습니까?")
                    .setIcon(R.drawable.handongi)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        SendPush sendPush = new SendPush();
                            sendPush.execute();
                        }
                    })
                    .setNegativeButton("취소", null)
                    .show();
        }
        else
        {
            if(post.getHas_pic().compareTo("1")==0) {
                construction();
            }

            if(carrier.getId().compareTo(post.getKakao_id())==0) {
                scrap_btn.setVisibility(View.GONE);
                report_btn.setVisibility(View.INVISIBLE);
                edit_btn.setVisibility(View.VISIBLE);
                del_btn.setVisibility(View.VISIBLE);
            }
            start_day.setText(post.getStart_date());
            end_day.setText(post.getEnd_date());
            //link.setText(post.getLink());
            type.setText(post.getCategory());
            post_day.setText(post.getPosting_date());
            title.setText(post.getTitle());
            content.setText(post.getContent());
            view_num.setText(post.getView_num()+1+" Views");
        }
    }

    public void construction() {
        post_img.setVisibility(View.VISIBLE);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screen_width = metrics.widthPixels;
        PostImageTask postImageTask;
        if(carrier.getFromWriting()==1) {
            postImageTask = new PostImageTask(poster,posting_id,post_img,screen_width,temp);
        }
        else {
            postImageTask = new PostImageTask(poster, post.getId(), post_img, screen_width, temp);//수영 수정, temp 추가
        }
            postImageTask.execute(0);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.pos_review_show_btn : {
                Intent intent = new Intent(PostDetail.this, Review.class).putExtra("carrier", carrier);
                startActivity(intent);
                break;
            }
            case R.id.pos_edit_btn : {

                carrier.setPost_id(post.getId());
                carrier.setBig_category(post.getBig_category());
                carrier.setCategory(post.getCategory());
                carrier.setTitle(post.getTitle());
                carrier.setContent(post.getContent());
                carrier.setStart_date(post.getStart_date());
                carrier.setEnd_date(post.getEnd_date());
                carrier.setLink(post.getLink());
                carrier.setHas_pic(Integer.parseInt(post.getHas_pic()));

                Log.d("postingid", String.valueOf(carrier.getPost_id()));
                Log.d("bigcategory", String.valueOf(carrier.getBig_category()));
                Log.d("category", carrier.getCategory());
                Log.d("title", carrier.getTitle());
                Log.d("content", carrier.getContent());
                Log.d("startdate", carrier.getStart_date());
                Log.d("enddate", carrier.getEnd_date());
                Log.d("editcount", String.valueOf(carrier.getEdit_count()));

                carrier.setEdit_count(1);

                Intent intent = new Intent(PostDetail.this, Writing.class).putExtra("carrier", carrier);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.pos_report_btn : {
                //신고하시겠습니까 메시지가 뜨고 난 후에 다음 엑티비티로.

                //넘어갈 때 필요한 변수,   게시물 아이디,
                carrier.setPost_id(post.getId());
                carrier.setPosting_date(post.getPosting_date());

                Intent intent = new Intent(PostDetail.this, ReportPost.class).putExtra("carrier", carrier);
                startActivity(intent);
                break;
            }
            case R.id.pos_del_btn : {
                new AlertDialog.Builder(this)
                        .setTitle("삭제")
                        .setMessage("삭제하시겠습니까?")
                        .setIcon(R.drawable.handongi)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                delPhp();
                                Intent intent = new Intent(PostDetail.this, yj_activity.class).putExtra("carrier", carrier);
                                startActivity(intent);
                                finish();
                                Log.d("delete_posting_id", String.valueOf(post.getId()));
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
                break;
            }
            case R.id.post_detail_home : {
                carrier.setFromWriting(0);
                carrier.setFromPostDetail(0);
                carrier.setEdit_count(0);
                carrier.setGroup_name("");
                carrier.setGroup_code("");
                carrier.setBig_category(null);
                carrier.setCategory(null);
                carrier.setTitle(null);
                carrier.setContent(null);
                carrier.setPosting_date(null);
                carrier.setStart_date(null);
                carrier.setEnd_date(null);
                carrier.setLink(null);
                Intent intent = new Intent(PostDetail.this,yj_activity.class).putExtra("carrier",carrier);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.post_forward_btn : {
                if(position==posting_list.size()-1)
                    break;
                else {
                    Intent intent=new Intent(this,PostDetail.class);
                    intent.putExtra("carrier", carrier);
                    intent.putExtra("post_list",posting_list);
                    intent.putExtra("position",position+1);
                    intent.putExtra("post", posting_list.get(position+1));
                    startActivity(intent);
                    finish();
                    break;
                }
            }

            case R.id.post_backward_btn : {
                if(position==0)
                    break;
                else {
                    Intent intent=new Intent(this,PostDetail.class);
                    intent.putExtra("carrier", carrier);
                    intent.putExtra("post_list",posting_list);
                    intent.putExtra("position",position-1);
                    intent.putExtra("post", posting_list.get(position-1));
                    startActivity(intent);
                    finish();
                    break;
                }
            }
        }
    }
    public void phpCreate() {
        postdbphp = new PostDB_Php(post_list,this);
        postdbphp.execute("http://hungry.portfolio1000.com/smarthandongi/posting_php.php?kakao_id=995977");
    }

    public class PostDB_Php extends AsyncTask<String, android.R.integer, String> {

        private ArrayList<PostDatabase> post_list;
        private Activity activity;

        public PostDB_Php(ArrayList<PostDatabase> post_list, Activity activity) {
            super();
            this.post_list = post_list;
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

                post_list.removeAll(post_list);

                posting_id=ja.getJSONObject(0).getInt("id");


            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
        //수영 추가
    private class SendPush extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loagindDialog = ProgressDialog.show(PostDetail.this, "키 등록 중입니다..",
                    "Please wait..", true, false);
        }

        @Override
        protected Void doInBackground(String... params) {
            HttpPostData(posting_id);
            return null;
        }

        protected void onPostExecute(Void result) {
            loagindDialog.dismiss();
        }
    }
    public void HttpPostData(int posting_id ) {
        try {
            String posting_id1 = String.valueOf(posting_id);
            URL url = new URL("http://hungry.portfolio1000.com/smarthandongi/want_push.php?posting_id="+posting_id1);       // URL 설정
            HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속
            //--------------------------
            //   전송 모드 설정 - 기본적인 설정이다
            //--------------------------
            http.setDefaultUseCaches(false);
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");

            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            StringBuffer buffer = new StringBuffer();
            buffer.append("posting_id").append("=").append(posting_id);                 // php 변수에 값 대입


            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "EUC-KR");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            writer.flush();
            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                builder.append(str + "\n");
            }

            myResult = builder.toString();

        } catch (MalformedURLException e) {
            //
        } catch (IOException e) {
            //
        } // try
    } // HttpPostData
    public void delPhp(){
        del_php = new DeletePhp();
        del_php.execute("http://hungry.portfolio1000.com/smarthandongi/delete_post.php?posting_id=" + post.getId());
    }

    public class DeletePhp extends AsyncTask<String, Integer, String>{

        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            String return_str = "";
            String result = "";

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
                try {
                    JSONObject root = new JSONObject(jsonHtml.toString());
                    JSONArray ja = root.getJSONArray("results");
                    JSONObject jo = ja.getJSONObject(0);
                    result = jo.getString("result");                                                       //php를 통해서 업로드가 되었는지 확인하기 위해 $result의 값을 받아온다.
                    Log.d("result", result);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return_str = jsonHtml.toString();
            }
            Log.v("연결 시도", "연결되어라doinbackground$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$4");
            return jsonHtml.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
