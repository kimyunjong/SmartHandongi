package com.smarthandongi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.PaintDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

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
    ImageView new_img, comment_register_on, comment_register_off;

    Dialog dialog_delete_reply;
    Button dialog_delete_confirm, dialog_delete_cancel;
    LinearLayout dialog_delete_background;

    PhpDownloadReview phpDownloadReview;
    PhpUploadPushReview phpUploadPushReview;
    Typeface typeface;

    int posting_id,position;
    String kakao_id;
    ReviewDatabase reviewDatabase;
    final Context context = this;
    ReviewAdapter adapter;
    private PostDatabase post;
    private ArrayList<ReviewDatabase> review_list = new ArrayList<ReviewDatabase>();
    ArrayList<PostDatabase> post_list, all_posting_list;
    private ListView review_listview;
    ScrollView scrollview;

    long time1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);
        Intent intent=getIntent();
        typeface = Typeface.createFromAsset(getAssets(), "KOPUBDOTUM_PRO_MEDIUM.OTF");

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
        review_write.setTypeface(typeface);
        reg_btn = (Button)findViewById(R.id.reg_btn);
        del_btn = (Button)findViewById(R.id.del_btn);
        back_btn = (Button)findViewById(R.id.back_btn);
        notify_btn = (Button)findViewById(R.id.notify_btn);
        new_img = (ImageView)findViewById(R.id.new_img);
        scrollview=(ScrollView)findViewById(R.id.scrollview);
        comment_register_off=(ImageView)findViewById(R.id.comment_register_off);
        comment_register_on=(ImageView)findViewById(R.id.comment_register_on);

        review_write.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                review_write.setCursorVisible(true);
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(review_write, 0);
                scrollview.setVerticalScrollBarEnabled(false);
                return true;
            }
        });


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        reg_btn.setOnClickListener(this);
        //del_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);

        review_listview = (ListView) findViewById(R.id.review_list);
        review_listview.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        review_listview.setSelector(new PaintDrawable(0x00000000));

        php_downloadCreate();

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charsequence, int i, int j, int k) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence charsequence, int i, int j, int k) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // TODO Auto-generated method stub
                comment_register_off.setVisibility(View.INVISIBLE);
                comment_register_on.setVisibility(View.VISIBLE);
            }
        };
        review_write.addTextChangedListener(watcher);

    }
    @Override
    //등록버튼을 눌렀을 경우
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reg_btn: {
                if(carrier.isLogged_in())
                {
                    php_uploadCreate();
                }
                else
                {
                    Toast.makeText(this, "로그인 후 이용하실 수 있습니다.", 300).show();
                }

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
            //        Log.d("dkssbd","d kjl;");
                    startActivity(intent);
                    finish();

                    break;
                }
            }
            case R.id.del_btn: {

                dialog_delete_reply = new Dialog(context);
                dialog_delete_reply.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_delete_reply.setContentView(R.layout.dialog_delete);
                dialog_delete_reply.show();

                dialog_delete_background = (LinearLayout)dialog_delete_reply.findViewById(R.id.dialog_delete_background);
                dialog_delete_background.setBackgroundResource(R.drawable.dialog_delete_reply);

                dialog_delete_confirm = (Button)dialog_delete_reply.findViewById(R.id.dialog_delete_confirm);
                dialog_delete_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                    }
                });

                dialog_delete_cancel = (Button)dialog_delete_reply.findViewById(R.id.dialog_delete_cancel);
                dialog_delete_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_delete_reply.dismiss();
                    }
                });


                break;
            }
            case R.id.notify_btn: {
                dialog_delete_reply = new Dialog(context);
                dialog_delete_reply.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_delete_reply.setContentView(R.layout.dialog_delete);
                dialog_delete_reply.show();

                dialog_delete_background = (LinearLayout)dialog_delete_reply.findViewById(R.id.dialog_delete_background);
                dialog_delete_background.setBackgroundResource(R.drawable.dialog_report_reply);

                dialog_delete_confirm = (Button)dialog_delete_reply.findViewById(R.id.dialog_delete_confirm);
                dialog_delete_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Review.this, ReportPost.class);
                        intent.putExtra("carrier", carrier);
                        startActivity(intent);
                    }
                });

                dialog_delete_cancel = (Button)dialog_delete_reply.findViewById(R.id.dialog_delete_cancel);
                dialog_delete_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_delete_reply.dismiss();
                    }
                });
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

         String reply_date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
         Log.d("날짜", reply_date);

         if(carrier.getContent()=="")
         {
             Log.d("con","내용이 빔");
             Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
             carrier.setNickname(reset_kakao_nick);
         }
         else
         {
             Log.d("content",carrier.getContent());
             //댓글 올리고 푸시보내기
             phpUploadPushReview = new PhpUploadPushReview();
             phpUploadPushReview.execute("http://hungry.portfolio1000.com/smarthandongi/review_up_push.php?"
                     + "kakao_id=" + carrier.getId()
                     + "&reply_date=" + reply_date
                     + "&kakao_nick=" + carrier.getNickname()
                     + "&posting_id=" + posting_id
                     + "&content=" + carrier.getContent());

             carrier.setNickname(reset_kakao_nick);

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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {    }

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
                int i;
                for ( i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    review_list.add(new ReviewDatabase(jo.getInt("posting_id"),
                             jo.getInt("review_id"),jo.getString("kakao_id"), jo.getString("kakao_nick"),

                             jo.getString("reply_date"), jo.getString("content")
                    ));
                }

                adapter = new ReviewAdapter(Review.this ,review_list, carrier);

                review_listview.setAdapter(adapter);
                Log.d("size down",String.valueOf(review_list.size()));
                Log.d("size i", String.valueOf(i));
                review_listview.setOnScrollListener(context);

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
    //================================================================
    public class PhpUploadPushReview extends AsyncTask<String, android.R.integer, String> {

        public PhpUploadPushReview() {
            super();
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
            super.onPostExecute(str);

        }
    }
}
