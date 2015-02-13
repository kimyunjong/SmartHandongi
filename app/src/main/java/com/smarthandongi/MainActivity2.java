package com.smarthandongi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kakao.APIErrorResult;
import com.kakao.LogoutResponseCallback;
import com.kakao.UserManagement;
import com.smarthandongi.kakao_api.KakaoTalkLoginActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity2 extends ActionBarActivity implements View.OnClickListener {

    private Carrier carrier;
    private TextView text;
    private Button button;
    Button yjbtn,yybtn,dsbtn;
    TextView dday;
    dday deadline= new dday();

    //수영 추가
    String myResult;
    ProgressDialog loagindDialog;
    String regid=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carrier = (Carrier)getIntent().getSerializableExtra("carrier");
        setContentView(R.layout.activity_main2);

        text = (TextView)findViewById(R.id.text);
        button = (Button)findViewById(R.id.button);
        dday=(TextView)findViewById(R.id.dday);
        button.setText(carrier.isLogged_in() ? "로그아웃" : "로그인");
        text.setText(carrier.getId() + " " + carrier.getNickname());

        dday.setText(String.valueOf(deadline.caldate(2015,1,18))+ "남았습니다!!!! "); //월은 -1, 일은 +1





        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carrier.isLogged_in()) {
                    UserManagement.requestLogout(new LogoutResponseCallback() {
                        @Override
                        protected void onSuccess(long l) {
                            carrier.setLogged_in(false);
                            carrier.setNickname("not_logged_in");
                            carrier.setId("000000");
                            // 수영추가
                           // carrier.setIsLogout_regid(2);
                            RegIDDeleteTask regIDDeleteTask= new RegIDDeleteTask();
                            regIDDeleteTask.execute(regid);
                            // 수영 추가 끝
                            Intent intent = new Intent(MainActivity2.this, KakaoTalkLoginActivity.class).putExtra("carrier", carrier);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        protected void onFailure(APIErrorResult apiErrorResult) {

                        }
                    });
                }
                else {
                    Intent intent = new Intent(MainActivity2.this, KakaoTalkLoginActivity.class).putExtra("carrier", carrier);
                    startActivity(intent);
                    finish();
                }
            }
        });

        yjbtn=(Button)findViewById(R.id.yjbtn);
        yybtn=(Button)findViewById(R.id.yybtn);
        dsbtn=(Button)findViewById(R.id.dsbtn);

        yybtn.setOnClickListener(this);
        yjbtn.setOnClickListener(this);
        dsbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.yjbtn:{
                Intent intent=new Intent(MainActivity2.this,yj_activity.class).putExtra("carrier",carrier);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.yybtn:{
                Intent intent=new Intent(MainActivity2.this,yy_activity.class).putExtra("carrier",carrier);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.dsbtn:{
                Intent intent=new Intent(MainActivity2.this,ds_activity.class).putExtra("carrier",carrier);
                startActivity(intent);
                finish();
                break;

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private class RegIDDeleteTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
        regid = carrier.getRegid();
            Log.d("regid는요","regid");
        }

        @Override
        protected Void doInBackground(String... params) {
            DeleteData(regid);
            return null;
        }

        protected void onPostExecute(Void result) {
//            loagindDialog.dismiss();
        }
    }

    public void DeleteData(String reg_id ) {
        try {
            URL url = new URL("http://hungry.portfolio1000.com/smarthandongi/gcm_delete_regid.php?reg_id="+regid);       // URL 설정
            HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속
            Log.d("php실행 ", "성공");
            //--------------------------
            //   전송 모드 설정 - 기본적인 설정이다
            //--------------------------
            http.setDefaultUseCaches(false);
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");

            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            StringBuffer buffer = new StringBuffer();
            buffer.append("reg_id").append("=").append(reg_id);                 // php 변수에 값 대입


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
