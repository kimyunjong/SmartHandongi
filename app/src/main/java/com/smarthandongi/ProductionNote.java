
package com.smarthandongi;



        import java.io.IOException;
        import java.io.InputStream;
        import java.net.HttpURLConnection;
        import java.net.URL;

        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Color;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.util.DisplayMetrics;
        import android.util.Log;
        import android.view.Display;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.Window;
        import android.view.WindowManager;
        import android.view.animation.AnimationUtils;
        import android.webkit.WebView;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.widget.ViewFlipper;

        import com.smarthandongi.Carrier;

public class ProductionNote extends Activity {

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

        Intent intent = new Intent(ProductionNote.this, yj_activity.class);

        intent.putExtra("carrier", carrier);
        setResult(0, intent);
        finish();
    }

}