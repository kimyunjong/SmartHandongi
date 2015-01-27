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

    Button individual_btn;
    Button group_btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_group_or_not);
        carrier = (Carrier) getIntent().getSerializableExtra("carrier");

        individual_btn =    (Button)findViewById(R.id.individual_btn);
        group_btn =         (Button)findViewById(R.id.group_btn);

        individual_btn.setOnClickListener(this);
        group_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.individual_btn : {
                //개인이니까 group indicator가 디폴트 값을 그대로 유지한다.
                carrier.setGroup_code(null);


                Intent intent = new Intent(SelectGroupOrNot.this, Writing.class).putExtra("carrier", carrier);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.group_btn : {
                //carrier.setGroup_code(); 여기서 그룹코드를 받아와서 세팅을 해주도록 함
                Intent intent = new Intent(SelectGroupOrNot.this,GroupSearch.class).putExtra("carrier",carrier);
                startActivity(intent);

                break;
            }

        }
    }
}
