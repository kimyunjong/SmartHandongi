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
    private String big_category,small_category;
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
        switch(post_list.get(position).getBig_category()){                                                     //대분류
            case "1" : big_category = "일반공지"; break;
            case "2" : big_category = "대외활동"; break;
            case "3" : big_category = "공연•세미나"; break;
            case "4" : big_category = "리쿠르팅"; break;
            case "5" : big_category = "붙어라"; break;
            default: break;
        }
        switch(post_list.get(position).getCategory()){
            case "together_sports_1"        : small_category = "운동경기"; break;          //소분류
            case "together_game_2"          : small_category = "게임"; break;
            case "together_nightfood_3"     : small_category = "야식"; break;
            case "together_gonggu_4"        : small_category = "공동구매"; break;
            case "together_carpool_5"       : small_category = "카풀"; break;
            case "together_study_6"         : small_category = "스터디"; break;
            case "together_trading_7"       : small_category = "사고팔기"; break;
            case "together_lost_8"          : small_category = "분실물"; break;
            case "together_recruiting_9"    : small_category = "구인구직"; break;
            case "together_exchange_10"     : small_category = "교환"; break;

            case "outer_contest_21"     : small_category = "공모전"; break;
            case "outer_intern_22"      : small_category = "인턴"; break;
            case "outer_service_23"     : small_category = "자원봉사"; break;

            case "seminar_perf_41"          : small_category = "공연"; break;
            case "seminar_seminar_42"       : small_category = "세미나"; break;
            case "seminar_presentation_43"  : small_category = "발표"; break;

            case "recruiting_scholarship_61" : small_category = "학술"; break;
            case "recruiting_sports_62"      : small_category = "운동"; break;
            case "recruiting_perf_63"        : small_category = "공연"; break;
            case "recruiting_faith_64"       : small_category = "신앙"; break;
            case "recruiting_display_65"     : small_category = "전시"; break;
            case "recruiting_service_66"     : small_category = "봉사"; break;
        }


        TextView category = (TextView)convert_view.findViewById(R.id.smp_category);
        category.setTypeface(typeface);
        if(post_list.get(position).getBig_category().compareTo("1")==0) {
            category.setText("[" + big_category + "]");
        }
        else {
            category.setText("[" + big_category + "/" + small_category + "]");
        }



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


