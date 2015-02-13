package com.smarthandongi;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smarthandongi.adapter.Post2Adapter;
import com.smarthandongi.adapter.PostAdapter;
import com.smarthandongi.adapter.postAdapterScrap;
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
 * Created by Joel on 2015-02-11.
 */
public class My_scrap extends Activity implements  View.OnTouchListener,AbsListView.OnScrollListener{



    Carrier carrier;
    private ArrayList<PostDatabase> post_list = new ArrayList<PostDatabase>(), scraped_list = new ArrayList<PostDatabase>();
    private Intent intent;
    private  ListView scraped_listview;
    private postAdapterScrap postAdapter;
    private AbsListView view;
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private PostDatabasePhp postDatabasePhp;
    int countScrap=0;
    TextView scrapNum;
    Button go_back;


    public void construction() {
        scraped_listview = (ListView) findViewById(R.id.scrap_list);

        phpCreate();
    }

    public void phpCreate() {
        postDatabasePhp = new PostDatabasePhp(post_list, this);
        postDatabasePhp.execute("http://hungry.portfolio1000.com/smarthandongi/posting_php.php?kakao_id="+carrier.getId());
        Log.v("연결 시도", "연결되어라$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        carrier = (Carrier) intent.getSerializableExtra("carrier");
        setContentView(R.layout.my_scrap);
        scrapNum=(TextView)findViewById(R.id.scrap_num);

        Log.v("연결 시도", "연결되어라$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        construction();
        Log.v("연결 시도", "연결되어라@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@&&");

        go_back=(Button)findViewById(R.id.push_back_btn);
        go_back.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        switch (v.getId())
        {


            case R.id.push_back_btn:
            {
                if (event.getAction() == 0)
                {
                }
                else if (event.getAction() == 1)
                {
                    Intent intent = new Intent(My_scrap.this, yj_activity.class);
                    intent.putExtra("carrier", carrier);

                    startActivityForResult(intent, 0);
                    overridePendingTransition(0,0);
                    finish();

                }


                break;
            }

        }

        return false;

    }

    public void onBackPressed()
    {
        Intent intent = new Intent(My_scrap.this, yj_activity.class);
        intent.putExtra("carrier", carrier);

        startActivityForResult(intent, 0);
        overridePendingTransition(0,0);
        finish();

    }

    public class PostDatabasePhp extends AsyncTask<String, android.R.integer, String>
    {

        private My_scrap context;
        private ArrayList<PostDatabase> post_list;


        public PostDatabasePhp(ArrayList<PostDatabase> post_list, My_scrap context)
        {
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


        protected void onPostExecute(String str)
        {
            try {
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("results");

                post_list.removeAll(post_list);

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);

                    post_list.add(new PostDatabase(
                                    jo.getString("title"), jo.getInt("id"), jo.getString("kakao_id"), jo.getString("big_category"), jo.getString("category"), jo.getString("group"),
                                    jo.getString("content"), jo.getString("posting_date"), jo.getString("link"), jo.getString("start_date"), jo.getString("end_date"), jo.getString("has_pic"),
                                    jo.getString("like"), jo.getInt("view"),jo.getString("group_name"),jo.getString("kakao_nick"),jo.getInt("push"))
                    );

                }


                //필터 카테고리 호출, 넣어준다.
                //filter_by_category();                         나중에 필터할때 손봐주기
                filter_by_Like();
                postAdapter = new postAdapterScrap(My_scrap.this, scraped_list, carrier);
                Log.v("연결 시도", "연결되어라@*********************************************");

                scraped_listview.setAdapter(postAdapter);
                scraped_listview.setOnScrollListener(context);
                scraped_listview.setOnItemClickListener(boardItemClickListener);
              }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void filter_by_Like()
    {

       scraped_list.removeAll(scraped_list);
       for (PostDatabase db : post_list)
       {

            if (db.getLike().equalsIgnoreCase("0")) continue;

           scraped_list.add(db);
            countScrap++;

        }
        scrapNum.setText(String.valueOf(countScrap));
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
            ViewNumPhp viewNumPhp = new ViewNumPhp(scraped_list.get(position));
            Log.d("글번호잘못??",String.valueOf(scraped_list.get(position).getId()));
            Log.d("글의 포지션",String.valueOf(position));
            viewNumPhp.execute("http://hungry.portfolio1000.com/smarthandongi/view_num.php?posting_id="+scraped_list.get(position).getId());
            Intent intent = new Intent(My_scrap.this, PostDetail.class);
            intent.putExtra("carrier", carrier);
            intent.putExtra("post_list",scraped_list);
            intent.putExtra("position",position);
            intent.putExtra("post", scraped_list.get(position));
            Log.d("니가 나중에되야해니가나중에되야해","으어어우엉오으우엉");
            startActivityForResult(intent, 0);
            overridePendingTransition(0,0);
        }
    };
}
