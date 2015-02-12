package com.smarthandongi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Joel on 2015-01-25.
 */
public class PostDetail extends Activity implements View.OnClickListener{

    private Carrier carrier;
    private PostDatabase post;
    private PostDB_Php postdbphp;
    private DeletePhp del_php;
    private TextView title, post_day, start_day, end_day, content, view_num,review_num,img,link, writer_group_name, writer_name,
            type, pos_between_days, dialog_push_title, dialog_push_text;
    final Context context = this;
    Dialog dialog_del, dialog_report, dialog_push;
    Button dialog_report_cancel, dialog_report_confirm, dialog_delete_cancel, dialog_delete_confirm, dialog_push_cancel, dialog_push_confirm;


    ImageView post_img;
    private Button scrap_btn, del_btn, review_show_btn, edit_btn,report_btn,group_btn,home_btn,backward_btn,forward_btn;
    ImageButton pos_push;

    ArrayList<PostDatabase> post_list = new ArrayList<PostDatabase>();
    ArrayList<PostDatabase> posting_list,all_posting_list;

    private int posting_id,position;
    int screen_width;
    Picture poster = new Picture();
    RelativeLayout pos_delete, pos_scrap, pos_edit, pos_report, popup_delete_1, popup_delete_2, popup_delete_3, popup_push_confirm, popup_cancel;
    LinearLayout pos_dates, pos_linkbar;
    String category = "", small_category = "";
    Typeface typeface;

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
        all_posting_list=(ArrayList)intent.getSerializableExtra("all_posting_list");

        setContentView(R.layout.post_detail);

        //버튼
        writer_group_name =(TextView)findViewById(R.id.writer_group_name);
        writer_name =(TextView)findViewById(R.id.writer_name);
        writer_group_name.setShadowLayer(0, 0, 0, 0);
        scrap_btn=(Button)findViewById(R.id.pos_scrap_btn);
        report_btn=(Button)findViewById(R.id.pos_report_btn);
        edit_btn=(Button)findViewById(R.id.pos_edit_btn);
        del_btn=(Button)findViewById(R.id.pos_del_btn);
        review_show_btn=(Button)findViewById(R.id.pos_review_show_btn);
        forward_btn=(Button)findViewById(R.id.post_forward_btn);
        backward_btn=(Button)findViewById(R.id.post_backward_btn);
        home_btn=(Button)findViewById(R.id.post_detail_home);
        pos_push = (ImageButton)findViewById(R.id.pos_push);

        writer_group_name.setOnClickListener(this);
        review_show_btn.setOnClickListener(this);
        edit_btn.setOnClickListener(this);
        del_btn.setOnClickListener(this);
        scrap_btn.setOnClickListener(this);
        report_btn.setOnClickListener(this);
        forward_btn.setOnClickListener(this);
        backward_btn.setOnClickListener(this);
        home_btn.setOnClickListener(this);
        pos_push.setOnClickListener(this);

        //텍스트뷰
        pos_between_days=(TextView)findViewById(R.id.pos_between_days);
        start_day=(TextView)findViewById(R.id.pos_start_day);
        end_day=(TextView)findViewById(R.id.pos_end_day);
        link=(TextView)findViewById(R.id.pos_link);
        type=(TextView)findViewById(R.id.pos_type);
        post_day=(TextView)findViewById(R.id.posting_date);
        title=(TextView)findViewById(R.id.pos_title);
        content=(TextView)findViewById(R.id.pos_content);
        view_num=(TextView)findViewById(R.id.pos_view_num);
        review_num=(TextView)findViewById(R.id.pos_review_num);


        typeface = Typeface.createFromAsset(getAssets(), "KOPUBDOTUM_PRO_LIGHT.OTF");

        //폰트설정
        start_day.setTypeface(typeface);
        end_day.setTypeface(typeface);
        link.setTypeface(typeface);
        type.setTypeface(typeface);
        post_day.setTypeface(typeface);
        title.setTypeface(typeface);
        content.setTypeface(typeface);
        view_num.setTypeface(typeface);
        review_num.setTypeface(typeface);
        writer_name.setTypeface(typeface);
        writer_group_name.setTypeface(typeface);
        pos_between_days.setTypeface(typeface);

        //이미지뷰
        post_img=(ImageView)findViewById(R.id.poster);

        pos_delete = (RelativeLayout)findViewById(R.id.pos_delete);  //작성자
        pos_edit = (RelativeLayout)findViewById(R.id.pos_edit);
        pos_report = (RelativeLayout)findViewById(R.id.pos_report);  //비작성자
        pos_scrap = (RelativeLayout)findViewById(R.id.pos_scrap);
        pos_dates = (LinearLayout)findViewById(R.id.pos_dates);
        pos_linkbar = (LinearLayout)findViewById(R.id.pos_linkbar);

