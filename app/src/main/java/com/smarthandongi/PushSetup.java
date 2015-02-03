package com.smarthandongi;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by LEWIS on 2015-01-28.
 */
public class PushSetup extends Activity implements View.OnClickListener {
    int sports, game, nightfood, id, gonggu, carpool, study, trading, lost, recruiting;
    int contest, intern, service, perf, seminar, presentation;
    int scholarship, r_sports, r_perf, faith, display, r_service;

    int sports_in, game_in, nightfood_in, gonggu_in, carpool_in, study_in, trading_in, lost_in, recruiting_in;  //푸시 설정에 들어왔을 때 초기값
    int contest_in, intern_in, service_in, perf_in, seminar_in, presentation_in;
    int scholarship_in, r_sports_in, r_perf_in, faith_in, display_in, r_service_in;

    Carrier carrier;
    CheckBox outer_chk, together_chk, recruiting_chk, seminar_chk; //대분류    아래는 소분류
    CheckBox push_sports_chk, push_game_chk, push_nightfood_chk, push_gonggu_chk, push_carpool_chk, push_study_chk, push_trading_chk, push_lost_chk, push_recruiting_chk;
    CheckBox push_contest_chk, push_intern_chk, push_service_chk, push_perf_chk, push_seminar_chk, push_presentation_chk;
    CheckBox push_scholarship_chk, push_r_sports_chk, push_r_perf_chk, push_faith_chk, push_display_chk, push_r_service_chk;
    Button push_save_btn;
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

        // 대분류 체크박스
        together_chk = (CheckBox)findViewById(R.id.together_chk);
        outer_chk = (CheckBox)findViewById(R.id.outer_chk);
        recruiting_chk = (CheckBox)findViewById(R.id.recruiting_chk);
        seminar_chk = (CheckBox)findViewById(R.id.seminar_chk);

        // 소분류 체크박스
        push_sports_chk = (CheckBox) findViewById(R.id.push_sports_chk);
        push_game_chk = (CheckBox) findViewById(R.id.push_game_chk);
        push_nightfood_chk = (CheckBox) findViewById(R.id.push_nightfood_chk);
        push_gonggu_chk = (CheckBox) findViewById(R.id.push_gonggu_chk);
        push_carpool_chk = (CheckBox) findViewById(R.id.push_carpool_chk);
        push_study_chk = (CheckBox) findViewById(R.id.push_study_chk);
        push_trading_chk = (CheckBox) findViewById(R.id.push_trading_chk);
        push_lost_chk = (CheckBox) findViewById(R.id.push_lost_chk);
        push_recruiting_chk = (CheckBox) findViewById(R.id.push_recruiting_chk);

        push_contest_chk = (CheckBox) findViewById(R.id.push_contest_chk);
        push_intern_chk = (CheckBox) findViewById(R.id.push_intern_chk);
        push_service_chk = (CheckBox) findViewById(R.id.push_service_chk);

        push_perf_chk = (CheckBox) findViewById(R.id.push_perf_chk);
        push_seminar_chk = (CheckBox) findViewById(R.id.push_seminar_chk);
        push_presentation_chk = (CheckBox) findViewById(R.id.push_presentation_chk);

        push_scholarship_chk = (CheckBox) findViewById(R.id.push_scholarship_chk);
        push_r_sports_chk = (CheckBox) findViewById(R.id.push_r_sports_chk);
        push_r_perf_chk = (CheckBox) findViewById(R.id.push_r_perf_chk);
        push_faith_chk = (CheckBox) findViewById(R.id.push_faith_chk);
        push_display_chk = (CheckBox) findViewById(R.id.push_display_chk);
        push_r_service_chk = (CheckBox) findViewById(R.id.push_r_service_chk);

        together_chk.setOnClickListener(this);
        outer_chk.setOnClickListener(this);
        seminar_chk.setOnClickListener(this);
        recruiting_chk.setOnClickListener(this);

