package com.smarthandongi;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.smarthandongi.database.PushDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by LEWIS on 2015-01-28.
 */
public class PushSetup extends Activity implements View.OnClickListener{
    Carrier carrier;
    CheckBox push_sports_chk, push_game_chk, push_nightfood_chk;
    String[] register_id = {};
    CollectPushInfoPhp push_php;
    ArrayList<PushDatabase> push_list = new ArrayList<PushDatabase>();

    public void phpCreate(){
        push_php = new CollectPushInfoPhp(push_list, this);
        push_php.execute("http://hungry.portfolio1000.com/smarthandongi/push_setup.php/");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("in", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.push_setup);
        carrier = (Carrier)getIntent().getSerializableExtra("carrier");

        push_sports_chk = (CheckBox)findViewById(R.id.push_sports_chk);
        push_game_chk = (CheckBox)findViewById(R.id.push_game_chk);
        push_nightfood_chk = (CheckBox)findViewById(R.id.push_nightfood_chk);

        push_sports_chk.setOnClickListener(this);
        push_game_chk.setOnClickListener(this);
        push_nightfood_chk.setOnClickListener(this);

        phpCreate();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.push_sports_chk : {
                break;
            }
            case R.id.push_game_chk : {
                break;
            }
            case R.id.push_nightfood_chk : {
                break;
            }
        }
    }


    public class CollectPushInfoPhp extends AsyncTask<String, Integer, String> {
        private ArrayList<PushDatabase> push_list;
        private PushSetup context;

        public CollectPushInfoPhp(ArrayList<PushDatabase> push_list, PushSetup context){
            super();
            this.push_list = push_list;
            this.context = context;
        }

        @Override
        protected String doInBackground(String... urls) {
            Log.d("in", "doInBackground");
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return_str = jsonHtml.toString();

            }
            return jsonHtml.toString();
        }
        protected void onPostExecute(String str) {
            Log.d("in", str);
            try {
                Log.d("in", "11");
                JSONObject root = new JSONObject(str);
                Log.d("in", "22");
                JSONArray ja = root.getJSONArray("results");
                Log.d("in", "33");

                push_list.removeAll(push_list);
                Log.d("in", "onPostExecute2");
                for (int i = 0; i < ja.length(); i++) {
                    Log.d("in", "onPostExecute3");
                    JSONObject jo = ja.getJSONObject(i);
                    push_list.add(new PushDatabase(jo.getInt("id"), jo.getString("kakao_id"), jo.getString("kakao_nick"), jo.getString("register_id")));
                    Log.d("!!!!!!", jo.getString("register_id"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

