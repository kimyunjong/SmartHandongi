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
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.smarthandongi.adapter.ReviewAdapter;
import com.smarthandongi.database.PostDatabase;
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

    int posting_id,position;
    String kakao_id;
    ReviewDatabase reviewDatabase;
    Context context;
    ReviewAdapter adapter;
    private PostDatabase post;
    private ArrayList<ReviewDatabase> review_list = new ArrayList<ReviewDatabase>();
    ArrayList<PostDatabase> post_list,all_posting_list;
    private ListView review_listview;

    long time1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);
        Intent intent=getIntent();

        carrier = (Carrier)getIntent().getSerializableExtra("carrier");
        posting_id =(int)intent.getSerializableExtra("posting_id");
        post_list = (ArrayList) intent.getSerializableExtra("post_list");

        if(carrier.getFromSMP()==0) {
            post = (PostDatabase) intent.getSerializableExtra("post");
            Log.d("yes I can","Yes we Can");
            position = (int) intent.getSerializableExtra("position");
        }
       // kakao_id =(String)intent.getSerializableExtra("kakao_id");

        review_write = (EditText)findViewById(R.id.review_write);
        reg_btn = (Button)findViewById(R.id.reg_btn);
        del_btn = (Button)findViewById(R.id.del_btn);
        back_btn = (Button)findViewById(R.id.back_btn);
        notify_btn = (Button)findViewById(R.id.notify_btn);
        new_img = (ImageView)findViewById(R.id.new_img);

        review_write.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                review_write.setCursorVisible(true);
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(review_write, 0);
                return true;
            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //reg_btn.setBackgroundResource( getItem(position).getLike().compareTo("0") ==0 ? R.drawable.like : R.drawable.not_like);
        reg_btn.setOnClickListener(this);
        //del_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);

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
                if(carrier.getFromSMP()==1) {
                    Intent intent=new Intent(Review.this,SeeMyPost.class);
                    intent.putExtra("post_list",post_list);
                    intent.putExtra("carrier", carrier);
                    startActivity(intent);
                    finish();
                    break;
                }
                else {
                    Intent intent = new Intent(Review.this, PostDetail.class);
                    //intent.putExtra("posting_id", posting_id);
                    intent.putExtra("carrier", carrier);
                    intent.putExtra("post_list", post_list);
                    intent.putExtra("position", position);
                    intent.putExtra("post", post);
                    Log.d("dkssbd","dkjl;");
                    startActivity(intent);
                    finish();

                    break;
                }
            }
            case R.id.del_btn: {
                Intent intent = new Intent(Review.this,Review.class);

                if(carrier.getFromSMP()==0) {
                    intent.putExtra("post",post);
                    intent.putExtra("position",position);
                }
                else if(carrier.getFromSMP()==1) {//내가쓴글에서 댓글을 추가하고 엑스를 눌리라고할때를 위해서
                    carrier.setFromSMP(1);
                    carrier.setFromSMPcomment(1);
                    intent.putExtra("post_list",post_list);
                }
                intent.putExtra("carrier",carrier);
                intent.putExtra("posting_id",posting_id);

                startActivity(intent);
                finish();



                break;
            }
            case R.id.notify_btn: {
               // Toast.makeText(this, "신고버튼",Toast.LENGTH_SHORT).show();

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
         final String reset_kakao_nick;
         String content;

         content = review_write.getText().toString();
         kakao_nick = carrier.getNickname();
         kakao_id = carrier.getId();
         carrier.setContent(content);

         reset_kakao_nick = kakao_nick;

         Log.d("kakao_nick_get", carrier.getNickname());
         Log.d("reset_kakao_nick", reset_kakao_nick);

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

                            Log.d("kakao_nick_get전", carrier.getNickname());
                            carrier.setNickname(reset_kakao_nick);

                            Log.d("kakao_nick_get후", carrier.getNickname());

                            String nowtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                            review_write.setText(null);
                            Intent intent = new Intent(Review.this,Review.class);
                            intent.putExtra("carrier",carrier);
                            intent.putExtra("posting_id",posting_id);
                            intent.putExtra("post_list",post_list);
                            if(carrier.getFromSMP()==1) {
                                carrier.setFromSMPcomment(1);
                            }
                            if(carrier.getFromSMP()==0) {
                                intent.putExtra("post",post);
                                intent.putExtra("position",position);
                            }
                            startActivity(intent);
                            finish();
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
