package com.smarthandongi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Joel on 2015-01-21.
 */
public class yy_activity extends Activity {
    Carrier carrier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        carrier = (Carrier) intent.getSerializableExtra("carrier");
        setContentView(R.layout.yy_layout);
    }
}
