package com.togetherhandongi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
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
    String regid = null, myResult;
    private ArrayList<GroupDatabase1> temp_list = new ArrayList<GroupDatabase1>();
    private ArrayList<GroupDatabase1> filtered_list = new ArrayList<GroupDatabase1>();
    EditText group_search;
    String str = null;
    ImageView search_cancel_img, search_glass_img, search_please, unresistered_background, background_hidden;
    Button backward_btn, unresistered_btn, search_cancel_btn, register_group;
    TextView unresistered;
    RelativeLayout unresistered_screen;
    LinearLayout layoutView, bottom_list;
    Typeface typeface;

    public void construction() {
        phpCreate();

    }

    public void phpCreate() {

       /* String introduce=group.getIntroduce() ;
        try {
            introduce = URLEncoder.encode(introduce, "UTF-8");
            //review = URLEncoder.encode(review, "UTF-8");
            //format2 = URLEncoder.encode(format2, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        group.setIntroduce(introduce);
        */

        group_Php = new GroupPhp(group_list, temp_list, this);
        group_Php.execute("http://hungry.portfolio1000.com/smarthandongi/group_info.php?");

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_lifo_list);
        group_list_view = (ListView) findViewById(R.id.group_list);
        carrier = (Carrier) getIntent().getSerializableExtra("carrier");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        regid = carrier.getRegid();
        typeface = Typeface.createFromAsset(getAssets(), "KOPUBDOTUM_PRO_MEDIUM.OTF");
        //Log.d("regid g infolist",carrier.getRegid());
        carrier.setRegid(regid);
        construction();
        //group_list_view.setOnItemClickListener(mItemClickListener);

        backward_btn = (Button) findViewById(R.id.back_btn);// 뒤로가기
        backward_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(group_infoList.this, yj_activity.class).putExtra("carrier", carrier);
                startActivity(intent);
                finish();


            }
        });

        register_group = (Button) findViewById(R.id.writing_confirm_btn);

        register_group.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                 Uri uri = Uri.parse("http://hungry.portfolio1000.com/smarthandongi/group_request.php");

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                 startActivity(intent);
            }
        });
        //search bar
        search_cancel_img = (ImageView) findViewById(R.id.search_cancel_img);
        search_glass_img = (ImageView) findViewById(R.id.search_glass_img);
        search_please = (ImageView) findViewById(R.id.search_please);


        unresistered_background = (ImageView) findViewById(R.id.unresistered_background);
        background_hidden = (ImageView) findViewById(R.id.background_hidden);
        unresistered_screen = (RelativeLayout) findViewById(R.id.unresistered_screen);
        unresistered = (TextView) findViewById(R.id.unresistered);

        bottom_list = (LinearLayout)findViewById(R.id.bottom_list);

        final LinearLayout view = (LinearLayout)findViewById(R.id.group_info_list);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if ((view.getRootView().getHeight() - view.getHeight()) >
                        view.getRootView().getHeight()/3) {
                    Log.d("keyboard", "open");
                    // keyboard is open
                    bottom_list.setVisibility(View.GONE);

                } else {
                    Log.d("keyboard", "close");
                    bottom_list.setVisibility(View.VISIBLE);
                    // keyboard is closed

                }
            }
        });


        //search
        group_search = (EditText) findViewById(R.id.groupsearch);
        group_search.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
