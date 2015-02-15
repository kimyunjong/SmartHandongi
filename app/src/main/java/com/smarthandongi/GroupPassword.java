package com.smarthandongi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by user on 2015-01-28.
 */
public class GroupPassword extends Activity {

    Carrier carrier;
    TextView group_name;
    EditText group_pw;
    RelativeLayout layoutView;
    LinearLayout dialog_check_background;
    RelativeLayout popup_1, popup_2, popup_5;
    Button backward_btn, cancel_btn, dialog_okay, dialog_writing_confirm, dialog_writing_cancel;
    Typeface typeface;
    Dialog dialog_input_pw, dialog_cancel;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        carrier = (Carrier) getIntent().getSerializableExtra("carrier");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_password);
        typeface = Typeface.createFromAsset(getAssets(), "KOPUBDOTUM_PRO_LIGHT.OTF");

        popup_1 = (RelativeLayout)findViewById(R.id.popup_1);
        popup_2 = (RelativeLayout)findViewById(R.id.popup_2);
        popup_5 = (RelativeLayout)findViewById(R.id.popup_5);

        group_name = (TextView) findViewById(R.id.pw_group_name);
        group_name.setTypeface(typeface);
        group_name.setText(carrier.getGroup_name());

        group_pw = (EditText) findViewById(R.id.password);
        group_pw.setTypeface(typeface);
        group_pw.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                group_pw.setCursorVisible(true);
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(group_pw, 0);
                return true;
            }
        });
        layoutView = (RelativeLayout) findViewById(R.id.pw_screen);
        layoutView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(group_pw
                        .getWindowToken(), 0);
                return true;
            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        backward_btn = (Button) findViewById(R.id.pw_backward_btn);
        backward_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                carrier.setGroup_name("");
                carrier.setGroup_code("");
                carrier.setGroup_pw(null);
                Intent intent = new Intent(GroupPassword.this, GroupSearch.class).putExtra("carrier", carrier);
                startActivity(intent);
                finish();
            }

        });

        cancel_btn = (Button) findViewById(R.id.pw_cancel_btn);
        cancel_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                dialog_cancel = new Dialog(context);
                dialog_cancel.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_cancel.setContentView(R.layout.dialog_cancel);
                dialog_cancel.show();
                //일반경로
                if (carrier.getEdit_count() == 0) {
                    dialog_writing_confirm = (Button) dialog_cancel.findViewById(R.id.dialog_writing_confirm);
                    dialog_writing_confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_cancel.dismiss();
                            new CountDownTimer(1500, 300) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    popup_1.setVisibility(VISIBLE);
                                    popup_2.setVisibility(VISIBLE);
                                    popup_5.setVisibility(VISIBLE);
                                }

                                @Override
                                public void onFinish() {
                                    popup_1.setVisibility(GONE);
                                    popup_2.setVisibility(GONE);
                                    popup_5.setVisibility(GONE);
                                    carrier.setGroup_name("");
                                    carrier.setGroup_code("");
                                    carrier.setGroup_pw(null);

                                    Intent intent = new Intent(GroupPassword.this, yj_activity.class).putExtra("carrier", carrier);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            }.start();

                        }
                    });

                    dialog_writing_cancel = (Button) dialog_cancel.findViewById(R.id.dialog_writing_cancel);
                    dialog_writing_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_cancel.dismiss();
                        }
                    });


                }

            }
        });
    }

    public void pwOnClick(View v) {
        String str = group_pw.getText().toString();
        Log.d("암것도 안쳤을때는?",str+"abc");
        if(str.length() < 1){
            dialog_input_pw = new Dialog(context);
            dialog_input_pw.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog_input_pw.setContentView(R.layout.dialog_writing_check);
            dialog_input_pw.show();

            dialog_check_background = (LinearLayout)dialog_input_pw.findViewById(R.id.dialog_check_background);
            dialog_check_background.setBackgroundResource(R.drawable.dialog_input_pw);
            dialog_okay = (Button)dialog_input_pw.findViewById(R.id.dialog_okay);
            dialog_okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_input_pw.dismiss();
                }
            });
        }

        if(str.compareTo(carrier.getGroup_pw())!=0) {
            Toast toastView =Toast.makeText(this, "패스워드가 일치하지 않습니다", Toast.LENGTH_SHORT);
            toastView.setGravity(Gravity.CENTER,0,0);
            toastView.show();
            Log.d("토스트 확인", "잘됨");
        }
        else if(str.compareTo(carrier.getGroup_pw())==0) {
            Intent intent = new Intent(GroupPassword.this,Writing.class).putExtra("carrier",carrier);
            startActivity(intent);
            finish();
        }

    }

    public void onBackPressed() {
        carrier.setGroup_name("");
        carrier.setGroup_code("");
        carrier.setGroup_pw(null);
        Intent intent = new Intent(GroupPassword.this,GroupSearch.class).putExtra("carrier",carrier);
        startActivity(intent);
        finish();
    }


}
