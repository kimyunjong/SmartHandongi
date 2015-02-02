package com.smarthandongi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kakao.APIErrorResult;
import com.kakao.LogoutResponseCallback;
import com.kakao.UserManagement;
import com.smarthandongi.kakao_api.KakaoTalkLoginActivity;

public class MainActivity2 extends ActionBarActivity implements View.OnClickListener {

    private Carrier carrier;
    private TextView text;
    private Button button;
    Button yjbtn,yybtn,dsbtn;
    TextView dday;
    dday deadline= new dday();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carrier = (Carrier)getIntent().getSerializableExtra("carrier");
        setContentView(R.layout.activity_main2);

        text = (TextView)findViewById(R.id.text);
        button = (Button)findViewById(R.id.button);
        dday=(TextView)findViewById(R.id.dday);
        button.setText(carrier.isLogged_in() ? "로그아웃" : "로그인");
        text.setText(carrier.getId() + " " + carrier.getNickname());

        dday.setText(String.valueOf(deadline.caldate(2015,1,18))+ "남았습니다!!!! "); //월은 -1, 일은 +1





        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carrier.isLogged_in()) {
                    UserManagement.requestLogout(new LogoutResponseCallback() {
                        @Override
                        protected void onSuccess(long l) {
                            carrier.setLogged_in(false);
                            carrier.setNickname("not_logged_in");
                            carrier.setId("000000");
                            Intent intent = new Intent(MainActivity2.this, KakaoTalkLoginActivity.class).putExtra("carrier", carrier);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        protected void onFailure(APIErrorResult apiErrorResult) {

                        }
                    });
                }
                else {
                    Intent intent = new Intent(MainActivity2.this, KakaoTalkLoginActivity.class).putExtra("carrier", carrier);
                    startActivity(intent);
                    finish();
                }
            }
        });

        yjbtn=(Button)findViewById(R.id.yjbtn);
        yybtn=(Button)findViewById(R.id.yybtn);
        dsbtn=(Button)findViewById(R.id.dsbtn);

        yybtn.setOnClickListener(this);
        yjbtn.setOnClickListener(this);
        dsbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.yjbtn:{
                Intent intent=new Intent(MainActivity2.this,yj_activity.class).putExtra("carrier",carrier);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.yybtn:{
                Intent intent=new Intent(MainActivity2.this,yy_activity.class).putExtra("carrier",carrier);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.dsbtn:{
                Intent intent=new Intent(MainActivity2.this,ds_activity.class).putExtra("carrier",carrier);
                startActivity(intent);
                finish();
                break;

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
