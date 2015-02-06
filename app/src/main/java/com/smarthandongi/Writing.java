
package com.smarthandongi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.smarthandongi.database.Picture;
import com.smarthandongi.database.PostDatabase;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;

/**
 * Created by LEWIS on 2015-01-23.
 */
public class Writing extends Activity implements OnClickListener {
    Carrier carrier;
    private final int REQ_CODE_PICK_GALLERY = 900001;
    private final int REQ_CODE_PICK_CAMERA = 900002;
    private final int REQ_CODE_PICK_CROP = 900003;
    private int has_pic = 0;
    int screen_height = 0;
    private PostDatabase post;

    Picture poster = new Picture();
    Button writing_confirm_btn, writing_image_btn, writing_back_btn, writing_cancel_btn;
    ImageView big_category_img, small_category_img, writing_title_img, writing_body_img, writing_preview_img;
    ImageView writing_startdate_img, writing_enddate_img;
    Button big_category_btn, small_category_btn;
    EditText writing_title, writing_content, writing_link;
    ImageButton writing_additional_btn, writing_additional_hide_btn, writing_remove_pic_btn;
    LinearLayout writing_additional, linear;
    ScrollView scroll;

    PhpUpload task;

    //날짜관련 변수
    int year, month, day; //날짜 받기위해
    Calendar dateAndtime = Calendar.getInstance(),startDay,endDay;
    TextView start_dateLabel, end_dateLabel;
    java.text.DateFormat fmDateAndTime = java.text.DateFormat.getDateInstance();
    Button startDate_btn, endDate_btn;

    DatePickerDialog.OnDateSetListener start_d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            String sdate;//carrier에 저장하기 위한 스트링

            dateAndtime.set(Calendar.YEAR, year);
            dateAndtime.set(Calendar.MONTH,monthOfYear);
            dateAndtime.set(Calendar.DAY_OF_MONTH,dayOfMonth);


            System.out.println(monthOfYear);
            System.out.println(String.valueOf(monthOfYear));


            startDay = Calendar.getInstance();
            startDay.set(year, monthOfYear, dayOfMonth);
            endDay=Calendar.getInstance();
            endDay.set(year, monthOfYear, dayOfMonth); //default로 같은날 설정
            Log.d("시작일년도", String.valueOf(year));
            Log.d("시작일먼스", String.valueOf(monthOfYear));
            Log.d("시작일데이",String.valueOf(dayOfMonth));
            System.out.println(dayOfMonth);
            sdate=convertDateToString(year,monthOfYear,dayOfMonth);
            carrier.setStart_date(sdate);
            carrier.setEnd_date(sdate);

