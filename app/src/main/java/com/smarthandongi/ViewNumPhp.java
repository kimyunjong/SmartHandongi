package com.smarthandongi;

import android.os.AsyncTask;

import com.smarthandongi.database.PostDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 2015-02-10.
 */
public class ViewNumPhp extends AsyncTask<String, Integer, String> {
    private PostDatabase postDatabase;

    public ViewNumPhp(PostDatabase postDatabase) {
        super();
        this.postDatabase=postDatabase;
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

        } catch(JSONException e) {
            e.printStackTrace();
        }

    }
}
