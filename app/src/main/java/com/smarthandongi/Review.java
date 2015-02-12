package com.smarthandongi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.smarthandongi.adapter.ReviewAdapter;
import com.smarthandongi.database.ReviewDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Review extends Activity implements View.OnClickListener, AbsListView.OnScrollListener {

    Carrier carrier;

    EditText review_write;
    Button reg_btn, back_btn, del_btn, notify_btn;
    ImageView new_img;

    PhpUploadReview phpUploadReview;
    PhpDownloadReview phpDownloadReview;

    int posting_id;
    String kakao_id;
    ReviewDatabase reviewDatabase;
    Context context;
    ReviewAdapter adapter;
    private ArrayList<ReviewDatabase> review_list = new ArrayList<ReviewDatabase>();
    private ListView review_listview;

    long time1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);
        Intent intent=getIntent();

        carrier = (Carrier)getIntent().getSerializableExtra("carrier");
        posting_id =(int)intent.getSerializableExtra("posting_id");
       // kakao_id =(String)intent.getSerializableExtra("kakao_id");

        review_write = (EditText)findViewById(R.id.review_write);
        reg_btn = (Button)findViewById(R.id.reg_btn);
        del_btn = (Button)findViewById(R.id.del_btn);
        back_btn = (Button)findViewById(R.id.back_btn);
        notify_btn = (Button)findViewById(R.id.notify_btn);
        new_img = (ImageView)findViewById(R.id.new_img);


        //reg_btn.setBackgroundResource( getItem(position).getLike().compareTo("0") ==0 ? R.drawable.like : R.drawable.not_like);
        reg_btn.setOnClickListener(this);
        //del_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
/*
        if(reviewDatabase.getKakao_id().compareTo(kakao_id)==0) {
            notify_btn.setVisibility(View.GONE);
            del_btn.setVisibility(View.VISIBLE);
        }else{
            notify_btn.setVisibility(View.VISIBLE);
            del_btn.setVisibility(View.GONE);
        }
*/
        review_listview = (ListView) findViewById(R.id.review_list);
        review_listview.setSelector(new PaintDrawable(0x00000000));

        php_downloadCreate();


    }
    @Override
    //등록버튼을 눌렀을 경우
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reg_btn: {
                php_uploadCreate();
                break;
            }
            case R.id.back_btn: {
                Intent intent = new Intent(Review.this, PostDetail.class);
                intent.putExtra("posting_id", posting_id);
                intent.putExtra("carrier", carrier);
                startActivity(intent);

                break;
            }
            case R.id.del_btn: {
                Toast.makeText(this, "삭제버튼", Toast.LENGTH_SHORT).show();


                // delPhp();

               /* new AlertDialog.Builder(this)
                        .setTitle("삭제")
                        .setMessage("삭제하시겠습니까?")
                        .setIcon(R.drawable.handongi)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               // delPhp();
                               // Intent intent = new Intent(PostDetail.this, yj_activity.class).putExtra("carrier", carrier);
                               // startActivity(intent);
                               // finish();
                                Log.d("delete_review", String.valueOf(reviewDatabase.getReview_id()));

                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();*/

                //int pos = review_listview.getItemAtPosition();
               // int pos = review_listview.getCheckedItemPosition();
               // Log.d("삭제", String.valueOf(pos));
               // if(pos != ListView.INVALID_POSITION) {
               //     review_list.remove(pos);
               //     Log.d("삭제", String.valueOf(pos));
               //     review_listview.clearChoices();
               //     adapter.notifyDataSetChanged();
               // }else{
               //     Log.d("삭제", "삭제 fail");
               // }
                break;
            }
            case R.id.notify_btn: {
                Toast.makeText(this, "신고버튼",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Review.this, ReportPost.class);
                intent.putExtra("carrier", carrier);
                startActivity(intent);

                break;
            }
        }
    }

//============================================================================================================

     public void php_uploadCreate(){


         String kakao_id, kakao_nick;
         String content;


         content = review_write.getText().toString();
         kakao_nick = carrier.getNickname();
         kakao_id = carrier.getId();
         carrier.setContent(content);

         final String temp_kakao_id = kakao_id;
         final String temp_kakao_nick = kakao_nick;
         final String temp_content = content;

         Log.d("TAG", content);
         Log.d("TAG", kakao_nick);

        try {
            kakao_id = URLEncoder.encode(kakao_id, "UTF-8");
            content = URLEncoder.encode(content, "UTF-8");
            kakao_nick = URLEncoder.encode(kakao_nick, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        carrier.setNickname(kakao_nick);
        carrier.setContent(content);


        new AlertDialog.Builder(this)
                .setTitle("댓글 등록")
                .setMessage("댓글을 등록하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(carrier.getContent() ==""){

                        }else {
                            carrier.setUpload_url("http://hungry.portfolio1000.com/smarthandongi/review_upload.php?"
                                            + "kakao_id=" + carrier.getId()
                                            + "&kakao_nick=" + carrier.getNickname()
                                            + "&posting_id=" + posting_id
                                            + "&content=" + carrier.getContent()
                            );
                            phpUploadReview = new PhpUploadReview();
                            phpUploadReview.execute(carrier.getUpload_url());
                            carrier.setFromWriting(1);

                            String nowtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                            review_list.add(0, new ReviewDatabase(posting_id,0 , temp_kakao_id, temp_kakao_nick , nowtime, temp_content ));//===

                            review_write.setText(null);
                            adapter.notifyDataSetChanged();
                        }
                    }

                })
                .setNegativeButton("취소", null)
                .show();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {    }

    private class PhpUploadReview extends AsyncTask<String, Integer, String> {
        //int posting_id;
        public PhpUploadReview(){ super();}
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            String posting_id = null;
            try {
                //연결 URL설정
                URL url = new URL(urls[0]);
                //커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //연결되었으면
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for(;;){
                            String line = br.readLine();

                            if(line == null) break;
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
                posting_id = jo.getString("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return posting_id;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }

 //=================================================================================================
    public void php_downloadCreate() {
        phpDownloadReview = new PhpDownloadReview(review_list, this);
        phpDownloadReview.execute("http://hungry.portfolio1000.com/smarthandongi/review_download.php?"+
                                   "&posting_id=" + posting_id
        );
    }
    public class PhpDownloadReview extends AsyncTask<String, android.R.integer, String> {

        private Review context;
        private ArrayList<ReviewDatabase> review_list;

        public PhpDownloadReview(ArrayList<ReviewDatabase> review_list, Review context) {
            super();
            this.review_list = review_list;
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
            return jsonHtml.toString();
        }

        protected void onPostExecute(String str) {
            try {
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("results");

                review_list.removeAll(review_list);

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    review_list.add(new ReviewDatabase(jo.getInt("posting_id"),
                             jo.getInt("review_id"),jo.getString("kakao_id"), jo.getString("kakao_nick"),
                             jo.getString("reply_date"), jo.getString("content")
                    ));
                }

                adapter = new ReviewAdapter(Review.this ,review_list, carrier);

                review_listview.setAdapter(adapter);
                review_listview.setOnScrollListener(context);

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }


}