            start_dateLabel.setText(fmDateAndTime.format(dateAndtime.getTime()));
            end_dateLabel.setText(fmDateAndTime.format(dateAndtime.getTime())); //default로 같은날 설정

        }
    };

    DatePickerDialog.OnDateSetListener end_d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String sdate;

            dateAndtime.set(Calendar.YEAR, year);
            dateAndtime.set(Calendar.MONTH,monthOfYear);
            dateAndtime.set(Calendar.DAY_OF_MONTH,dayOfMonth);


            endDay = Calendar.getInstance();
            endDay.set(year,monthOfYear,dayOfMonth);
            Log.d("종료일년도",String.valueOf(year));
            Log.d("종료일데이",String.valueOf(monthOfYear));
            Log.d("종료일데이",String.valueOf(dayOfMonth));

            sdate=convertDateToString(year,monthOfYear,dayOfMonth);
            carrier.setEnd_date(sdate);

            end_dateLabel.setText(fmDateAndTime.format(dateAndtime.getTime()));
        }
    };


    //날짜관련 변수 끝

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing);
        carrier = (Carrier)getIntent().getSerializableExtra("carrier");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screen_height = metrics.heightPixels;

        writing_additional = (LinearLayout)findViewById(R.id.writing_additional);

        big_category_btn    = (Button)findViewById(R.id.big_category_btn);
        big_category_btn.setOnClickListener(this);
        small_category_btn  = (Button)findViewById(R.id.small_category_btn);
        small_category_btn.setOnClickListener(this);

        writing_title =     (EditText)findViewById(R.id.writing_title);
        writing_content  =  (EditText)findViewById(R.id.writing_content);
        writing_link = (EditText)findViewById(R.id.writing_link);

        writing_content.setHeight((int)(screen_height*0.45));
        scroll = (ScrollView)findViewById(R.id.scroll);
        linear = (LinearLayout)findViewById(R.id.linear);
        writing_title.setOnClickListener(this);

        writing_image_btn   = (Button)findViewById(R.id.writing_image_btn);
        writing_confirm_btn = (Button)findViewById(R.id.writing_confirm_btn);
        writing_back_btn    = (Button)findViewById(R.id.writing_back_btn);
        writing_additional_btn = (ImageButton)findViewById(R.id.writing_additional_btn);
        writing_additional_hide_btn = (ImageButton)findViewById(R.id.writing_additional_hide_btn);
        writing_cancel_btn  = (Button)findViewById(R.id.writing_cancel_btn);


        writing_image_btn.setOnClickListener(this);
        writing_confirm_btn.setOnClickListener(this);
        writing_back_btn.setOnClickListener(this);
        writing_additional_btn.setOnClickListener(this);
        writing_additional_hide_btn.setOnClickListener(this);
        writing_cancel_btn.setOnClickListener(this);

        writing_title_img = (ImageView)findViewById(R.id.writing_title_img);
        writing_body_img = (ImageView)findViewById(R.id.writing_body_img);
        writing_preview_img = (ImageView)findViewById(R.id.writing_preview_img);
        small_category_img = (ImageView)findViewById(R.id.small_category_img);
        big_category_img = (ImageView)findViewById(R.id.big_category_img);
        writing_startdate_img = (ImageView)findViewById(R.id.writing_startdate_img);
        writing_enddate_img = (ImageView)findViewById(R.id.writing_enddate_img);

        writing_remove_pic_btn = (ImageButton)findViewById(R.id.writing_remove_pic_btn);
        writing_remove_pic_btn.setOnClickListener(this);

        start_dateLabel=(TextView)findViewById(R.id.start_date_label);
        end_dateLabel=(TextView)findViewById(R.id.end_date_label);
        startDate_btn=(Button)findViewById(R.id.startdate_choose);
        endDate_btn=(Button)findViewById(R.id.enddate_choose);

        startDate_btn.setOnClickListener(this);
        endDate_btn.setOnClickListener(this);

