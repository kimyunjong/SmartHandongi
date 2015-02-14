package com.smarthandongi;
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

        import com.smarthandongi.adapter.BusYgrAdapter;
        import com.smarthandongi.database.BusYgrData;

public class Bus_Schedule extends Activity{
    Carrier carrier;
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
        carrier = (Carrier)get_intent.getSerializableExtra("Carrier");
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
        ygr_list = new ArrayList<>();

        ygr_list.add( new BusYgrData("-", "-", "7:40", "7:55", "8:15", false, true, 0, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("", "환호동 정차지점(계절학기만 운행)", "", "7:55", "8:15", false, false, 2, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("7:35", "7:55", "8:10", "8:25", "8:45", false, false, 0, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("", "바로크 가구점 앞 출발", "", "8:25", "8:45", false, false, 2, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("", "환호동 정차지점(계절학기만 운행)", "", "8:25", "8:45", false, false, 2, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("8:35", "8:55", "9:10", "9:25", "9:45", false, false, 0, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("", "환호동 정차지점(계절학기만 운행)", "", "9:25", "9:45", false, false, 2, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("9:35", "9:55", "10:10", "10:25", "10:45", false, false, 0, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("", "환호동 정차지점(계절학기만 운행)", "", "10:25", "10:45", false, false, 2, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("10:30", "10:50", "11:05", "11:20", "11:40", false, false, 0, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("11:30", "11:50", "12:05", "12:20", "12:40", false, false, 0, past)); weekday_past_check();

        ygr_list.add( new BusYgrData("", "", "", "", "", true, false, 0, past));

        ygr_list.add( new BusYgrData("12:30", "12:50", "13:05", "13:20", "13:40", false, false, 0, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("13:30", "13:50", "14:05", "14:20", "14:40", false, false, 0, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("14:20", "14:40", "14:55", "15:10", "15:30", false, false, 0, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("15:10", "15:30", "15:45", "16:00", "16:20", false, false, 0, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("16:00", "16:20", "16:35", "16:50", "17:10", false, false, 0, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("16:30", "16:50", "17:05", "17:20", "17:40", false, false, 0, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("17:00", "17:20", "17:35", "17:50", "18:10", false, false, 0, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("17:40", "18:00", "18:15", "18:30", "18:50", false, false, 0, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("18:30", "18:50", "19:05", "19:20", "19:40", false, false, 0, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("19:00", "19:20", "19:35", "19:50", "20:10", false, false, 0, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("19:50", "20:10", "20:25", "20:40", "21:00", false, false, 0, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("20:20", "20:40", "20:55", "21:10", "21:30", false, false, 0, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("21:10", "21:30", "21:45", "22:00", "22:20", false, false, 0, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("21:50", "22:10", "22:25", "22:40", "23:00", false, false, 0, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("22:40", "23:00", "23:15", "23:30", "23:50", false, false, 0, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("23:10", "23:30", "-", "23:35", "23:55", false, false, 0, past)); weekday_past_check();
        ygr_list.add( new BusYgrData("23:40", "24:00", "24:15", "", "", false, false, 0, past)); weekday_past_check();


        ygr_adapter = new BusYgrAdapter(Bus_Schedule.this, ygr_list);
        ygr_listview.setAdapter(ygr_adapter);

        //---------------------------------------------------------------------------------------------- 주말

        ygr_weekend_listview = (ListView)findViewById(R.id.ygr_weekend_list_view);
        ygr_weekend_list = new ArrayList<>();

        ygr_weekend_list.add( new BusYgrData("-", "-", "7:40", "7:55", "8:15", false, false, 0, past)); weekend_past_check();
        ygr_weekend_list.add( new BusYgrData("7:35", "7:55", "8:10", "8:25", "8:45", false, false, 0, past)); weekend_past_check();
        ygr_weekend_list.add( new BusYgrData("8:30", "8:50", "9:05", "9:20", "9:40", false, false, 0, past)); weekend_past_check();
        ygr_weekend_list.add( new BusYgrData("9:30", "9:50", "10:05", "10:20", "10:40", false, false, 0, past)); weekend_past_check();
        ygr_weekend_list.add( new BusYgrData("10:30", "10:50", "11:05", "11:20", "11:40", false, false, 0, past)); weekend_past_check();
        ygr_weekend_list.add( new BusYgrData("11:20", "11:40", "11:55", "12:10", "12:30", false, false, 0, past)); weekend_past_check();

        ygr_weekend_list.add( new BusYgrData("", "", "", "", "", true, false, 0, past));

        ygr_weekend_list.add( new BusYgrData("12:10", "12:30", "12:45", "13:00", "13:20", false, false, 0, past)); weekend_past_check();
        ygr_weekend_list.add( new BusYgrData("13:00", "13:20", "13:35", "13:50", "14:10", false, false, 0, past)); weekend_past_check();
        ygr_weekend_list.add( new BusYgrData("14:00", "14:20", "14:35", "14:50", "15:10", false, false, 0, past)); weekend_past_check();
        ygr_weekend_list.add( new BusYgrData("14:50", "15:10", "15:25", "15:40", "16:00", false, false, 0, past)); weekend_past_check();
        ygr_weekend_list.add( new BusYgrData("15:40", "16:00", "16:15", "16:30", "16:50", false, false, 0, past)); weekend_past_check();
        ygr_weekend_list.add( new BusYgrData("16:30", "16:50", "17:05", "17:20", "17:40", false, false, 0, past)); weekend_past_check();
        ygr_weekend_list.add( new BusYgrData("17:20", "17:40", "17:55", "18:10", "18:30", false, false, 0, past)); weekend_past_check();
        ygr_weekend_list.add( new BusYgrData("18:10", "18:30", "18:45", "19:00", "19:20", false, false, 0, past)); weekend_past_check();
        ygr_weekend_list.add( new BusYgrData("19:00", "19:20", "19:35", "19:50", "20:10", false, false, 0, past)); weekend_past_check();
        ygr_weekend_list.add( new BusYgrData("19:50", "20:10", "20:25", "20:40", "21:00", false, false, 0, past)); weekend_past_check();
        ygr_weekend_list.add( new BusYgrData("20:30", "20:50", "21:05", "21:20", "21:40", false, false, 0, past)); weekend_past_check();
        ygr_weekend_list.add( new BusYgrData("21:10", "21:30", "21:45", "22:00", "22:20", false, false, 0, past)); weekend_past_check();
        ygr_weekend_list.add( new BusYgrData("22:00", "22:20", "22:35", "22:50", "23:10", false, false, 0, past)); weekend_past_check();

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