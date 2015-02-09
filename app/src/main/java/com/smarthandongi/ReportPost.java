package com.smarthandongi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by LEWIS on 2015-02-09.
 */
public class ReportPost extends Activity implements View.OnClickListener{

    Button report_post_cancel_btn, report_post_confirm_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_post);


    }

    @Override
    public void onClick(View v) {

    }
}