//        entire_layout = (LinearLayout)findViewById(R.id.entire_layout);
//        entire_layout.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputManager.hideSoftInputFromWindow(writing_title.getWindowToken(), 0);
//                inputManager.hideSoftInputFromWindow(writing_content.getWindowToken(), 0);
//                inputManager.hideSoftInputFromWindow(writing_link_text.getWindowToken(), 0);
//                return true;
//            }
//        });
        carrier.setEdit_count(1);

        if(carrier.getEdit_count() == 0){
        //날짜표시
        start_dateLabel.setText(fmDateAndTime.format(dateAndtime.getTime()));
        String str=convertDateToString(dateAndtime.get(Calendar.YEAR),dateAndtime.get(Calendar.MONTH),dateAndtime.get(Calendar.DAY_OF_MONTH));
        carrier.setStart_date(str);
        carrier.setEnd_date(str);
        carrier.setPosting_date(str);

        end_dateLabel.setText(fmDateAndTime.format(dateAndtime.getTime()));
        }
        else {
            preset();
        }
    }

    public void preset(){
        String[] category_temp;

        big_category_img.setImageResource(R.drawable.writing_category_empty);       //대분류
        switch(carrier.getBig_category()) {
            case 1 : { big_category_btn.setText("일반공지");
                small_category_img.setBackgroundResource(0);
                break;}
            case 2 : { big_category_btn.setText("대외활동"); break;}
            case 3 : { big_category_btn.setText("공연/세미나"); break;}
            case 4 : { big_category_btn.setText("리쿠르팅"); break;}
            case 5 : { big_category_btn.setText("붙어라"); break;}
        }

        category_temp = getResources().getStringArray(R.array.category_name);       //소분류
        for(int i = 0; i < category_temp.length; i++){
            if(carrier.getCategory().compareTo(category_temp[i]) == 0){
                small_category_btn.setText(category_temp[i]);
                break;
            }
        }

        writing_title.setText(carrier.getTitle());                                  //제목
        writing_content.setText(carrier.getContent());                              //내용
        start_dateLabel.setText(carrier.getStart_date());                           //시작일
        writing_startdate_img.setVisibility(GONE);
        end_dateLabel.setText(carrier.getEnd_date());                               //종료일
        writing_enddate_img.setVisibility(GONE);
        writing_link.setText(carrier.getLink());                                    //링크

        if(has_pic == 1){
            writing_preview_img.setVisibility(GONE);
            PostImageTask postImageTask = new PostImageTask(poster, post.getId(), writing_preview_img, writing_preview_img.getWidth());
            postImageTask.execute(0);
        }

        //사진이 없는 경우 표시 안 되게
        //사진불러오기 - 사진은 글 아이디에 맞는걸 가지고 와야함. 글 아이디를 미리 받아와야 함.
        //글을 수정하는 PHP
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.writing_confirm_btn:{
                phpCreate();
                break;
            }//TODO 카테고리 번호가 0인지 체크하는 코드 필요
            
            case R.id.writing_image_btn:{
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                i.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQ_CODE_PICK_GALLERY);
                break;
            }
            case R.id.writing_additional_btn : {
                writing_additional_btn.setVisibility(GONE);
                writing_additional_hide_btn.setVisibility(VISIBLE);
                writing_additional.setVisibility(VISIBLE);
                //scroll.fullScroll(View.FOCUS_DOWN);
                scroll.post(new Runnable() {
                    @Override
                    public void run() {
                        // This method works but animates the scrolling
                        // which looks weird on first load
                        // scroll_view.fullScroll(View.FOCUS_DOWN);

                        // This method works even better because there are no animations.
                        scroll.scrollTo(0, scroll.getBottom());
                    }
                });
                break;
            }
            case R.id.writing_additional_hide_btn : {
                writing_additional_hide_btn.setVisibility(GONE);
                writing_additional_btn.setVisibility(VISIBLE);
                writing_additional.setVisibility(GONE);
                break;
            }
            case R.id.writing_back_btn :{               //TODO 뒤로 가기를 눌렀을 때 이미 작성된 내용은 저장되지 않는다는 경고팝업 띄우기
                carrier.setCategory(null);
                carrier.setTitle(null);
                carrier.setContent(null);
                carrier.setPosting_date(null);
                carrier.setStart_date(null);
                carrier.setEnd_date(null);
                carrier.setLink(null);
                carrier.setGroup_code("");

                if(carrier.getSelector() == 0) {                //개인인 경우 그룹이름이 존재하지 않는다.
                    Intent intent = new Intent(Writing.this, SelectGroupOrNot.class).putExtra("carrier", carrier);
                    startActivity(intent);
                    finish();
                }
                else {
                    carrier.setGroup_name("");
                    Intent intent = new Intent(Writing.this, GroupSearch.class).putExtra("carrier", carrier);
                    startActivity(intent);
                    finish();
                }
                break;
            }
            case R.id.writing_cancel_btn :{
                Intent intent = new Intent(Writing.this, MainActivity2.class).putExtra("carrier", carrier);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.startdate_choose : {
                year = dateAndtime.get(Calendar.YEAR);
                month = dateAndtime.get(Calendar.MONTH);
                day = dateAndtime.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(Writing.this,start_d,year,month,day).show();
                if (day != 0) {
                    writing_startdate_img.setVisibility(GONE);
                    writing_enddate_img.setVisibility(GONE);
                    start_dateLabel.setVisibility(VISIBLE);
                    end_dateLabel.setVisibility(VISIBLE);}
                break;
            }
            case R.id.enddate_choose : {
                year = dateAndtime.get(Calendar.YEAR);
                month = dateAndtime.get(Calendar.MONTH);
                day = dateAndtime.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(Writing.this,end_d,year,month,day).show();
                if (day != 0) {
                    writing_startdate_img.setVisibility(GONE);
                    writing_enddate_img.setVisibility(GONE);
                   }
                break;
            }
            case R.id.big_category_btn : {
                new AlertDialog.Builder(this)
                        .setTitle("대분류")
                        .setItems(R.array.big_category,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        String[] temp = getResources().getStringArray(R.array.big_category);
                                        big_category_btn.setText(temp[which]);
                                        if(temp[which].compareTo("일반공지") == 0){ carrier.setBig_category(1);}
                                        if(temp[which].compareTo("대외활동") == 0){ carrier.setBig_category(2);}
                                        if(temp[which].compareTo("공연/세미나") == 0){ carrier.setBig_category(3);}
                                        if(temp[which].compareTo("리쿠르팅") == 0){ carrier.setBig_category(4);}
                                        if(temp[which].compareTo("붙어라") == 0){ carrier.setBig_category(5);}
                                        if(carrier.getBig_category() > 0 && carrier.getBig_category() < 6)
                                            big_category_img.setBackgroundResource(R.drawable.writing_category_empty);
                                        if(carrier.getBig_category() == 1){
                                            small_category_img.setBackgroundResource(0);         //일반공지인 경우 소분류 탭을 사라지게 만듬. 혹은 일반공지가 뜨게끔?
                                        }
                                        else small_category_img.setBackgroundResource(R.drawable.writing_small_category);
                                        small_category_btn.setText("");
                                        carrier.setCategory(null);
                                    }
                                })
                        .setNegativeButton("취소", null)
                        .show();

                break;
            }
            case R.id.small_category_btn : {
                if(carrier.getBig_category() == 5) {  //TODO 붙어라!!
                    new AlertDialog.Builder(this)
                            .setTitle("소분류")
                            .setItems(R.array.small_category_together,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            String[] temp = getResources().getStringArray(R.array.small_category_together);
                                            small_category_btn.setText(temp[which]);
                                            if (temp[which].compareTo("운동경기") == 0) { carrier.setCategory("together_sports_1");}
                                            if (temp[which].compareTo("게임") == 0) { carrier.setCategory("together_game_2");}
                                            if (temp[which].compareTo("야식") == 0) { carrier.setCategory("together_nightfood_3");}
                                            if (temp[which].compareTo("공동구매") == 0) { carrier.setCategory("together_gonggu_4");}
                                            if (temp[which].compareTo("카풀") == 0) { carrier.setCategory("together_carpool_5");}
                                            if (temp[which].compareTo("스터디") == 0) { carrier.setCategory("together_study_6");}
                                            if (temp[which].compareTo("사고팔기") == 0) { carrier.setCategory("together_trading_7");}
                                            if (temp[which].compareTo("분실물") == 0) { carrier.setCategory("together_lost_8");}
                                            if (temp[which].compareTo("구인구직") == 0) { carrier.setCategory("together_recruiting_9");}
                                            if (temp[which].compareTo("교환") == 0) { carrier.setCategory("together_exchange_10");}
                                            small_category_img.setBackgroundResource(R.drawable.writing_category_empty);
                                        }
                                    })
                            .setNegativeButton("취소", null)
                            .show();
                }
                if(carrier.getBig_category() == 2) {   //TODO 대외활동!!!!!
                    new AlertDialog.Builder(this)
                            .setTitle("소분류")
                            .setItems(R.array.small_category_outer,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            String[] temp = getResources().getStringArray(R.array.small_category_outer);
                                            small_category_btn.setText(temp[which]);
                                            if (temp[which].compareTo("공모전") == 0) { carrier.setCategory("outer_contest_21");}
                                            if (temp[which].compareTo("인턴") == 0) { carrier.setCategory("outer_intern_22");}
                                            if (temp[which].compareTo("자원봉사") == 0) { carrier.setCategory("outer_service_23");}
                                            small_category_img.setBackgroundResource(R.drawable.writing_category_empty);
                                        }
                                    })
                            .setNegativeButton("취소", null)
                            .show();
                }
                if(carrier.getBig_category() == 3) {     //TODO 공연세미나!!!!!!
                    new AlertDialog.Builder(this)
                            .setTitle("소분류")
                            .setItems(R.array.small_category_seminar,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            String[] temp = getResources().getStringArray(R.array.small_category_seminar);
                                            small_category_btn.setText(temp[which]);
                                            if (temp[which].compareTo("공연") == 0) { carrier.setCategory("seminar_perf_41");}
                                            if (temp[which].compareTo("세미나") == 0) { carrier.setCategory("seminar_seminar_42");}
                                            if (temp[which].compareTo("발표") == 0) { carrier.setCategory("seminar_presentation_43");}
                                            small_category_img.setBackgroundResource(R.drawable.writing_category_empty);
                                        }
                                    })
                            .setNegativeButton("취소", null)
                            .show();
                }
                if(carrier.getBig_category() == 4) {        //TODO 리쿠르팅!!!
                    new AlertDialog.Builder(this)
                            .setTitle("소분류")
                            .setItems(R.array.small_category_recruiting,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            String[] temp = getResources().getStringArray(R.array.small_category_recruiting);
                                            small_category_btn.setText(temp[which]);
                                            if (temp[which].compareTo("학술") == 0) { carrier.setCategory("recruiting_scholarship_61");}
                                            if (temp[which].compareTo("운동") == 0) { carrier.setCategory("recruiting_sports_62");}
                                            if (temp[which].compareTo("공연") == 0) { carrier.setCategory("recruiting_perf_63");}
                                            if (temp[which].compareTo("신앙") == 0) { carrier.setCategory("recruiting_faith_64");}
                                            if (temp[which].compareTo("전시") == 0) { carrier.setCategory("recruiting_display_65");}
                                            if (temp[which].compareTo("봉사") == 0) { carrier.setCategory("recruiting_service_66");}
                                            small_category_img.setBackgroundResource(R.drawable.writing_category_empty);
                                        }
                                    })
                            .setNegativeButton("취소", null)
                            .show();
                }
                break;
            }
            case R.id.writing_remove_pic_btn : {
                has_pic = 0;
                ((ImageView) findViewById(R.id.writing_preview_img)).setImageBitmap(null);
                writing_preview_img.setBackgroundResource(R.drawable.writing_picture);
                writing_remove_pic_btn.setVisibility(GONE);
            }
        }
    }

    public void onBackPressed(){
        carrier.setCategory(null);
        carrier.setTitle(null);
        carrier.setContent(null);
        carrier.setPosting_date(null);
        carrier.setStart_date(null);
        carrier.setEnd_date(null);
        carrier.setLink(null);
        carrier.setGroup_code("");

        if(carrier.getSelector() == 0) {                //개인인 경우 그룹이름이 존재하지 않는다.
            Intent intent = new Intent(Writing.this, SelectGroupOrNot.class).putExtra("carrier", carrier);
            startActivity(intent);
            finish();
        }
        else {
            carrier.setGroup_name("");
            Intent intent = new Intent(Writing.this, GroupSearch.class).putExtra("carrier", carrier);
            startActivity(intent);
            finish();
        }
    }

    private class PhpUpload extends AsyncTask<String, Integer, String> {

        public PhpUpload(){ super();}
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
            if(has_pic == 1) {
                DoPhotoUpLoad(getSelectedImageFile().getAbsolutePath(), posting_id);
            }
            return posting_id;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //TODO 확인 혹은 취소 하는 팝업 표시
        }
    }

    public void phpCreate(){

        int possible; // 날짜계산한게 맞나 안맞나 체크

        String title, content,kakao_nick, link;
        String group_name = "", group_code = "";


        title = writing_title.getText().toString();
        Log.d("타이틀",title);
        content = writing_content.getText().toString();
        kakao_nick = carrier.getNickname();
        link = writing_link.getText().toString();

        if(startDay!=null) { //사용자가 날짜를 선택했을떄
            possible = dayCal(startDay, endDay);
            Log.d("함수실행", "true");
            Log.d("possible 값", String.valueOf(possible));
            if (possible == -1) {
                Toast toastView = Toast.makeText(this, "날짜를 올바르게 입력하세요", Toast.LENGTH_SHORT);
                toastView.setGravity(Gravity.CENTER, 0, 0);
                toastView.show();
                //Toast.makeText(this, "날짜입력을 다시하십시오",Toast.LENGTH_SHORT).show();
            }
        }

        if ((carrier.getGroup_code().compareTo("") == 0) && (carrier.getGroup_name().compareTo("") != 0)) {     //임의 입력 단체의 경우
            group_name = carrier.getGroup_name();
        }
        if ((carrier.getGroup_code().compareTo("") != 0) && (carrier.getGroup_name().compareTo("") != 0)) {     //리스트내 존재하는 단체의 경우
            group_name = carrier.getGroup_name();
            group_code = carrier.getGroup_code();
        }

        try {
            title = URLEncoder.encode(title, "UTF-8");
            content = URLEncoder.encode(content, "UTF-8");
            kakao_nick = URLEncoder.encode(kakao_nick, "UTF-8");
            link = URLEncoder.encode(link, "UTF-8");
            group_name = URLEncoder.encode(group_name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        carrier.setTitle(title);
        carrier.setContent(content);
        carrier.setLink(link);
        carrier.setGroup_name(group_name);
        carrier.setGroup_code(group_code);
        carrier.setNickname(kakao_nick);

//        carrier.setBig_category(1);
//
//        if(carrier.getTitle().length()==0 ||carrier.getContent().length()==0 || carrier.getCategory() == null)
//        {
//            Toast toastView =Toast.makeText(this, "글을 올바르게 작성하세요", Toast.LENGTH_SHORT);
//            toastView.setGravity(Gravity.CENTER,0,0);
//            toastView.show();
//        }
//        else {
            {
            new AlertDialog.Builder(this)
                    .setTitle("게시물 등록")
                    .setMessage("글을 등록하시겠습니까?")
                    .setIcon(R.drawable.handongi)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            carrier.setUpload_url("http://hungry.portfolio1000.com/smarthandongi/posting_upload.php?"
                                    + "kakao_id=" + carrier.getId()
                                    + "&kakao_nick=" + carrier.getNickname()
                                    + "&category=" + carrier.getCategory()
                                    + "&group_code=" + carrier.getGroup_code()
                                    + "&group_name=" + carrier.getGroup_name()
                                    + "&title=" + carrier.getTitle()
                                    + "&content=" + carrier.getContent()
                                    + "&link=" + carrier.getLink()
                                    + "&posting_date=" + carrier.getPosting_date()
                                    + "&start_date=" + carrier.getStart_date()
                                    + "&end_date=" + carrier.getEnd_date()
                                    + "&has_pic=" + has_pic);

                            task = new PhpUpload();
                            task.execute(carrier.getUpload_url());
                            carrier.setFromWriting(1);
                            Intent intent = new Intent(Writing.this, PostDetail.class).putExtra("carrier", carrier);
                            startActivity(intent);
                            finish();


                        }
                    })
                    .setNegativeButton("취소", null)
                    .show();
            //TODO 개인일 때와 단체 일 때를 구분하여 다른 케이스를 준다.


        }





    }

    private void DoPhotoUpLoad(String fileName, String posting_id){
        HttpPhotoUpload("http://hungry.portfolio1000.com/smarthandongi/photo/upload.php?id=" + posting_id, posting_id, fileName);
//        HttpPhotoUpload("http://hungry.portfolio1000.com/smarthandongi/upload.php?id=" + posting_id, posting_id, fileName);
    }

    private void HttpPhotoUpload(String url, String posting_id, String fileName){
//        Log.d("in","HttpPhotoUpload");
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeFile(fileName);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        byte [] ba = bao.toByteArray();
        String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("image", ba1));
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = client.execute(post);
            //HttpEntity entity = response.getEntity();

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("mCropRequested", mCropRequested);
        savedInstanceState.putInt("mCropAspectWidth", mCropAspectWidth);
        savedInstanceState.putInt("mCropAspectHeight", mCropAspectHeight);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCropRequested = savedInstanceState.getBoolean("mCropRequested");
        mCropAspectWidth = savedInstanceState.getInt("mCropAspectWidth");
        mCropAspectHeight = savedInstanceState.getInt("mCropAspectHeight");
    }

    public File getSelectedImageFile() {
        return getTempImageFile();
    }

    private boolean mCropRequested = false;

    /**
     * crop 이 필요한 경우 설정함. 설정하지 않으면 crop 하지 않음.
     * @param width
     *            crop size width.
     * @param height
     *            crop size height.
     */
    private int mCropAspectWidth = 1, mCropAspectHeight = 1;
    public void setCropOption(int aspectX, int aspectY) {
        mCropRequested = true;
        mCropAspectWidth = aspectX;
        mCropAspectHeight = aspectY;
    }

    /**
     * 사용할 이미지의 최대 크기 설정. 가로, 세로 지정한 크기보다 작은 사이즈로 이미지 크기를 조절함. default size :
     * 500
     *
     * @param sizePixel
     *            기본 500
     */
    private int mImageSizeBoundary = 500;

    public void setImageSizeBoundary(int sizePixel) {
        mImageSizeBoundary = sizePixel;
    }

    private boolean checkWriteExternalPermission() {
        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int res = checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private boolean checkSDisAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
    private File getTempImageFile() {
        File path = new File(Environment.getExternalStorageDirectory() + "/Android/data/com.hungry_handongi/temp/");
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path, "tempimage.png");
        Log.d("path", path.toString());
        return file;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.d("in", "onActivityResult");
        if (requestCode == REQ_CODE_PICK_GALLERY && resultCode == Activity.RESULT_OK) {
            // 갤러리의 경우 곧바로 data 에 uri가 넘어옴.
//            Log.d("in", "gallery");
            Uri uri = data.getData();
            Log.d("uri", uri.toString());
            copyUriToFile(uri, getTempImageFile());
//            Log.d("in", "afterURIcopy");
            if (mCropRequested) {
                cropImage();
            } else {
                doFinalProcess();
            }
        } else if (requestCode == REQ_CODE_PICK_CAMERA && resultCode == Activity.RESULT_OK) {
            // 카메라의 경우 file 로 결과물이 돌아옴.
            // 카메라 회전 보정.
            correctCameraOrientation(getTempImageFile());
            if (mCropRequested) {
                cropImage();
            } else {
                doFinalProcess();
            }
        } else if (requestCode == REQ_CODE_PICK_CROP && resultCode == Activity.RESULT_OK) {
            // crop 한 결과는 file로 돌아옴.
            doFinalProcess();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void doFinalProcess() {
        // sample size 를 적용하여 bitmap load.
        Bitmap bitmap = loadImageWithSampleSize(getTempImageFile());

        // image boundary size 에 맞도록 이미지 축소.
        bitmap = resizeImageWithinBoundary(bitmap);

        // 결과 file 을 얻어갈 수 있는 메서드 제공.
        saveBitmapToFile(bitmap);

        // show image on ImageView
        Bitmap bm = BitmapFactory.decodeFile(getTempImageFile().getAbsolutePath());
        writing_preview_img.setBackgroundResource(0);
        ((ImageView) findViewById(R.id.writing_preview_img)).setImageBitmap(bm);
        writing_remove_pic_btn.setVisibility(VISIBLE);
        has_pic = 1;
    }

    private void saveBitmapToFile(Bitmap bitmap) {
        File target = getTempImageFile();
        try {
            FileOutputStream fos = new FileOutputStream(target, false);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
        }
    }

    /** 이미지 사이즈 수정 후, 카메라 rotation 정보가 있으면 회전 보정함. */
    private void correctCameraOrientation(File imgFile) {
        Bitmap bitmap = loadImageWithSampleSize(imgFile);
        try {
            ExifInterface exif = new ExifInterface(imgFile.getAbsolutePath());
            int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int exifRotateDegree = exifOrientationToDegrees(exifOrientation);
            bitmap = rotateImage(bitmap, exifRotateDegree);
            saveBitmapToFile(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap rotateImage(Bitmap bitmap, int degrees) {
        if (degrees != 0 && bitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
            try {
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                if (bitmap != converted) {
                    bitmap.recycle();
                    bitmap = converted;
                }
            } catch (OutOfMemoryError ex) {
            }
        }
        return bitmap;
    }

    /**
     * EXIF정보를 회전각도로 변환하는 메서드
     *
     * @param exifOrientation
     *            EXIF 회전각
     * @return 실제 각도
     */
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    /** 원하는 크기의 이미지로 options 설정. */
    private Bitmap loadImageWithSampleSize(File file) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        int width = options.outWidth;
        int height = options.outHeight;
        int longSide = Math.max(width, height);
        int sampleSize = 1;
        if (longSide > mImageSizeBoundary) {
            sampleSize = longSide / mImageSizeBoundary;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        options.inPurgeable = true;
        options.inDither = false;

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        return bitmap;
    }

    /**
     * mImageSizeBoundary 크기로 이미지 크기 조정. mImageSizeBoundary 보다 작은 경우 resize하지
     * 않음.
     */
    private Bitmap resizeImageWithinBoundary(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        if (width > height) {
            if (width > mImageSizeBoundary) {
                bitmap = resizeBitmapWithWidth(bitmap, mImageSizeBoundary);
            }
        } else {
            if (height > mImageSizeBoundary) {
                bitmap = resizeBitmapWithHeight(bitmap, mImageSizeBoundary);
            }
        }
        return bitmap;
    }

    private Bitmap resizeBitmapWithHeight(Bitmap source, int wantedHeight) {
        if (source == null)
            return null;

        int width = source.getWidth();
        int height = source.getHeight();

        float resizeFactor = wantedHeight * 1f / height;

        int targetWidth, targetHeight;
        targetWidth = (int) (width * resizeFactor);
        targetHeight = (int) (height * resizeFactor);

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, true);

        return resizedBitmap;
    }

    private Bitmap resizeBitmapWithWidth(Bitmap source, int wantedWidth) {
        if (source == null)
            return null;

        int width = source.getWidth();
        int height = source.getHeight();

        float resizeFactor = wantedWidth * 1f / width;

        int targetWidth, targetHeight;
        targetWidth = (int) (width * resizeFactor);
        targetHeight = (int) (height * resizeFactor);

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, true);

        return resizedBitmap;
    }

    private void copyUriToFile(Uri srcUri, File target) {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        FileChannel fcin = null;
        FileChannel fcout = null;
        try {
            // 스트림 생성
            inputStream = (FileInputStream) getContentResolver().openInputStream(srcUri);
            outputStream = new FileOutputStream(target);

            // 채널 생성
            fcin = inputStream.getChannel();
            fcout = outputStream.getChannel();

            // 채널을 통한 스트림 전송
            long size = fcin.size();
            fcin.transferTo(0, size, fcout);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fcout.close();
            } catch (IOException ioe) {
            }
            try {
                fcin.close();
            } catch (IOException ioe) {
            }
            try {
                outputStream.close();
            } catch (IOException ioe) {
            }
            try {
                inputStream.close();
            } catch (IOException ioe) {
            }
        }
    }

    private void cropImage() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List<ResolveInfo> cropToolLists = getPackageManager().queryIntentActivities(intent, 0);
        int size = cropToolLists.size();
        if (size == 0) {
            // crop 을 처리할 앱이 없음. 곧바로 처리.
            doFinalProcess();
        } else {
            intent.setData(Uri.fromFile(getTempImageFile()));
            intent.putExtra("aspectX", mCropAspectWidth);
            intent.putExtra("aspectY", mCropAspectHeight);
            intent.putExtra("output", Uri.fromFile(getTempImageFile()));
            Intent i = new Intent(intent);
            ResolveInfo res = cropToolLists.get(0);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            startActivityForResult(i, REQ_CODE_PICK_CROP);
        }
    }

    public int dayCal(Calendar startday, Calendar endday) {
        Calendar startDay, endDay;
        long sday,eday;

        startDay = startday;
        endDay=endday;

        sday = startDay.getTimeInMillis()/86400000;
        Log.d("시작일 계산",String.valueOf(sday));
        eday = endDay.getTimeInMillis()/86400000;
        Log.d("마감일 계산",String.valueOf(eday));

        if(sday>eday)
            return -1;
        else
            return 1;

    } //날짜입력을 제대로 했나 안했나 확인하는 함수

    public String convertDateToString(int year, int month, int day) {
        String syear = String.valueOf(year);
        String sday = String.valueOf(day);
        String str=null;//월 반환을 위해
        String sdate=null;
        switch(month) {
            case 0 :
                str="01";
                break;
            case 1 :
                str="02";
                break;
            case 2 :
                str="03";
                break;
            case 3 :
                str="04";
                break;
            case 4 :
                str="05";
                break;
            case 5 :
                str="06";
                break;
            case 6 :
                str="07";
                break;
            case 7 :
                str="08";
                break;
            case 8 :
                str="09";
                break;
            case 9 :
                str="10";
                break;
            case 10 :
                str="11";
                break;
            case 11 :
                str="12";
                break;

        }
        switch(day) {
            case 1 :
            case 2 :
            case 3 :
            case 4 :
            case 5 :
            case 6 :
            case 7 :
            case 8 :
            case 9 :

                String zero="0";
                sday=zero.concat(sday);
                Log.d("day",sday);
                break;
        }

        sdate=syear.concat(str);
        sdate=sdate.concat(sday);
        Log.d("작성일",sdate);

        return sdate;
    }
}