        push_sports_chk.setOnClickListener(this);
        push_game_chk.setOnClickListener(this);
        push_nightfood_chk.setOnClickListener(this);
        push_gonggu_chk.setOnClickListener(this);
        push_carpool_chk.setOnClickListener(this);
        push_study_chk.setOnClickListener(this);
        push_trading_chk.setOnClickListener(this);
        push_lost_chk.setOnClickListener(this);
        push_recruiting_chk.setOnClickListener(this);

        push_contest_chk.setOnClickListener(this);
        push_intern_chk.setOnClickListener(this);
        push_service_chk.setOnClickListener(this);

        push_perf_chk.setOnClickListener(this);
        push_seminar_chk.setOnClickListener(this);
        push_presentation_chk.setOnClickListener(this);

        push_scholarship_chk.setOnClickListener(this);
        push_r_sports_chk.setOnClickListener(this);
        push_r_perf_chk.setOnClickListener(this);
        push_faith_chk.setOnClickListener(this);
        push_display_chk.setOnClickListener(this);
        push_r_service_chk.setOnClickListener(this);

        push_save_btn = (Button) findViewById(R.id.push_save_btn);
        push_save_btn.setOnClickListener(this);

        phpCreate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.together_chk :{
                if(sports == 0 || game == 0 || nightfood == 0 || gonggu == 0 || carpool == 0 || study == 0 || trading == 0 || lost == 0 || recruiting == 0) {
                    together_chk.setChecked(true);
                    sports = 1;
                    game = 1;
                    nightfood = 1;
                    gonggu = 1;
                    carpool = 1;
                    study = 1;
                    trading = 1;
                    lost = 1;
                    recruiting = 1;
                    push_sports_chk.setChecked(true);
                    push_game_chk.setChecked(true);
                    push_nightfood_chk.setChecked(true);
                    push_gonggu_chk.setChecked(true);
                    push_carpool_chk.setChecked(true);
                    push_study_chk.setChecked(true);
                    push_trading_chk.setChecked(true);
                    push_lost_chk.setChecked(true);
                    push_recruiting_chk.setChecked(true);
                }
                else {
                    together_chk.setChecked(false);
                    sports = 0;
                    game = 0;
                    nightfood = 0;
                    gonggu = 0;
                    carpool = 0;
                    study = 0;
                    trading = 0;
                    lost = 0;
                    recruiting = 0;
                    push_sports_chk.setChecked(false);
                    push_game_chk.setChecked(false);
                    push_nightfood_chk.setChecked(false);
                    push_gonggu_chk.setChecked(false);
                    push_carpool_chk.setChecked(false);
                    push_study_chk.setChecked(false);
                    push_trading_chk.setChecked(false);
                    push_lost_chk.setChecked(false);
                    push_recruiting_chk.setChecked(false);
                }
                break;
            }
            case R.id.outer_chk : {
                if(contest == 0 || intern == 0 || service == 0){
                    outer_chk.setChecked(true);
                    contest = 1;
                    intern = 1;
                    service = 1;
                    push_contest_chk.setChecked(true);
                    push_intern_chk.setChecked(true);
                    push_service_chk.setChecked(true);
                }
                else {
                    outer_chk.setChecked(false);
                    contest = 0;
                    intern = 0;
                    service = 0;
                    push_contest_chk.setChecked(false);
                    push_intern_chk.setChecked(false);
                    push_service_chk.setChecked(false);
                }
                break;
            }
            case R.id.seminar_chk : {
                if(perf == 0 || seminar == 0 || presentation == 0){
                    seminar_chk.setChecked(true);
                    perf = 1;
                    seminar = 1;
                    presentation = 1;
                    push_perf_chk.setChecked(true);
                    push_seminar_chk.setChecked(true);
                    push_presentation_chk.setChecked(true);
                }
                else {
                    seminar_chk.setChecked(false);
                    perf = 0;
                    seminar = 0;
                    presentation = 0;
                    push_perf_chk.setChecked(false);
                    push_seminar_chk.setChecked(false);
                    push_presentation_chk.setChecked(false);
                }
                break;
            }
            case R.id.recruiting_chk : {
                if(scholarship == 0 || r_sports == 0 || r_perf == 0 || faith == 0 || display == 0 || r_service == 0){
                    recruiting_chk.setChecked(true);
                    scholarship = 1;
                    r_sports = 1;
                    r_perf = 1;
                    faith = 1;
                    display = 1;
                    r_service = 1;
                    push_scholarship_chk.setChecked(true);
                    push_r_sports_chk.setChecked(true);
                    push_r_perf_chk.setChecked(true);
                    push_faith_chk.setChecked(true);
                    push_display_chk.setChecked(true);
                    push_r_service_chk.setChecked(true);
                }
                else {
                    recruiting_chk.setChecked(false);
                    scholarship = 0;
                    r_sports = 0;
                    r_perf = 0;
                    faith = 0;
                    display = 0;
                    r_service = 0;
                    push_scholarship_chk.setChecked(false);
                    push_r_sports_chk.setChecked(false);
                    push_r_perf_chk.setChecked(false);
                    push_faith_chk.setChecked(false);
                    push_display_chk.setChecked(false);
                    push_r_service_chk.setChecked(false);
                }
                break;
            }
                                                                //붙어라
            case R.id.push_sports_chk: {                        //운동
                if(sports == 0){
                    push_sports_chk.setChecked(true);       //클릭하기전에 값이 0이었으면 true를 만들어야 하므로 1로 바꿈
                    sports = 1;
                } else {push_sports_chk.setChecked(false);
                    sports = 0;}
                break;
            }
            case R.id.push_game_chk: {                          //게임
                if(game == 0){
                    push_game_chk.setChecked(true);
                    game = 1;
                } else {push_game_chk.setChecked(false);
                    game = 0;}
                break;
            }
            case R.id.push_nightfood_chk: {                     //야식
                if(nightfood == 0){
                    push_nightfood_chk.setChecked(true);
                    nightfood = 1;
                } else {push_nightfood_chk.setChecked(false);
                    nightfood = 0;}
                break;
            }
            case R.id.push_gonggu_chk: {                          //공동구매
                if(gonggu == 0){
                    push_gonggu_chk.setChecked(true);
                    gonggu = 1;
                } else {push_gonggu_chk.setChecked(false);
                    gonggu = 0;}
                break;
            }
            case R.id.push_carpool_chk: {                          //카풀
                if(carpool == 0){
                    push_carpool_chk.setChecked(true);
                    carpool = 1;
                } else {push_carpool_chk.setChecked(false);
                    carpool = 0;}
                break;
            }
            case R.id.push_study_chk: {                          //스터디
                if(study == 0){
                    push_study_chk.setChecked(true);
                    study = 1;
                } else {push_study_chk.setChecked(false);
                    study = 0;}
                break;
            }
            case R.id.push_trading_chk: {                          //교환
                if(trading == 0){
                    push_trading_chk.setChecked(true);
                    trading = 1;
                } else {push_trading_chk.setChecked(false);
                    trading = 0;}
                break;
            }
            case R.id.push_lost_chk: {                          //분실물
                if(lost == 0){
                    push_lost_chk.setChecked(true);
                    lost = 1;
                } else {push_lost_chk.setChecked(false);
                    lost = 0;}
                break;
            }
            case R.id.push_recruiting_chk: {                          //구인구직
                if(recruiting == 0){
                    push_recruiting_chk.setChecked(true);
                    recruiting = 1;
                } else {push_recruiting_chk.setChecked(false);
                    recruiting = 0;}
                break;
            }
            case R.id.push_contest_chk: {                          //공모전
                if(contest == 0){
                    push_contest_chk.setChecked(true);
                    contest = 1;
                } else {push_contest_chk.setChecked(false);
                    contest = 0;}
                break;
            }
            case R.id.push_intern_chk: {                          //인턴
                if(intern == 0){
                    push_intern_chk.setChecked(true);
                    intern = 1;
                } else {push_intern_chk.setChecked(false);
                    intern = 0;}
                break;
            }
            case R.id.push_service_chk: {                          //자원봉사
                if(service == 0){
                    push_service_chk.setChecked(true);
                    service = 1;
                } else {push_service_chk.setChecked(false);
                    service = 0;}
                break;
            }
            case R.id.push_perf_chk: {                          //공연
                if(perf == 0){
                    push_perf_chk.setChecked(true);
                    perf = 1;
                } else {push_perf_chk.setChecked(false);
                    perf = 0;}
                break;
            }
            case R.id.push_seminar_chk: {                          //세미나
                if(seminar == 0){
                    push_seminar_chk.setChecked(true);
                    seminar = 1;
                } else {push_seminar_chk.setChecked(false);
                    seminar = 0;}
                break;
            }
            case R.id.push_presentation_chk: {                          //발표
                if(presentation == 0){
                    push_presentation_chk.setChecked(true);
                    presentation = 1;
                } else {push_presentation_chk.setChecked(false);
                    presentation = 0;}
                break;
            }
                                                                //리쿠르팅
            case R.id.push_scholarship_chk: {                          //학술
                if(scholarship == 0){
                    push_scholarship_chk.setChecked(true);
                    scholarship = 1;
                } else {push_scholarship_chk.setChecked(false);
                    scholarship = 0;}
                break;
            }
            case R.id.push_r_sports_chk: {                          //운동
                if(r_sports == 0){
                    push_r_sports_chk.setChecked(true);
                    r_sports = 1;
                } else {push_r_sports_chk.setChecked(false);
                    r_sports = 0;}
                break;
            }
            case R.id.push_r_perf_chk: {                          //공연
                if(r_perf == 0){
                    push_r_perf_chk.setChecked(true);
                    r_perf = 1;
                } else {push_r_perf_chk.setChecked(false);
                    r_perf = 0;}
                break;
            }
            case R.id.push_faith_chk: {                          //신앙
                if(faith == 0){
                    push_faith_chk.setChecked(true);
                    faith = 1;
                } else {push_faith_chk.setChecked(false);
                   faith = 0;}
                break;
            }
            case R.id.push_display_chk: {                          //전시
                if(display == 0){
                    push_display_chk.setChecked(true);
                    display = 1;
                } else {push_display_chk.setChecked(false);
                    display = 0;}
                break;
            }
            case R.id.push_r_service_chk: {                          //봉사
                if(r_service == 0){
                    push_r_service_chk.setChecked(true);
                    r_service = 1;
                } else {push_r_service_chk.setChecked(false);
                    r_service = 0;}
                break;
            }

