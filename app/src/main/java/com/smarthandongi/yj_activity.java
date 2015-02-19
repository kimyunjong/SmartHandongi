package com.smarthandongi;

import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.APIErrorResult;
import com.kakao.LogoutResponseCallback;
import com.kakao.UserManagement;
import com.smarthandongi.adapter.Post2Adapter;
import com.smarthandongi.adapter.PostAdapter;
import com.smarthandongi.database.PostDatabase;
import com.smarthandongi.kakao_api.KakaoTalkLoginActivity;

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


/**
 * Created by Joel on 2015-01-21.
 */
public class yj_activity extends Activity implements View.OnTouchListener,AbsListView.OnScrollListener,View.OnClickListener {
    Button notice_btn, outer_btn, seminar_btn, recruit_btn, agora_btn, board_btn, timeline_btn, search_btn, menu_btn,write_btn,write_btn_in_menu,scrap_menu,my_posting_btn,push_set_btn, show_group_btn,bustime_table_btn,report_btn, history_btn,login_menu_btn, logout_btn;
    ImageView notice_img, outer_img, seminar_img, recruit_img, agora_img, board_img, timeline_img, search_img, menu_img,write_img,write_btn_in_menu_img,scrap_menu_img,my_posting_btn_img,push_set_btn_img, show_group_btn_img,bustime_table_btn_img,report_btn_img, history_btn_img,login_menu_low_img, logout_btn_img,login_menu_img;
    ImageButton search_default_btn;
    RelativeLayout menu,search_layout, default_layout;
    TextView login_menu_txt;
    EditText post_search;
    Carrier carrier;
    Dialog dialog_logout;
    Button dialog_logout_okay, dialog_logout_no;
    LinearLayout dialog_logout_background;
    private Intent intent;
    int ca1=1,ca2=1,ca3=1, ca4=1, ca5=1;//켜진상태
    boolean board_on=true,timeline_on=false, search_on=false,default_on=true;

