package com.togetherhandongi;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015-02-06.
 */
public class GroupinfoAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<GroupDatabase1> group_list;
    private Context context;
    private int layout ;
    Typeface typeface;

    public  GroupinfoAdapter(Context context, ArrayList<GroupDatabase1>group_list){
        this.context = context;
        this.group_list= group_list;


    }
    public int getCount(){
        return group_list.size();
    }
    public GroupDatabase1 getItem(int position){
        return group_list.get(position);
    }
    public List<GroupDatabase1> getlist(){
        return group_list;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View v= convertView;
        //typeface = Typeface.createFromAsset(context.getAssets(), "KOPUBDOTUM_PRO_MEDIUM.OTF");
        if (v == null) {

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            typeface = Typeface.createFromAsset(context.getAssets(), "KOPUBDOTUM_PRO_MEDIUM.OTF");

            v = inflater.inflate(R.layout.group_info_item,null);
            holder  = new ViewHolder();

            holder.name =  (TextView)v.findViewById(R.id.group_category);
            holder.local = (TextView)v.findViewById(R.id.group_name);

            holder.name.setTypeface(typeface);
            holder.local.setTypeface(typeface);

            v.setTag(holder);
        }
        else {
            holder = (ViewHolder)v.getTag();
        }
        //TextView name = (TextView)convertView.findViewById(R.id.group_category);
        holder.name.setTypeface(typeface);
        holder.name.setText(group_list.get(position).getGroup_category());

        //TextView local = (TextView)convertView.findViewById(R.id.group_name);
        holder.local.setTypeface(typeface);
        holder.local.setText( group_list.get(position).getGroup_name());


        return v;
    }

    static class ViewHolder {
        TextView name,local;
    }

}

