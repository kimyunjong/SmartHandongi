package com.smarthandongi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by LEWIS on 2015-02-09.
 */
public class ReportPost extends Activity implements View.OnClickListener{
    Carrier carrier;
    Button report_post_cancel_btn, report_post_confirm_btn, report_post_back_btn, dialog_okay, dialog_cancel_okay, dialog_cancel_no;
    EditText report_post_content;
    ReportPostPhp report_post_php;
    RelativeLayout popup_report_1, popup_report_2, popup_report_3, popup_report_4;
    LinearLayout dialog_check_background;
    Typeface typeface;
    Dialog dialog_register, dialog_cancel;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_post);
        carrier = (Carrier)getIntent().getSerializableExtra("carrier");
        typeface = Typeface.createFromAsset(getAssets(), "KOPUBDOTUM_PRO_LIGHT.OTF");

        report_post_confirm_btn = (Button)findViewById(R.id.report_post_confirm_btn);
        report_post_cancel_btn = (Button)findViewById(R.id.report_post_cancel_btn);
        report_post_back_btn = (Button)findViewById(R.id.report_post_back_btn);
        report_post_content = (EditText)findViewById(R.id.repost_post_content);

        report_post_confirm_btn.setOnClickListener(this);
        report_post_cancel_btn.setOnClickListener(this);
        report_post_back_btn.setOnClickListener(this);

        popup_report_1 = (RelativeLayout)findViewById(R.id.popup_report_1);
        popup_report_2 = (RelativeLayout)findViewById(R.id.popup_report_2);
        popup_report_3 = (RelativeLayout)findViewById(R.id.popup_report_3);
        popup_report_4 = (RelativeLayout)findViewById(R.id.popup_report_4);

        // 폰트
        report_post_content.setTypeface(typeface);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.report_post_back_btn : {
                if(report_post_content.getText().toString().length() > 0) {

                    dialog_cancel = new Dialog(context);
                    dialog_cancel.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog_cancel.setContentView(R.layout.dialog_cancel);
                    dialog_cancel.show();

                    dialog_cancel_okay = (Button) dialog_cancel.findViewById(R.id.dialog_writing_confirm);
                    dialog_cancel_okay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new CountDownTimer(1500, 300) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    // do something after 1s
                                    popup_report_1.setVisibility(VISIBLE);
                                    popup_report_2.setVisibility(VISIBLE);
                                    popup_report_4.setVisibility(VISIBLE);
                                }

                                @Override
                                public void onFinish() {
                                    // do something end times 5s
                                    popup_report_1.setVisibility(GONE);
                                    popup_report_2.setVisibility(GONE);
                                    popup_report_4.setVisibility(GONE);
                                    finish();
                                }
                            }.start();
                        }
                    });

                    dialog_cancel_no = (Button) dialog_cancel.findViewById(R.id.dialog_writing_cancel);
                    dialog_cancel_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_cancel.dismiss();
                        }
                    });
                }
                else {
                    finish();
                }

                break;
            }

            case R.id.report_post_confirm_btn : {

                if(report_post_content.getText().toString().length() < 1) {

                    dialog_register = new Dialog(context);
                    dialog_register.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog_register.setContentView(R.layout.dialog_writing_check);
                    dialog_register.show();

                    dialog_check_background = (LinearLayout) dialog_register.findViewById(R.id.dialog_check_background);
                    dialog_check_background.setBackgroundResource(R.drawable.dialog_check_content);
                    dialog_okay = (Button)dialog_register.findViewById(R.id.dialog_okay);
                    dialog_okay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_register.dismiss();
                        }
                    });
                }
                else {

                    phpCreate();

                    new CountDownTimer(1500, 300) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // do something after 1s
                            popup_report_1.setVisibility(VISIBLE);
                            popup_report_2.setVisibility(VISIBLE);
                            popup_report_3.setVisibility(VISIBLE);
                        }
                        @Override
                        public void onFinish() {
                            // do something end times 5s
                            popup_report_1.setVisibility(GONE);
                            popup_report_2.setVisibility(GONE);
                            popup_report_3.setVisibility(GONE);
                            finish();
                        }
                    }.start();
                }
                break;
            }

            case R.id.report_post_cancel_btn : {

                if(report_post_content.getText().toString().length() > 0) {

                    dialog_cancel = new Dialog(context);
                    dialog_cancel.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog_cancel.setContentView(R.layout.dialog_cancel);
                    dialog_cancel.show();

                    dialog_cancel_okay = (Button) dialog_cancel.findViewById(R.id.dialog_writing_confirm);
                    dialog_cancel_okay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new CountDownTimer(1500, 300) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    // do something after 1s
                                    popup_report_1.setVisibility(VISIBLE);
                                    popup_report_2.setVisibility(VISIBLE);
                                    popup_report_4.setVisibility(VISIBLE);
                                }

                                @Override
                                public void onFinish() {
                                    // do something end times 5s
                                    popup_report_1.setVisibility(GONE);
                                    popup_report_2.setVisibility(GONE);
                                    popup_report_4.setVisibility(GONE);
                                    finish();
                                }
                            }.start();
                        }
                    });

                    dialog_cancel_no = (Button) dialog_cancel.findViewById(R.id.dialog_writing_cancel);
                    dialog_cancel_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_cancel.dismiss();
                        }
                    });
                }
                else {
                    finish();
                }

                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void phpCreate(){
        String task, reason, kakao_nick;
        reason = report_post_content.getText().toString();
        kakao_nick = carrier.getNickname();

        try {
            kakao_nick = URLEncoder.encode(kakao_nick, "UTF-8");
            reason = URLEncoder.encode(reason, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        task = "http://hungry.portfolio1000.com/smarthandongi/report_post_reply.php?"
                + "kakao_id=" + carrier.getId()
                + "&kakao_nick=" + kakao_nick
                + "&report_date=" + carrier.getPosting_date()
                + "&posting_id=" + carrier.getPost_id()
                + "&reply_id=0"
                + "&reason=" + reason;

        report_post_php = new ReportPostPhp();
        report_post_php.execute(task);

    }

    private class ReportPostPhp extends AsyncTask<String, Integer, String> {

        public ReportPostPhp(){ super();}
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            String posting_id = null, result;
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

            return posting_id;
        }

        @Override
        protected void onPostExecute(String s) {

            //TODO 확인 혹은 취소 하는 팝업 표시

        }
    }
}
