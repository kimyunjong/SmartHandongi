package com.smarthandongi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smarthandongi.R;
import com.smarthandongi.database.PostDatabase;

import java.util.List;

/**
 * Created by Joel on 2015-01-23.
 */
public class Post2Adapter extends BaseAdapter{

    private LayoutInflater inflater;
    private List<PostDatabase> list;
    private Context context;

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

            holder.post_title = (TextView)v.findViewById(R.id.title);
            holder.first_date=(TextView)v.findViewById(R.id.first_date);
            holder.post_group=(TextView)v.findViewById(R.id.group);
            holder.left_bar=(ImageView)v.findViewById(R.id.left_bar);
            v.setTag(holder);

        }
        else {
            holder = (ViewHolder)v.getTag();
        }

        holder.post_title.setText(getItem(position).getTitle()+ " ");
        holder.post_group.setText(getItem(position).getGroup()+" ");


        //나중에 이미지 추가되면 이미지 넣기
        if(getItem(position).getFirst_day())
        {   holder.left_bar.setImageResource(R.drawable.first_event);
            if(getItem(position).getDday()==0)
                {holder.first_date.setText("오늘");}
            else if(getItem(position).getDday()==-1)
                {
                    holder.first_date.setText("내일");
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
                holder.first_date.setText(String.valueOf(s_month)+"월 "+String.valueOf(s_day)+"일");
             }
        }

        else{
            holder.left_bar.setImageResource(R.drawable.non_first_event);
            holder.first_date.setText("  ");
        }


        return v;
    }

    static class ViewHolder{
        TextView post_title, post_group,first_date;
        ImageView left_bar;

    }
}

