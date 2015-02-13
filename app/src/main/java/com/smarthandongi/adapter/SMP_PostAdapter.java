package com.smarthandongi.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smarthandongi.Carrier;
import com.smarthandongi.R;
import com.smarthandongi.database.PostDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2015-02-11.
 */
public class SMP_PostAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<PostDatabase> post_list,all_posting_list;
    private Context context;
    private int layout;
    private Carrier carrier;
    Typeface typeface;

    public  SMP_PostAdapter(Context context, int alayout, ArrayList<PostDatabase> post_list,ArrayList<PostDatabase> all_posting_list, Carrier carrier) {
        this.context=context;
        inflater = LayoutInflater.from(context);
        this.post_list=post_list;
        this.all_posting_list=all_posting_list;
        layout=alayout;
        this.carrier=carrier;
    }
    public int getCount(){return post_list.size();}
    public PostDatabase getItem(int position) {return post_list.get(position);}
    public List<PostDatabase> getLists() {return post_list;}
    public long getItemId(int position) {return position;}

    public View getView(final int position, View convert_view, ViewGroup parent) {

        typeface = Typeface.createFromAsset(context.getAssets(), "KOPUBDOTUM_PRO_LIGHT.OTF");
        if(convert_view==null) {
            convert_view=inflater.inflate(layout,parent,false);
        }

        TextView category = (TextView)convert_view.findViewById(R.id.smp_category);
        category.setTypeface(typeface);
        category.setText(post_list.get(position).getBig_category());

        TextView posting_date = (TextView)convert_view.findViewById(R.id.smp_posting_date);
        posting_date.setTypeface(typeface);
        posting_date.setText(post_list.get(position).getPosting_date());

        TextView title = (TextView)convert_view.findViewById(R.id.smp_title);
        title.setTypeface(typeface);
        title.setText(post_list.get(position).getTitle());

        /*
        Button smp_forward_btn=(Button)convert_view.findViewById(R.id.my_post_forward_btn);
        smp_forward_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=0;
                int fromSMP=1;
                for(int i=0; i<all_posting_list.size();i++) {
                    if(post_list.get(position).getId()==all_posting_list.get(i).getId()) {
                        pos=i;
                        break;
                    }
                }
                carrier.setFromSMP(1);
                Log.d("pos",String.valueOf(pos));
                Intent intent=new Intent(context, PostDetail.class);
                intent.putExtra("post_list",all_posting_list);
                intent.putExtra("position",pos);
                intent.putExtra("post",post_list.get(position));
                intent.putExtra("carrier",carrier);
                context.startActivity(intent);
            }
        });
        */

        return convert_view;
    }



}


