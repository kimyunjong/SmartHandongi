package com.smarthandongi;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by LEWIS on 2015-01-28.
 */
public class PushSetup extends Activity implements View.OnClickListener {

    int sports, game, nightfood, id, gonggu, carpool, study, trading, lost, recruiting, exchange;
    int contest, intern, service, perf, seminar, presentation;
    int scholarship, r_sports, r_perf, faith, display, r_service;

    int sports_in, game_in, nightfood_in, gonggu_in, carpool_in, study_in, trading_in, lost_in, recruiting_in, exchange_in;  //푸시 설정에 들어왔을 때 초기값
    int contest_in, intern_in, service_in, perf_in, seminar_in, presentation_in;
    int scholarship_in, r_sports_in, r_perf_in, faith_in, display_in, r_service_in;

    Carrier carrier;
    ImageButton outer_btn, together_btn, recruiting_btn, seminar_btn; //대분류    아래는 소분류
    ImageButton push_sports_btn, push_game_btn, push_nightfood_btn, push_gonggu_btn, push_carpool_btn, push_study_btn, push_trading_btn, push_lost_btn, push_recruiting_btn, push_exchange_btn;
    ImageButton push_contest_btn, push_intern_btn, push_service_btn, push_perf_btn, push_seminar_btn, push_presentation_btn;
    ImageButton push_scholarship_btn, push_r_sports_btn, push_r_perf_btn, push_faith_btn, push_display_btn, push_r_service_btn;
    Button push_save_btn, push_back_btn;
    RelativeLayout popup_pushsetup_1, popup_pushsetup_2, popup_pushsetup_3;

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
        together_btn = (ImageButton)findViewById(R.id.together_btn);
        outer_btn = (ImageButton)findViewById(R.id.outer_btn);
        recruiting_btn = (ImageButton)findViewById(R.id.recruiting_btn);
        seminar_btn = (ImageButton)findViewById(R.id.seminar_btn);

        // 소분류 체크박스
        push_sports_btn = (ImageButton) findViewById(R.id.push_sports_btn);
        push_game_btn = (ImageButton) findViewById(R.id.push_game_btn);
        push_nightfood_btn = (ImageButton) findViewById(R.id.push_nightfood_btn);
        push_gonggu_btn = (ImageButton) findViewById(R.id.push_gonggu_btn);
        push_carpool_btn = (ImageButton) findViewById(R.id.push_carpool_btn);
        push_study_btn = (ImageButton) findViewById(R.id.push_study_btn);
        push_trading_btn = (ImageButton) findViewById(R.id.push_trading_btn);
        push_lost_btn = (ImageButton) findViewById(R.id.push_lost_btn);
        push_recruiting_btn = (ImageButton) findViewById(R.id.push_recruiting_btn);
        push_exchange_btn = (ImageButton) findViewById(R.id.push_exchange_btn);

        push_contest_btn = (ImageButton) findViewById(R.id.push_contest_btn);
        push_intern_btn = (ImageButton) findViewById(R.id.push_intern_btn);
        push_service_btn = (ImageButton) findViewById(R.id.push_service_btn);

        push_perf_btn = (ImageButton) findViewById(R.id.push_perf_btn);
        push_seminar_btn = (ImageButton) findViewById(R.id.push_seminar_btn);
        push_presentation_btn = (ImageButton) findViewById(R.id.push_presentation_btn);

        push_scholarship_btn = (ImageButton) findViewById(R.id.push_scholarship_btn);
        push_r_sports_btn = (ImageButton) findViewById(R.id.push_r_sports_btn);
        push_r_perf_btn = (ImageButton) findViewById(R.id.push_r_perf_btn);
        push_faith_btn = (ImageButton) findViewById(R.id.push_faith_btn);
        push_display_btn = (ImageButton) findViewById(R.id.push_display_btn);
        push_r_service_btn = (ImageButton) findViewById(R.id.push_r_service_btn);

        together_btn.setOnClickListener(this);
        outer_btn.setOnClickListener(this);
        seminar_btn.setOnClickListener(this);
        recruiting_btn.setOnClickListener(this);

