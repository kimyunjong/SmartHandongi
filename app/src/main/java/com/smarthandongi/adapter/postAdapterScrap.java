package com.smarthandongi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smarthandongi.Carrier;
import com.smarthandongi.R;
import com.smarthandongi.database.PostDatabase;
import com.smarthandongi.dday;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Joel on 2015-01-23.
 */
public class postAdapterScrap extends BaseAdapter {

    private LayoutInflater inflater;
    private List<PostDatabase> list;
    private Context context;
    Typeface typeface;

    dday deadline=new dday();

    String temp_date;
    int s_date;
    int s_year;
    int s_month;
    int s_day;
    String temp_date_e;
    int e_date;
    int e_year;
    int e_month;
    int e_day;
    private Carrier carrier;

    public postAdapterScrap(Context context, List<PostDatabase> list, Carrier carrier){
        this.context=context;
        this.list=list;
        this.carrier=carrier;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }
    @Override
    public PostDatabase getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    public List<PostDatabase> getLists(){
        return list;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        View v = convertView;
        if (v == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            typeface = Typeface.createFromAsset(context.getAssets(), "KOPUBDOTUM_PRO_LIGHT.OTF");

            v = inflater.inflate(R.layout.post_item, null);
            holder = new ViewHolder();

            holder.post_title = (TextView) v.findViewById(R.id.post_title);
            holder.post_id = (TextView) v.findViewById(R.id.post_id);
            holder.post_category = (ImageView) v.findViewById(R.id.post_category);
            holder.post_dday = (TextView) v.findViewById(R.id.post_dday);
            holder.post_group = (TextView) v.findViewById(R.id.post_group);
            holder.like = (ImageButton) v.findViewById(R.id.like_button);
            holder.line_item=(LinearLayout)v.findViewById(R.id.line_item);
            //폰트작업
            holder.post_title.setTypeface(typeface);
            holder.post_id.setTypeface(typeface);
            holder.post_dday.setTypeface(typeface);
            holder.post_group.setTypeface(typeface);


            v.setTag(holder);
        } else {

            holder = (ViewHolder) v.getTag();
        }

        holder.like.setVisibility(carrier.isLogged_in() ? View.VISIBLE : View.GONE);
        holder.like.setFocusable(false);
        holder.like.setBackgroundResource(getItem(position).getLike().compareTo("0") == 0 ? R.drawable.not_like : R.drawable.like);
        holder.like.setOnTouchListener(new LikeListener(carrier.getId(), getItem(position).getId(), holder.like, getItem(position)));
        String strColor="#ffffeded";
        holder.line_item.setBackgroundColor(Color.parseColor(strColor));

        holder.post_title.setText(getItem(position).getTitle() + " ");
        holder.post_id.setText(getItem(position).getId() + "");
        if (getItem(position).getGroup().compareTo("") == 0) {
            holder.post_group.setText("[" + getItem(position).getKakao_nic() + "]" + " ");
        } else if (getItem(position).getGroup().compareTo("") != 0) {
            holder.post_group.setText("[" + getItem(position).getGroup_name() + "]" + " ");
        }

        if(!getItem(position).getStart_date().equalsIgnoreCase("0")&&!getItem(position).getEnd_date().equalsIgnoreCase("0"))
        {
            temp_date = getItem(position).getStart_date();
            s_date = Integer.parseInt(temp_date);
            s_year = s_date / 10000;
            s_date = s_date - (s_year * 10000);
            s_month = s_date / 100;
            s_date = s_date - s_month * 100;
            s_day = s_date;

            temp_date_e = getItem(position).getEnd_date();
            e_date = Integer.parseInt(temp_date_e);

            e_year = e_date / 10000;
            e_date = e_date - (s_year * 10000);
            e_month = e_date / 100;
            e_date = e_date - e_month * 100;
            e_day = e_date;

            int dday_s = deadline.caldate(s_year, s_month - 1, s_day + 1);
            int dday_e = deadline.caldate(e_year, e_month - 1, e_day + 1);


            if (dday_s < 0) {
                holder.post_dday.setText("D" + String.valueOf(dday_s));
                holder.post_group.setAlpha(1);
                holder.post_id.setAlpha(1);
                holder.post_title.setAlpha(1);
                holder.post_dday.setAlpha(1);
                if (getItem(position).getBig_category().equalsIgnoreCase("1")) {
                    holder.post_category.setImageResource(R.drawable.notice);
                    holder.post_category.setAlpha(1f);
                } else if (getItem(position).getBig_category().equalsIgnoreCase("2")) {
                    holder.post_category.setImageResource(R.drawable.outer);
                    holder.post_category.setAlpha(1f);
                } else if (getItem(position).getBig_category().equalsIgnoreCase("3")) {
                    holder.post_category.setImageResource(R.drawable.seminar);
                    holder.post_category.setAlpha(1f);
                } else if (getItem(position).getBig_category().equalsIgnoreCase("4")) {
                    holder.post_category.setImageResource(R.drawable.recruit);
                    holder.post_category.setAlpha(1f);
                } else if (getItem(position).getBig_category().equalsIgnoreCase("5")) {
                    holder.post_category.setImageResource(R.drawable.agora);
                    holder.post_category.setAlpha(1f);
                }


            } else if (dday_s >= 0 && dday_e <= 0) {
                holder.post_dday.setText("진행중");
                holder.post_group.setAlpha(1);
                holder.post_id.setAlpha(1);
                holder.post_title.setAlpha(1);
                holder.post_dday.setAlpha(1);


                if (getItem(position).getBig_category().equalsIgnoreCase("1")) {
                    holder.post_category.setImageResource(R.drawable.notice);
                    holder.post_category.setAlpha(1f);
                } else if (getItem(position).getBig_category().equalsIgnoreCase("2")) {
                    holder.post_category.setImageResource(R.drawable.outer);
                    holder.post_category.setAlpha(1f);
                } else if (getItem(position).getBig_category().equalsIgnoreCase("3")) {
                    holder.post_category.setImageResource(R.drawable.seminar);
                    holder.post_category.setAlpha(1f);
                } else if (getItem(position).getBig_category().equalsIgnoreCase("4")) {
                    holder.post_category.setImageResource(R.drawable.recruit);
                    holder.post_category.setAlpha(1f);
                } else if (getItem(position).getBig_category().equalsIgnoreCase("5")) {
                    holder.post_category.setImageResource(R.drawable.agora);
                    holder.post_category.setAlpha(1f);
                }

            } else if (dday_e > 0) //지난 이벤트의 경우
            {
                holder.post_dday.setText("");
                holder.post_title.setAlpha(0.3f);
                holder.post_id.setAlpha(0.3f);
                holder.post_group.setAlpha(0.3f);

                if (getItem(position).getBig_category().equalsIgnoreCase("1")) {
                    holder.post_category.setImageResource(R.drawable.notice_passed);

                } else if (getItem(position).getBig_category().equalsIgnoreCase("2")) {
                    holder.post_category.setImageResource(R.drawable.outer_passed);

                } else if (getItem(position).getBig_category().equalsIgnoreCase("3")) {
                    holder.post_category.setImageResource(R.drawable.seminar_passed);

                } else if (getItem(position).getBig_category().equalsIgnoreCase("4")) {
                    holder.post_category.setImageResource(R.drawable.recruit_passed);

                } else if (getItem(position).getBig_category().equalsIgnoreCase("5")) {
                    holder.post_category.setImageResource(R.drawable.agora_passed);

                }
            }
        }
        else if(!getItem(position).getStart_date().equalsIgnoreCase("0")&&getItem(position).getEnd_date().equalsIgnoreCase("0")) {
            temp_date = getItem(position).getStart_date();
            s_date = Integer.parseInt(temp_date);
            s_year = s_date / 10000;
            s_date = s_date - (s_year * 10000);
            s_month = s_date / 100;
            s_date = s_date - s_month * 100;
            s_day = s_date;
            int dday_s = deadline.caldate(s_year, s_month - 1, s_day + 1);
            int dday_e = deadline.caldate(s_year, s_month - 1, s_day + 1);

            if (dday_s < 0) {
                holder.post_dday.setText("D" + String.valueOf(dday_s));
                holder.post_group.setAlpha(1);
                holder.post_id.setAlpha(1);
                holder.post_title.setAlpha(1);
                holder.post_dday.setAlpha(1);
                if (getItem(position).getBig_category().equalsIgnoreCase("1")) {
                    holder.post_category.setImageResource(R.drawable.notice);
                    holder.post_category.setAlpha(1f);
                } else if (getItem(position).getBig_category().equalsIgnoreCase("2")) {
                    holder.post_category.setImageResource(R.drawable.outer);
                    holder.post_category.setAlpha(1f);
                } else if (getItem(position).getBig_category().equalsIgnoreCase("3")) {
                    holder.post_category.setImageResource(R.drawable.seminar);
                    holder.post_category.setAlpha(1f);
                } else if (getItem(position).getBig_category().equalsIgnoreCase("4")) {
                    holder.post_category.setImageResource(R.drawable.recruit);
                    holder.post_category.setAlpha(1f);
                } else if (getItem(position).getBig_category().equalsIgnoreCase("5")) {
                    holder.post_category.setImageResource(R.drawable.agora);
                    holder.post_category.setAlpha(1f);
                }


            } else if (dday_s >= 0 && dday_e <= 0) {
                holder.post_dday.setText("진행중");
                holder.post_group.setAlpha(1);
                holder.post_id.setAlpha(1);
                holder.post_title.setAlpha(1);
                holder.post_dday.setAlpha(1);


                if (getItem(position).getBig_category().equalsIgnoreCase("1")) {
                    holder.post_category.setImageResource(R.drawable.notice);
                    holder.post_category.setAlpha(1f);
                } else if (getItem(position).getBig_category().equalsIgnoreCase("2")) {
                    holder.post_category.setImageResource(R.drawable.outer);
                    holder.post_category.setAlpha(1f);
                } else if (getItem(position).getBig_category().equalsIgnoreCase("3")) {
                    holder.post_category.setImageResource(R.drawable.seminar);
                    holder.post_category.setAlpha(1f);
                } else if (getItem(position).getBig_category().equalsIgnoreCase("4")) {
                    holder.post_category.setImageResource(R.drawable.recruit);
                    holder.post_category.setAlpha(1f);
                } else if (getItem(position).getBig_category().equalsIgnoreCase("5")) {
                    holder.post_category.setImageResource(R.drawable.agora);
                    holder.post_category.setAlpha(1f);
                }

            } else if (dday_e > 0) //지난 이벤트의 경우
            {
                holder.post_dday.setText("");
                holder.post_title.setAlpha(0.3f);
                holder.post_id.setAlpha(0.3f);
                holder.post_group.setAlpha(0.3f);

                if (getItem(position).getBig_category().equalsIgnoreCase("1")) {
                    holder.post_category.setImageResource(R.drawable.notice_passed);

                } else if (getItem(position).getBig_category().equalsIgnoreCase("2")) {
                    holder.post_category.setImageResource(R.drawable.outer_passed);

                } else if (getItem(position).getBig_category().equalsIgnoreCase("3")) {
                    holder.post_category.setImageResource(R.drawable.seminar_passed);

                } else if (getItem(position).getBig_category().equalsIgnoreCase("4")) {
                    holder.post_category.setImageResource(R.drawable.recruit_passed);

                } else if (getItem(position).getBig_category().equalsIgnoreCase("5")) {
                    holder.post_category.setImageResource(R.drawable.agora_passed);

                }
            }
        }
            else if(getItem(position).getStart_date().equalsIgnoreCase("0")&&getItem(position).getEnd_date().equalsIgnoreCase("0"))
            {

                holder.post_dday.setText("");
                holder.post_group.setAlpha(1);
                holder.post_id.setAlpha(1);
                holder.post_title.setAlpha(1);
                holder.post_dday.setAlpha(1);
                if (getItem(position).getBig_category().equalsIgnoreCase("1")) {
                    holder.post_category.setImageResource(R.drawable.notice);
                    holder.post_category.setAlpha(1f);
                } else if (getItem(position).getBig_category().equalsIgnoreCase("2")) {
                    holder.post_category.setImageResource(R.drawable.outer);
                    holder.post_category.setAlpha(1f);
                } else if (getItem(position).getBig_category().equalsIgnoreCase("3")) {
                    holder.post_category.setImageResource(R.drawable.seminar);
                    holder.post_category.setAlpha(1f);
                } else if (getItem(position).getBig_category().equalsIgnoreCase("4")) {
                    holder.post_category.setImageResource(R.drawable.recruit);
                    holder.post_category.setAlpha(1f);
                } else if (getItem(position).getBig_category().equalsIgnoreCase("5")) {
                    holder.post_category.setImageResource(R.drawable.agora);
                    holder.post_category.setAlpha(1f);
                }
            }



        return v;
    }

