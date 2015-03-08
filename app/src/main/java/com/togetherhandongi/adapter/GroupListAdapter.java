package com.togetherhandongi.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.togetherhandongi.GroupDatabase1;
import com.togetherhandongi.R;

import java.util.ArrayList;
import java.util.List;

public class GroupListAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private ArrayList<GroupDatabase1> group_list;
    private Context context;
    //private int layout;
    Typeface typeface;

    public  GroupListAdapter(Context context, ArrayList<GroupDatabase1> group_list) {
        this.context=context;
        //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.group_list=group_list;

    }

    public int getCount(){return group_list.size();}
    public GroupDatabase1 getItem(int position) {return group_list.get(position);}
    public List<GroupDatabase1> getLists() {return group_list;}
    public long getItemId(int position) {return position;}

    public View getView(int position, View convert_view, ViewGroup parent) {
        ViewHolder holder;
        View v = convert_view;

        //typeface = Typeface.createFromAsset(context.getAssets(), "KOPUBDOTUM_PRO_MEDIUM.OTF");

        if(v==null) {
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            typeface = Typeface.createFromAsset(context.getAssets(), "KOPUBDOTUM_PRO_MEDIUM.OTF");

            v=inflater.inflate(R.layout.group_listview,null);
            holder = new ViewHolder();

            holder.group_name=(TextView)v.findViewById(R.id.group_name);
            holder.group_name.setTypeface(typeface);

            v.setTag(holder);

        }
        else {
            holder = (ViewHolder)v.getTag();
        }

       // TextView group_name = (TextView)convert_view.findViewById(R.id.group_name);
        holder.group_name.setText(group_list.get(position).getGroup_name());
        holder.group_name.setTypeface(typeface);



        return v;
    }

    static class ViewHolder {
        TextView group_name;
    }

}