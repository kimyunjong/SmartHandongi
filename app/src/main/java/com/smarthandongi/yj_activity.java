package com.smarthandongi;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.R.integer;

import com.smarthandongi.adapter.Post2Adapter;
import com.smarthandongi.adapter.PostAdapter;
import com.smarthandongi.database.PostDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by Joel on 2015-01-21.
 */
public class yj_activity extends Activity implements View.OnTouchListener,AbsListView.OnScrollListener {
    Button notice_btn, outer_btn, seminar_btn, recruit_btn, agora_btn, board_btn, timeline_btn, search_btn, menu_btn;
    ImageView notice_img, outer_img, seminar_img, recruit_img, agora_img, board_img, timeline_img, search_img, menu_img;
    ImageButton write_btn;
    RelativeLayout menu;
    Carrier carrier;
    private Intent intent;
    int ca1=1,ca2=1,ca3=1, ca4=1, ca5=1;//켜진상태
    boolean board_on=true,timeline_on=false;


    private ArrayList<PostDatabase> post_list = new ArrayList<PostDatabase>(),board_list = new ArrayList<PostDatabase>(), timeline_list = new ArrayList<PostDatabase>(), timeline_list_today = new ArrayList<PostDatabase>(),timeline_list_tomorrow = new ArrayList<PostDatabase>(),timeline_list_after_tomorrow = new ArrayList<PostDatabase>(), liked_list = new ArrayList<PostDatabase>(),temp_plist = new ArrayList<PostDatabase>();
    private ListView board_listview,timeline_listview;
    private RelativeLayout timeline_listviewR;
    private PostAdapter postAdapter;
    private Post2Adapter post2Adapter;
    private AbsListView view;
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private PostDatabasePhp postDatabasePhp;
    private PostAdapter adapter;
    private Post2Adapter adapter2;


    private Thread thread;
    private Boolean thread_running = false;
    private Boolean menu_on = false;



    int timer = 1000;
    int count;
    int taskPosition = -1;
    int boardOrNot=0; // 보드에서 클릭한건지 타임라인에서 클릭한건지 체크하기 위해서

    public void construction() {
        board_listview = (ListView) findViewById(R.id.list);
        timeline_listview=(ListView)findViewById(R.id.list2);
        timeline_listviewR = (RelativeLayout) findViewById(R.id.list_container);
        phpCreate();
    }

