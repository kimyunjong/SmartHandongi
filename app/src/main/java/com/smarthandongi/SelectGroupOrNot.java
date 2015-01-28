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

    Button individual_btn, group_btn, sel_forward_btn, sel_cancel_btn;


    int selector;                       //개인을 눌렀을 때 0, 단체를 눌렀을 때 1 로 변환
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_group_or_not);
        carrier = (Carrier) getIntent().getSerializableExtra("carrier");

        individual_btn =    (Button)findViewById(R.id.individual_btn);
        group_btn =         (Button)findViewById(R.id.group_btn);
        sel_forward_btn =   (Button)findViewById(R.id.sel_forward_btn);
        sel_cancel_btn =    (Button)findViewById(R.id.sel_cancel_btn);

        individual_btn.setOnClickListener(this);
        group_btn.setOnClickListener(this);
        sel_forward_btn.setOnClickListener(this);
        sel_cancel_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.individual_btn : {
                selector = 0;               //selector를 변환하고 이미지 변환한
                individual_btn.setText("클릭");
                group_btn.setText("단체");
                break;
            }
            case R.id.group_btn : {
                selector = 1;
                individual_btn.setText("개인");
                group_btn.setText("클릭");
                break;
            }
            case R.id.sel_forward_btn : {
                if(selector == 0){
                    Intent intent = new Intent(SelectGroupOrNot.this, Writing.class).putExtra("carrier", carrier);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(SelectGroupOrNot.this,GroupSearch.class).putExtra("carrier",carrier);
                    startActivity(intent);
                    finish();
                }
                break;
            }
            case R.id.sel_cancel_btn : {
                Intent intent = new Intent(SelectGroupOrNot.this, MainActivity2.class).putExtra("carrier", carrier);
                startActivity(intent);
                finish();
                break;
            }
        }
    }
}
