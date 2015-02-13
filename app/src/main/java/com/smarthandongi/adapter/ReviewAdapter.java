package com.smarthandongi.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.smarthandongi.Carrier;
import com.smarthandongi.R;
import com.smarthandongi.database.ReviewDatabase;
import com.smarthandongi.dday;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by user on 2015-02-10.
 */
public class ReviewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<ReviewDatabase> list;
    private Context context;
    private Carrier carrier;
    ReviewDatabase reviewDatabase;
    private ArrayList<ReviewDatabase> review_list = new ArrayList<ReviewDatabase>();

    public ReviewAdapter(Context context, List<ReviewDatabase> list, Carrier carrier) {
        this.context = context;
        this.list = list;
        this.carrier = carrier;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ReviewDatabase getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    public List<ReviewDatabase> getLists() {
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
            v = inflater.inflate(R.layout.review_detail, null);
            holder = new ViewHolder();

            holder.kakao_nick = (TextView) v.findViewById(R.id.kakao_nick);
            holder.date = (TextView) v.findViewById(R.id.date);
            holder.content = (TextView) v.findViewById(R.id.content);
            holder.notify_btn = (Button) v.findViewById(R.id.notify_btn);
            holder.del_btn = (Button) v.findViewById(R.id.del_btn);
            holder.new_img = (ImageView) v.findViewById(R.id.new_img);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        Calendar today = Calendar.getInstance();
        int dd=today.get(Calendar.DAY_OF_MONTH);
        int mm=today.get(Calendar.MONTH);
        int yy=today.get(Calendar.YEAR);


        dday dday;

        System.out.println(getItem(position).getReply_date()+"오늘 날짜를 확인합니다. "+Integer.valueOf(yy)+"-"+Integer.valueOf(mm+1)+"-"+Integer.valueOf(dd));
        //if(getItem(position).getReply_date().equalsIgnoreCase())

        if(mm+1<10)
        {
            if(getItem(position).getReply_date().equalsIgnoreCase(Integer.valueOf(yy)+"-0"+Integer.valueOf(mm+1)+"-"+Integer.valueOf(dd)))
            {
                holder.new_img.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.new_img.setVisibility(View.GONE);
            }
        }
        else if(mm+1>=10)
        {
            if(getItem(position).getReply_date().equalsIgnoreCase(Integer.valueOf(yy)+"-"+Integer.valueOf(mm+1)+"-"+Integer.valueOf(dd)))
            {
                holder.new_img.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.new_img.setVisibility(View.GONE);
            }
        }
        holder.date.setText(getItem(position).getReply_date() + " ");
        holder.kakao_nick.setText(getItem(position).getKakao_nick() + " ");
        holder.content.setText(getItem(position).getContent() + " ");

        if(getItem(position).getKakao_id().compareTo(carrier.getId())==0) {
            holder.notify_btn.setVisibility(View.GONE);
            holder.del_btn.setVisibility(View.VISIBLE);
        }else{
            holder.notify_btn.setVisibility(View.VISIBLE);
            holder.del_btn.setVisibility(View.GONE);
        }

        //  holder.del_btn.setVisibility(carrier.isLogged_in() ? View.VISIBLE : View.GONE);
        holder.del_btn.setFocusable(true);
        // holder.del_btn.setBackgroundResource(true ? R.drawable.like : R.drawable.not_like);
        int review_id;
        review_id = getItem(position).getReview_id();
        ReviewDatabase touch_position;
        touch_position = getItem(position);
        holder.del_btn.setOnTouchListener(new DeleteListener(review_id, position ,touch_position ,holder.del_btn, getItem(position)));

         //holder.del_btn.setVisibility(View.VISIBLE);
            //holder.notify_btn.setVisibility(View.VISIBLE);

         return v;
    }

    //-----------------------------------------
    class DeleteListener implements View.OnTouchListener {
        private int review_id;
        private View view;
        ReviewDatabase reviewDatabase;
        ListView review_listview;
        ReviewAdapter adapter;
        ReviewDatabase touch_position;
        int position;
        private ArrayList<ReviewDatabase> review_list = new ArrayList<ReviewDatabase>();



        public DeleteListener(int review_id,int position, ReviewDatabase touch_position, View view, ReviewDatabase reviewDatabase) {
            this.review_id = review_id;
            this.touch_position = touch_position;
            this.view = view;
            this.reviewDatabase = reviewDatabase;
            this.position = position;
        }

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (view.getId() == R.id.del_btn) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    DeletePhp deletePhp = new DeletePhp();

                    Log.d("아이디", String.valueOf(review_id));
                    deletePhp.execute("http://hungry.portfolio1000.com/smarthandongi/review_delete.php?review_id=" + review_id);


                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                }
            }
            return false;
        }

    }
    class DeletePhp extends AsyncTask {

        public DeletePhp() {        }

        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub
            StringBuilder jsonHtml = new StringBuilder();
            String return_str = "";
            System.out.println("hihi");
            while (return_str.equalsIgnoreCase("")) {
                try {
                    URL data_url = new URL((String) params[0]);
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
            }
            return jsonHtml.toString();
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

        }
    }

    static class ViewHolder {
        TextView kakao_nick, content;
        TextView date;
        ImageView new_img;
        Button notify_btn;
        Button del_btn;
    }

}

