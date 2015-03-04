package com.smarthandongi.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smarthandongi.R;
import com.smarthandongi.database.PostDatabase;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Joel on 2015-01-23.
 */
public class Post2Adapter extends BaseAdapter{

    private LayoutInflater inflater;
    private List<PostDatabase> list;
    private Context context;
    Calendar calendar;

    Typeface typeface;

    public Post2Adapter(Context context, List<PostDatabase> list){
        this.context=context;
        this.list=list;
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
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.post_item2, null);

            holder = new ViewHolder();
            holder.firstday_check_img=(ImageView)v.findViewById(R.id.first_date_img);
            holder.post_title = (TextView)v.findViewById(R.id.title);
            holder.first_date=(TextView)v.findViewById(R.id.first_date);
            holder.post_group=(TextView)v.findViewById(R.id.group);
            holder.left_bar=(ImageView)v.findViewById(R.id.left_bar);
            holder.first_weekday=(TextView)v.findViewById(R.id.first_weekday);
            holder.upperLine=(LinearLayout)v.findViewById(R.id.item_upperline);
            holder.first_empty_layout=(LinearLayout)v.findViewById(R.id.first_empty_layout);
            holder.first_empty_img=(ImageView)v.findViewById(R.id.left_bar_first);

            v.setTag(holder);


            //폰트
            typeface = Typeface.createFromAsset(context.getAssets(), "KOPUBDOTUM_PRO_LIGHT.OTF");
            holder.post_title.setTypeface(typeface);
            holder.first_date.setTypeface(typeface);
            holder.post_group.setTypeface(typeface);
            holder.first_weekday.setTypeface(typeface);

        }
        else {
            holder = (ViewHolder)v.getTag();
        }

        holder.post_title.setText(getItem(position).getTitle()+ " ");
        holder.first_empty_img.setImageResource(R.drawable.timeline_non_first_img);
        if(getItem(position).getGroup_name().compareTo("")==0)
        {
            holder.post_group.setText("["+getItem(position).getKakao_nic()+"]"+" ");
        }
        else if(getItem(position).getGroup_name().compareTo("")!=0)
        {
            holder.post_group.setText("["+getItem(position).getGroup_name()+"]"+" ");
        }





        //나중에 이미지 추가되면 이미지 넣기
        if(getItem(position).getFirst_day())
        {   holder.left_bar.setImageResource(R.drawable.timeline_first_img);


            if(getItem(position).getLastDay())
            {
                holder.upperLine.setAlpha(0f);
                holder.first_empty_layout.setVisibility(View.GONE);
            }
            else
            {
                holder.upperLine.setAlpha(1f);
                holder.first_empty_layout.setVisibility(View.VISIBLE);
            }
            if(getItem(position).getDday()==0)
            {holder.first_date.setText("오늘");
                holder.first_weekday.setText("");}
            else if(getItem(position).getDday()==-1)
            {
                holder.first_date.setText("내일");
                holder.first_weekday.setText("");
            }
            else if(getItem(position).getDday()==-2)
            {
                holder.first_date.setText("모레");
                holder.first_weekday.setText("");
            }
            else{
                int temp=Integer.parseInt(getItem(position).getStart_date());
                int s_year;
                int s_month;
                int s_day;
                s_year=temp/10000;
                temp=temp-(s_year*10000);
                s_month=temp/100;
                temp=temp-s_month*100;
                s_day=temp;
                holder.first_date.setText(String.valueOf(s_month)+"."+String.valueOf(s_day)+"");

                Calendar calendar=Calendar.getInstance();
                calendar.set(s_year,s_month-1,s_day);

                if(calendar.get(Calendar.DAY_OF_WEEK)==1)
                {
                    holder.first_weekday.setText("일요일");
                }
                else if(calendar.get(Calendar.DAY_OF_WEEK)==2)
                {
                    holder.first_weekday.setText("월요일");
                }
                else if(calendar.get(Calendar.DAY_OF_WEEK)==3)
                {
                    holder.first_weekday.setText("화요일");
                }
                else if(calendar.get(Calendar.DAY_OF_WEEK)==4)
                {
                    holder.first_weekday.setText("수요일");
                }
                else if(calendar.get(Calendar.DAY_OF_WEEK)==5)
                {
                    holder.first_weekday.setText("목요일");
                }
                else if(calendar.get(Calendar.DAY_OF_WEEK)==6)
                {
                    holder.first_weekday.setText("금요일");
                }
                else if(calendar.get(Calendar.DAY_OF_WEEK)==7)
                {
                    holder.first_weekday.setText("토요일");
                }
            }
        }

        else{
            holder.left_bar.setImageResource(R.drawable.timeline_non_first_img);

            holder.first_empty_layout.setVisibility(View.GONE);
            holder.first_date.setText("  ");
            holder.first_weekday.setText("");
            holder.upperLine.setAlpha(0f);
        }


        return v;
    }

    static class ViewHolder{
        TextView post_title, post_group,first_date,first_weekday;
        ImageView left_bar,firstday_check_img,first_empty_img;
        LinearLayout upperLine,first_empty_layout;
    }
}