//                bottom_list.setVisibility(View.GONE);
                group_search.setCursorVisible(true);
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(group_search, 0);
                search_please.setVisibility(View.INVISIBLE);
                return true;
            }
        });

        groupinfoAdapter = new GroupinfoAdapter(this, group_list);

        layoutView = (LinearLayout) findViewById(R.id.group_info_list);
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


        groupinfoAdapter = new GroupinfoAdapter(this, group_list);
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
            public void beforeTextChanged(CharSequence charsequence, int i, int j, int k) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // TODO Auto-generated method stub
                search_glass_img.setVisibility(View.INVISIBLE);
                search_cancel_img.setVisibility(View.VISIBLE);
                search_cancel_btn = (Button) findViewById(R.id.search_cancel_btn);
                search_cancel_btn.setVisibility(View.VISIBLE);
                search_cancel_btn.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        group_search.setText("");
                        search_cancel_img.setVisibility(View.INVISIBLE);
                        search_glass_img.setVisibility(View.VISIBLE);
                    }
                });

                str = group_search.getText().toString();
                filtered_list.removeAll(filtered_list);
                Log.d("test111", Integer.toString(temp_list.size()));

                for (int i = 0; i < group_list.size(); i++) {
                    if (group_list.get(i).getNickname_list().contains(str)) {
                        filtered_list.add(new GroupDatabase1(group_list.get(i).getGroup_id(), group_list.get(i).getGroup_name(), group_list.get(i).getNickname_list(),
                                group_list.get(i).getGroup_category(), group_list.get(i).getIntroduce(),group_list.get(i).getPassword(),group_list.get(i).getGroup_code()
                        ));

                        //      groupinfoAdapter = new GroupinfoAdapter(group_infoList.this, filtered_list, R.layout.group_lifo_list);
                        //     group_list_view.setAdapter(groupinfoAdapter);
                        //     Log.d("filtered_list", Integer.toString(filtered_list.size()));
                        groupinfoAdapter = new GroupinfoAdapter(group_infoList.this, filtered_list);
                        group_list_view.setAdapter(groupinfoAdapter);
                        group_list_view.setOnItemClickListener(groupListClickListener);
                    }
                }


                Log.d("sjsjijj",str);

                for(int i=0; i<filtered_list.size();i++){
                    Log.d("이거되야해",filtered_list.get(i).getGroup_name());
                }


                if(str.length()>1&&filtered_list.size()==0) {
                    group_list_view.setVisibility(View.GONE);
                    background_hidden.setVisibility(View.GONE);
                    unresistered_background.setVisibility(View.VISIBLE);
                    unresistered_screen.setVisibility(View.VISIBLE);
//                    unresistered.setText("\""+str+"\"");

                }
                else if(str.length()==0) {
                    group_list_view.setVisibility(View.VISIBLE);
                    unresistered_background.setVisibility(View.GONE);
                    unresistered_screen.setVisibility(View.GONE);
                    groupinfoAdapter = new GroupinfoAdapter(group_infoList.this, group_list);
                    group_list_view.setAdapter(groupinfoAdapter);
                    group_list_view.setOnItemClickListener(groupListClickListener);

                }
                else
                {

                    group_list_view.setVisibility(View.VISIBLE);
                    unresistered_background.setVisibility(View.GONE);
                    unresistered_screen.setVisibility(View.GONE);
                    if(filtered_list.size()!=0) {
                        background_hidden.getLayoutParams().height=getTotalHeightOfListView(group_list_view);
                        background_hidden.setVisibility(View.VISIBLE);
                        unresistered_background.setVisibility(View.VISIBLE);
                    }

                }
            }
        };
        group_search.addTextChangedListener(watcher);

        unresistered_btn = (Button) findViewById(R.id.unresistered_btn);
        unresistered_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (filtered_list.size() == 0 && group_search.getText().toString().length() != 0) {
                    Log.d("선택된거", str);
                    group.setGroup_name(str);// carrier to group
                    Intent intent = new Intent(group_infoList.this, group_info.class).putExtra("group", group);
                    intent.putExtra("from", 0);
                    startActivity(intent);

                }

            }
        });


        //group search end
    }

    AdapterView.OnItemClickListener groupListClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            /*Intent intent = new Intent(group_infoList.this,group_info.class );
            intent.putExtra("group_id",group_list.get(position).getGroup_id());
            intent.putExtra("group_name",group_list.get(position).getGroup_name());
            intent.putExtra("group_category",group_list.get(position).getGroup_category());
            intent.putExtra("introduce",group_list.get(position).getIntroduce());

            startActivity(intent);
            */
            //group search
            int pos = position;
            if (filtered_list.size() != 0) //검색했는데 검색결과가 리스트에 있을때
            {
                Log.d("선택된거", filtered_list.get(pos).getGroup_category());
                //  group.setGroup_code(filtered_list.get(pos).getGroup_code());

                Intent intent = new Intent(group_infoList.this, group_info.class);
                intent.putExtra("group_id", filtered_list.get(pos).getGroup_id());
                intent.putExtra("group_name", filtered_list.get(pos).getGroup_name());
                intent.putExtra("group_category", filtered_list.get(pos).getGroup_category());
                intent.putExtra("introduce", filtered_list.get(pos).getIntroduce());
                intent.putExtra("from", 0);
                intent.putExtra("carrier", carrier);

                startActivity(intent);
//                finish();
            } else {
                Log.d("선택된거", group_list.get(pos).getGroup_category());
                Log.d("index", String.valueOf(pos));
                //group.setGroup_code(group_list.get(pos).getGroup_code());
                Intent intent = new Intent(group_infoList.this, group_info.class);
                intent.putExtra("group_id", group_list.get(pos).getGroup_id());
                intent.putExtra("group_name", group_list.get(pos).getGroup_name());
                intent.putExtra("group_category", group_list.get(pos).getGroup_category());
                intent.putExtra("introduce", group_list.get(pos).getIntroduce());
                intent.putExtra("from", 0);
                intent.putExtra("carrier", carrier);
                startActivity(intent);
//                finish();
            }
            //group search
        }
    };

    public int getTotalHeightOfListView(ListView listview) { ///////리스트뷰의 높이를 구하기 위한 함수
        ListAdapter mAdapter = listview.getAdapter();
        int totalHeight = 0;

        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listview);
            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += mView.getMeasuredHeight();

        }
        return totalHeight;
    }

    public void onBackPressed() {

        Intent intent = new Intent(group_infoList.this, yj_activity.class).putExtra("carrier", carrier);
        startActivity(intent);
        finish();


    }


    public class GroupPhp extends AsyncTask<String, Integer, String> {

        private ArrayList<GroupDatabase1> group_list, temp_list;
        private group_infoList context;

        public GroupPhp(ArrayList<GroupDatabase1> group_list, ArrayList<GroupDatabase1> temp_list, group_infoList context) {
            super();
            this.group_list = group_list;
            this.context = context;
            this.temp_list = temp_list;
        }

        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            String return_str = "";

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
            Log.d("리턴 한 값", jsonHtml.toString());

            return jsonHtml.toString();
        }

        protected void onPostExecute(String str) {
            try {
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("results");

                Log.d("되나보자", "여긴되나?");

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);

                   /* String introduce= jo.getString("introduce");
                    try {
                        introduce = URLEncoder.encode(introduce, "UTF-8");

                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }*/
                    ;
                    //group_list.add(new GroupDatabase1(jo.getInt("group_id"), jo.getString("group_name"), jo.getString("nickname"), jo.getString("group_category"), jo.getString("introduce")));
                    temp_list.add(new GroupDatabase1(jo.getInt("group_id"), jo.getString("group_name"), jo.getString("nickname"), jo.getString("group_category"),
                            jo.getString("introduce"),jo.getString("password"),jo.getString("group_code")));

                }
                while(temp_list.size() != 0) {

                    int j=0;
                    for (int k = 0; k < temp_list.size(); k++) {
                        if (temp_list.get(j).getGroup_name().compareToIgnoreCase(temp_list.get(k).getGroup_name()) >= 0)
                            j = k;

                    }
                    group_list.add(temp_list.remove(j));
                }

                group_list_view.setAdapter(groupinfoAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


 }


