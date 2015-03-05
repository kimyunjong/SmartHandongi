package com.smarthandongi;

import android.app.Activity;
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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smarthandongi.adapter.GroupListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
/**
 * Created by user on 2015-01-27.
 */
public class GroupSearch extends Activity {

    Carrier carrier;
    private ListView listview;
    private GroupListAdapter adapter;
    private ArrayList<GroupDatabase1> group_list = new ArrayList<GroupDatabase1>();
    private ArrayList<GroupDatabase1> temp_list = new ArrayList<GroupDatabase1>();
    private ArrayList<GroupDatabase1> filtered_list = new ArrayList<GroupDatabase1>();
    private GroupNewPhp group_php;
    EditText group_search;
    RelativeLayout layoutView,unresistered_screen;
    LinearLayout group_listitem;
    String str=null;
    Button backward_btn,unresistered_btn,search_cancel_btn;
    ImageView search_cancel_img,search_glass_img,search_please,unresistered_background,background_hidden;
    TextView unresistered, group_nomatch_1, group_nomatch_2, group_nomatch_3, group_nomatch_4;
    Typeface typeface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        carrier = (Carrier)getIntent().getSerializableExtra("carrier");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_search);
        typeface = Typeface.createFromAsset(getAssets(), "KOPUBDOTUM_PRO_MEDIUM.OTF");
        group_php = new GroupNewPhp(group_list,temp_list, this);
        group_php.execute("http://hungry.portfolio1000.com/smarthandongi/group_info.php");

        search_cancel_img=(ImageView)findViewById(R.id.search_cancel_img);
        search_glass_img=(ImageView)findViewById(R.id.search_glass_img);
        search_please=(ImageView)findViewById(R.id.search_please);
        unresistered_background=(ImageView)findViewById(R.id.unresistered_background);
        background_hidden=(ImageView)findViewById(R.id.background_hidden);

        unresistered_screen=(RelativeLayout)findViewById(R.id.unresistered_screen);

        unresistered=(TextView)findViewById(R.id.unresistered);
        group_nomatch_1 = (TextView)findViewById(R.id.group_nomatch_1);
        group_nomatch_2 = (TextView)findViewById(R.id.group_nomatch_2);
        group_nomatch_3 = (TextView)findViewById(R.id.group_nomatch_3);
        group_nomatch_4 = (TextView)findViewById(R.id.group_nomatch_4);

        search_cancel_btn=(Button)findViewById(R.id.search_cancel_btn);
        search_cancel_btn.setOnClickListener(new Button.OnClickListener () {
            public void onClick(View v) {
                group_search.setText("");
                search_cancel_img.setVisibility(View.INVISIBLE);
                search_glass_img.setVisibility(View.VISIBLE);
            }
        });


        unresistered.setTypeface(typeface);
        group_nomatch_1.setTypeface(typeface);
        group_nomatch_2.setTypeface(typeface);
        group_nomatch_3.setTypeface(typeface);
        group_nomatch_4.setTypeface(typeface);

        group_search = (EditText)findViewById(R.id.groupsearch);
        group_search.setTypeface(typeface);
        group_search.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(group_search,0);
                search_please.setVisibility(View.INVISIBLE);
                group_search.setCursorVisible(true);
                return true;
            }
        });

        listview = (ListView)findViewById(R.id.list);
        layoutView = (RelativeLayout)findViewById(R.id.screen);
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

        adapter = new GroupListAdapter(this,group_list);
        //adapter = new GroupListAdapter(this,group_list);

        listview.setAdapter(adapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listview.setOnItemClickListener(groupListClickListener);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
/*
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int i, int j, int k) {
                // TODO Auto-generated method stub
                search_glass_img.setVisibility(View.INVISIBLE);
                search_cancel_img.setVisibility(View.VISIBLE);
                search_cancel_btn.setVisibility(View.VISIBLE);

                //str = group_search.getText().toString();
                filtered_list.removeAll(filtered_list);

                for (int a = 0; a < group_list.size(); a++) {
                    if (group_list.get(a).getNickname_list().contains(s)) {
                        filtered_list.add(new GroupDatabase(group_list.get(a).getId(), group_list.get(a).getGroup_name(),
                                group_list.get(a).getNickname_list(),group_list.get(a).getGroup_code(),
                                group_list.get(a).getGroup_pw()));
                    }
                }
                adapter = new GroupListAdapter(GroupSearch.this,R.layout.group_listview,filtered_list);
                listview.setAdapter(adapter);

                if(s.length()>1&&filtered_list.size()==0) {
                    listview.setVisibility(View.GONE);
                    background_hidden.setVisibility(View.GONE);
                    unresistered_background.setVisibility(View.VISIBLE);
                    unresistered_screen.setVisibility(View.VISIBLE);
                    unresistered.setText("\""+s+"\"");

                }
                else
                {

                    listview.setVisibility(View.VISIBLE);
                    unresistered_background.setVisibility(View.GONE);
                    unresistered_screen.setVisibility(View.GONE);
                    if(filtered_list.size()!=0) {
                        background_hidden.getLayoutParams().height=getTotalHeightOfListView(listview);
                        background_hidden.setVisibility(View.VISIBLE);
                        unresistered_background.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void beforeTextChanged(CharSequence charsequence, int i, int j,  int k) {
                // TODO Auto-generated method stub
            }
            @Override
            public synchronized void afterTextChanged(Editable editable) {
                // TODO Auto-generated method stub


            }
        };*/
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                str = group_search.getText().toString();
                filtered_list.removeAll(filtered_list);
                Log.d("test", Integer.toString(filtered_list.size()));
                for (int i = 0; i < group_list.size(); i++) {
                    if (group_list.get(i).getNickname_list().contains(str)) {
                        filtered_list.add(new GroupDatabase1(group_list.get(i).getGroup_id(), group_list.get(i).getGroup_name(), group_list.get(i).getNickname_list(),
                                group_list.get(i).getGroup_category(), group_list.get(i).getIntroduce(),group_list.get(i).getPassword(),group_list.get(i).getGroup_code()
                        ));
                    }

                }
                Log.d("sjsjijj",str);

                for(int i=0; i<filtered_list.size();i++){
                    Log.d("이거되야해",filtered_list.get(i).getGroup_name());
                }
               adapter = new GroupListAdapter(GroupSearch.this,filtered_list);
                //adapter = new GroupListAdapter(GroupSearch.this,filtered_list);
                listview.setAdapter(adapter);
                listview.setOnItemClickListener(groupListClickListener);

                if(str.length()>1&&filtered_list.size()==0) {
                    listview.setVisibility(View.GONE);
                    background_hidden.setVisibility(View.GONE);
                    unresistered_background.setVisibility(View.VISIBLE);
                    unresistered_screen.setVisibility(View.VISIBLE);
                    unresistered.setText("\""+str+"\"");

                }
                else
                {

                    listview.setVisibility(View.VISIBLE);
                    unresistered_background.setVisibility(View.GONE);
                    unresistered_screen.setVisibility(View.GONE);
                    if(filtered_list.size()!=0) {
                        background_hidden.getLayoutParams().height=getTotalHeightOfListView(listview);
                        background_hidden.setVisibility(View.VISIBLE);
                        unresistered_background.setVisibility(View.VISIBLE);
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
                    carrier.setGroup_name(str);
                    Intent intent = new Intent(GroupSearch.this,Writing.class).putExtra("carrier",carrier);
                    startActivity(intent);
                    finish();
                }

            }
        });

        backward_btn = (Button)findViewById(R.id.group_backward_btn);
        backward_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GroupSearch.this,SelectGroupOrNot.class).putExtra("carrier",carrier);
                startActivity(intent);
                finish();
            }
        });
    }

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

    AdapterView.OnItemClickListener groupListClickListener = new AdapterView.OnItemClickListener() {
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          int pos = position;
          if(filtered_list.size()!=0) //검색했는데 검색결과가 리스트에 있을때
          {
              //Log.d("선택된거", filtered_list.get(pos).getGroup_code());
              carrier.setGroup_code(filtered_list.get(pos).getGroup_code());
              carrier.setGroup_name(filtered_list.get(pos).getGroup_name());
              carrier.setGroup_pw(filtered_list.get(pos).getPassword());
              carrier.setGroup_id(filtered_list.get(pos).getGroup_id());
              Intent intent = new Intent(GroupSearch.this,GroupPassword.class).putExtra("carrier",carrier);
              startActivity(intent);
              finish();
          }
          else {
              Log.d("선택된거", group_list.get(pos).getGroup_code());
              Log.d("index", String.valueOf(pos));
              carrier.setGroup_code(group_list.get(pos).getGroup_code());
              carrier.setGroup_name(group_list.get(pos).getGroup_name());
              carrier.setGroup_pw(group_list.get(pos).getPassword());
              carrier.setGroup_id(group_list.get(pos).getGroup_id());
              Intent intent = new Intent(GroupSearch.this, GroupPassword.class).putExtra("carrier", carrier);
              startActivity(intent);
              finish();
          }

      }
    };

    public void onBackPressed() {

        Intent intent = new Intent(GroupSearch.this,SelectGroupOrNot.class).putExtra("carrier",carrier);
        startActivity(intent);
        finish();
    }
    public class GroupNewPhp extends AsyncTask<String, android.R.integer , String> {
        private ArrayList<GroupDatabase1> group_list,temp_list;
        private Context context;

        public GroupNewPhp(ArrayList<GroupDatabase1> group_list,ArrayList<GroupDatabase1> temp_list, Context context) {
            super();
            this.group_list=group_list;
            this.temp_list = temp_list;
            this.context=context;
        }

        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            String return_str="";

            while(return_str.equalsIgnoreCase("")) {
                try{
                    URL data_url=new URL(urls[0]);
                    HttpURLConnection conn = (HttpURLConnection)data_url.openConnection();
                    if(conn !=null){
                        conn.setConnectTimeout(10000);
                        conn.setUseCaches(false);
                        if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                            for(;;){
                                String line = br.readLine();
                                if(line==null) break;
                                jsonHtml.append(line+"\n");
                            }
                            br.close();
                        }
                        conn.disconnect();
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                return_str=jsonHtml.toString();
            }
            return jsonHtml.toString();
        }

        protected void onPostExecute(String str) {
            try{
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("results");

                for(int i=0; i<ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);

                    //temp_list.add(new GroupDatabase(jo.getInt("id"),jo.getString("group_name"),jo.getString("nickname"),jo.getString("group_code"),jo.getString("password")));
                    temp_list.add(new GroupDatabase1(jo.getInt("group_id"), jo.getString("group_name"), jo.getString("nickname"), jo.getString("group_category"), jo.getString("introduce"),jo.getString("password"),jo.getString("group_code")));

                }

                while(temp_list.size() != 0) {

                    int j=0;
                    for (int k = 0; k < temp_list.size(); k++) {
                        if (temp_list.get(j).getGroup_name().compareToIgnoreCase(temp_list.get(k).getGroup_name()) >= 0)
                            j = k;

                    }
                    group_list.add(temp_list.remove(j));
                }

                adapter = new GroupListAdapter(GroupSearch.this,group_list);
               // adapter = new GroupListAdapter(GroupSearch.this,group_list);
                listview.setAdapter(adapter);
                listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                listview.setOnItemClickListener(groupListClickListener);
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }






    }


}
