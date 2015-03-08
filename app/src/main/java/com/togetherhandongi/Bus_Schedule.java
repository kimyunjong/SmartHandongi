package com.togetherhandongi;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.togetherhandongi.adapter.BusYgrAdapter;
import com.togetherhandongi.database.BusYgrData;

public class Bus_Schedule extends Activity{
	Carrier passed;
	Intent get_intent;
	ListView ygr_listview, ygr_weekend_listview;
	ArrayList<BusYgrData> ygr_list, ygr_weekend_list;
	BusYgrAdapter ygr_adapter, ygr_weekend_adapter;
	LinearLayout top_ygr, top_hh;
	Date today;
	Calendar calendar;
	private SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.KOREA);
	FrameLayout weekday, weekend;
	RelativeLayout heunghae;
	boolean past = false;
	boolean weekday_check = false, weekend_check = false;
	String format;
	String phone_no;
	Intent dial_intent;
	
	ImageButton bt1, bt2, bt3, bt4;
	
	protected void onStart() {
		super.onStart();

	}
	@Override
	protected void onStop() {
		super.onStop();

	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //
		get_intent = getIntent();
		passed = (Carrier)get_intent.getSerializableExtra("passed");
		setContentView(R.layout.bus_schedule);
		weekday = (FrameLayout)findViewById(R.id.weekday_schedule);
		weekend = (FrameLayout)findViewById(R.id.weekend_schedule);
		heunghae = (RelativeLayout)findViewById(R.id.heunghae_schedule);
		top_ygr = (LinearLayout)findViewById(R.id.top_ygr);
		top_hh = (LinearLayout)findViewById(R.id.top_hh);
		
		Date today = new Date();
		calendar = Calendar.getInstance();
		calendar.setTime(today);
		format = formatter.format(today);
		
		bt1 = (ImageButton)findViewById(R.id.bt_1);
		bt1.setOnTouchListener(new OnTouchListener() {	//------------------------------- 리뷰로
			@SuppressLint("NewApi") @Override
			public boolean onTouch(View view, MotionEvent event) {
				if (event.getAction()==MotionEvent.ACTION_DOWN)
					bt1.setBackgroundResource(R.drawable.bus_weekday_over);
				else if (event.getAction()==MotionEvent.ACTION_UP) {
					bt2.setBackgroundResource(R.drawable.bus_weekend);
					bt3.setBackgroundResource(R.drawable.bus_heunghae);
					weekday.setVisibility(View.VISIBLE);
					weekend.setVisibility(View.GONE);
					heunghae.setVisibility(View.GONE);
					top_ygr.setVisibility(View.VISIBLE);
					top_hh.setVisibility(View.GONE);
				}
				return false;
			}
		});
		
		bt2 = (ImageButton)findViewById(R.id.bt_2);
		bt2.setOnTouchListener(new OnTouchListener() {	//------------------------------- 리뷰로
			@SuppressLint("NewApi") @Override
			public boolean onTouch(View view, MotionEvent event) {
				if (event.getAction()==MotionEvent.ACTION_DOWN)
					bt2.setBackgroundResource(R.drawable.bus_weekend_over);
				else if (event.getAction()==MotionEvent.ACTION_UP) {
					bt1.setBackgroundResource(R.drawable.bus_weekday);
					bt3.setBackgroundResource(R.drawable.bus_heunghae);
					weekday.setVisibility(View.GONE);
					weekend.setVisibility(View.VISIBLE);
					heunghae.setVisibility(View.GONE);
					top_ygr.setVisibility(View.VISIBLE);
					top_hh.setVisibility(View.GONE);
				}
				return false;
			}
		});
		
		bt3 = (ImageButton)findViewById(R.id.bt_3);
		bt3.setOnTouchListener(new OnTouchListener() {	//------------------------------- 리뷰로
			@SuppressLint("NewApi") @Override
			public boolean onTouch(View view, MotionEvent event) {
				if (event.getAction()==MotionEvent.ACTION_DOWN)
					bt3.setBackgroundResource(R.drawable.bus_heunghae_over);
				else if (event.getAction()==MotionEvent.ACTION_UP) {
					bt2.setBackgroundResource(R.drawable.bus_weekend);
					bt1.setBackgroundResource(R.drawable.bus_weekday);
					weekday.setVisibility(View.GONE);
					weekend.setVisibility(View.GONE);
					heunghae.setVisibility(View.VISIBLE);
					top_ygr.setVisibility(View.GONE);
					top_hh.setVisibility(View.VISIBLE);
				}
				return false;
			}
		});
		
		bt4 = (ImageButton)findViewById(R.id.bt_4);
		bt4.setOnTouchListener(new OnTouchListener() {	//------------------------------- 리뷰로
			@SuppressLint("NewApi") @Override
			public boolean onTouch(View view, MotionEvent event) {
				if (event.getAction()==MotionEvent.ACTION_DOWN)
					bt4.setBackgroundResource(R.drawable.bus_taxi_over);
				else if (event.getAction()==MotionEvent.ACTION_UP) {
					bt4.setBackgroundResource(R.drawable.bus_taxi);
					
					new AlertDialog.Builder(Bus_Schedule.this).setItems(R.array.taxi, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							switch (which) {
							case 0:
								dial_intent = new Intent(Intent.ACTION_DIAL);
								phone_no = "0542312330";
								dial_intent.setData(Uri.parse("tel:"+ phone_no));
								startActivity(dial_intent);
								break;
							case 1:
								dial_intent = new Intent(Intent.ACTION_DIAL);
								phone_no = "0542826161";
								dial_intent.setData(Uri.parse("tel:"+ phone_no));
								startActivity(dial_intent);
								break;
							case 2:		
								dial_intent = new Intent(Intent.ACTION_DIAL);
								phone_no = "054-276-1442";
								dial_intent.setData(Uri.parse("tel:"+ phone_no));
								startActivity(dial_intent);
								break;
							case 3:		
								dial_intent = new Intent(Intent.ACTION_DIAL);
								phone_no = "0542521111";
								dial_intent.setData(Uri.parse("tel:"+ phone_no));
								startActivity(dial_intent);
								break;
							}
						}
					}).show();
					
				}
				return false;
			}
		});
		
		ygr_listview = (ListView)findViewById(R.id.ygr_list_view);
		ygr_list = new ArrayList<BusYgrData>();
		
		ygr_list.add( new BusYgrData("-", "-", "6:40", "6:55", "7:15", false, true, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("", "두호주민센터 앞 주차장 7시 35분", "", "7:40", "8:00", false, false, 2, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("7:05", "7:25", "7:40", "7:55", "8:15", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("", "환호동 정차지점", "", "7:55", "8:15", false, false, 2, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("", "바로크 가구점 앞 출발", "", "7:55", "8:15", false, false, 2, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("7:35", "7:55", "8:10", "8:25", "8:45", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("", "환호동 정차지점", "", "8:25", "8:45", false, false, 2, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("8:30", "8:50", "9:05", "9:20", "9:40", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("8:55", "9:15", "-", "9:20", "9:40", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("", "바로크 가구점 앞 출발", "", "9:20", "9:40", false, false, 2, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("9:00", "9:20", "9:35", "9:50", "10:10", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("9:50", "10:10", "-", "10:15", "10:35", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("10:00", "10:20", "10:35", "10:50", "11:10", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("10:25", "10:45", "-", "10:50", "11:10", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("", "바로크 가구점 앞 출발", "", "10:50", "11:10", false, false, 2, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("11:20", "11:40", "-", "11:45", "12:05", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("11:35", "11:55", "12:10", "12:25", "12:45", false, false, 0, past)); weekday_past_check();
		
		ygr_list.add( new BusYgrData("", "", "", "", "", true, false, 0, past));
		
		ygr_list.add( new BusYgrData("12:00", "12:20", "-", "12:25", "12:45", false, true, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("12:40", "13:00", "-", "13:05", "13:25", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("13:00", "13:20", "13:35", "13:50", "14:10", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("13:25", "13:45", "-", "13:50", "14:10", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("14:00", "14:20", "-", "14:25", "14:45", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("14:30", "14:50", "15:05", "15:20", "15:40", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("14:30", "14:50", "-", "14:55", "15:15", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("14:55", "15:15", "-", "15:20", "15:40", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("15:20", "15:40", "15:55", "16:10", "16:30", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("15:40", "16:00", "-", "16:05", "16:25", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("16:00", "16:20", "-", "16:25", "16:45", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("16:00", "16:20", "16:35", "16:50", "17:10", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("16:40", "17:00", "-", "17:05", "17:25", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("16:40", "17:00", "17:15", "17:30", "17:50", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("17:00", "17:20", "-", "17:25", "17:45", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("17:30", "17:50", "-", "17:55", "18:15", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("17:30", "17:50", "18:05", "18:20", "18:40", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("17:50", "18:10", "18:25", "18:40", "19:00", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("18:15", "18:35", "18:50", "19:05", "19:25", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("18:15", "18:35", "-", "18:40", "19:00", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("18:30", "18:50", "19:05", "19:20", "19:40", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("19:00", "19:20", "-", "19:25", "19:45", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("19:00", "19:20", "19:35", "19:50", "20:10", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("19:20", "19:40", "-", "19:45", "20:05", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("19:40", "20:00", "20:15", "20:30", "20:50", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("20:00", "20:20", "-", "20:25", "20:45", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("20:20", "20:40", "-", "20:45", "21:05", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("20:40", "21:00", "21:15", "21:30", "21:50", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("21:00", "21:20", "-", "21:25", "21:45", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("21:20", "21:40", "-", "21:45", "22:05", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("21:40", "22:00", "22:15", "22:30", "22:50", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("22:00", "22:20", "-", "22:25", "22:45", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("22:20", "22:40", "-", "22:45", "23:05", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("22:55", "23:15", "23:30", "23:45", "24:05", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("22:55", "23:15", "-", "23:15", "23:35", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("23:20", "23:40", "-", "23:45", "24:05", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("23:40", "24:00", "24:15", "24:30", "24:50", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("23:40", "24:00", "-", "24:05", "24:25", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("24:20", "24:40", "-", "-", "-", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("24:50", "25:00", "25:15", "-", "-", false, false, 0, past)); weekday_past_check();
		ygr_list.add( new BusYgrData("24:50", "25:00", "", "정원 초과시 운행", "", false, false, 3, past)); weekday_past_check();
		
		ygr_adapter = new BusYgrAdapter(Bus_Schedule.this, ygr_list);
		ygr_listview.setAdapter(ygr_adapter);
		
		//---------------------------------------------------------------------------------------------- 주말
		
		ygr_weekend_listview = (ListView)findViewById(R.id.ygr_weekend_list_view);
		ygr_weekend_list = new ArrayList<BusYgrData>();
		
		ygr_weekend_list.add( new BusYgrData("-", "-", "7:30", "7:45", "8:05", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("7:40", "8:00", "8:15", "8:30", "8:50", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("8:20", "8:40", "-", "8:45", "9:05", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("9:00", "9:20", "9:35", "9:50", "10:10", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("9:30", "9:50", "10:05", "10:20", "10:40", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("10:00", "10:20", "10:35", "10:50", "11:10", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("10:20", "10:40", "10:55", "11:10", "11:30", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("10:40", "11:00", "-", "11:05", "11:25", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("11:00", "11:20", "11:35", "11:50", "12:10", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("11:20", "11:40", "-", "11:45", "12:05", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("11:40", "12:00", "12:15", "12:30", "12:50", false, false, 0, past)); weekend_past_check();
		
		ygr_weekend_list.add( new BusYgrData("", "", "", "", "", true, false, 0, past));
		
		ygr_weekend_list.add( new BusYgrData("12:00", "12:20", "-", "12:25", "12:45", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("12:20", "12:40", "12:55", "13:10", "13:30", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("12:40", "13:00", "-", "13:05", "13:25", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("13:00", "13:20", "13:35", "13:50", "14:10", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("13:20", "13:40", "13:55", "14:10", "14:30", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("13:40", "14:00", "14:15", "14:30", "14:50", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("14:00", "14:20", "-", "14:25", "14:45", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("14:20", "14:40", "14:55", "15:10", "15:30", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("14:40", "15:00", "-", "15:05", "15:25", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("15:00", "15:20", "15:35", "15:50", "16:10", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("15:20", "15:40", "-", "15:45", "16:05", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("15:40", "16:00", "16:15", "16:30", "16:50", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("16:00", "16:20", "-", "16:25", "16:45", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("16:20", "16:40", "16:55", "17:10", "17:30", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("16:40", "17:00", "-", "17:05", "17:25", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("17:00", "17:20", "17:35", "17:50", "18:10", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("17:20", "17:40", "17:55", "18:10", "18:30", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("17:40", "18:00", "-", "18:05", "18:25", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("18:00", "18:20", "18:35", "18:50", "19:10", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("18:20", "18:40", "18:55", "19:10", "19:30", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("18:40", "19:00", "-", "19:05", "19:25", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("19:00", "19:20", "19:35", "19:50", "20:10", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("19:20", "19:40", "19:55", "20:10", "20:30", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("19:40", "20:00", "-", "20:05", "20:25", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("20:00", "20:20", "20:35", "20:50", "21:10", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("20:20", "20:40", "20:55", "21:10", "21:30", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("20:40", "21:00", "-", "21:05", "21:25", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("21:00", "21:20", "21:35", "21:50", "22:10", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("21:30", "21:50", "22:05", "22:20", "22:40", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("21:50", "22:10", "22:25", "22:40", "23:00", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("22:20", "22:40", "22:55", "23:10", "23:30", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("23:05", "23:25", "-", "23:30", "23:50", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("24:00", "24:20", "24:35", "-", "-", false, false, 0, past)); weekend_past_check();
		ygr_weekend_list.add( new BusYgrData("24:00", "24:20", "", "정원 초과시 운행", "", false, false, 3, past)); weekend_past_check();
		
		ygr_weekend_adapter = new BusYgrAdapter(Bus_Schedule.this, ygr_weekend_list);
		ygr_weekend_listview.setAdapter(ygr_weekend_adapter);
		
		
		
		if (calendar.get(Calendar.DAY_OF_WEEK) == 7 || calendar.get(Calendar.DAY_OF_WEEK) == 1) {
			weekend.setVisibility(View.VISIBLE);
			weekday.setVisibility(View.GONE);
			heunghae.setVisibility(View.GONE);
			bt1.setBackgroundResource(R.drawable.bus_weekday);
			bt2.setBackgroundResource(R.drawable.bus_weekend_over);
		}
		else {
			weekend.setVisibility(View.GONE);
			weekday.setVisibility(View.VISIBLE);
			heunghae.setVisibility(View.GONE);
			bt1.setBackgroundResource(R.drawable.bus_weekday_over);
			bt2.setBackgroundResource(R.drawable.bus_weekend);
		}
	}	
	
	public void weekday_past_check() {
		if (!weekday_check) {
			if (toInt(format) < toInt(ygr_list.get(ygr_list.size()-1).getS1()) || 
				toInt(format) < toInt(ygr_list.get(ygr_list.size()-1).getS2()) || 
				toInt(format) < toInt(ygr_list.get(ygr_list.size()-1).getS3()) || 
				toInt(format) < toInt(ygr_list.get(ygr_list.size()-1).getS4()) || 
				toInt(format) < toInt(ygr_list.get(ygr_list.size()-1).getS5())
					) {
					ygr_list.get(ygr_list.size()-1).setPast(true);
					
					weekday_check = true;
			}
		}
	}
	
	public void weekend_past_check() {
		if (!weekend_check) {
			if (toInt(format) < toInt(ygr_weekend_list.get(ygr_weekend_list.size()-1).getS1()) || 
				toInt(format) < toInt(ygr_weekend_list.get(ygr_weekend_list.size()-1).getS2()) || 
				toInt(format) < toInt(ygr_weekend_list.get(ygr_weekend_list.size()-1).getS3()) || 
				toInt(format) < toInt(ygr_weekend_list.get(ygr_weekend_list.size()-1).getS4()) || 
				toInt(format) < toInt(ygr_weekend_list.get(ygr_weekend_list.size()-1).getS5())
					) {
					ygr_weekend_list.get(ygr_weekend_list.size()-1).setPast(true);
					
					weekend_check = true;
			}
		}
	}
	
	public int toInt(String str) {
		if (str.indexOf(":") > 0)
			return Integer.parseInt(str.replace(":", ""));
		else
			return 0;
	}
	
	public void onBackPressed() {
		finish();
		overridePendingTransition(0,0);
	}
}