    String regid=null;
    private ArrayList<PostDatabase> post_list = new ArrayList<PostDatabase>(),board_list = new ArrayList<PostDatabase>(),
            timeline_list = new ArrayList<PostDatabase>(), timeline_list_today = new ArrayList<PostDatabase>(),
            timeline_list_tomorrow = new ArrayList<PostDatabase>(),timeline_list_after_tomorrow = new ArrayList<PostDatabase>(),
            liked_list = new ArrayList<PostDatabase>(),temp_plist = new ArrayList<PostDatabase>(),
            temp_list = new ArrayList<PostDatabase>(), filtered_list = new ArrayList<PostDatabase>();
    private ListView board_listview,timeline_listview;
    private RelativeLayout timeline_listviewR;
    private PostAdapter postAdapter;
    private Post2Adapter post2Adapter;
    private AbsListView view;
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private PostDatabasePhp postDatabasePhp;
    private PostAdapter adapter;
    private Post2Adapter adapter2;
    String myResult,str;
    Typeface typeface;
    final Context context = this;

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
        postDatabasePhp = new PostDatabasePhp(post_list,temp_list, this);
        postDatabasePhp.execute("http://hungry.portfolio1000.com/smarthandongi/posting_php.php?kakao_id="+carrier.getId());
        Log.v("연결 시도", "연결되어라$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        carrier = (Carrier) intent.getSerializableExtra("carrier");
        setContentView(R.layout.dashboard);

        typeface = Typeface.createFromAsset(getAssets(), "KOPUBDOTUM_PRO_LIGHT.OTF");
        Log.v("연결 시도", "연결되어라$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        construction();
        Log.v("연결 시도", "연결되어라@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@&&");

        login_menu_low_img=(ImageView)findViewById(R.id.login_img);
        login_menu_btn=(Button)findViewById(R.id.login_btn);
        login_menu_img=(ImageView)findViewById(R.id.login_img_info);
        login_menu_txt=(TextView)findViewById(R.id.login_txt_info);
        menu = (RelativeLayout) findViewById(R.id.menu);
        search_default_btn=(ImageButton)findViewById(R.id.delete_search);
        notice_btn = (Button) findViewById(R.id.notice_btn);
        outer_btn = (Button) findViewById(R.id.outer_btn);
        seminar_btn = (Button) findViewById(R.id.seminar_btn);
        recruit_btn = (Button) findViewById(R.id.recruit_btn);
        agora_btn = (Button) findViewById(R.id.agora_btn);
        board_btn = (Button) findViewById(R.id.board_btn);
        timeline_btn = (Button) findViewById(R.id.timeline_btn);
        search_btn = (Button) findViewById(R.id.search_btn);
        menu_btn = (Button) findViewById(R.id.menu_btn);
        write_btn = (Button) findViewById(R.id.write_btn);
        write_btn_in_menu=(Button)findViewById(R.id.write_btn_in_menu);
        scrap_menu=(Button)findViewById(R.id.scrap_btn);
        my_posting_btn=(Button)findViewById(R.id.my_posting_btn);
        push_set_btn=(Button)findViewById(R.id.push_set_btn);
        show_group_btn=(Button)findViewById(R.id.show_group);
        bustime_table_btn=(Button)findViewById(R.id.bustime_table_btn);
        report_btn=(Button)findViewById(R.id.error_report_btn);
         history_btn=(Button)findViewById(R.id.ourstory_btn);

        logout_btn=(Button)findViewById(R.id.logout_btn);
        write_btn=(Button)findViewById(R.id.write_btn);

        search_layout=(RelativeLayout)findViewById(R.id.search_bar_layout);
        default_layout=(RelativeLayout)findViewById(R.id.default_layout);


        notice_img = (ImageView) findViewById(R.id.notice_img);
        outer_img = (ImageView) findViewById(R.id.outer_img);
        seminar_img = (ImageView) findViewById(R.id.seminar_img);
        recruit_img = (ImageView) findViewById(R.id.recruit_img);
        agora_img = (ImageView) findViewById(R.id.agora_img);
        board_img = (ImageView) findViewById(R.id.board_img);
        timeline_img = (ImageView) findViewById(R.id.timeline_img);
        search_img = (ImageView) findViewById(R.id.search_img);
        menu_img = (ImageView) findViewById(R.id.menu_img);
        write_btn_in_menu_img=(ImageView)findViewById(R.id.write_btn_in_menu_img);
        scrap_menu_img=(ImageView)findViewById(R.id.scrap_img);
        my_posting_btn_img=(ImageView)findViewById(R.id.my_posting_img);
        push_set_btn_img=(ImageView)findViewById(R.id.push_set_img);
        show_group_btn_img=(ImageView)findViewById(R.id.show_group_img);
        bustime_table_btn_img=(ImageView)findViewById(R.id.bustime_table_img);
        report_btn_img=(ImageView)findViewById(R.id.error_report_img);
        history_btn_img=(ImageView)findViewById(R.id.ourstory_img);

        logout_btn_img=(ImageView)findViewById(R.id.logout_img);
        write_img=(ImageView)findViewById(R.id.write_btn_img);

        search_default_btn.setOnClickListener(this);
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
        write_btn_in_menu.setOnTouchListener(this);
        scrap_menu.setOnTouchListener(this);
        my_posting_btn.setOnTouchListener(this);
        push_set_btn.setOnTouchListener(this);
         show_group_btn.setOnTouchListener(this);
        bustime_table_btn.setOnTouchListener(this);
        report_btn.setOnTouchListener(this);
        history_btn.setOnTouchListener(this);
        login_menu_btn.setOnTouchListener(this);
        logout_btn.setOnTouchListener(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        post_search=(EditText)findViewById(R.id.post_search);
        post_search.setOnTouchListener(this);
        post_search.setTypeface(typeface);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charsequence, int i, int j, int k) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence charsequence, int i, int j,  int k) {
                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable editable) {
                // TODO Auto-generated method stub
                str = post_search.getText().toString();
                filtered_list.removeAll(filtered_list);
                Log.d("test", Integer.toString(filtered_list.size()));

                for (int i = 0; i < temp_list.size(); i++) {
                    if (temp_list.get(i).getTitle().contains(str)) {
                        filtered_list.add(new PostDatabase(
                                        temp_list.get(i).getTitle(),temp_list.get(i).getId(),temp_list.get(i).getKakao_id(),
                                        temp_list.get(i).getBig_category(), temp_list.get(i).getCategory(),temp_list.get(i).getGroup(),
                                        temp_list.get(i).getContent(),temp_list.get(i).getPosting_date(),temp_list.get(i).getLink(),
                                        temp_list.get(i).getStart_date(),temp_list.get(i).getEnd_date(), temp_list.get(i).getHas_pic()
                                        , temp_list.get(i).getLike(), temp_list.get(i).getView_num(),temp_list.get(i).getGroup_name(),
                                        temp_list.get(i).getKakao_nic(), temp_list.get(i).getPush(), temp_list.get(i).getRegid()));
                        adapter = new PostAdapter(yj_activity.this, filtered_list,carrier);
                        adapter2= new Post2Adapter(yj_activity.this, filtered_list );


                        board_listview.setAdapter(adapter);
                        board_listview.setOnScrollListener(yj_activity.this);
                        board_listview.setOnItemClickListener(boardItemClickListener);

                        timeline_listview.setAdapter(adapter2);
                        timeline_listview.setOnScrollListener(yj_activity.this);
                        timeline_listview.setOnItemClickListener(timelineItemClickListener);

                    }
                    Log.d("test", Integer.toString(filtered_list.size()));
                }
            }
        };
        post_search.addTextChangedListener(watcher);

