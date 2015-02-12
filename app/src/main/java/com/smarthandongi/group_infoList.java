package com.smarthandongi;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smarthandongi.adapter.GroupListAdapter;
import com.smarthandongi.database.Picture;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015-02-06.
 */
public class group_infoList extends Activity {
    GroupinfoAdapter groupinfoAdapter;
    ListView group_list_view;

    ArrayList<GroupDatabase1> group_list = new ArrayList<GroupDatabase1>();
    GroupPhp group_Php;
    Carrier carrier;
    GroupDatabase1 group;

    private ArrayList<GroupDatabase1> temp_list = new ArrayList<GroupDatabase1>();
    private ArrayList<GroupDatabase1> filtered_list = new ArrayList<GroupDatabase1>();
    EditText group_search;
    String str=null;
    ImageView search_cancel_img,search_glass_img,search_please,unresistered_background,background_hidden;
    Button backward_btn,unresistered_btn,search_cancel_btn, register_group;
    TextView unresistered;
    RelativeLayout layoutView,unresistered_screen;

    public void construction(){
        phpCreate();

    }

    public void phpCreate(){
        group_Php = new GroupPhp(group_list,temp_list, this);
        group_Php.execute("http://hungry.portfolio1000.com/smarthandongi/group_info.php?");
    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_lifo_list);
        group_list_view = (ListView)findViewById(R.id.group_list);
        carrier = (Carrier)getIntent().getSerializableExtra("carrier");
        carrier.getRegid();
        construction();
        //group_list_view.setOnItemClickListener(mItemClickListener);