        push_sports_btn.setOnClickListener(this);
        push_game_btn.setOnClickListener(this);
        push_nightfood_btn.setOnClickListener(this);
        push_gonggu_btn.setOnClickListener(this);
        push_carpool_btn.setOnClickListener(this);
        push_study_btn.setOnClickListener(this);
        push_trading_btn.setOnClickListener(this);
        push_lost_btn.setOnClickListener(this);
        push_recruiting_btn.setOnClickListener(this);
        push_exchange_btn.setOnClickListener(this);

        push_contest_btn.setOnClickListener(this);
        push_intern_btn.setOnClickListener(this);
        push_service_btn.setOnClickListener(this);

        push_perf_btn.setOnClickListener(this);
        push_seminar_btn.setOnClickListener(this);
        push_presentation_btn.setOnClickListener(this);

        push_scholarship_btn.setOnClickListener(this);
        push_r_sports_btn.setOnClickListener(this);
        push_r_perf_btn.setOnClickListener(this);
        push_faith_btn.setOnClickListener(this);
        push_display_btn.setOnClickListener(this);
        push_r_service_btn.setOnClickListener(this);

        push_save_btn = (Button) findViewById(R.id.push_save_btn);
        push_save_btn.setOnClickListener(this);
        push_back_btn = (Button) findViewById(R.id.push_back_btn);
        push_back_btn.setOnClickListener(this);

        popup_pushsetup_1 = (RelativeLayout)findViewById(R.id.popup_pushsetup_1);
        popup_pushsetup_2 = (RelativeLayout)findViewById(R.id.popup_pushsetup_2);
        popup_pushsetup_3 = (RelativeLayout)findViewById(R.id.popup_pushsetup_3);

