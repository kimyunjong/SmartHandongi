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
    Button ym_activity;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carrier = (Carrier) getIntent().getSerializableExtra("carrier");
        setContentView(R.layout.yy_layout);

        ym_activity = (Button) findViewById(R.id.write_btn);
        ym_activity.setOnClickListener(this);
    }

    public void onClick(View v) {
        Intent intent = new Intent(yy_activity.this, com.smarthandongi.UploadPost.class).putExtra("carrier", carrier);
        startActivity(intent);
        finish();
    }
}