            case R.id.push_save_btn:{                           //저장하기
                phpCreateSend();
                break;
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
                        sports      = sports_in     = jo.getInt("together_sports_1");
                        game        = game_in       = jo.getInt("together_game_2");
                        nightfood   = nightfood_in  = jo.getInt("together_nightfood_3");
                        gonggu      = gonggu_in     = jo.getInt("together_gonggu_4");
                        carpool     = carpool_in    = jo.getInt("together_carpool_5");
                        study       = study_in      = jo.getInt("together_study_6");
                        trading     = trading_in    = jo.getInt("together_trading_7");
                        lost        = lost_in       = jo.getInt("together_lost_8");
                        recruiting  = recruiting_in = jo.getInt("together_recruiting_9");

                        contest     = contest_in    = jo.getInt("outer_contest_21");
                        intern      = intern_in     = jo.getInt("outer_intern_22");
                        service     = service_in    = jo.getInt("outer_service_23");
                        perf        = perf_in       = jo.getInt("seminar_perf_41");
                        seminar     = seminar_in    = jo.getInt("seminar_seminar_42");
                        presentation = presentation_in = jo.getInt("seminar_presentation_43");

                        scholarship = scholarship_in = jo.getInt("recruiting_scholarship_61");
                        r_sports    = r_sports_in   = jo.getInt("recruiting_sports_62");
                        r_perf      = r_perf_in     = jo.getInt("recruiting_perf_63");
                        faith       = faith_in      = jo.getInt("recruiting_faith_64");
                        display     = display_in    = jo.getInt("recruiting_display_65");
                        r_service   = r_service_in  = jo.getInt("recruiting_service_66");
                        break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (sports == 0) {
                push_sports_chk.setChecked(false);        //읽어들일 때 1이면 체크 표시
            } else push_sports_chk.setChecked(true);
            if (game == 0) {
                push_game_chk.setChecked(false);
            } else push_game_chk.setChecked(true);
            if (nightfood == 0) {
                push_nightfood_chk.setChecked(false);
            } else push_nightfood_chk.setChecked(true);
            if (gonggu == 0) {
                push_gonggu_chk.setChecked(false);
            } else push_gonggu_chk.setChecked(true);
            if (carpool == 0) {
                push_carpool_chk.setChecked(false);
            } else push_carpool_chk.setChecked(true);
            if (study == 0) {
                push_study_chk.setChecked(false);
            } else push_study_chk.setChecked(true);
            if (trading == 0) {
                push_trading_chk.setChecked(false);
            } else push_trading_chk.setChecked(true);
            if (lost == 0) {
                push_lost_chk.setChecked(false);
            } else push_lost_chk.setChecked(true);
            if (recruiting == 0) {
                push_recruiting_chk.setChecked(false);
            } else push_recruiting_chk.setChecked(true);

            if (contest == 0) {
                push_contest_chk.setChecked(false);
            } else push_contest_chk.setChecked(true);
            if (intern == 0) {
                push_intern_chk.setChecked(false);
            } else push_intern_chk.setChecked(true);
            if (service == 0) {
                push_service_chk.setChecked(false);
            } else push_service_chk.setChecked(true);
            if (perf == 0) {
                push_perf_chk.setChecked(false);
            } else push_perf_chk.setChecked(true);
            if (seminar == 0) {
                push_seminar_chk.setChecked(false);
            } else push_seminar_chk.setChecked(true);
            if (presentation == 0) {
                push_presentation_chk.setChecked(false);
            } else push_presentation_chk.setChecked(true);

            if (scholarship == 0) {
                push_scholarship_chk.setChecked(false);
            } else push_scholarship_chk.setChecked(true);
            if (r_sports == 0) {
                push_r_sports_chk.setChecked(false);
            } else push_r_sports_chk.setChecked(true);
            if (r_perf == 0) {
                push_r_perf_chk.setChecked(false);
            } else push_r_perf_chk.setChecked(true);
            if (faith == 0) {
                push_faith_chk.setChecked(false);
            } else push_faith_chk.setChecked(true);
            if (display == 0) {
                push_display_chk.setChecked(false);
            } else push_display_chk.setChecked(true);
            if (r_service == 0) {
                push_r_service_chk.setChecked(false);
            } else push_r_service_chk.setChecked(true);
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
        String task;
        String kakao_nick;

        kakao_nick = carrier.getNickname();

        try {
            kakao_nick = URLEncoder.encode(kakao_nick, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



        task = "http://hungry.portfolio1000.com/smarthandongi/send_pushinfo.php?"
                + "id=" + id                    + "&sports=" + sports       + "&game=" + game
                + "&nightfood=" + nightfood     + "&gonggu=" + gonggu       + "&carpool=" + carpool
                + "&study=" + study             + "&trading=" + trading     + "&lost=" + lost       + "&recruiting=" + recruiting
                + "&contest=" + contest         + "&intern=" + intern       + "&service=" + service
                + "&perf=" + perf               + "&seminar=" + seminar     + "&presentation=" + presentation
                + "&scholarship=" + scholarship + "&r_sports=" + r_sports   + "&r_perf=" + r_perf
                + "&faith=" + faith             + "&display=" + display     + "&r_service=" + r_service
                + "&kakao_id=" + carrier.getId() + "&kakao_nick=" + kakao_nick   + "&regid=" + carrier.getRegid()
                + "&sports_in=" + sports_in     + "&game_in=" + game_in     + "&nightfood_in=" + nightfood_in
                + "&gonggu_in=" + gonggu_in     + "&carpool_in=" + carpool_in + "&study_in=" + study_in
                + "&trading_in=" + trading_in   + "&lost_in=" + lost_in + "&recruiting_in=" + recruiting_in
                + "&contest_in=" + contest_in   + "&intern_in=" + intern_in + "&service_in=" + service_in
                + "&perf_in=" + perf_in         + "&seminar_in=" + seminar_in + "&presentation_in=" + presentation_in
                + "&scholarship_in=" + scholarship_in + "&r_sports_in=" +r_sports_in + "&r_perf_in=" + r_perf_in
                + "&faith_in=" + faith_in       + "&display_in=" + display_in + "&r_service_in=" + r_service_in;

        if(sports_in == 1 && sports == 0) sports_in = 0;       //푸시설정 화면에 처음 들어갔을 때 클릭(1)이 되어있는데 변경되었을 경우 in값도 변경해준다.
        if(sports_in == 0 && sports == 1) sports_in = 1;
        if(game_in == 1 && game == 0) game_in = 0;
        if(game_in == 0 && game == 1) game_in = 1;
        if(nightfood_in == 1 && nightfood == 0) nightfood_in = 0;
        if(nightfood_in == 0 && nightfood == 1) nightfood_in = 1;
        if(gonggu_in == 1 && gonggu == 0) gonggu_in = 0;
        if(gonggu_in == 0 && gonggu == 1) gonggu_in = 1;
        if(carpool_in == 1 && carpool == 0) carpool_in = 0;
        if(carpool_in == 0 && carpool == 1) carpool_in = 1;
        if(study_in == 1 && study == 0) study_in = 0;
        if(study_in == 0 && study == 1) study_in = 1;
        if(trading_in == 1 && trading == 0) trading_in = 0;
        if(trading_in == 0 && trading == 1) trading_in = 1;
        if(lost_in == 1 && lost == 0) lost_in = 0;
        if(lost_in == 0 && lost == 1) lost_in = 1;
        if(recruiting_in == 1 && recruiting == 0) recruiting_in = 0;
        if(recruiting_in == 0 && recruiting == 1) recruiting_in = 1;

        if(contest_in == 1 && contest == 0) contest_in = 0;
        if(contest_in == 0 && contest == 1) contest_in = 1;
        if(intern_in == 1 && intern == 0) intern_in = 0;
        if(intern_in == 0 && intern == 1) intern_in = 1;
        if(service_in == 1 && service == 0) service_in = 0;
        if(service_in == 0 && service == 1) service_in = 1;
        if(perf_in == 1 && perf == 0) perf_in = 0;
        if(perf_in == 0 && perf == 1) perf_in = 1;
        if(seminar_in == 1 && seminar == 0) seminar_in = 0;
        if(seminar_in == 0 && seminar == 1) seminar_in = 1;
        if(presentation_in == 1 && presentation == 0) presentation_in = 0;
        if(presentation_in == 0 && presentation == 1) presentation_in = 1;

        if(scholarship_in == 1 && scholarship == 0) scholarship_in = 0;
        if(scholarship_in == 0 && scholarship == 1) scholarship_in = 1;
        if(r_sports_in == 1 && r_sports == 0) r_sports_in = 0;
        if(r_sports_in == 0 && r_sports == 1) r_sports_in = 1;
        if(r_perf_in == 1 && r_perf == 0) r_perf_in = 0;
        if(r_perf_in == 0 && r_perf == 1) r_perf_in = 1;
        if(faith_in == 1 && faith == 0) faith_in = 0;
        if(faith_in == 0 && faith == 1) faith_in = 1;
        if(display_in == 1 && display == 0) display_in = 0;
        if(display_in == 0 && display == 1) display_in = 1;
        if(r_service_in == 1 && r_service == 0) r_service_in = 0;
        if(r_service_in == 0 && r_service == 1) r_service_in = 1;


        send_php = new SendPushInfoPhp();
        send_php.execute(task);
    }
}