        phpCreate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.together_btn :{
                if(sports == 0 || game == 0 || nightfood == 0 || gonggu == 0 || carpool == 0 || study == 0 || trading == 0 || lost == 0 || recruiting == 0 || exchange == 0) {
                    together_btn.setBackgroundResource(R.drawable.push_together_on);
                    sports = 1;
                    game = 1;
                    nightfood = 1;
                    gonggu = 1;
                    carpool = 1;
                    study = 1;
                    trading = 1;
                    lost = 1;
                    recruiting = 1;
                    exchange = 1;
                    push_sports_btn.setBackgroundResource(R.drawable.push_together_sports_on);
                    push_game_btn.setBackgroundResource(R.drawable.push_together_game_on);
                    push_nightfood_btn.setBackgroundResource(R.drawable.push_together_nightfood_on);
                    push_gonggu_btn.setBackgroundResource(R.drawable.push_together_gonggu_on);
                    push_carpool_btn.setBackgroundResource(R.drawable.push_together_carpool_on);
                    push_study_btn.setBackgroundResource(R.drawable.push_together_study_on);
                    push_trading_btn.setBackgroundResource(R.drawable.push_together_trading_on);
                    push_lost_btn.setBackgroundResource(R.drawable.push_together_lost_on);
                    push_recruiting_btn.setBackgroundResource(R.drawable.push_together_recruiting_on);
                    push_exchange_btn.setBackgroundResource(R.drawable.push_together_exchange_on);
                }
                else {
                    together_btn.setBackgroundResource(R.drawable.push_together);
                    sports = 0;
                    game = 0;
                    nightfood = 0;
                    gonggu = 0;
                    carpool = 0;
                    study = 0;
                    trading = 0;
                    lost = 0;
                    recruiting = 0;
                    exchange = 0;
                    push_sports_btn.setBackgroundResource(R.drawable.push_together_sports);
                    push_game_btn.setBackgroundResource(R.drawable.push_together_game);
                    push_nightfood_btn.setBackgroundResource(R.drawable.push_together_nightfood);
                    push_gonggu_btn.setBackgroundResource(R.drawable.push_together_gonggu);
                    push_carpool_btn.setBackgroundResource(R.drawable.push_together_carpool);
                    push_study_btn.setBackgroundResource(R.drawable.push_together_study);
                    push_trading_btn.setBackgroundResource(R.drawable.push_together_trading);
                    push_lost_btn.setBackgroundResource(R.drawable.push_together_lost);
                    push_recruiting_btn.setBackgroundResource(R.drawable.push_together_recruiting);
                    push_exchange_btn.setBackgroundResource(R.drawable.push_together_exchange);
                }
                break;
            }
            case R.id.outer_btn : {
                if(contest == 0 || intern == 0 || service == 0){
                    outer_btn.setBackgroundResource(R.drawable.push_outer_on);
                    contest = 1;
                    intern = 1;
                    service = 1;
                    push_contest_btn.setBackgroundResource(R.drawable.push_outer_contest_on);
                    push_intern_btn.setBackgroundResource(R.drawable.push_outer_intern_on);
                    push_service_btn.setBackgroundResource(R.drawable.push_outer_service_on);
                }
                else {
                    outer_btn.setBackgroundResource(R.drawable.push_outer);
                    contest = 0;
                    intern = 0;
                    service = 0;
                    push_contest_btn.setBackgroundResource(R.drawable.push_outer_contest);
                    push_intern_btn.setBackgroundResource(R.drawable.push_outer_intern);
                    push_service_btn.setBackgroundResource(R.drawable.push_outer_service);
                }
                break;
            }
            case R.id.seminar_btn : {
                if(perf == 0 || seminar == 0 || presentation == 0){
                    seminar_btn.setBackgroundResource(R.drawable.push_seminar_on);
                    perf = 1;
                    seminar = 1;
                    presentation = 1;
                    push_perf_btn.setBackgroundResource(R.drawable.push_seminar_perf_on);
                    push_seminar_btn.setBackgroundResource(R.drawable.push_seminar_seminar_on);
                    push_presentation_btn.setBackgroundResource(R.drawable.push_seminar_presentation_on);
                }
                else {
                    seminar_btn.setBackgroundResource(R.drawable.push_seminar);
                    perf = 0;
                    seminar = 0;
                    presentation = 0;
                    push_perf_btn.setBackgroundResource(R.drawable.push_seminar_perf);
                    push_seminar_btn.setBackgroundResource(R.drawable.push_seminar_seminar);
                    push_presentation_btn.setBackgroundResource(R.drawable.push_seminar_presentation);
                }
                break;
            }
            case R.id.recruiting_btn : {
                if(scholarship == 0 || r_sports == 0 || r_perf == 0 || faith == 0 || display == 0 || r_service == 0){
                    recruiting_btn.setBackgroundResource(R.drawable.push_recruiting_on);
                    scholarship = 1;
                    r_sports = 1;
                    r_perf = 1;
                    faith = 1;
                    display = 1;
                    r_service = 1;
                    push_scholarship_btn.setBackgroundResource(R.drawable.push_recruiting_scholarship_on);
                    push_r_sports_btn.setBackgroundResource(R.drawable.push_recruiting_sports_on);
                    push_r_perf_btn.setBackgroundResource(R.drawable.push_recruiting_perf_on);
                    push_faith_btn.setBackgroundResource(R.drawable.push_recruiting_faith_on);
                    push_display_btn.setBackgroundResource(R.drawable.push_recruiting_display_on);
                    push_r_service_btn.setBackgroundResource(R.drawable.push_recruiting_service_on);
                }
                else {
                    recruiting_btn.setBackgroundResource(R.drawable.push_recruiting);
                    scholarship = 0;
                    r_sports = 0;
                    r_perf = 0;
                    faith = 0;
                    display = 0;
                    r_service = 0;
                    push_scholarship_btn.setBackgroundResource(R.drawable.push_recruiting_scholarship);
                    push_r_sports_btn.setBackgroundResource(R.drawable.push_recruiting_sports);
                    push_r_perf_btn.setBackgroundResource(R.drawable.push_recruiting_perf);
                    push_faith_btn.setBackgroundResource(R.drawable.push_recruiting_faith);
                    push_display_btn.setBackgroundResource(R.drawable.push_recruiting_display);
                    push_r_service_btn.setBackgroundResource(R.drawable.push_recruiting_service);
                }
                break;
            }
            //붙어라
            case R.id.push_sports_btn: {                        //운동
                if(sports == 0){
                    push_sports_btn.setBackgroundResource(R.drawable.push_together_sports_on);       //클릭하기전에 값이 0이었으면 true를 만들어야 하므로 1로 바꿈
                    sports = 1;
                } else {
                    push_sports_btn.setBackgroundResource(R.drawable.push_together_sports);
                    sports = 0;}
                break;
            }
            case R.id.push_game_btn: {                          //게임
                if(game == 0){
                    push_game_btn.setBackgroundResource(R.drawable.push_together_game_on);
                    game = 1;
                } else {
                    push_game_btn.setBackgroundResource(R.drawable.push_together_game);
                    game = 0;}
                break;
            }
            case R.id.push_nightfood_btn: {                     //야식
                if(nightfood == 0){
                    push_nightfood_btn.setBackgroundResource(R.drawable.push_together_nightfood_on);
                    nightfood = 1;
                } else {
                    push_nightfood_btn.setBackgroundResource(R.drawable.push_together_nightfood);
                    nightfood = 0;}
                break;
            }
            case R.id.push_gonggu_btn: {                          //공동구매
                if(gonggu == 0){
                    push_gonggu_btn.setBackgroundResource(R.drawable.push_together_gonggu_on);
                    gonggu = 1;
                } else {
                    push_gonggu_btn.setBackgroundResource(R.drawable.push_together_gonggu);
                    gonggu = 0;}
                break;
            }
            case R.id.push_carpool_btn: {                          //카풀
                if(carpool == 0){
                    push_carpool_btn.setBackgroundResource(R.drawable.push_together_carpool_on);
                    carpool = 1;
                } else {
                    push_carpool_btn.setBackgroundResource(R.drawable.push_together_carpool);
                    carpool = 0;}
                break;
            }
            case R.id.push_study_btn: {                          //스터디
                if(study == 0){
                    push_study_btn.setBackgroundResource(R.drawable.push_together_study_on);
                    study = 1;
                } else {
                    push_study_btn.setBackgroundResource(R.drawable.push_together_study);
                    study = 0;}
                break;
            }
            case R.id.push_trading_btn: {                          //사고팔기
                if(trading == 0){
                    push_trading_btn.setBackgroundResource(R.drawable.push_together_trading_on);
                    trading = 1;
                } else {
                    push_trading_btn.setBackgroundResource(R.drawable.push_together_trading);
                    trading = 0;}
                break;
            }
            case R.id.push_lost_btn: {                          //분실물
                if(lost == 0){
                    push_lost_btn.setBackgroundResource(R.drawable.push_together_lost_on);
                    lost = 1;
                } else {
                    push_lost_btn.setBackgroundResource(R.drawable.push_together_lost);
                    lost = 0;}
                break;
            }
            case R.id.push_recruiting_btn: {                          //구인구직
                if(recruiting == 0){
                    push_recruiting_btn.setBackgroundResource(R.drawable.push_together_recruiting_on);
                    recruiting = 1;
                } else {
                    push_recruiting_btn.setBackgroundResource(R.drawable.push_together_recruiting);
                    recruiting = 0;}
                break;
            }
            case R.id.push_exchange_btn: {                          //교환
                if(exchange == 0){
                    push_exchange_btn.setBackgroundResource(R.drawable.push_together_exchange_on);
                    exchange = 1;
                } else {
                    push_exchange_btn.setBackgroundResource(R.drawable.push_together_exchange);
                    exchange = 0;}
                break;
            }
            case R.id.push_contest_btn: {                          //공모전
                if(contest == 0){
                    push_contest_btn.setBackgroundResource(R.drawable.push_outer_contest_on);
                    contest = 1;
                } else {
                    push_contest_btn.setBackgroundResource(R.drawable.push_outer_contest);
                    contest = 0;}
                break;
            }
            case R.id.push_intern_btn: {                          //인턴
                if(intern == 0){
                    push_intern_btn.setBackgroundResource(R.drawable.push_outer_intern_on);
                    intern = 1;
                } else {
                    push_intern_btn.setBackgroundResource(R.drawable.push_outer_intern);
                    intern = 0;}
                break;
            }
            case R.id.push_service_btn: {                          //자원봉사
                if(service == 0){
                    push_service_btn.setBackgroundResource(R.drawable.push_outer_service_on);
                    service = 1;
                } else {
                    push_service_btn.setBackgroundResource(R.drawable.push_outer_service);
                    service = 0;}
                break;
            }
            case R.id.push_perf_btn: {                          //공연
                if(perf == 0){
                    push_perf_btn.setBackgroundResource(R.drawable.push_seminar_perf_on);
                    perf = 1;
                } else {
                    push_perf_btn.setBackgroundResource(R.drawable.push_seminar_perf);
                    perf = 0;}
                break;
            }
            case R.id.push_seminar_btn: {                          //세미나
                if(seminar == 0){
                    push_seminar_btn.setBackgroundResource(R.drawable.push_seminar_seminar_on);
                    seminar = 1;
                } else {
                    push_seminar_btn.setBackgroundResource(R.drawable.push_seminar_seminar);
                    seminar = 0;}
                break;
            }
            case R.id.push_presentation_btn: {                          //발표
                if(presentation == 0){
                    push_presentation_btn.setBackgroundResource(R.drawable.push_seminar_presentation_on);
                    presentation = 1;
                } else {
                    push_presentation_btn.setBackgroundResource(R.drawable.push_seminar_presentation);
                    presentation = 0;}
                break;
            }
            //리쿠르팅
            case R.id.push_scholarship_btn: {                          //학술
                if(scholarship == 0){
                    push_scholarship_btn.setBackgroundResource(R.drawable.push_recruiting_scholarship_on);
                    scholarship = 1;
                } else {
                    push_scholarship_btn.setBackgroundResource(R.drawable.push_recruiting_scholarship);
                    scholarship = 0;}
                break;
            }
            case R.id.push_r_sports_btn: {                          //운동
                if(r_sports == 0){
                    push_r_sports_btn.setBackgroundResource(R.drawable.push_recruiting_sports_on);
                    r_sports = 1;
                } else {
                    push_r_sports_btn.setBackgroundResource(R.drawable.push_recruiting_sports);
                    r_sports = 0;}
                break;
            }
            case R.id.push_r_perf_btn: {                          //공연
                if(r_perf == 0){
                    push_r_perf_btn.setBackgroundResource(R.drawable.push_recruiting_perf_on);
                    r_perf = 1;
                } else {
                    push_r_perf_btn.setBackgroundResource(R.drawable.push_recruiting_perf);
                    r_perf = 0;}
                break;
            }
            case R.id.push_faith_btn: {                          //신앙
                if(faith == 0){
                    push_faith_btn.setBackgroundResource(R.drawable.push_recruiting_faith_on);
                    faith = 1;
                } else {
                    push_faith_btn.setBackgroundResource(R.drawable.push_recruiting_faith);
                    faith = 0;}
                break;
            }
            case R.id.push_display_btn: {                          //전시
                if(display == 0){
                    push_display_btn.setBackgroundResource(R.drawable.push_recruiting_display_on);
                    display = 1;
                } else {
                    push_display_btn.setBackgroundResource(R.drawable.push_recruiting_display);
                    display = 0;}
                break;
            }
            case R.id.push_r_service_btn: {                          //봉사
                if(r_service == 0){
                    push_r_service_btn.setBackgroundResource(R.drawable.push_recruiting_service_on);
                    r_service = 1;
                } else {
                    push_r_service_btn.setBackgroundResource(R.drawable.push_recruiting_service);
                    r_service = 0;}
                break;
            }
            case R.id.push_back_btn:{
                //phpCreateSend();
                //저장하시겠습니까해서 예 하면 저장하고 나가게

                //Intent intent = new Intent(PushSetup.this, yj_activity.class).putExtra("carrier", carrier);
                //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(intent);
                finish();
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
                        exchange    = exchange_in   = jo.getInt("together_exchange_10");

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
            //붙어라 전체 선택버튼
            if(sports == 1 && game == 1 && nightfood == 1 && gonggu == 1 & carpool == 1 & study == 1 & trading == 1 & lost == 1 & recruiting == 1 & exchange == 1){
                together_btn.setBackgroundResource(R.drawable.push_together_on);
            }
            //붙어라 개별 선택버튼
            if (sports == 0) {
                push_sports_btn.setBackgroundResource(R.drawable.push_together_sports);        //읽어들일 때 1이면 체크 표시
            } else push_sports_btn.setBackgroundResource(R.drawable.push_together_sports_on);
            if (game == 0) {
                push_game_btn.setBackgroundResource(R.drawable.push_together_game);
            } else push_game_btn.setBackgroundResource(R.drawable.push_together_game_on);
            if (nightfood == 0) {
                push_nightfood_btn.setBackgroundResource(R.drawable.push_together_nightfood);
            } else push_nightfood_btn.setBackgroundResource(R.drawable.push_together_nightfood_on);
            if (gonggu == 0) {
                push_gonggu_btn.setBackgroundResource(R.drawable.push_together_gonggu);
            } else push_gonggu_btn.setBackgroundResource(R.drawable.push_together_gonggu_on);
            if (carpool == 0) {
                push_carpool_btn.setBackgroundResource(R.drawable.push_together_carpool);
            } else push_carpool_btn.setBackgroundResource(R.drawable.push_together_carpool_on);
            if (study == 0) {
                push_study_btn.setBackgroundResource(R.drawable.push_together_study);
            } else push_study_btn.setBackgroundResource(R.drawable.push_together_study_on);
            if (trading == 0) {
                push_trading_btn.setBackgroundResource(R.drawable.push_together_trading);
            } else push_trading_btn.setBackgroundResource(R.drawable.push_together_trading_on);
            if (lost == 0) {
                push_lost_btn.setBackgroundResource(R.drawable.push_together_lost);
            } else push_lost_btn.setBackgroundResource(R.drawable.push_together_lost_on);
            if (recruiting == 0) {
                push_recruiting_btn.setBackgroundResource(R.drawable.push_together_recruiting);
            } else push_recruiting_btn.setBackgroundResource(R.drawable.push_together_recruiting_on);
            if (exchange == 0) {
                push_exchange_btn.setBackgroundResource(R.drawable.push_together_exchange);
            } else push_exchange_btn.setBackgroundResource(R.drawable.push_together_exchange_on);

            //대외활동 전체선택버튼
            if(contest == 1 & intern == 1 & service == 1){
                outer_btn.setBackgroundResource(R.drawable.push_outer_on);
            }
            //대외활동 개별선택버튼
            if (contest == 0) {
                push_contest_btn.setBackgroundResource(R.drawable.push_outer_contest);
            } else push_contest_btn.setBackgroundResource(R.drawable.push_outer_contest_on);
            if (intern == 0) {
                push_intern_btn.setBackgroundResource(R.drawable.push_outer_intern);
            } else push_intern_btn.setBackgroundResource(R.drawable.push_outer_intern_on);
            if (service == 0) {
                push_service_btn.setBackgroundResource(R.drawable.push_outer_service);
            } else push_service_btn.setBackgroundResource(R.drawable.push_outer_service_on);

            //공연세미나 전체선택버튼
            if(perf == 1 & seminar == 1 & presentation == 1){
                seminar_btn.setBackgroundResource(R.drawable.push_seminar_on);
            }
            //공연세미나 개별선택버튼
            if (perf == 0) {
                push_perf_btn.setBackgroundResource(R.drawable.push_seminar_perf);
            } else push_perf_btn.setBackgroundResource(R.drawable.push_seminar_perf_on);
            if (seminar == 0) {
                push_seminar_btn.setBackgroundResource(R.drawable.push_seminar_seminar);
            } else push_seminar_btn.setBackgroundResource(R.drawable.push_seminar_seminar_on);
            if (presentation == 0) {
                push_presentation_btn.setBackgroundResource(R.drawable.push_seminar_presentation);
            } else push_presentation_btn.setBackgroundResource(R.drawable.push_seminar_presentation_on);

            if(scholarship == 1 & r_sports == 1 & r_perf == 1 & faith == 1 & display == 1 & r_service == 1){
                recruiting_btn.setBackgroundResource(R.drawable.push_recruiting_on);
            }
            if (scholarship == 0) {
                push_scholarship_btn.setBackgroundResource(R.drawable.push_recruiting_scholarship);
            } else push_scholarship_btn.setBackgroundResource(R.drawable.push_recruiting_scholarship_on);
            if (r_sports == 0) {
                push_r_sports_btn.setBackgroundResource(R.drawable.push_recruiting_sports);
            } else push_r_sports_btn.setBackgroundResource(R.drawable.push_recruiting_sports_on);
            if (r_perf == 0) {
                push_r_perf_btn.setBackgroundResource(R.drawable.push_recruiting_perf);
            } else push_r_perf_btn.setBackgroundResource(R.drawable.push_recruiting_perf_on);
            if (faith == 0) {
                push_faith_btn.setBackgroundResource(R.drawable.push_recruiting_faith);
            } else push_faith_btn.setBackgroundResource(R.drawable.push_recruiting_faith_on);
            if (display == 0) {
                push_display_btn.setBackgroundResource(R.drawable.push_recruiting_display);
            } else push_display_btn.setBackgroundResource(R.drawable.push_recruiting_display_on);
            if (r_service == 0) {
                push_r_service_btn.setBackgroundResource(R.drawable.push_recruiting_service);
            } else push_r_service_btn.setBackgroundResource(R.drawable.push_recruiting_service_on);
        }
    }


    public void onBackPressed() {     ///////여기서는 저장 ㄴㄴ?
        Log.d("","onBackPressed");
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
                + "&study=" + study             + "&trading=" + trading     + "&lost=" + lost       + "&recruiting=" + recruiting + "&exchange=" + exchange
                + "&contest=" + contest         + "&intern=" + intern       + "&service=" + service
                + "&perf=" + perf               + "&seminar=" + seminar     + "&presentation=" + presentation
                + "&scholarship=" + scholarship + "&r_sports=" + r_sports   + "&r_perf=" + r_perf
                + "&faith=" + faith             + "&display=" + display     + "&r_service=" + r_service
                + "&kakao_id=" + carrier.getId() + "&kakao_nick=" + kakao_nick   + "&regid=" + carrier.getRegid()
                + "&sports_in=" + sports_in     + "&game_in=" + game_in     + "&nightfood_in=" + nightfood_in
                + "&gonggu_in=" + gonggu_in     + "&carpool_in=" + carpool_in + "&study_in=" + study_in
                + "&trading_in=" + trading_in   + "&lost_in=" + lost_in + "&recruiting_in=" + recruiting_in + "&exchange_in=" + exchange_in
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
        if(exchange_in == 1 && exchange == 0) exchange_in = 0;
        if(exchange_in == 0 && exchange == 1) exchange_in = 1;

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

        new CountDownTimer(1500, 300) {
            @Override
            public void onTick(long millisUntilFinished) {
                popup_pushsetup_1.setVisibility(VISIBLE);
                popup_pushsetup_2.setVisibility(VISIBLE);
                popup_pushsetup_3.setVisibility(VISIBLE);
            }

            @Override
            public void onFinish() {
                popup_pushsetup_1.setVisibility(GONE);
                popup_pushsetup_2.setVisibility(GONE);
                popup_pushsetup_3.setVisibility(GONE);
                finish();
            }
        }.start();
    }
}

