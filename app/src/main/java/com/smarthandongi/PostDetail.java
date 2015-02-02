package com.smarthandongi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarthandongi.database.PostDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Joel on 2015-01-25.
 */
public class PostDetail extends Activity{

    private Carrier carrier;
    private PostDatabase post;
    private PostDB_Php postdbphp;
    private TextView title, post_day, start_day, end_day, content, view_num,review_num,img,link,
                     type;

    private ImageView background,post_img;
    private Button scrap_btn, del_btn, review_show_btn, edit_btn,writer_btn;

    ArrayList<PostDatabase> post_list = new ArrayList<PostDatabase>();

    private int posting_id;
   //수영 추가
    String myResult;
    ProgressDialog loagindDialog;
    //수영 추가 끝
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        carrier=(Carrier)intent.getSerializableExtra("carrier");
        post=(PostDatabase)intent.getSerializableExtra("post");

        setContentView(R.layout.post_detail);

        //버튼
        writer_btn=(Button)findViewById(R.id.writer_name);
        writer_btn.setShadowLayer(0,0,0,0);

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
            start_day.setText(post.getStart_date());
            end_day.setText(post.getEnd_date());
            //link.setText(post.getLink());
            type.setText(post.getCategory());
            post_day.setText(post.getPosting_date());
            title.setText(post.getTitle());
            content.setText(post.getContent());
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

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    post_list.add(new PostDatabase(
                            jo.getString("title"), jo.getInt("id"), jo.getString("kakao_id"), jo.getString("category"), jo.getString("group"),
                            jo.getString("content"), jo.getString("posting_date"), jo.getString("image_link"), jo.getString("start_date"), jo.getString("end_date"), jo.getString("has_pic"), jo.getString("like")
                    ));

                    System.out.println(jo.getString("title")+"확인할 부분 입니다."+jo.getString("like"));
                }
                posting_id=ja.getJSONObject(0).getInt("id");
                System.out.println(posting_id);
                start_day.setText(ja.getJSONObject(0).getString("start_date"));
                end_day.setText(ja.getJSONObject(0).getString("end_date"));
                link.setText(ja.getJSONObject(0).getString("link"));
                //type.setText(carrier.getCategory());
                post_day.setText(ja.getJSONObject(0).getString("posting_date"));
                title.setText(ja.getJSONObject(0).getString("title"));
                content.setText(ja.getJSONObject(0).getString("content"));
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
            URL url = new URL("http://hungry.portfolio1000.com/smarthandongi/want_push.php?posting_id="+posting_id);       // URL 설정
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

}