    public void phpCreate() {
        postDatabasePhp = new PostDatabasePhp(post_list, this);
        postDatabasePhp.execute("http://hungry.portfolio1000.com/smarthandongi/posting_php.php?kakao_id=995977");
        Log.v("연결 시도", "연결되어라$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        carrier = (Carrier) intent.getSerializableExtra("carrier");
        setContentView(R.layout.dashboard);

        Log.v("연결 시도", "연결되어라$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        construction();
        Log.v("연결 시도", "연결되어라@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@&&");


        menu = (RelativeLayout) findViewById(R.id.menu);

        notice_btn = (Button) findViewById(R.id.notice_btn);
        outer_btn = (Button) findViewById(R.id.outer_btn);
        seminar_btn = (Button) findViewById(R.id.seminar_btn);
        recruit_btn = (Button) findViewById(R.id.recruit_btn);
        agora_btn = (Button) findViewById(R.id.agora_btn);
        board_btn = (Button) findViewById(R.id.board_btn);
        timeline_btn = (Button) findViewById(R.id.timeline_btn);
        search_btn = (Button) findViewById(R.id.search_btn);
        menu_btn = (Button) findViewById(R.id.menu_btn);
        write_btn = (ImageButton) findViewById(R.id.write_btn);


        notice_img = (ImageView) findViewById(R.id.notice_img);
        outer_img = (ImageView) findViewById(R.id.outer_img);
        seminar_img = (ImageView) findViewById(R.id.seminar_img);
        recruit_img = (ImageView) findViewById(R.id.recruit_img);
        agora_img = (ImageView) findViewById(R.id.agora_img);
        board_img = (ImageView) findViewById(R.id.board_img);
        timeline_img = (ImageView) findViewById(R.id.timeline_img);
        search_img = (ImageView) findViewById(R.id.search_img);
        menu_img = (ImageView) findViewById(R.id.menu_img);


        notice_btn.setOnTouchListener(this);
        outer_btn.setOnTouchListener(this);
        seminar_btn.setOnTouchListener(this);
        recruit_btn.setOnTouchListener(this);
        agora_btn.setOnTouchListener(this);
        board_btn.setOnTouchListener(this);
        timeline_btn.setOnTouchListener(this);
        search_btn.setOnTouchListener(this);
        menu_btn.setOnTouchListener(this);
        write_btn.setOnTouchListener(this);

        timeline_listviewR.setVisibility(View.GONE);

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (thread_running == true) {
                    timer++;
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                }
            }
        });
        thread.setDaemon(true);

    }

    @Override
    public boolean onTouch(View v,
                           MotionEvent event) {


        switch (v.getId()) {


            case R.id.notice_btn: {
                if (event.getAction() == 0) {
                    if(ca1==1&&ca2==1&&ca3==1&&ca4==1&&ca5==1){

                }


                else if(ca1==1) {
                        notice_img.setImageResource(R.drawable.notice_btn_on);
                    }
                    else if(ca1==0) {
                        notice_img.setImageResource(R.drawable.notice_btn);
                    }

                    }
                else if (event.getAction() == 1) {
                    if(ca1==1&&ca2==1&&ca3==1&&ca4==1&&ca5==1){
                        notice_img.setImageResource(R.drawable.notice_btn_on);
                        agora_img.setImageResource(R.drawable.agora_btn);
                        outer_img.setImageResource(R.drawable.outer_btn);
                        seminar_img.setImageResource(R.drawable.seminar_btn);
                        recruit_img.setImageResource(R.drawable.recruit_btn);
                        ca1=1;
                        ca2=0;
                        ca3=0;
                        ca4=0;
                        ca5=0;
                        filter_by_category();
                        filter_by_date();


                    }

                    else if(ca1==1){
                        notice_img.setImageResource(R.drawable.notice_btn);
                        ca1=0;
                        filter_by_category();
                        filter_by_date();
                    }
                    else if(ca1==0){
                        notice_img.setImageResource(R.drawable.notice_btn_on);
                        ca1=1;
                        filter_by_category();
                        filter_by_date();

                    }
                    }

                    break;
                }

            case R.id.outer_btn:
            {
                if (event.getAction() == 0) {
                    if(ca1==1&&ca2==1&&ca3==1&&ca4==1&&ca5==1){

                    }
                    else if(ca2==1) {
                        outer_img.setImageResource(R.drawable.outer_btn_on);}
                    else if(ca2==0) {
                        outer_img.setImageResource(R.drawable.outer_btn);
                    }

                }
                else if (event.getAction() == 1)
                    {
                    if(ca1==1&&ca2==1&&ca3==1&&ca4==1&&ca5==1){
                        notice_img.setImageResource(R.drawable.notice_btn);
                        agora_img.setImageResource(R.drawable.agora_btn);
                        outer_img.setImageResource(R.drawable.outer_btn_on);
                        seminar_img.setImageResource(R.drawable.seminar_btn);
                        recruit_img.setImageResource(R.drawable.recruit_btn);
                        ca1=0;
                        ca2=1;
                        ca3=0;
                        ca4=0;
                        ca5=0;
                        filter_by_category();
                        filter_by_date();
                    }


                    else if(ca2==1){
                        outer_img.setImageResource(R.drawable.outer_btn);
                        ca2=0;
                        filter_by_category();
                        filter_by_date();
                        }
                    else if(ca2==0){
                        outer_img.setImageResource(R.drawable.outer_btn_on);
                        ca2=1;
                        filter_by_category();
                        filter_by_date();
                    }
                }

                break;
            }

            case R.id.seminar_btn: {



                    if (event.getAction() == 0) {
                        if (ca1 == 1 && ca2 == 1 && ca3 == 1 && ca4 == 1 && ca5 == 1) {

                        } else if (ca3 == 1) {
                            seminar_img.setImageResource(R.drawable.seminar_btn_on);
                        } else if (ca3 == 0) {
                            seminar_img.setImageResource(R.drawable.seminar_btn);
                        }

                    }

                    else if (event.getAction() == 1) {
                        if (ca1 == 1 && ca2 == 1 && ca3 == 1 && ca4 == 1 && ca5 == 1) {
                            notice_img.setImageResource(R.drawable.notice_btn);
                            agora_img.setImageResource(R.drawable.agora_btn);
                            outer_img.setImageResource(R.drawable.outer_btn);
                            seminar_img.setImageResource(R.drawable.seminar_btn_on);
                            recruit_img.setImageResource(R.drawable.recruit_btn);
                            ca1 = 0;
                            ca2 = 0;
                            ca3 = 1;
                            ca4 = 0;
                            ca5 = 0;
                            filter_by_category();
                            filter_by_date();
                        } else if (ca3 == 1) {
                            seminar_img.setImageResource(R.drawable.seminar_btn);
                            ca3 = 0;
                            filter_by_category();
                            filter_by_date();
                        } else if (ca3 == 0) {
                            seminar_img.setImageResource(R.drawable.seminar_btn_on);
                            ca3 = 1;
                            filter_by_category();
                            filter_by_date();
                        }
                    }


                    break;
                }

            case R.id.recruit_btn: {
                if (event.getAction() == 0) {

                    if (ca1 == 1 && ca2 == 1 && ca3 == 1 && ca4 == 1 && ca5 == 1) {

                    }

                    else if(ca4==1) {
                        recruit_img.setImageResource(R.drawable.recruit_btn_on);
                    }
                    else if(ca4==0) {
                        recruit_img.setImageResource(R.drawable.recruit_btn);
                    }

                }
                else if (event.getAction() == 1) {
                    if(ca1==1&&ca2==1&&ca3==1&&ca4==1&&ca5==1){
                        notice_img.setImageResource(R.drawable.notice_btn);
                        agora_img.setImageResource(R.drawable.agora_btn);
                        outer_img.setImageResource(R.drawable.outer_btn);
                        seminar_img.setImageResource(R.drawable.seminar_btn);
                        recruit_img.setImageResource(R.drawable.recruit_btn_on);
                        ca1=0;
                        ca2=0;
                        ca3=0;
                        ca4=1;
                        ca5=0;
                        filter_by_category();
                        filter_by_date();
                    }
                    else if(ca4==1){
                        recruit_img.setImageResource(R.drawable.recruit_btn);
                        ca4=0;
                        filter_by_category();
                        filter_by_date();
                    }
                    else if(ca4==0){
                        recruit_img.setImageResource(R.drawable.recruit_btn_on);

                        ca4=1;
                        filter_by_category();
                        filter_by_date();
                    }
                }

                break;
            }

            case R.id.agora_btn:{
                if (event.getAction() == 0) {

                    if (ca1 == 1 && ca2 == 1 && ca3 == 1 && ca4 == 1 && ca5 == 1) {

                    }

                    else if(ca5==1) {
                        agora_img.setImageResource(R.drawable.agora_btn_on);}
                    else if(ca5==0) {
                        agora_img.setImageResource(R.drawable.agora_btn);
                    }

                }
                else if (event.getAction() == 1) {
                    if(ca1==1&&ca2==1&&ca3==1&&ca4==1&&ca5==1){
                        notice_img.setImageResource(R.drawable.notice_btn);
                        agora_img.setImageResource(R.drawable.agora_btn_on);
                        outer_img.setImageResource(R.drawable.outer_btn);
                        seminar_img.setImageResource(R.drawable.seminar_btn);
                        recruit_img.setImageResource(R.drawable.recruit_btn);
                        ca1=0;
                        ca2=0;
                        ca3=0;
                        ca4=0;
                        ca5=1;
                        filter_by_category();
                        filter_by_date();
                    }

                    else if(ca5==1){
                        agora_img.setImageResource(R.drawable.agora_btn);
                        ca5=0;
                        filter_by_category();
                        filter_by_date();
                    }
                    else if(ca5==0){
                        agora_img.setImageResource(R.drawable.agora_btn_on);
                        ca5=1;
                        filter_by_category();
                        filter_by_date();
                    }
                }

                break;

            }
            case R.id.board_btn: {
               if(board_on==false&&timeline_on==true) {

                   if (event.getAction() == 0) {
                       board_img.setImageResource(R.drawable.board_btn);
                   } else if (event.getAction() == 1) {
                       board_img.setImageResource(R.drawable.board_btn_on);
                       timeline_img.setImageResource(R.drawable.timeline_btn);
                       board_on=true;
                       timeline_on=false;
                       board_toogle();


                   }
               }
                break;
            }

            case R.id.timeline_btn: {
                if(board_on==true&&timeline_on==false) {
                    if (event.getAction() == 0) {
                        timeline_img.setImageResource(R.drawable.timeline_btn);
                    } else if (event.getAction() == 1) {
                        timeline_img.setImageResource(R.drawable.timeline_btn_on);
                        board_img.setImageResource(R.drawable.board_btn);

                        board_on=false;
                        timeline_on=true;

                        board_toogle();
//                        filter_by_category();
//                        filter_by_date();
                    }
                }
                break;
            }
            case R.id.search_btn: {
                if (event.getAction() == 0) {
                    search_img.setImageResource(R.drawable.search_btn);
                } else if (event.getAction() == 1) {
                    search_img.setImageResource(R.drawable.search_btn);
                }
                break;
            }

            case R.id.menu_btn: {
                if (event.getAction() == 0) {
                    menu_img.setImageResource(R.drawable.menu_btn);
                } else if (event.getAction() == 1) {
                    menu_img.setImageResource(R.drawable.menu_btn);
                    menu_toggle();
                }
                break;
            }
            case R.id.write_btn: {
                if (event.getAction() == 0) {
                    write_btn.setImageResource(R.drawable.write_btn);
                } else if (event.getAction() == 1) {
                    write_btn.setImageResource(R.drawable.write_btn);
                }
                break;
            }
        }

        return false;
    }


    public void menu_toggle() {
        if (menu_on) {
            menu.setVisibility(View.GONE);
            menu_on = false;
        } else {
            menu.setVisibility(View.VISIBLE);
            menu_on = true;
        }
    }

    public void board_toogle(){
        if(board_on) {
            board_listview.setVisibility(View.VISIBLE);
            timeline_listviewR.setVisibility(View.GONE);

        }else{
            board_listview.setVisibility(View.GONE);
            timeline_listviewR.setVisibility(View.VISIBLE);
        }
    }

    public void onBackPressed() {

        if (menu_on) {
            menu_toggle();
        } else {

            if (thread_running == false) {
                thread_running = true;
                thread.start();
            }

            if (timer > 300) {
                timer = 0;
                Toast.makeText(this, "뒤로가기를 한 번 더 누르면 종료됩니다", 300).show();
            } else {
                thread_running = false;
                finish();
                overridePendingTransition(0, 0);
            }
        }
    }

    public class PostDatabasePhp extends AsyncTask<String, integer, String> {

        private yj_activity context;
        private ArrayList<PostDatabase> post_list;
        private Activity activity;

        public PostDatabasePhp(ArrayList<PostDatabase> post_list, yj_activity context) {
            super();
            this.post_list = post_list;
            this.context = context;
        }

        public PostDatabasePhp(ArrayList<PostDatabase> post_list, Activity activity) {
            super();
            this.post_list = post_list;
            this.activity=activity;
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

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    post_list.add(new PostDatabase(
                            jo.getString("title"), jo.getInt("id"), jo.getString("kakao_id"), jo.getString("category"), jo.getString("group"),
                            jo.getString("content"), jo.getString("posting_date"), jo.getString("image_link"), jo.getString("start_date"), jo.getString("end_date"), jo.getString("has_pic"), jo.getString("like")
                    ));

                    System.out.println(jo.getString("title")+"확인할 부분 입니다."+jo.getString("like"));
                }


                //필터 카테고리 호출, 넣어준다.
                //filter_by_category();                         나중에 필터할때 손봐주기
                filter_by_category();
                filter_by_date();
                adapter = new PostAdapter(yj_activity.this, board_list,carrier);
                adapter2= new Post2Adapter(yj_activity.this, timeline_list );
                Log.v("연결 시도", "연결되어라@*********************************************");

                board_listview.setAdapter(adapter);
                board_listview.setOnScrollListener(context);
                board_listview.setOnItemClickListener(boardItemClickListener);

                timeline_listview.setAdapter(adapter2);
                timeline_listview.setOnScrollListener(context);
                timeline_listview.setOnItemClickListener(timelineItemClickListener);

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void filter_by_category() {

        board_list.removeAll(board_list);
        timeline_list.removeAll(timeline_list);

        for (PostDatabase db : post_list) {

            if (db.getCategory().equalsIgnoreCase("1")&&ca1==0) continue;
            if (db.getCategory().equalsIgnoreCase("2")&&ca2==0) continue;
            if (db.getCategory().equalsIgnoreCase("3")&&ca3==0) continue;
            if (db.getCategory().equalsIgnoreCase("4")&&ca4==0) continue;
            if (db.getCategory().equalsIgnoreCase("5")&&ca5==0) continue;

            board_list.add(db);
            timeline_list.add(db);
        }

        adapter = new PostAdapter(yj_activity.this, board_list,carrier);
        board_listview.setAdapter(adapter);

    }
    public void filter_by_date(){
            int last_day=0;


        //temp 비우고 여기에 값 넣어준다.

        filter_by_category();

       //분류된 (카테고리별로) 타임라인 리스트에 디데이를 넣어준다
        for(PostDatabase db:timeline_list){
            String temp_date=db.getStart_date();
            dday deadline=new dday();

           int s_date=Integer.parseInt(temp_date);
           int s_year;
           int s_month;
           int s_day;
            s_year=s_date/10000;
            s_date=s_date-(s_year*10000);
            s_month=s_date/100;
            s_date=s_date-s_month*100;
            s_day=s_date;

            String temp_date_e=db.getEnd_date();
            int e_date=Integer.parseInt(temp_date_e);

            int e_year=e_date/10000;
            e_date=e_date-(s_year*10000);
            int e_month=e_date/100;
            e_date=e_date-e_month*100;
            int e_day=e_date;

            int dday_s=deadline.caldate(s_year,s_month-1,s_day+1);
            int dday_e=deadline.caldate(e_year,e_month-1,e_day+1);

            if(dday_s<0) //아직기간이 남았으면
            {
                db.setDday(dday_s);

                 if(last_day>db.getDday()){
                    last_day=db.getDday();
                }
            }

            else if(dday_s>=0&&dday_e<=0){
                db.setDday(0);
            }
            else{
                db.setDday(9999); //기간이 이미 종료 됬으면 빼 준다.
            }
            }

        temp_plist.removeAll(temp_plist);

        //dday 가 0인 데이터베이스 먼저 넣기/ 나중에 각 해당 날짜의 1일 일때 추가 해주기
//        for(PostDatabase db:timeline_list){
//            if(db.getDday()==0){
//                temp_plist.add(db);
//
//            }
//        }
        //dday가 0이 아닌 경우에
        for(int j=0;j>=last_day;j--)
        {
            for(PostDatabase db:timeline_list){
                if(db.getDday()==j){
                    temp_plist.add(db);
                }
            }
        }


        for(PostDatabase db:temp_plist){
                db.setFirst_day_F();
                System.out.println(db.getId()+"앞서 나와야할 정보:설정 2"+db.getDday()+db.getFirst_day());
        }
        //첫번째 이벤트에 대한 정보주기
        int first_day=0;
        boolean day_turn=false;
        int before_dday=0;

        for(PostDatabase db:temp_plist)
        {
           //날바뀐지 안바뀐지 설정
            if(temp_plist.indexOf(db)==0)
            {
              day_turn=true;
            }
            else
            {
                if (before_dday != db.getDday())
                {

                    day_turn = true;
                }
                else
                {
                    day_turn = false;
                }
            }

            if(day_turn)
            {
                db.setFirst_day_T();
                    // 하루씩만 깎이면 안됨, 바뀌는 날에
            }
            else//디데이 바뀌지 않음.
            {
                System.out.println("바뀌지 않음."+first_day);
            }
            before_dday=db.getDday();
        }


        for(PostDatabase db:temp_plist){
            System.out.println(db.getId()+"앞서 나와야할 정보설정점2:퍼스트데이설정"+db.getDday()+db.getFirst_day());
        }


        timeline_list.removeAll(timeline_list);
        for(PostDatabase db:temp_plist){
            timeline_list.add(db);
        }
        for(PostDatabase db:temp_plist){
            System.out.println("첫째날입니다."+db.getFirst_day());
        }

        adapter2= new Post2Adapter(yj_activity.this, timeline_list );
        timeline_listview.setAdapter(adapter2);


    }



    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        this.view = view;

        this.firstVisibleItem = firstVisibleItem;
        this.visibleItemCount = visibleItemCount;
        this.totalItemCount = totalItemCount;

    }


    AdapterView.OnItemClickListener boardItemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent intent = new Intent(yj_activity.this, PostDetail.class);
            intent.putExtra("carrier", carrier);
            intent.putExtra("post", board_list.get(position));
            startActivityForResult(intent, 0);
            overridePendingTransition(0,0);
        }
    };

    AdapterView.OnItemClickListener timelineItemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent intent = new Intent(yj_activity.this, PostDetail.class);
            intent.putExtra("carrier", carrier);
            intent.putExtra("post", timeline_list.get(position));
            startActivityForResult(intent, 0);
            overridePendingTransition(0,0);
        }
    };


}

