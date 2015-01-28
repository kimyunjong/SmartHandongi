package com.smarthandongi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by LEWIS on 2015-01-28.
 */
public class BugReport extends Activity implements View.OnClickListener {
    Carrier carrier;
    Button bug_back_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bug_report);
        carrier = (Carrier)getIntent().getSerializableExtra("carrier");

        bug_back_btn = (Button)findViewById(R.id.bug_back_btn);
        bug_back_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bug_back_btn : {
                Intent intent = new Intent(BugReport.this, yy_activity.class).putExtra("Carrier", carrier);
                startActivity(intent);
                finish();
                break;
            }
        }
    }
}