        backward_btn=(Button)findViewById(R.id.back_btn);// 뒤로가기
        backward_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(group_infoList.this, yj_activity.class).putExtra("carrier",carrier);
                startActivity(intent);
            }
        });

        register_group = (Button)findViewById(R.id.writing_confirm_btn);

        register_group.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Uri uri = Uri.parse("http://me2.do/FY0wtxRF");

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        //search bar
        search_cancel_img=(ImageView)findViewById(R.id.search_cancel_img);
        search_glass_img=(ImageView)findViewById(R.id.search_glass_img);
        search_please=(ImageView)findViewById(R.id.search_please);




        unresistered_background=(ImageView)findViewById(R.id.unresistered_background);
        background_hidden=(ImageView)findViewById(R.id.background_hidden);
        unresistered_screen=(RelativeLayout)findViewById(R.id.unresistered_screen);
        unresistered=(TextView)findViewById(R.id.unresistered);


        //search
        group_search = (EditText)findViewById(R.id.groupsearch);
        group_search.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                group_search.setCursorVisible(true);
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(group_search,0);
                search_please.setVisibility(View.INVISIBLE);
                return true;
            }
        });

        groupinfoAdapter = new GroupinfoAdapter(this, group_list, R.layout.group_lifo_list );

            layoutView = (RelativeLayout)findViewById(R.id.group_info_list);
            layoutView.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(group_search
                            .getWindowToken(), 0);
                    group_search.setCursorVisible(false);
                    search_please.setVisibility(View.INVISIBLE);
                    return true;
                }
            });



        groupinfoAdapter= new GroupinfoAdapter(this, group_list,R.layout.group_lifo_list );
        group_list_view.setAdapter(groupinfoAdapter);
        group_list_view.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        group_list_view.setOnItemClickListener(groupListClickListener);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //group search start
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
                search_glass_img.setVisibility(View.INVISIBLE);
                search_cancel_img.setVisibility(View.VISIBLE);
                search_cancel_btn=(Button)findViewById(R.id.search_cancel_btn);
                search_cancel_btn.setVisibility(View.VISIBLE);
                search_cancel_btn.setOnClickListener(new Button.OnClickListener () {
                    public void onClick(View v) {
                        group_search.setText("");
                        search_cancel_img.setVisibility(View.INVISIBLE);
                        search_glass_img.setVisibility(View.VISIBLE);
                    }
                });

                str = group_search.getText().toString();
                filtered_list.removeAll(filtered_list);
                Log.d("test", Integer.toString(temp_list.size()));

                for (int i = 0; i < temp_list.size(); i++) {
                        if (temp_list.get(i).getNickname_list().contains(str)) {
                        filtered_list.add(new GroupDatabase1(temp_list.get(i).getGroup_id(), temp_list.get(i).getGroup_name(),temp_list.get(i).getNickname_list(),
                                temp_list.get(i).getGroup_category(),temp_list.get(i).getIntroduce()
                        ));

                        groupinfoAdapter= new GroupinfoAdapter(group_infoList.this, filtered_list, R.layout.group_lifo_list );
                        group_list_view.setAdapter(groupinfoAdapter);
                        Log.d("filtered_list", Integer.toString(filtered_list.size()));

                    }
                }



                if(str.length()>1&&filtered_list.size()==0) {
                    group_list_view.setVisibility(View.INVISIBLE);
                    background_hidden.setVisibility(View.GONE);
                    unresistered_background.setVisibility(View.VISIBLE);
                    unresistered_screen.setVisibility(View.VISIBLE);
                   // unresistered.setText("\""+str+"\"");

                }
                else
                {

                    group_list_view.setVisibility(View.VISIBLE);
                    unresistered_background.setVisibility(View.INVISIBLE);
                    unresistered_screen.setVisibility(View.INVISIBLE);
                    if(filtered_list.size()!=0) {
                        background_hidden.getLayoutParams().height=getTotalHeightOfListView(group_list_view);
                        background_hidden.setVisibility(View.VISIBLE);
                        Log.d("이거되는건가", "제발되람고");
                        unresistered_background.setVisibility(View.VISIBLE);
                        Log.d("이거되는건가","아마될거야아마아망마아마");

                    }

                }

            }
        };
        group_search.addTextChangedListener(watcher);

            unresistered_btn = (Button)findViewById(R.id.unresistered_btn);
            unresistered_btn.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    if(filtered_list.size()==0&&group_search.getText().toString().length()!=0) {
                        Log.d("선택된거",str);
                        group.setGroup_name(str);// carrier to group
                        Intent intent = new Intent(group_infoList.this,group_info.class).putExtra("group",group);
                        startActivity(intent);
                        finish();
                    }

                }
            });


        //group search end
    }

    AdapterView.OnItemClickListener groupListClickListener = new AdapterView.OnItemClickListener(){
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){

            /*Intent intent = new Intent(group_infoList.this,group_info.class );
            intent.putExtra("group_id",group_list.get(position).getGroup_id());
            intent.putExtra("group_name",group_list.get(position).getGroup_name());
            intent.putExtra("group_category",group_list.get(position).getGroup_category());
            intent.putExtra("introduce",group_list.get(position).getIntroduce());

            startActivity(intent);
            */
            //group search
            int pos = position;
            if(filtered_list.size()!=0) //검색했는데 검색결과가 리스트에 있을때
            {
                Log.d("선택된거", filtered_list.get(pos).getGroup_category());
                //  group.setGroup_code(filtered_list.get(pos).getGroup_code());

                Intent intent = new Intent(group_infoList.this,group_info.class );
                intent.putExtra("group_id",filtered_list.get(pos).getGroup_id());
                intent.putExtra("group_name",filtered_list.get(pos).getGroup_name());
                intent.putExtra("group_category",filtered_list.get(pos).getGroup_category());
                intent.putExtra("introduce",filtered_list.get(pos).getIntroduce());

                startActivity(intent);
                finish();
            }
            else {
                Log.d("선택된거", group_list.get(pos).getGroup_category());
                Log.d("index", String.valueOf(pos));
                //group.setGroup_code(group_list.get(pos).getGroup_code());
                Intent intent = new Intent(group_infoList.this,group_info.class );
                intent.putExtra("group_id",group_list.get(pos).getGroup_id());
                intent.putExtra("group_name",group_list.get(pos).getGroup_name());
                intent.putExtra("group_category",group_list.get(pos).getGroup_category());
                intent.putExtra("introduce",group_list.get(pos).getIntroduce());

                startActivity(intent);
                finish();
            }
            //group search
        }
    };
    public int getTotalHeightOfListView(ListView listview) { ///////리스트뷰의 높이를 구하기 위한 함수
        ListAdapter mAdapter = listview.getAdapter();
        int totalHeight=0;

        for(int i=0; i<mAdapter.getCount();i++) {
            View mView=mAdapter.getView(i,null,listview);
            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += mView.getMeasuredHeight();

        }
        return totalHeight;
    }





    public class GroupPhp extends AsyncTask<String, Integer, String> {

        private ArrayList<GroupDatabase1> group_list, temp_list;
        private group_infoList context;

        public GroupPhp(ArrayList<GroupDatabase1> group_list,ArrayList<GroupDatabase1> temp_list, group_infoList context) {
            super();
            this.group_list = group_list;
            this.context = context;
            this.temp_list= temp_list;
        }

        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            String return_str="";

            try{
                URL data_url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)data_url.openConnection();
                if(conn != null){
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
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return_str = jsonHtml.toString();
            Log.d("리턴 한 값",jsonHtml.toString());

            return jsonHtml.toString();
        }
        protected void onPostExecute(String str){
            try{
                JSONObject root= new JSONObject(str);
                JSONArray ja = root.getJSONArray("results");

                Log.d("되나보자","여긴되나?");
                for(int i=0; i<ja.length();i++) {
                    JSONObject jo = ja.getJSONObject(i);

                    group_list.add(new GroupDatabase1(jo.getInt("group_id"), jo.getString("group_name"),jo.getString("nickname"), jo.getString("group_category"), jo.getString("introduce")));
                    temp_list.add(new GroupDatabase1(jo.getInt("group_id"), jo.getString("group_name"),jo.getString("nickname"), jo.getString("group_category"), jo.getString("introduce")));

                }

                //groupinfoAdapter= new GroupinfoAdapter(group_infoList.this, group_list,R.layout.group_lifo_list );
                group_list_view.setAdapter(groupinfoAdapter);
                //group_list_view.setOnScrollListener(context);
                //group_list_view.setOnItemClickListener(context);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
