package com.smarthandongi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smarthandongi.R;
import com.smarthandongi.database.PostDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2015-02-11.
 */
public class SMP_PostAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<PostDatabase> post_list;
    private Context context;
    private int layout;

    public  SMP_PostAdapter(Context context, int alayout, ArrayList<PostDatabase> post_list) {
        this.context=context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.post_list=post_list;
        layout=alayout;
    }
    public int getCount(){return post_list.size();}
    public PostDatabase getItem(int position) {return post_list.get(position);}
    public List<PostDatabase> getLists() {return post_list;}
    public long getItemId(int position) {return position;}

    public View getView(int position, View convert_view, ViewGroup parent) {

        if(convert_view==null) {
            convert_view=inflater.inflate(layout,parent,false);
        }

        TextView category = (TextView)convert_view.findViewById(R.id.smp_category);
        category.setText(post_list.get(position).getBig_category());

        TextView posting_date = (TextView)convert_view.findViewById(R.id.smp_posting_date);
        posting_date.setText(post_list.get(position).getPosting_date());

        TextView title = (TextView)convert_view.findViewById(R.id.smp_title);
        title.setText(post_list.get(position).getTitle());

        return convert_view;
    }
}
