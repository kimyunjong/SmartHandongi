package com.smarthandongi.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smarthandongi.GroupDatabase;
import com.smarthandongi.GroupDatabase1;
import com.smarthandongi.R;

import java.util.ArrayList;
import java.util.List;

public class GroupListAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private ArrayList<GroupDatabase1> group_list;
    private Context context;
    private int layout;
    Typeface typeface;

    public  GroupListAdapter(Context context, int alayout, ArrayList<GroupDatabase1> group_list) {
        this.context=context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.group_list=group_list;
        layout=alayout;
    }

    public int getCount(){return group_list.size();}
    public GroupDatabase1 getItem(int position) {return group_list.get(position);}
    public List<GroupDatabase1> getLists() {return group_list;}
    public long getItemId(int position) {return position;}

    public View getView(int position, View convert_view, ViewGroup parent) {
        typeface = Typeface.createFromAsset(context.getAssets(), "KOPUBDOTUM_PRO_LIGHT.OTF");

        if(convert_view==null) {
            convert_view=inflater.inflate(layout,parent,false);
        }

        TextView group_name = (TextView)convert_view.findViewById(R.id.group_name);
        group_name.setTypeface(typeface);
        group_name.setText(group_list.get(position).getGroup_name());


        return convert_view;
    }

}
