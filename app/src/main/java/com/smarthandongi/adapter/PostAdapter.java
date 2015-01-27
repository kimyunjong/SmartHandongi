package com.smarthandongi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarthandongi.R;
import com.smarthandongi.database.PostDatabase;
import com.smarthandongi.dday;

import java.util.List;

/**
 * Created by Joel on 2015-01-23.
 */
public class PostAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private List<PostDatabase> list;
    private Context context;
    dday deadline=new dday();

    String temp_date;
    int s_date;
    int s_year;
    int s_month;
    int s_day;
    String temp_date_e;
    int e_date;
    int e_year;
    int e_month;
    int e_day;

    public PostAdapter(Context context, List<PostDatabase> list){
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

    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        ViewHolder holder;
        View v = convertView;
        if (v == null) {
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.post_item, null);
            holder = new ViewHolder();

            holder.post_title = (TextView)v.findViewById(R.id.post_title);
            holder.post_id = (TextView)v.findViewById(R.id.post_id);
            holder.post_category = (ImageView)v.findViewById(R.id.post_category);
            holder.post_dday = (TextView)v.findViewById(R.id.post_dday);
            holder.post_group=(TextView)v.findViewById(R.id.post_group);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder)v.getTag();
        }

        holder.post_title.setText(getItem(position).getTitle()+ " ");
        holder.post_id.setText(getItem(position).getId()+ " ");
        holder.post_group.setText(getItem(position).getGroup()+" ");

        temp_date=getItem(position).getStart_date();
        s_date=Integer.parseInt(temp_date);


        s_year=s_date/10000;
        s_date=s_date-(s_year*10000);
        s_month=s_date/100;
        s_date=s_date-s_month*100;
        s_day=s_date;
        System.out.println(s_year+"년"+s_month+"월"+s_day+"일"+"확인하려는 것 음수인지 아닌지");

        temp_date_e=getItem(position).getEnd_date();
        e_date=Integer.parseInt(temp_date_e);

        e_year=e_date/10000;
        e_date=e_date-(s_year*10000);
        e_month=e_date/100;
        e_date=e_date-e_month*100;
        e_day=e_date;



         int dday_s=deadline.caldate(s_year,s_month-1,s_day+1);
        int dday_e=deadline.caldate(e_year,e_month-1,e_day+1);
        System.out.println(dday_e+"시작"+dday_s+"끝"+"확인하려는것입니다."+getItem(position).getId());

        if(dday_s<0)
        { holder.post_dday.setText(String.valueOf(dday_s));}
        else if(dday_s>=0&&dday_e<=0){
            holder.post_dday.setText("진행중");
        }
        else{
            holder.post_dday.setText("종료");
        }

       //나중에 이미지 추가되면 이미지 넣기
        if(getItem(position).getCategory().equalsIgnoreCase("1"))
        {holder.post_category.setImageResource(R.drawable.notice);
            }
        else if(getItem(position).getCategory().equalsIgnoreCase("2"))
        {   holder.post_category.setImageResource(R.drawable.outer);
            }

        else if(getItem(position).getCategory().equalsIgnoreCase("3"))
        { holder.post_category.setImageResource(R.drawable.seminar);
            }

        else if(getItem(position).getCategory().equalsIgnoreCase("4"))
        { holder.post_category.setImageResource(R.drawable.recruit);
            }

        else if(getItem(position).getCategory().equalsIgnoreCase("5"))
        { holder.post_category.setImageResource(R.drawable.agora);
            }



        return v;
    }

    static class ViewHolder{
        TextView post_title, post_id, post_group, post_dday;
        ImageView post_category;

    }
}
