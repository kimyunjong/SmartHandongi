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
public class yj_activity extends Activity implements View.OnTouchListener, AdapterView.OnItemClickListener,AbsListView.OnScrollListener {
    Button notice_btn, outer_btn, seminar_btn, recruit_btn, agora_btn, board_btn, timeline_btn, search_btn, menu_btn;
    ImageView notice_img, outer_img, seminar_img, recruit_img, agora_img, board_img, timeline_img, search_img, menu_img;
    ImageButton write_btn;
    RelativeLayout menu;
    Carrier carrier;
    private Intent intent;
    int ca1=1,ca2=1,ca3=1, ca4=1, ca5=1;//켜진상태
    boolean board_on=true,timeline_on=false;


    private ArrayList<PostDatabase> post_list = new ArrayList<PostDatabase>(),board_list = new ArrayList<PostDatabase>(), timeline_list = new ArrayList<PostDatabase>(), liked_list = new ArrayList<PostDatabase>();
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

    public void construction() {
        board_listview = (ListView) findViewById(R.id.list);
        timeline_listview=(ListView)findViewById(R.id.list2);
        timeline_listviewR = (RelativeLayout) findViewById(R.id.list_container);
        phpCreate();
    }

    public void phpCreate() {
        postDatabasePhp = new PostDatabasePhp(post_list, this);
        postDatabasePhp.execute("http://hungry.portfolio1000.com/smarthandongi/posting_php.php/");
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

                    }

                    else if(ca1==1){
                        notice_img.setImageResource(R.drawable.notice_btn);
                        ca1=0;
                        filter_by_category();
                        }
                    else if(ca1==0){
                        notice_img.setImageResource(R.drawable.notice_btn_on);
                        ca1=1;
                        filter_by_category();

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

                    }


                    else if(ca2==1){
                        outer_img.setImageResource(R.drawable.outer_btn);
                        ca2=0;
                        filter_by_category();
                        }
                    else if(ca2==0){
                        outer_img.setImageResource(R.drawable.outer_btn_on);
                        ca2=1;
                        filter_by_category();

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

                        } else if (ca3 == 1) {
                            seminar_img.setImageResource(R.drawable.seminar_btn);
                            ca3 = 0;
                            filter_by_category();
                        } else if (ca3 == 0) {
                            seminar_img.setImageResource(R.drawable.seminar_btn_on);
                            ca3 = 1;
                            filter_by_category();
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

                    }
                    else if(ca4==1){
                        recruit_img.setImageResource(R.drawable.recruit_btn);
                        ca4=0;
                        filter_by_category();
                        }
                    else if(ca4==0){
                        recruit_img.setImageResource(R.drawable.recruit_btn_on);

                        ca4=1;
                        filter_by_category();

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

                    }

                    else if(ca5==1){
                        agora_img.setImageResource(R.drawable.agora_btn);
                        ca5=0;
                        filter_by_category();
                        }
                    else if(ca5==0){
                        agora_img.setImageResource(R.drawable.agora_btn_on);
                        ca5=1;
                        filter_by_category();

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


        public PostDatabasePhp(ArrayList<PostDatabase> post_list, yj_activity context) {
            super();
            this.post_list = post_list;
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
                    Log.v("jodfsfd", jo.getString("title"));
                    post_list.add(new PostDatabase(
                            jo.getString("title"), jo.getInt("id"), jo.getString("kakao_id"), jo.getString("category"), jo.getString("group"),
                            jo.getString("content"), jo.getString("posting_date"), jo.getString("image_link"), jo.getString("start_date"), jo.getString("end_date"), jo.getString("has_pic")
                    ));
                }
                //필터 카테고리 호출, 넣어준다.
                //filter_by_category();                         나중에 필터할때 손봐주기
                adapter = new PostAdapter(yj_activity.this, post_list);
                adapter2= new Post2Adapter(yj_activity.this,post_list );
                Log.v("연결 시도", "연결되어라@*********************************************");

                board_listview.setAdapter(adapter);
                board_listview.setOnScrollListener(context);
                board_listview.setOnItemClickListener(context);

                timeline_listview.setAdapter(adapter2);
                timeline_listview.setOnScrollListener(context);
                timeline_listview.setOnItemClickListener(context);

            } catch (JSONException e) {
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

        adapter = new PostAdapter(yj_activity.this, board_list);
        adapter2= new Post2Adapter(yj_activity.this, timeline_list );
        Log.v("연결 시도", "연결되어라@*********************************************");

        board_listview.setAdapter(adapter);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub

        Intent intent = new Intent(yj_activity.this, PostDetail.class);
        intent.putExtra("carrier", carrier);
        intent.putExtra("post", board_list.get(position));
        startActivityForResult(intent, 0);
        overridePendingTransition(0,0);
    }


}

