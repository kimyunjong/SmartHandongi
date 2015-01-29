package com.smarthandongi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;


import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    RelativeLayout layoutView;
    String str=null;
    Button unresistered_forward_btn, backward_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        carrier = (Carrier)getIntent().getSerializableExtra("carrier");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_search);
        group_php = new GroupPhp(group_list,temp_list,this);
        group_php.execute("http://hungry.portfolio1000.com/smarthandongi/group.php");


        group_search = (EditText)findViewById(R.id.groupsearch);
        listview = (ListView)findViewById(R.id.list);
        layoutView = (RelativeLayout)findViewById(R.id.screen);
        layoutView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(group_search
                        .getWindowToken(), 0);
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
                    LinearLayout popup = (LinearLayout)findViewById(R.id.group_popup);
                    EditText unresistered = (EditText)findViewById(R.id.unresistered);
                    popup.setVisibility(View.VISIBLE);
                    unresistered.setText(str);

                }
                else
                {
                    LinearLayout popup = (LinearLayout)findViewById(R.id.group_popup);
                    popup.setVisibility(View.INVISIBLE);
                }

            }
        };


        group_search.addTextChangedListener(watcher);

        //미등록단체 확인하기
        unresistered_forward_btn = (Button)findViewById(R.id.unresistered_btn);
        unresistered_forward_btn.setOnClickListener(new Button.OnClickListener() {
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


/*
    public void groupForwardOnClick(View v) {
        switch(v.getId()) {
            case R.id.group_forward_btn :

                int pos = listview.getCheckedItemPosition();
                Log.d("pos값은 뭘까요?",String.valueOf(pos));


                if(filtered_list.size()!=0)
                {
                    Log.d("선택된거", filtered_list.get(pos).getGroup_code());
                    carrier.setGroup_code(filtered_list.get(pos).getGroup_code());
                    carrier.setGroup_name(filtered_list.get(pos).getGroup_name());
                    carrier.setGroup_pw(filtered_list.get(pos).getGroup_pw());
                    Intent intent = new Intent(GroupSearch.this,GroupPassword.class).putExtra("carrier",carrier);
                    startActivity(intent);
                    finish();
                }
                else if(filtered_list.size()==0&&group_search.getText().toString().length()!=0) {
                    Log.d("선택된거",str);
                    carrier.setGroup_name(str);
                    Intent intent = new Intent(GroupSearch.this,Writing.class).putExtra("carrier",carrier);
                    startActivity(intent);
                    finish();
                }

                else if(filtered_list.size()==0&&str==null){
                    if(pos==-1)
                    {
                        Toast toastView =Toast.makeText(this, "단체를 선택/입력하십시오", Toast.LENGTH_SHORT);
                        toastView.setGravity(Gravity.CENTER,0,0);
                        toastView.show();
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

                break;

        }
    }
    */
}
