package com.smarthandongi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;


import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.smarthandongi.adapter.GroupListAdapter;

import java.util.ArrayList;
/**
 * Created by user on 2015-01-27.
 */
public class GroupSearch extends Activity {

    private ListView listview;
    private GroupListAdapter adapter;
    private ArrayList<GroupDatabase> group_list = new ArrayList<GroupDatabase>();
    private ArrayList<GroupDatabase> temp_list = new ArrayList<GroupDatabase>();
    private ArrayList<GroupDatabase> filtered_list = new ArrayList<GroupDatabase>();
    private GroupPhp group_php;
    EditText group_search;
    String str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_search);
        group_php = new GroupPhp(group_list,temp_list,this);
        Log.d("1","1");
        group_php.execute("http://hungry.portfolio1000.com/smarthandongi/group.php");
        Log.d("2","2");


        listview = (ListView)findViewById(R.id.list);
        Log.d("3","3");

        adapter = new GroupListAdapter(this,R.layout.group_listview,group_list);
        listview.setAdapter(adapter);



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
                        filtered_list.add(new GroupDatabase(temp_list.get(i).getId(), temp_list.get(i).getGroup_name(),temp_list.get(i).getNickname_list()));
                        adapter = new GroupListAdapter(GroupSearch.this,R.layout.group_listview,filtered_list);
                        listview.setAdapter(adapter);
                        Log.d("filtered_list", Integer.toString(filtered_list.size()));
                    }
                }

            }
        };

        group_search = (EditText)findViewById(R.id.groupsearch);
        group_search.addTextChangedListener(watcher);

    }
}