    //like list 에 관련된 것 !!
    class LikeListener implements View.OnTouchListener {
        private String kakao_id;

        private int scrap_id;
        private View view;
        private PostDatabase database;

        public LikeListener(String kakao_id, int scrap_id, View view, PostDatabase database){
            this.scrap_id =scrap_id;
            this.kakao_id= kakao_id;
            this.view = view;
            this.database = database;
        }

        @Override
        public boolean onTouch(View view, MotionEvent event)
        {
            if (event.getAction()==MotionEvent.ACTION_DOWN) {
            }
            else if (event.getAction()==MotionEvent.ACTION_UP)
            {
                LikeTask like_task = new LikeTask(view, database);
                like_task.execute("http://hungry.portfolio1000.com/smarthandongi/scrap.php?post_id=" + scrap_id + "&kakao_id=" + String.valueOf(kakao_id));

            }
            else if (event.getAction()==MotionEvent.ACTION_MOVE) {
            }
            return false;
        }
    }

    class LikeTask extends AsyncTask {

        private View view;
        private PostDatabase database;
        public LikeTask(View view, PostDatabase database) {
            this.view = view;
            this.database = database;
        }

        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub
            StringBuilder jsonHtml = new StringBuilder();
            String return_str="";

            while (return_str.equalsIgnoreCase("")) {
                try{
                    URL data_url = new URL((String)params[0]);
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
            }

            return jsonHtml.toString();
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try{
                JSONObject root = new JSONObject((String)result);
                JSONArray ja = root.getJSONArray("results");
                JSONObject jo = ja.getJSONObject(0);
                view.setBackgroundResource(jo.getInt("result") == 0 ? R.drawable.not_like : R.drawable.like);
                database.setLike(jo.getString("result"));
            }
            catch(JSONException e) {
                e.printStackTrace();
            }
        }
    }


    static class ViewHolder{
        TextView post_title, post_id, post_group, post_dday;
        ImageView post_category;
        ImageButton like;
        LinearLayout line_item;

    }
}

