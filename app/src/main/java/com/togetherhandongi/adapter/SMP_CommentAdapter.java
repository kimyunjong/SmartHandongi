package com.togetherhandongi.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.togetherhandongi.Carrier;
import com.togetherhandongi.R;
import com.togetherhandongi.database.PostDatabase;
import com.togetherhandongi.database.ReviewDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2015-02-12.
 */
public class SMP_CommentAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<ReviewDatabase> comment_list;
    private ArrayList<PostDatabase> posting_list;
    private Context context;
    private int layout;
    private Carrier carrier;
    Typeface typeface;

    public  SMP_CommentAdapter(Context context, int alayout, ArrayList<ReviewDatabase> comment_list, Carrier carrier,ArrayList<PostDatabase> posting_list) {
        this.context=context;
        inflater = LayoutInflater.from(context);
        this.comment_list=comment_list;
        layout=alayout;
        this.carrier=carrier;
        this.posting_list=posting_list;
    }
    public int getCount(){return comment_list.size();}
    public ReviewDatabase getItem(int position) {return comment_list.get(position);}
    public List<ReviewDatabase> getLists() {return comment_list;}
    public long getItemId(int position) {return position;}

    public View getView(final int position, View convert_view, ViewGroup parent) {
        String title;
        typeface = Typeface.createFromAsset(context.getAssets(), "KOPUBDOTUM_PRO_MEDIUM.OTF");

        if (convert_view == null) {
            convert_view = inflater.inflate(layout, parent, false);
        }

        TextView posting_date = (TextView)convert_view.findViewById(R.id.smp_comment_date);
        posting_date.setTypeface(typeface);
        posting_date.setText(comment_list.get(position).getReply_date());

        TextView comment_title =(TextView)convert_view.findViewById(R.id.smp_comment_title);
        comment_title.setTypeface(typeface);
        title=checkTitle(posting_list,comment_list.get(position).getPosting_id());
        comment_title.setText(title);

        TextView comment=(TextView)convert_view.findViewById(R.id.smp_comment);
        comment.setTypeface(typeface);
        comment.setText(comment_list.get(position).getContent());
        return convert_view;
    }

    public String checkTitle(ArrayList<PostDatabase> posting_list,int posting_id) {
        String title=null;

        for(int i=0; i<posting_list.size();i++) {
            if(posting_list.get(i).getId()==posting_id) {
                title=posting_list.get(i).getTitle();
                break;
            }
        }

        return title;
    }
}
