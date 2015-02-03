package com.smarthandongi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by LEWIS on 2015-01-23.
 */

public class SelectGroupOrNot extends Activity implements View.OnClickListener {
    Carrier carrier;

    Button individual_btn, group_btn, sel_cancel_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_group_or_not);
        carrier = (Carrier) getIntent().getSerializableExtra("carrier");

        individual_btn =    (Button)findViewById(R.id.individual_btn);
        group_btn =         (Button)findViewById(R.id.group_btn);
//        sel_cancel_btn =    (Button)findViewById(R.id.sel_cancel_btn);

        individual_btn.setOnClickListener(this);
        group_btn.setOnClickListener(this);
//        sel_cancel_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.individual_btn : {
                carrier.setSelector(0);
                Intent intent = new Intent(SelectGroupOrNot.this, Writing.class).putExtra("carrier", carrier);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.group_btn : {
                carrier.setSelector(1);
                Intent intent = new Intent(SelectGroupOrNot.this,GroupSearch.class).putExtra("carrier",carrier);
                startActivity(intent);
                finish();
                break;
            }
//            case R.id.sel_cancel_btn : {
//                Intent intent = new Intent(SelectGroupOrNot.this, MainActivity2.class).putExtra("carrier", carrier);
//                startActivity(intent);
//                finish();
//                break;
//            }
        }
    }
    public void onBackPressed(){
            Intent intent = new Intent(SelectGroupOrNot.this, yy_activity.class).putExtra("carrier", carrier);
            startActivity(intent);
            finish();
    }
}
