package com.smarthandongi;

import android.app.Activity;

import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;

import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
        String temp_groupname="AKADEV";
        ArrayList<GroupDatabase1> group_list = new ArrayList<GroupDatabase1>();
        GroupPhp group_Php;

          GroupDatabase1 group;

        public void construction(){
            phpCreate();

        }

        public void phpCreate(){
            group_Php = new GroupPhp(group_list, this);
            group_Php.execute("http://hungry.portfolio1000.com/smarthandongi/group_info.php?");
        }
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.group_lifo_list);
            group_list_view =(ListView)findViewById(R.id.group_list);


         /*   ArrayAdapter<String> adWord = new ArrayAdapter<String>(list.this,android.R.layout.simple_dropdown_item_1line,arWords);
            AutoCompleteTextView autoEdit = (AutoCompleteTextView)findViewById(R.id.autoedit);
            autoEdit.setAdapter(adWord);
*/
            construction();
            group_list_view.setOnItemClickListener(mItemClickListener);
        }
    AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener(){
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            Intent intent = new Intent(group_infoList.this,group_info.class );
            intent.putExtra("group_id",group_list.get(position).getGroup_id());
            intent.putExtra("group_name",group_list.get(position).getGroup_name());
            intent.putExtra("group_category",group_list.get(position).getGroup_category());
            intent.putExtra("introduce",group_list.get(position).getIntroduce());

            startActivity(intent);

        }
    };

    /*
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(group_infoList.this, group_info.class);
                /*group.setGroup_id(groupinfoAdapter.getItem(position).getGroup_id());
                group.setGroup_name(groupinfoAdapter.getItem(position).getGroup_name());
                group.setGroup_category(groupinfoAdapter.getItem(position).getGroup_category());
                group.setIntroduce(groupinfoAdapter.getItem(position).getIntroduce());

                intent.putExtra("group",group);

                startActivity(intent);

            }
*/
                public class GroupPhp extends AsyncTask<String, Integer, String> {

                    private ArrayList<GroupDatabase1> group_list;
                    private group_infoList context;

                    public GroupPhp(ArrayList<GroupDatabase1> group_list, group_infoList context) {
                        super();
                        this.group_list = group_list;
                        this.context = context;
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

                                    group_list.add(new GroupDatabase1(jo.getInt("group_id"), jo.getString("group_name"), jo.getString("group_category"), jo.getString("introduce")));

                                }

                             groupinfoAdapter= new GroupinfoAdapter(group_infoList.this, group_list,R.layout.group_lifo_list );
                            group_list_view.setAdapter(groupinfoAdapter);
                            //group_list_view.setOnScrollListener(context);
                            //group_list_view.setOnItemClickListener(context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }


