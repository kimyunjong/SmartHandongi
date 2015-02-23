package com.smarthandongi;

//import io.userhabit.service.Userhabit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
/**
 * Created by user on 2015-02-24.
 */
public class ProdutionNote extends Activity {

    WebView webView;
    Carrier carrier;
    Intent put_intent;
    Intent get_intent;

    protected void onStart() {
        super.onStart();
        //Userhabit.activityStart(this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        //Userhabit.activityStop(this);
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //
        get_intent = getIntent();
        put_intent = new Intent();
        carrier = (Carrier)get_intent.getSerializableExtra("carrier");

        webView = new WebView(this);
        setContentView(webView);

        webView.setInitialScale(1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);

        webView.loadUrl("http://hungry.portfolio1000.com/production_note/smart_android.php");

    }

    public void onBackPressed() {
        Intent intent = new Intent(ProdutionNote.this,yj_activity.class).putExtra("carrier",carrier);
        setResult(0, intent);
        //startActivity(intent);
        finish();
    }
}