        popup_delete_1 = (RelativeLayout)findViewById(R.id.popup_delete_1);
        popup_delete_2 = (RelativeLayout)findViewById(R.id.popup_delete_2);
        popup_delete_3 = (RelativeLayout)findViewById(R.id.popup_delete_3);
        popup_push_confirm = (RelativeLayout)findViewById(R.id.popup_push);
        popup_cancel = (RelativeLayout)findViewById(R.id.popup_cancel);

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
                pos_scrap.setVisibility(View.GONE);
                pos_report.setVisibility(View.GONE);
                pos_edit.setVisibility(View.VISIBLE);
                pos_delete.setVisibility(View.VISIBLE);
                pos_push.setVisibility(View.VISIBLE);
            }
            if(post.getGroup().compareTo("") != 0){                             //리스트 내에 있을 때만 이거 적용
                //리스트 내부에 있는지 체크해서 있으면 clickable하게 바꿈
                writer_name.setVisibility(View.GONE);
                writer_group_name.setVisibility(View.VISIBLE);
                writer_group_name.setText(post.getGroup_name());                     //그룹코드 말고 그룹 네임 받아와야 한다.
                writer_group_name.setTextColor(Color.parseColor("#7CD752"));
            }
            else writer_name.setText(post.getKakao_nic());

            if(post.getStart_date().length() < 4) {                                             //시작일
                pos_dates.setVisibility(View.GONE);
            } else {
                pos_dates.setVisibility(View.VISIBLE);
                start_day.setText(post.getStart_date());
                end_day.setText(post.getEnd_date());                                            //종료일
            }

            if(post.getLink().length() == 0){
                pos_linkbar.setVisibility(View.GONE);                                       //링크
            } else {
                pos_linkbar.setVisibility(View.VISIBLE);
                link.setText(post.getLink());
            }

            switch(post.getBig_category()){                                                     //대분류
                case "1" : category = "일반공지"; break;
                case "2" : category = "대외활동"; break;
                case "3" : category = "공연·세미나"; break;
                case "4" : category = "리쿠르팅"; break;
                case "5" : category = "붙어라"; break;
                default: break;
            }
            switch(post.getCategory()){
                case "together_sports_1"        : small_category = "운동경기"; break;          //소분류
                case "together_game_2"          : small_category = "게임"; break;
                case "together_nightfood_3"     : small_category = "야식"; break;
                case "together_gonggu_4"        : small_category = "공동구매"; break;
                case "together_carpool_5"       : small_category = "카풀"; break;
                case "together_study_6"         : small_category = "스터디"; break;
                case "together_trading_7"       : small_category = "사고팔기"; break;
                case "together_lost_8"          : small_category = "분실물"; break;
                case "together_recruiting_9"    : small_category = "구인구직"; break;
                case "together_exchange_10"     : small_category = "교환"; break;

                case "outer_contest_21"     : small_category = "공모전"; break;
                case "outer_intern_22"      : small_category = "인턴"; break;
                case "outer_service_23"     : small_category = "자원봉사"; break;

                case "seminar_perf_41"          : small_category = "공연"; break;
                case "seminar_seminar_42"       : small_category = "세미나"; break;
                case "seminar_presentation_43"  : small_category = "발표"; break;

                case "recruiting_scholarship_61" : small_category = "학술"; break;
                case "recruiting_sports_62"      : small_category = "운동"; break;
                case "recruiting_perf_63"        : small_category = "공연"; break;
                case "recruiting_faith_64"       : small_category = "신앙"; break;
                case "recruiting_display_65"     : small_category = "전시"; break;
                case "recruiting_service_66"     : small_category = "봉사"; break;
            }
            type.setText("[" + category + "/" + small_category + "]");                                // [대분류/소분류]

            post_day.setText(post.getPosting_date());                                           //등록일

            title.setText(post.getTitle());                                                     //제목

            content.setText(post.getContent());                                                 //내용

            view_num.setText(post.getView_num()+1+"");                                          //조회수

            carrier.setEdit_count(0);
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
                intent.putExtra("posting_id", post.getId());
                intent.putExtra("kakao_id", post.getKakao_id());
                startActivity(intent);
                break;
            }
            case R.id.pos_edit_btn : {

                carrier.setPost_id(post.getId());
                carrier.setBig_category(post.getBig_category());
                carrier.setCategory(post.getCategory());
                carrier.setTitle(post.getTitle());
                carrier.setContent(post.getContent());
                carrier.setPosting_date(post.getPosting_date());
                carrier.setStart_date(post.getStart_date());
                carrier.setEnd_date(post.getEnd_date());
                carrier.setLink(post.getLink());
                carrier.setHas_pic(Integer.parseInt(post.getHas_pic()));
                carrier.setEdit_count(1);

                Log.d("postingid", String.valueOf(carrier.getPost_id()));
                Log.d("bigcategory", String.valueOf(carrier.getBig_category()));
                Log.d("category", carrier.getCategory());
                Log.d("title", carrier.getTitle());
                Log.d("content", carrier.getContent());
                Log.d("startdate", carrier.getStart_date());
                Log.d("enddate", carrier.getEnd_date());
                Log.d("editcount", String.valueOf(carrier.getEdit_count()));

                Intent intent = new Intent(PostDetail.this, Writing.class).putExtra("carrier", carrier);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.pos_report_btn : {

                dialog_report = new Dialog(context);
                dialog_report.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_report.setContentView(R.layout.dialog_report);
                dialog_report.show();

                dialog_report_cancel = (Button)dialog_report.findViewById(R.id.dialog_report_cancel);
                dialog_report_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_report.dismiss();
                    }
                });

                dialog_report_confirm = (Button)dialog_report.findViewById(R.id.dialog_report_confirm);
                dialog_report_confirm.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //신고하시겠습니까 메시지가 뜨고 난 후에 다음 엑티비티로.
                        dialog_report.dismiss();
                        //넘어갈 때 필요한 변수,   게시물 아이디,
                        carrier.setPost_id(post.getId());
                        carrier.setPosting_date(post.getPosting_date());
                        Intent intent = new Intent(PostDetail.this, ReportPost.class).putExtra("carrier", carrier);
                        startActivity(intent);
                    }
                });
                break;
            }
            case R.id.pos_scrap_btn : {
                //스크랩 동작
                break;
            }

            case R.id.pos_del_btn : {

                dialog_del = new Dialog(context);
                dialog_del.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_del.setContentView(R.layout.dialog_delete);
                dialog_del.show();

                dialog_delete_cancel = (Button)dialog_del.findViewById(R.id.dialog_delete_cancel);
                dialog_delete_cancel.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        dialog_del.dismiss();
                    }
                });

                dialog_delete_confirm = (Button)dialog_del.findViewById(R.id.dialog_delete_confirm);
                dialog_delete_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_del.dismiss();
                        delPhp();
                        new CountDownTimer(1500, 300) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                popup_delete_1.setVisibility(VISIBLE);
                                popup_delete_2.setVisibility(VISIBLE);
                                popup_delete_3.setVisibility(VISIBLE);
                            }
                            @Override
                            public void onFinish() {
                                popup_delete_1.setVisibility(GONE);
                                popup_delete_2.setVisibility(GONE);
                                popup_delete_3.setVisibility(GONE);

                                Intent intent = new Intent(PostDetail.this, yj_activity.class).putExtra("carrier", carrier);
                                startActivity(intent);
                                finish();
                            }
                        }.start();

                    }
                });
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
            case R.id.pos_push : {
                String popup_message;
                popup_message = "항목을 설정한 사람들에게만 보내집니다. 알람은 1회만 가능합니다.";

                dialog_push = new Dialog(context);
                dialog_push.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_push.setContentView(R.layout.dialog_push);
                dialog_push.show();

                dialog_push_title = (TextView)dialog_push.findViewById(R.id.dialog_push_title);
                dialog_push_title.setText("알람을 보내시겠습니까?");
                dialog_push_title.setTypeface(typeface);

                dialog_push_text = (TextView)dialog_push.findViewById(R.id.dialog_push_text);
                dialog_push_text.setText("'" + small_category + "' "+ popup_message);
                dialog_push_text.setTypeface(typeface);

                dialog_push_cancel = (Button)dialog_push.findViewById(R.id.dialog_push_cancel);
                dialog_push_cancel.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        dialog_push.dismiss();

                        new CountDownTimer(1500, 300) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                popup_delete_1.setVisibility(VISIBLE);
                                popup_delete_2.setVisibility(VISIBLE);
                                popup_cancel.setVisibility(VISIBLE);
                            }
                            @Override
                            public void onFinish() {
                                popup_delete_1.setVisibility(GONE);
                                popup_delete_2.setVisibility(GONE);
                                popup_cancel.setVisibility(GONE);
                            }
                        }.start();
                    }
                });

                dialog_push_confirm = (Button)dialog_push.findViewById(R.id.dialog_push_confirm);
                dialog_push_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_push.dismiss();
                        pos_push.setVisibility(GONE);
                        //푸시보내는거
                        //푸시카운트 0으로 초기화

                        new CountDownTimer(1500, 300) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                popup_delete_1.setVisibility(VISIBLE);
                                popup_delete_2.setVisibility(VISIBLE);
                                popup_push_confirm.setVisibility(VISIBLE);
                            }
                            @Override
                            public void onFinish() {
                                popup_delete_1.setVisibility(GONE);
                                popup_delete_2.setVisibility(GONE);
                                popup_push_confirm.setVisibility(GONE);
                            }
                        }.start();

                    }
                });
                break;
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
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if ( dialog_del != null)
            dialog_del.dismiss();
    }
    public void onBackPressed() {
        if(carrier.getFromSMP()==1) {
            Intent intent = new Intent(PostDetail.this,SeeMyPost.class).putExtra("carrier",carrier);
            intent.putExtra("post_list",posting_list);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(PostDetail.this, yj_activity.class).putExtra("carrier", carrier);
            startActivity(intent);
            finish();
        }
    }
}
