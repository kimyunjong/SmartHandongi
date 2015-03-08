package com.togetherhandongi;

import android.R.integer;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

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
public class GroupPhp extends AsyncTask<String, integer , String> {
    private ArrayList<GroupDatabase> group_list,temp_list;
    private Activity activity;

    public GroupPhp(ArrayList<GroupDatabase> group_list,ArrayList<GroupDatabase> temp_list, Activity activity) {
        super();
        this.group_list=group_list;
        this.temp_list = temp_list;
        this.activity=activity;
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

                group_list.add(new GroupDatabase(jo.getInt("id"),jo.getString("group_name"),jo.getString("nickname"),jo.getString("group_code"),jo.getString("password")));
                temp_list.add(new GroupDatabase(jo.getInt("id"),jo.getString("group_name"),jo.getString("nickname"),jo.getString("group_code"),jo.getString("password")));

            }

            Log.d("groupsize",Integer.toString(group_list.size()));
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }






}
