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
            holder.post_category = (ImageView)v.findViewById(R.id.post_category);
            holder.post_group=(TextView)v.findViewById(R.id.group);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder)v.getTag();
        }

        holder.post_title.setText(getItem(position).getTitle()+ " ");
        holder.post_group.setText(getItem(position).getGroup()+" ");


        //나중에 이미지 추가되면 이미지 넣기
        if(getItem(position).getCategory()=="1")
        {holder.post_category.setImageResource(R.drawable.notice_btn);}
        else if(getItem(position).getCategory()=="2")
        {   holder.post_category.setImageResource(R.drawable.notice_btn);}

        else if(getItem(position).getCategory()=="3")
        { holder.post_category.setImageResource(R.drawable.notice_btn);}

        else if(getItem(position).getCategory()=="4")
        { holder.post_category.setImageResource(R.drawable.notice_btn);}

        else if(getItem(position).getCategory()=="5")
        { holder.post_category.setImageResource(R.drawable.notice_btn);}



        return v;
    }

    static class ViewHolder{
        TextView post_title, post_id, post_group, post_dday;
        ImageView post_category;

    }
}

