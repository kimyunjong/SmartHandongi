package com.smarthandongi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Joel on 2015-01-21.
 */
public class yy_activity extends Activity implements View.OnClickListener{

    Carrier carrier;
    Button yy_sel_btn, yy_bugreport_btn, yy_push_btn;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carrier = (Carrier) getIntent().getSerializableExtra("carrier");
        setContentView(R.layout.yy_layout);

        yy_sel_btn = (Button)findViewById(R.id.yy_sel_btn);
        yy_sel_btn.setOnClickListener(this);

        yy_bugreport_btn = (Button)findViewById(R.id.yy_bugreport_btn);
        yy_bugreport_btn.setOnClickListener(this);

        yy_push_btn = (Button)findViewById(R.id.yy_push_btn);
        yy_push_btn.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yy_sel_btn : {
                Intent intent = new Intent(yy_activity.this, SelectGroupOrNot.class).putExtra("carrier", carrier);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.yy_bugreport_btn : {
                Intent intent = new Intent(yy_activity.this, BugReport.class).putExtra("carrier", carrier);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.yy_push_btn : {
                Intent intent = new Intent(yy_activity.this, PushSetup.class).putExtra("carrier", carrier);
                startActivity(intent);
                finish();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(yy_activity.this, MainActivity2.class).putExtra("carrier", carrier);
        startActivity(intent);
        finish();
    }
}
