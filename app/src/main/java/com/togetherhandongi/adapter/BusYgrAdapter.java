package com.togetherhandongi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

import com.togetherhandongi.R;
import com.togetherhandongi.database.BusYgrData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BusYgrAdapter extends BaseAdapter{ //-------------------------- 배달업체 리스트
	
	private LayoutInflater inflater;
	private List<BusYgrData> list;
	private Context context;
	private SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.KOREA);
	boolean past_now = false;
	boolean past_now_checked = false;
    Typeface typeface;
	
	public BusYgrAdapter(Context context, List<BusYgrData> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public BusYgrData getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}
	
	public List<BusYgrData> getLists(){
		   return list;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		View v = convertView;

		if (v == null) {
			inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.bus_ygr_item, null);
			holder = new ViewHolder();
            typeface = Typeface.createFromAsset(context.getAssets(), "KOPUBDOTUM_PRO_MEDIUM.OTF");
			holder.times = (LinearLayout)v.findViewById(R.id.times);
			holder.divider = (LinearLayout)v.findViewById(R.id.divider);
			holder.line = (RelativeLayout)v.findViewById(R.id.line);
			holder.s1rl = (RelativeLayout)v.findViewById(R.id.s1rl);
			holder.s2rl = (RelativeLayout)v.findViewById(R.id.s2rl);
			holder.s3rl = (RelativeLayout)v.findViewById(R.id.s3rl);
			holder.s4rl = (RelativeLayout)v.findViewById(R.id.s4rl);
			holder.s5rl = (RelativeLayout)v.findViewById(R.id.s5rl);
			holder.s1 = (TextView)v.findViewById(R.id.s1);
			holder.s2 = (TextView)v.findViewById(R.id.s2);
			holder.s3 = (TextView)v.findViewById(R.id.s3);
			holder.s4 = (TextView)v.findViewById(R.id.s4);
			holder.s5 = (TextView)v.findViewById(R.id.s5);
            holder.afternoon = (TextView)v.findViewById(R.id.afternoon);
			holder.s1.setTypeface(typeface);
            holder.s2.setTypeface(typeface);
            holder.s3.setTypeface(typeface);
            holder.s4.setTypeface(typeface);
            holder.s5.setTypeface(typeface);
            holder.afternoon.setTypeface(typeface);
			v.setTag(holder);	
		}
		else {
			holder = (ViewHolder)v.getTag();
		}
		
		if (getItem(position).getDivider()==false) {
			holder.divider.setVisibility(View.GONE);
			holder.times.setVisibility(View.VISIBLE);
			
			if (getItem(position).getHide_line())
				holder.line.setVisibility(View.GONE);
			else
				holder.line.setVisibility(View.VISIBLE);
			
			if (getItem(position).getMerge_code() == 1) {
				holder.s1rl.setVisibility(View.GONE);
				holder.s3rl.setVisibility(View.VISIBLE);
				holder.s5rl.setVisibility(View.VISIBLE);
				LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        0,
                        LayoutParams.MATCH_PARENT, 2.0f);
				holder.s2rl.setLayoutParams(param);
				LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
                        0,
                        LayoutParams.MATCH_PARENT, 1.0f);
				holder.s4rl.setLayoutParams(param2);
				holder.s2.setTextColor(Color.parseColor("#bfbfbf"));
				holder.s4.setTextColor(Color.parseColor("#231f20"));
			}
			else if (getItem(position).getMerge_code() == 2) {
				holder.s1rl.setVisibility(View.GONE);
				holder.s3rl.setVisibility(View.GONE);
				holder.s5rl.setVisibility(View.VISIBLE);
				LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        0,
                        LayoutParams.MATCH_PARENT, 3.0f);
				holder.s2rl.setLayoutParams(param);
				LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
                        0,
                        LayoutParams.MATCH_PARENT, 1.0f);
				holder.s4rl.setLayoutParams(param2);
				holder.s2.setTextColor(Color.parseColor("#bfbfbf"));
				holder.s4.setTextColor(Color.parseColor("#231f20"));
			}
			else if (getItem(position).getMerge_code() == 3) {
				holder.s3rl.setVisibility(View.GONE);
				holder.s5rl.setVisibility(View.GONE);
				holder.s1rl.setVisibility(View.VISIBLE);
				LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        0,
                        LayoutParams.MATCH_PARENT, 3.0f);
				holder.s4rl.setLayoutParams(param);
				LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
                        0,
                        LayoutParams.MATCH_PARENT, 1.0f);
				holder.s2rl.setLayoutParams(param2);
				holder.s2.setTextColor(Color.parseColor("#231f20"));
				holder.s4.setTextColor(Color.parseColor("#bfbfbf"));
			}
			else {
				holder.s1rl.setVisibility(View.VISIBLE);
				holder.s3rl.setVisibility(View.VISIBLE);
				holder.s5rl.setVisibility(View.VISIBLE);
				LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        0,
                        LayoutParams.MATCH_PARENT, 1.0f);
				holder.s2rl.setLayoutParams(param);
				holder.s4rl.setLayoutParams(param);
				holder.s2.setTextColor(Color.parseColor("#231f20"));
				holder.s4.setTextColor(Color.parseColor("#231f20"));
			}
		}
		else {
			holder.divider.setVisibility(View.VISIBLE);
			holder.times.setVisibility(View.GONE);
		}
		
		Date date = new Date();
		String format = formatter.format(date);
		
		if (!getItem(position).getDivider()) {
			if (toInt(format) > toInt(getItem(position).getS1())) {
				holder.s1.setTextColor(Color.parseColor("#bcbec0"));
			}
			else {
				holder.s1.setTextColor(Color.parseColor("#231f20"));
				past_now = true;
			}
			holder.s1.setText(convert(getItem(position).getS1()));
			
			if (toInt(format) > toInt(getItem(position).getS2())) {
				holder.s2.setTextColor(Color.parseColor("#bcbec0"));
			}
			else {
				holder.s2.setTextColor(Color.parseColor("#231f20"));
				past_now = true;
				}
				
			holder.s2.setText(getItem(position).getMerge_code()!=1 && getItem(position).getMerge_code()!=2 ? convert(getItem(position).getS2()) : getItem(position).getS2());
			
			if (toInt(format) > toInt(getItem(position).getS3())) {
				holder.s3.setTextColor(Color.parseColor("#bcbec0"));
			}
			else {
				holder.s3.setTextColor(Color.parseColor("#231f20"));
				past_now = true;
			}
			holder.s3.setText(convert(getItem(position).getS3()));
			
			if (toInt(format) > toInt(getItem(position).getS4())) {
				holder.s4.setTextColor(Color.parseColor("#bcbec0"));
			}
			else {
				holder.s4.setTextColor(Color.parseColor("#231f20"));
				past_now = true;
				}
			holder.s4.setText(getItem(position).getMerge_code()!=3 ? convert(getItem(position).getS4()) : getItem(position).getS4());
			
			if (toInt(format) > toInt(getItem(position).getS5())) {
				holder.s5.setTextColor(Color.parseColor("#bcbec0"));
			}
			else {
				holder.s5.setTextColor(Color.parseColor("#231f20"));
				past_now = true;
			}
			holder.s5.setText(convert(getItem(position).getS5()));
			
			holder.line.setBackgroundColor(getItem(position).getPast() ? Color.parseColor("#ff4200") : Color.parseColor("#eaeaea"));
			
		}
		
		return v;
	}
	
	public String convert(String str) {
		if (str.equalsIgnoreCase("-") || str.equalsIgnoreCase(""))		
			return str;
		else {
			if (str.substring(0, str.indexOf(":")).equals("12") || str.substring(0, str.indexOf(":")).equals("24"))
				str = "12" + str.subSequence(str.indexOf(":"), str.length());
			else
				str = String.valueOf(Integer.parseInt(str.substring(0, str.indexOf(":")))%12) + str.subSequence(str.indexOf(":"), str.length());
			
			return str;
		}
	}
	
	public int toInt(String str) {
		if (str.indexOf(":") > 0)
			return Integer.parseInt(str.replace(":", ""));
		else
			return 0;
	}
	
	static class ViewHolder{
			LinearLayout times, divider;
			RelativeLayout line, s1rl, s2rl, s3rl, s4rl, s5rl;
			TextView s1, s2, s3, s4, s5, afternoon;
	   }
}