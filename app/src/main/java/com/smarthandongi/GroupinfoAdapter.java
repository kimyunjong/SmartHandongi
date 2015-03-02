package com.smarthandongi;

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

    public  GroupinfoAdapter(Context context, ArrayList<GroupDatabase1>group_list, int layout){
        this.context = context;
        this.group_list= group_list;
        this.layout = layout;

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

        typeface = Typeface.createFromAsset(context.getAssets(), "KOPUBDOTUM_PRO_MEDIUM.OTF");
        if (convertView == null) {

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_info_item,null);

        }
        TextView name = (TextView)convertView.findViewById(R.id.group_category);
        name.setTypeface(typeface);
        name.setText(group_list.get(position).getGroup_category());

        TextView local = (TextView)convertView.findViewById(R.id.group_name);
        local.setTypeface(typeface);
        local.setText( group_list.get(position).getGroup_name());


        return convertView;
    }

}