        timeline_listviewR.setVisibility(View.GONE);

        //로그인 토글하기
        login_toggle();

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

        //수영 추가
       /* if(carrier.isBy_GCM()){
            Log.d("yj_activity","푸시로 들어왔어요");
            Log.d("yj_posting_id",String.valueOf(carrier.getPost_id()));
            int position=1 ;
            Log.d("board_list.get",String.valueOf(board_list.get(position).getId()));*/
            /*while(board_list.get(position).getId()==carrier.getPost_id())
            {
                position++;
                Log.d("push_position1",String.valueOf(position));
            }*/
            //Log.d("push_position2",String.valueOf(position));
            /*
            ViewNumPhp viewNumPhp = new ViewNumPhp(board_list.get(position));
            Log.d("글번호잘못??",String.valueOf(board_list.get(position).getId()));
            Log.d("글의 포지션",String.valueOf(position));
            viewNumPhp.execute("http://hungry.portfolio1000.com/smarthandongi/view_num.php?posting_id="+board_list.get(position).getId());
            Intent intent = new Intent(yj_activity.this, PostDetail.class);
            intent.putExtra("carrier", carrier);
            intent.putExtra("post_list",board_list);
            intent.putExtra("position",position);
            intent.putExtra("post", board_list.get(position));
            Log.d("니가 나중에되야해니가나중에되야해","으어어우엉오으우엉");

            startActivityForResult(intent, 0);
            overridePendingTransition(0,0);
            */
            //수영 추가 끝
       // }

    }

    @Override
    public  void onClick(View v)
    {
       switch (v.getId())
       {
           case  R.id.delete_search:{
               adapter = new PostAdapter(yj_activity.this, board_list,carrier);
               adapter2= new Post2Adapter(yj_activity.this, timeline_list );
               Log.v("연결 시도", "연결되어라@*********************************************");

               board_listview.setAdapter(adapter);
               board_listview.setOnScrollListener(yj_activity.this);
               board_listview.setOnItemClickListener(boardItemClickListener);

               timeline_listview.setAdapter(adapter2);
               timeline_listview.setOnScrollListener(yj_activity.this);
               timeline_listview.setOnItemClickListener(timelineItemClickListener);
               default_on=true;
               search_on=false;
               search_toggle();

           }

       }

    }
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {


        switch (v.getId())
        {


            case R.id.notice_btn: {
                if (event.getAction() == 0) {
                    if(ca1==1&&ca2==1&&ca3==1&&ca4==1&&ca5==1){

                }
                else if(ca1==1)
                    {
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
                   } else if (event.getAction() == 1)
                   {
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

                } else if (event.getAction() == 1)
                {
                   if(search_on==true&&default_on==false)
                   {
                       search_on=false;
                       default_on=true;
                       search_toggle();

                   }
                   else
                   {   search_on=true;
                       default_on=false;
                       search_toggle();

                   }
                }
                break;
            }

            case R.id.menu_btn: {
                if (event.getAction() == 0)
                {
                    menu_img.setImageResource(R.drawable.menu_btn_on);
                }
                else if (event.getAction() == 1)
                {
                    menu_img.setImageResource(R.drawable.menu_btn);
                    menu_toggle();
                }
                break;
            }
            case R.id.write_btn: {
                if (event.getAction() == 0) {
                    write_img.setImageResource(R.drawable.write_btn_on);
                }
                else if (event.getAction() == 1) {
                    write_img.setImageResource(R.drawable.write_btn);
                    carrier.setEdit_count(0);
                    Intent intent = new Intent(yj_activity.this, SelectGroupOrNot.class);
                    intent.putExtra("carrier", carrier);
                    startActivityForResult(intent, 0);
                    overridePendingTransition(0,0);
                   finish();
                }
                break;
            }
            case R.id.write_btn_in_menu: {
                //돌아올곳
                if (event.getAction() == 0) {
                    write_btn_in_menu_img.setImageResource(R.drawable.write_btn_menu_on);
                } else if (event.getAction() == 1) {
                    write_btn_in_menu_img.setImageResource(R.drawable.write_btn_menu);
                    carrier.setEdit_count(0);
                    Intent intent = new Intent(yj_activity.this, SelectGroupOrNot.class);
                    intent.putExtra("carrier", carrier);

                    startActivityForResult(intent, 0);
                    overridePendingTransition(0,0);
                    finish();
                }
                break;
            }


            case R.id.my_posting_btn: {
                //돌아올곳
                if (event.getAction() == 0) {
                    my_posting_btn_img.setImageResource(R.drawable.my_post_on);
                } else if (event.getAction() == 1) {
                    my_posting_btn_img.setImageResource(R.drawable.my_post);
                    Intent intent = new Intent(yj_activity.this, SeeMyPost.class);
                    intent.putExtra("carrier", carrier);
                    intent.putExtra("post_list",post_list);
                    startActivityForResult(intent, 0);
                    overridePendingTransition(0,0);

                }
                break;
            }

            case R.id.scrap_btn: {
                //돌아올곳
                if (event.getAction() == 0) {
                    scrap_menu_img.setImageResource(R.drawable.scrap_menu_on);
                } else if (event.getAction() == 1) {
                    scrap_menu_img.setImageResource(R.drawable.scrap_menu);
                    Intent intent = new Intent(yj_activity.this, My_scrap.class);
                    intent.putExtra("carrier", carrier);
                    startActivityForResult(intent, 0);
                    overridePendingTransition(0,0);
                    finish();
                }
                break;
            }

            case R.id.push_set_btn: {
                //돌아올곳
                if (event.getAction() == 0) {
                    push_set_btn_img.setImageResource(R.drawable.alarm_menu_on);
                } else if (event.getAction() == 1) {
                    push_set_btn_img.setImageResource(R.drawable.alarm_menu);
                    Intent intent = new Intent(yj_activity.this, PushSetup.class);
                    intent.putExtra("carrier", carrier);

                    startActivityForResult(intent, 0);
                    overridePendingTransition(0,0);

                }
                break;
            }

            case R.id.show_group: {
                //돌아올곳
                if (event.getAction() == 0) {

                    show_group_btn_img.setImageResource(R.drawable.group_menu_on);
                } else if (event.getAction() == 1) {
                    show_group_btn_img.setImageResource(R.drawable.group_menu);
                    Intent intent = new Intent(yj_activity.this, group_infoList.class);
                    intent.putExtra("carrier", carrier);



                    startActivity(intent);
                    finish();

                    overridePendingTransition(0,0);

                }
                break;
            }
            case R.id.bustime_table_btn: {
                //돌아올곳
                if (event.getAction() == 0) {
                    bustime_table_btn_img.setImageResource(R.drawable.bustime_table_on);
                } else if (event.getAction() == 1) {
                    bustime_table_btn_img.setImageResource(R.drawable.bustime_table_menu);
                    Intent intent = new Intent(yj_activity.this, Bus_Schedule.class);
                    intent.putExtra("carrier", carrier);

                    startActivityForResult(intent, 0);
                    overridePendingTransition(0,0);

                }
                break;
            }
            case R.id.error_report_btn: {
                //돌아올곳
                if (event.getAction() == 0) {
                    report_btn_img.setImageResource(R.drawable.error_report_on);
                } else if (event.getAction() == 1) {
                    report_btn_img.setImageResource(R.drawable.error_report);
                    Intent intent = new Intent(yj_activity.this, BugReport.class);
                    intent.putExtra("carrier", carrier);

                    startActivityForResult(intent, 0);
                    overridePendingTransition(0,0);

                }
                break;
            }
            case R.id.ourstory_btn: {
                //돌아올곳
                if (event.getAction() == 0) {
                    history_btn_img.setImageResource(R.drawable.our_history_on);
                } else if (event.getAction() == 1) {
                    history_btn_img.setImageResource(R.drawable.our_history);
//                   Intent intent = new Intent(yj_activity.this, SeeMyPost.class);
//                    intent.putExtra("carrier", carrier);
//                    intent.putExtra("post_list",post_list);
//                    startActivityForResult(intent, 0);
//                    overridePendingTransition(0,0);

                }
                break;
            }
            case R.id.logout_btn: {
                //돌아올곳
                if (event.getAction() == 0) {
                    logout_btn_img.setImageResource(R.drawable.logout_menu_on);
                }
                else if (event.getAction() == 1) {
                    logout_btn_img.setImageResource(R.drawable.logout_menu);

                    dialog_logout = new Dialog(context);
                    dialog_logout.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog_logout.setContentView(R.layout.dialog_cancel);
                    dialog_logout.show();

                    dialog_logout_background = (LinearLayout)dialog_logout.findViewById(R.id.dialog_writing_background);
                    dialog_logout_background.setBackgroundResource(R.drawable.dialog_logout);
                    dialog_logout_okay = (Button)dialog_logout.findViewById(R.id.dialog_writing_confirm);
                    dialog_logout_okay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_logout.dismiss();
                            if (carrier.isLogged_in()) {

                                UserManagement.requestLogout(new LogoutResponseCallback() {
                                    @Override
                                    protected void onSuccess(long l) {
                                        carrier.setLogged_in(false);
                                        carrier.setNickname("not_logged_in");
                                        carrier.setId("000000");
                                        // 수영추가
                                        // carrier.setIsLogout_regid(2);
                                        //RegIDDeleteTask regIDDeleteTask = new RegIDDeleteTask();
                                        //regIDDeleteTask.execute(regid);
                                        // 수영 추가 끝
                                        Intent intent = new Intent(yj_activity.this, KakaoTalkLoginActivity.class).putExtra("carrier", carrier);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    protected void onFailure(APIErrorResult apiErrorResult) {

                                    }
                                });
                                login_toggle();
                            }
                        }
                    });

                    dialog_logout_no = (Button)dialog_logout.findViewById(R.id.dialog_writing_cancel);
                    dialog_logout_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_logout.dismiss();
                        }
                    });
                }
                break;
            }
            case R.id.login_btn: {
                //돌아올곳
                if (event.getAction() == 0) {
                    login_menu_low_img.setImageResource(R.drawable.login_img_on);
                } else if (event.getAction() == 1) {
                    login_menu_low_img.setImageResource(R.drawable.login_img);
                        System.out.println("들어 오나? ");
                        Intent intent = new Intent(yj_activity.this, KakaoTalkLoginActivity.class).putExtra("carrier", carrier);
                        startActivity(intent);
                    login_toggle();
                        finish();
                    }


                break;
            }

            case R.id.post_search : {
                post_search.setCursorVisible(true);
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(post_search,0);
                break;
            }



            }

       return false;
    }


    public void menu_toggle()
    {
        if (menu_on) {
            menu.setVisibility(View.GONE);
            menu_img.setImageResource(R.drawable.menu_btn);
            menu_on = false;
        } else {
            menu.setVisibility(View.VISIBLE);
            menu_img.setImageResource(R.drawable.menu_btn_on);
            menu_on = true;
        }
    }

    public void search_toggle()
    {
        if(search_on) {
            search_layout.setVisibility(View.VISIBLE);
            default_layout.setVisibility(View.GONE);

        }else{
            search_layout.setVisibility(View.GONE);
            default_layout.setVisibility(View.VISIBLE);
        }
    }

    public void login_toggle()
    {
        if(carrier.isLogged_in()) {
            login_menu_txt.setText(carrier.getNickname());
            login_menu_txt.setTypeface(typeface);
            login_menu_img.setImageResource(R.drawable.login_bg_img);
            login_menu_btn.setVisibility(View.GONE);
            login_menu_low_img.setVisibility(View.GONE);
            logout_btn.setVisibility(View.VISIBLE);
            logout_btn_img.setVisibility(View.VISIBLE);

        }else{
            login_menu_txt.setText("");
            login_menu_img.setImageResource(R.drawable.logout_bg_img);
            login_menu_btn.setVisibility(View.VISIBLE);
            login_menu_low_img.setVisibility(View.VISIBLE);
            logout_btn.setVisibility(View.GONE);
            logout_btn_img.setVisibility(View.GONE);
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

    public void onBackPressed()
    {

        if (menu_on) {
            menu_toggle();
        } else {

            if (thread_running == false)
            {
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

    public class PostDatabasePhp extends AsyncTask<String, integer, String>
    {

        private yj_activity context;
        private ArrayList<PostDatabase> post_list,temp_list;


        public PostDatabasePhp(ArrayList<PostDatabase> post_list,ArrayList<PostDatabase> temp_list, yj_activity context) {
            super();
            this.post_list = post_list;
            this.context = context;
            this.temp_list=temp_list;
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
                temp_list.removeAll(temp_list);

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);

                    post_list.add(new PostDatabase(
                            jo.getString("title"), jo.getInt("id"), jo.getString("kakao_id"), jo.getString("big_category"), jo.getString("category"), jo.getString("group"),
                            jo.getString("content"), jo.getString("posting_date"), jo.getString("link"), jo.getString("start_date"), jo.getString("end_date"), jo.getString("has_pic"),
                            jo.getString("like"), jo.getInt("view"),jo.getString("group_name"),jo.getString("kakao_nick"),jo.getInt("push"),jo.getString("regid"))
                    );
                    temp_list.add(new PostDatabase(
                                    jo.getString("title"), jo.getInt("id"), jo.getString("kakao_id"), jo.getString("big_category"), jo.getString("category"), jo.getString("group"),
                                    jo.getString("content"), jo.getString("posting_date"), jo.getString("link"), jo.getString("start_date"), jo.getString("end_date"), jo.getString("has_pic"),
                                    jo.getString("like"), jo.getInt("view"),jo.getString("group_name"),jo.getString("kakao_nick"),jo.getInt("push"),jo.getString("regid"))
                    );

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

            if (db.getBig_category().equalsIgnoreCase("1")&&ca1==0) continue;
            if (db.getBig_category().equalsIgnoreCase("2")&&ca2==0) continue;
            if (db.getBig_category().equalsIgnoreCase("3")&&ca3==0) continue;
            if (db.getBig_category().equalsIgnoreCase("4")&&ca4==0) continue;
            if (db.getBig_category().equalsIgnoreCase("5")&&ca5==0) continue;

            board_list.add(db);
            timeline_list.add(db);


        }
        if(carrier.isBy_GCM()) {
            Log.d("yj_activity", "푸시로 들어왔어요");
            Log.d("yj_posting_id", String.valueOf(carrier.getPost_id()));
            int position=0 ;
            //Log.d("board_list.get", String.valueOf(board_list.get(position).getId()));
            Log.d("board_list1",String.valueOf(board_list.get(position).getId()));
            Log.d("getPost_id1",String.valueOf(carrier.getPost_id()));
            for(position=0;board_list.get(position).getId()==carrier.getPost_id();position++){

                Log.d("board_list2",String.valueOf(board_list.get(position).getId()));
                Log.d("getPost_id2",String.valueOf(carrier.getPost_id()));

            }
            /*do{
                Log.d("board_list",String.valueOf(board_list.get(position).getId()));
                Log.d("getPost_id",String.valueOf(carrier.getPost_id()));
                position++;

            }while(board_list.get(position).getId()==carrier.getPost_id());
             */
            Log.d("push_position1",String.valueOf(position));

            ViewNumPhp viewNumPhp = new ViewNumPhp(board_list.get(position));
            viewNumPhp.execute("http://hungry.portfolio1000.com/smarthandongi/view_num.php?posting_id="+carrier.getPost_id());
            Intent intent = new Intent(yj_activity.this, PostDetail.class);
            intent.putExtra("carrier", carrier);
            intent.putExtra("post_list",board_list);
            intent.putExtra("position",position);
            intent.putExtra("post", board_list.get(position));


            startActivityForResult(intent, 0);
            overridePendingTransition(0,0);
            finish();
        }
            //수영 추가 끝
        adapter = new PostAdapter(yj_activity.this, board_list,carrier);
        board_listview.setAdapter(adapter);

    }
    public void filter_by_date()
    {
            int last_day=0;


        //temp 비우고 여기에 값 넣어준다.

        filter_by_category();




       //분류된 (카테고리별로) 타임라인 리스트에 디데이를 넣어준다
        for(PostDatabase db:timeline_list)
        {
           if(!db.getEnd_date().equalsIgnoreCase("0")&&!db.getStart_date().equalsIgnoreCase("0"))
           {
               String temp_date = db.getStart_date();
               dday deadline = new dday();

               int s_date = Integer.parseInt(temp_date);
               int s_year;
               int s_month;
               int s_day;
               s_year = s_date / 10000;
               s_date = s_date - (s_year * 10000);
               s_month = s_date / 100;
               s_date = s_date - s_month * 100;
               s_day = s_date;

               String temp_date_e = db.getEnd_date();
               int e_date = Integer.parseInt(temp_date_e);

               int e_year = e_date / 10000;
               e_date = e_date - (s_year * 10000);
               int e_month = e_date / 100;
               e_date = e_date - e_month * 100;
               int e_day = e_date;

               int dday_s = deadline.caldate(s_year, s_month - 1, s_day + 1);
               int dday_e = deadline.caldate(e_year, e_month - 1, e_day + 1);

               if (dday_s < 0) //아직기간이 남았으면
               {
                   db.setDday(dday_s);

                   if (last_day > db.getDday()) {
                       last_day = db.getDday();
                   }
               } else if (dday_s >= 0 && dday_e <= 0) {
                   db.setDday(0);
               } else {
                   db.setDday(9999); //기간이 이미 종료 됬으면 빼 준다.
               }
           }
           else if(!db.getStart_date().equalsIgnoreCase("0")&&db.getEnd_date().equalsIgnoreCase("0")) //시작일만 있는 경우
           {
               String temp_date = db.getStart_date();
               dday deadline = new dday();

               int s_date = Integer.parseInt(temp_date);
               int s_year;
               int s_month;
               int s_day;
               s_year = s_date / 10000;
               s_date = s_date - (s_year * 10000);
               s_month = s_date / 100;
               s_date = s_date - s_month * 100;
               s_day = s_date;

               int dday_s = deadline.caldate(s_year, s_month - 1, s_day + 1);
               int dday_e = deadline.caldate(s_year, s_month - 1, s_day + 1);

               if (dday_s < 0) //아직기간이 남았으면
               {
                   db.setDday(dday_s);

                   if (last_day > db.getDday()) {
                       last_day = db.getDday();
                   }
               } else if (dday_s >= 0 && dday_e <= 0) {
                   db.setDday(0);
               } else {
                   db.setDday(9999); //기간이 이미 종료 됬으면 빼 준다.
               }
           }
            else if(db.getStart_date().equalsIgnoreCase("0")&&db.getEnd_date().equalsIgnoreCase("0"))//start없고 end 없는 경우
           {
               db.setDday(99999); //타임라인에는 보이지 않지만 대쉬보드에는 보인다 , 그리고 그것은 회색이 아니다.
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
              db.setLast_day_T();
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
            ViewNumPhp viewNumPhp = new ViewNumPhp(board_list.get(position));
            Log.d("글번호잘못??",String.valueOf(board_list.get(position).getId()));
            Log.d("글의 포지션",String.valueOf(position));
            viewNumPhp.execute("http://hungry.portfolio1000.com/smarthandongi/view_num.php?posting_id="+board_list.get(position).getId());
            Intent intent = new Intent(yj_activity.this, PostDetail.class);
            intent.putExtra("carrier", carrier);
            intent.putExtra("post_list",board_list);
            intent.putExtra("position",position);
            intent.putExtra("post", board_list.get(position));
            Log.d("니가 나중에되야해니가나중에되야해","으어어우엉오으우엉");

            startActivityForResult(intent, 0);
            overridePendingTransition(0,0);

        }
    };

    AdapterView.OnItemClickListener timelineItemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            ViewNumPhp viewNumPhp = new ViewNumPhp(timeline_list.get(position));
            Log.d("글번호잘못??",String.valueOf(timeline_list.get(position).getId()));
            Log.d("글의 포지션",String.valueOf(position));
            viewNumPhp.execute("http://hungry.portfolio1000.com/smarthandongi/view_num.php?posting_id="+timeline_list.get(position).getId());
            Intent intent = new Intent(yj_activity.this, PostDetail.class);
            intent.putExtra("carrier", carrier);
            intent.putExtra("post_list",timeline_list);

            intent.putExtra("position",position);
            intent.putExtra("post", timeline_list.get(position));
            Log.d("니가 나중에되야해니가나중에되야해","으어어우엉오으우엉");
            startActivityForResult(intent, 0);
            overridePendingTransition(0,0);

        }
    };

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == RESULT_OK) {
            PostDatabase db = (PostDatabase)data.getSerializableExtra("post");
            Carrier carrier=(Carrier)data.getSerializableExtra("carrier");
            System.out.println("확인합니다 확인이요"+carrier.getPost_position_num()+"해당것의 스크랩여부"+db.getLike());
             board_list.get(carrier.getPost_position_num()).setLike(db.getLike());

             adapter.notifyDataSetChanged();

        }
    }

}

