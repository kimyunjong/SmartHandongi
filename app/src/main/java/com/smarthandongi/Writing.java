package com.smarthandongi;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by LEWIS on 2015-01-23.
 */
public class Writing extends Activity implements View.OnClickListener {
    Carrier carrier;

    //Category Buttons
    Button writing_notice_btn, writing_outer_btn, writing_seminar_btn, writing_recruit_btn, writing_agora_btn;

    EditText writing_title, writing_body;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing);

        carrier.setId("21100114");
        carrier.setNickname("루이스");

        //Category Buttons
        writing_notice_btn  = (Button) findViewById(R.id.writing_notice_btn);
        writing_outer_btn   = (Button) findViewById(R.id.writing_outer_btn);
        writing_seminar_btn = (Button) findViewById(R.id.writing_seminar_btn);
        writing_recruit_btn = (Button) findViewById(R.id.writing_recruit_btn);
        writing_agora_btn   = (Button) findViewById(R.id.writing_agora_btn);

        writing_notice_btn.setOnClickListener(this);
        writing_outer_btn.setOnClickListener(this);
        writing_seminar_btn.setOnClickListener(this);
        writing_recruit_btn.setOnClickListener(this);
        writing_agora_btn.setOnClickListener(this);

        //Category buttons done

        writing_title = (EditText)findViewById(R.id.writing_title);
        writing_body  = (EditText)findViewById(R.id.writing_body);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            //Category buttons
            case R.id.writing_notice_btn:{                //카테고리를 0으로 시작해서 처음에는 아무것도 선택되지 않은 상태에서 확인을 누를 시 완료하지 않았다는 경고메시지가 뜸
                //카테고리 번호 지정
                carrier.setCategory(1);

                //배경 초기화
                writing_notice_btn.setBackgroundResource(R.drawable.notice_btn_on);
                writing_outer_btn.setBackgroundResource(R.drawable.outer_btn);
                writing_seminar_btn.setBackgroundResource(R.drawable.seminar_btn);
                writing_recruit_btn.setBackgroundResource(R.drawable.recruit_btn);
                writing_agora_btn.setBackgroundResource(R.drawable.agora_btn);

                break;
            }
            case R.id.writing_outer_btn:{
                carrier.setCategory(2);
                writing_notice_btn.setBackgroundResource(R.drawable.notice_btn);
                writing_outer_btn.setBackgroundResource(R.drawable.outer_btn_on);
                writing_seminar_btn.setBackgroundResource(R.drawable.seminar_btn);
                writing_recruit_btn.setBackgroundResource(R.drawable.recruit_btn);
                writing_agora_btn.setBackgroundResource(R.drawable.agora_btn);
                break;
            }
            case R.id.writing_seminar_btn:{
                carrier.setCategory(3);
                writing_notice_btn.setBackgroundResource(R.drawable.notice_btn);
                writing_outer_btn.setBackgroundResource(R.drawable.outer_btn);
                writing_seminar_btn.setBackgroundResource(R.drawable.seminar_btn_on);
                writing_recruit_btn.setBackgroundResource(R.drawable.recruit_btn);
                writing_agora_btn.setBackgroundResource(R.drawable.agora_btn);
                break;
            }
            case R.id.writing_recruit_btn:{
                carrier.setCategory(4);
                writing_notice_btn.setBackgroundResource(R.drawable.notice_btn);
                writing_outer_btn.setBackgroundResource(R.drawable.outer_btn);
                writing_seminar_btn.setBackgroundResource(R.drawable.seminar_btn);
                writing_recruit_btn.setBackgroundResource(R.drawable.recruit_btn_on);
                writing_agora_btn.setBackgroundResource(R.drawable.agora_btn);
                break;
            }
            case R.id.writing_agora_btn:{
                carrier.setCategory(5);
                writing_notice_btn.setBackgroundResource(R.drawable.notice_btn);
                writing_outer_btn.setBackgroundResource(R.drawable.outer_btn);
                writing_seminar_btn.setBackgroundResource(R.drawable.seminar_btn);
                writing_recruit_btn.setBackgroundResource(R.drawable.recruit_btn);
                writing_agora_btn.setBackgroundResource(R.drawable.agora_btn_on);
                break;
            }
            //Category buttons done
        }
    }

    private class PhpUpload extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            try {
                //연결 URL설정
                URL url = new URL(urls[0]);
                //커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //연결되었으면
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    br.close();
                    conn.disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return jsonHtml.toString();
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
        }
    }

    public void phpCreate(){
        String title, body, upload_url;

        title = writing_title.getText().toString();
        body = writing_body.getText().toString();

        try {
            title = URLEncoder.encode(title, "UTF-8");
            body = URLEncoder.encode(body, "UTF-8");
            } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        upload_url = "http://hungry.portfolio1000.com/smarthandongi/posting_upload.php?"
                    + "kakao_id=" + carrier.getId()
                    + "&kakao_nick=" + carrier.getNickname()
                    + "&category=" + carrier.getCategory()
                    + "&group=" + carrier.getGroup_indicator()
                    + "&title=" + title
                    + "&body=" + body;
    }

}
