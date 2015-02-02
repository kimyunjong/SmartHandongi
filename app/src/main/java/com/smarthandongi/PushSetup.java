package com.smarthandongi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LEWIS on 2015-01-28.
 */
public class PushSetup extends Activity implements View.OnClickListener {
    int sports, game, nightfood, id;

    Carrier carrier;
    Button push_sports_chk, push_game_chk, push_nightfood_chk, push_save_btn;
    CollectPushInfoPhp push_php;
    SendPushInfoPhp send_php;
//    PushDatabase push_list;   //한 줄의 정보만 빼올 것이기 때문에 어레이리스트로 안 해도 될 것 같지만 일단..

    public void phpCreate() {
        Log.d("in", "phpCreate");
        push_php = new CollectPushInfoPhp(this);
        push_php.execute("http://hungry.portfolio1000.com/smarthandongi/collect_pushinfo.php/");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("in", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.push_setup);
        carrier = (Carrier) getIntent().getSerializableExtra("carrier");

        push_sports_chk = (Button) findViewById(R.id.push_sports_chk);
        push_game_chk = (Button) findViewById(R.id.push_game_chk);
        push_nightfood_chk = (Button) findViewById(R.id.push_nightfood_chk);
        push_save_btn = (Button) findViewById(R.id.push_save_btn);

        push_sports_chk.setOnClickListener(this);
        push_game_chk.setOnClickListener(this);
        push_nightfood_chk.setOnClickListener(this);
        push_save_btn.setOnClickListener(this);

        phpCreate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.push_sports_chk: {
                if(sports == 0){
                    push_sports_chk.setBackgroundColor(Color.parseColor("#FFFF11"));       //클릭하기전에 값이 0이었으면 true를 만들어야 하므로 1로 바꿈. 노란색
                    sports = 1;
                }
                else {push_sports_chk.setBackgroundColor(Color.parseColor("#ffffff"));
                    sports = 0;
                }
                break;
            }
            case R.id.push_game_chk: {
                if(game == 0){
                    push_game_chk.setBackgroundColor(Color.parseColor("#ffff11"));
                    game = 1;
                }
                else {push_game_chk.setBackgroundColor(Color.parseColor("#ffffff"));
                    game = 0;}
                break;
            }
            case R.id.push_nightfood_chk: {
                if(nightfood == 0){
                    push_nightfood_chk.setBackgroundColor(Color.parseColor("#ffff11"));
                    nightfood = 1;
                }
                else {push_nightfood_chk.setBackgroundColor(Color.parseColor("#ffffff"));
                    nightfood = 0;}
                break;
            }
            case R.id.push_save_btn:{
                phpCreateSend();
            }
        }
    }


    public class CollectPushInfoPhp extends AsyncTask<String, Integer, String> {
        private PushSetup context;

        public CollectPushInfoPhp(PushSetup context) {
            super();
            this.context = context;
        }

        @Override
        protected String doInBackground(String... urls) {
            Log.d("in","doInBackground - CollectPushInfoPhp");
            StringBuilder jsonHtml = new StringBuilder();

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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            return jsonHtml.toString();


        }

        protected void onPostExecute(String str) {
            Log.d("in","onPostExecute - Collect");
            try {
                String temp;
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("results");

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    temp = jo.getString("kakao_id");

                    if (temp.compareTo(carrier.getId()) == 0) {   //이 두 값이 같을 경우 해당 데이터베이스 테이블에 아이디가 존재한다는 말
                        Log.d("in","compare");
//                        push_list = new PushDatabase(jo.getInt("id"), jo.getString("kakao_id"), jo.getString("kakao_nick"),
//                                jo.getInt("together_sports_1"), jo.getInt("together_game_2"), jo.getInt("together_nightfood_3"));  //1이면 클릭 0이면 언클릭이 되게 설정
                        id = jo.getInt("id");
                        sports = jo.getInt("together_sports_1");
                        game = jo.getInt("together_game_2");
                        nightfood = jo.getInt("together_nightfood_3");
                        break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (sports == 0) {
                Log.d("","sports setChecked");
                push_sports_chk.setBackgroundColor(Color.parseColor("#ffffff"));        //읽어들일 때 1이면 노란색으로 표시
            } else push_sports_chk.setBackgroundColor(Color.parseColor("#ffff11"));

            if (game == 0) {
                Log.d("","game setChecked");
                push_game_chk.setBackgroundColor(Color.parseColor("#ffffff"));
            } else push_game_chk.setBackgroundColor(Color.parseColor("#ffff11"));

            if (nightfood == 0) {
                Log.d("","nightfood setChecked");
                push_nightfood_chk.setBackgroundColor(Color.parseColor("#ffffff"));
            } else push_nightfood_chk.setBackgroundColor(Color.parseColor("#ffff11"));

        }
    }


    public void onBackPressed() {     ///////여기서는 저장 ㄴㄴ?
        Log.d("","onBackPressed");

        phpCreateSend();

        Intent intent = new Intent(PushSetup.this, yy_activity.class).putExtra("carrier", carrier);
        startActivity(intent);
        finish();
    }

    public class SendPushInfoPhp extends AsyncTask<String, Integer, String> {

        protected String doInBackground(String... urls) {
            Log.d("", "doInBackground SendPushInfo");
            StringBuilder jsonHtml = new StringBuilder();

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
                } catch (Exception e) {
                    e.printStackTrace();
                }

            return jsonHtml.toString();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    public void phpCreateSend(){
        Log.d("","sendPushInfoPhp");
        String task;
        task = "http://hungry.portfolio1000.com/smarthandongi/send_pushinfo.php?"
                + "id=" + id
                + "&sports=" + sports
                + "&game=" + game
                + "&nightfood=" + nightfood;

        Log.d("id", String.valueOf(id));
        Log.d("id", String.valueOf(sports));
        Log.d("id", String.valueOf(game));
        Log.d("id", String.valueOf(nightfood));

        send_php = new SendPushInfoPhp();
        send_php.execute(task);
    }

}

