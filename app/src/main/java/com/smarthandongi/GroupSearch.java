package com.smarthandongi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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

import java.util.ArrayList;
/**
 * Created by user on 2015-01-27.
 */
public class GroupSearch extends Activity {

    Carrier carrier;
    private ListView listview;
    private GroupListAdapter adapter;
    private ArrayList<GroupDatabase> group_list = new ArrayList<GroupDatabase>();
    private ArrayList<GroupDatabase> temp_list = new ArrayList<GroupDatabase>();
    private ArrayList<GroupDatabase> filtered_list = new ArrayList<GroupDatabase>();
    private GroupPhp group_php;
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
        typeface = Typeface.createFromAsset(getAssets(), "KOPUBDOTUM_PRO_LIGHT.OTF");
        group_php = new GroupPhp(group_list,temp_list,this);
        group_php.execute("http://hungry.portfolio1000.com/smarthandongi/group.php");

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

        unresistered.setTypeface(typeface);
        group_nomatch_1.setTypeface(typeface);
        group_nomatch_2.setTypeface(typeface);
        group_nomatch_3.setTypeface(typeface);
        group_nomatch_4.setTypeface(typeface);

        group_search = (EditText)findViewById(R.id.groupsearch);
        group_search.setTypeface(typeface);
        group_search.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                group_search.setCursorVisible(true);
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(group_search,0);
                search_please.setVisibility(View.INVISIBLE);
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

        adapter = new GroupListAdapter(this,R.layout.group_listview,group_list);
        listview.setAdapter(adapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listview.setOnItemClickListener(groupListClickListener);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
                        filtered_list.add(new GroupDatabase(temp_list.get(i).getId(), temp_list.get(i).getGroup_name(),
                                                            temp_list.get(i).getNickname_list(),temp_list.get(i).getGroup_code(),
                                                            temp_list.get(i).getGroup_pw()));

                        adapter = new GroupListAdapter(GroupSearch.this,R.layout.group_listview,filtered_list);
                        listview.setAdapter(adapter);
                        Log.d("filtered_list", Integer.toString(filtered_list.size()));

                    }
                }



                if(str.length()>1&&filtered_list.size()==0) {
                    listview.setVisibility(View.INVISIBLE);
                    background_hidden.setVisibility(View.GONE);
                    unresistered_background.setVisibility(View.VISIBLE);
                    unresistered_screen.setVisibility(View.VISIBLE);
                    unresistered.setText("\""+str+"\"");

                }
                else
                {

                        listview.setVisibility(View.VISIBLE);
                        unresistered_background.setVisibility(View.INVISIBLE);
                        unresistered_screen.setVisibility(View.INVISIBLE);
                        if(filtered_list.size()!=0) {
                            background_hidden.getLayoutParams().height=getTotalHeightOfListView(listview);
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
              Log.d("선택된거", filtered_list.get(pos).getGroup_code());
              carrier.setGroup_code(filtered_list.get(pos).getGroup_code());
              carrier.setGroup_name(filtered_list.get(pos).getGroup_name());
              carrier.setGroup_pw(filtered_list.get(pos).getGroup_pw());
              Intent intent = new Intent(GroupSearch.this,GroupPassword.class).putExtra("carrier",carrier);
              startActivity(intent);
              finish();
          }
          else {
              Log.d("선택된거", group_list.get(pos).getGroup_code());
              Log.d("index", String.valueOf(pos));
              carrier.setGroup_code(group_list.get(pos).getGroup_code());
              carrier.setGroup_name(group_list.get(pos).getGroup_name());
              carrier.setGroup_pw(group_list.get(pos).getGroup_pw());
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


}
