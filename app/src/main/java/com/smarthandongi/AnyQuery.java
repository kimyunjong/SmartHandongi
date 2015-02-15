package com.smarthandongi;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LEWIS on 2015-02-15.
 */
public class AnyQuery {
    anyquery_work anyquery_php;

    public void phpCreate(String sql){

        anyquery_php = new anyquery_work();
        anyquery_php.execute("http://hungry.portfolio1000.com/smarthandongi/push_update.php?query=" + sql);
    }

    public class anyquery_work extends AsyncTask<String, Integer, String> {

        protected String doInBackground(String... urls) {
            Log.d("", "doInBackground SendPushInfo");
            StringBuilder jsonHtml = new StringBuilder();

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

            return jsonHtml.toString();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}
