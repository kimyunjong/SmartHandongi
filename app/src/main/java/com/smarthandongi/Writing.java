package com.smarthandongi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by LEWIS on 2015-01-23.
 */
public class Writing extends Activity implements View.OnClickListener {
    Carrier carrier;

    //Category Buttons
    Button writing_notice_btn, writing_outer_btn, writing_seminar_btn, writing_recruit_btn, writing_agora_btn;

    EditText writing_title, writing_body;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing);

        carrier.setId("21100114");
        carrier.setNickname("루이스");

        //Category Buttons
        writing_notice_btn  = (Button) findViewById(R.id.writing_notice_btn);
        writing_outer_btn   = (Button) findViewById(R.id.writing_outer_btn);
        writing_seminar_btn = (Button) findViewById(R.id.writing_seminar_btn);
        writing_recruit_btn = (Button) findViewById(R.id.writing_recruit_btn);
        writing_agora_btn   = (Button) findViewById(R.id.writing_agora_btn);

        writing_notice_btn.setOnClickListener(this);
        writing_outer_btn.setOnClickListener(this);
        writing_seminar_btn.setOnClickListener(this);
        writing_recruit_btn.setOnClickListener(this);
        writing_agora_btn.setOnClickListener(this);

        //Category buttons done

        writing_title = (EditText)findViewById(R.id.writing_title);
        writing_body  = (EditText)findViewById(R.id.writing_body);

        writing_title.setOnClickListener(this);
        writing_body.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            //Category buttons
            case R.id.writing_notice_btn:{                //카테고리를 0으로 시작해서 처음에는 아무것도 선택되지 않은 상태에서 확인을 누를 시 완료하지 않았다는 경고메시지가 뜸
                //카테고리 번호 지정
                carrier.setCategory(1);

                //배경 초기화
                writing_notice_btn.setBackgroundResource(R.drawable.notice_btn_on);
                writing_outer_btn.setBackgroundResource(R.drawable.outer_btn);
                writing_seminar_btn.setBackgroundResource(R.drawable.seminar_btn);
                writing_recruit_btn.setBackgroundResource(R.drawable.recruit_btn);
                writing_agora_btn.setBackgroundResource(R.drawable.agora_btn);

                break;
            }
            case R.id.writing_outer_btn:{
                carrier.setCategory(2);
                writing_notice_btn.setBackgroundResource(R.drawable.notice_btn);
                writing_outer_btn.setBackgroundResource(R.drawable.outer_btn_on);
                writing_seminar_btn.setBackgroundResource(R.drawable.seminar_btn);
                writing_recruit_btn.setBackgroundResource(R.drawable.recruit_btn);
                writing_agora_btn.setBackgroundResource(R.drawable.agora_btn);
                break;
            }
            case R.id.writing_seminar_btn:{
                carrier.setCategory(3);
                writing_notice_btn.setBackgroundResource(R.drawable.notice_btn);
                writing_outer_btn.setBackgroundResource(R.drawable.outer_btn);
                writing_seminar_btn.setBackgroundResource(R.drawable.seminar_btn_on);
                writing_recruit_btn.setBackgroundResource(R.drawable.recruit_btn);
                writing_agora_btn.setBackgroundResource(R.drawable.agora_btn);
                break;
            }
            case R.id.writing_recruit_btn:{
                carrier.setCategory(4);
                writing_notice_btn.setBackgroundResource(R.drawable.notice_btn);
                writing_outer_btn.setBackgroundResource(R.drawable.outer_btn);
                writing_seminar_btn.setBackgroundResource(R.drawable.seminar_btn);
                writing_recruit_btn.setBackgroundResource(R.drawable.recruit_btn_on);
                writing_agora_btn.setBackgroundResource(R.drawable.agora_btn);
                break;
            }
            case R.id.writing_agora_btn:{
                carrier.setCategory(5);
                writing_notice_btn.setBackgroundResource(R.drawable.notice_btn);
                writing_outer_btn.setBackgroundResource(R.drawable.outer_btn);
                writing_seminar_btn.setBackgroundResource(R.drawable.seminar_btn);
                writing_recruit_btn.setBackgroundResource(R.drawable.recruit_btn);
                writing_agora_btn.setBackgroundResource(R.drawable.agora_btn_on);
                break;
            }
            //Category buttons done

            //Title and Body
            case R.id.writing_title:{
                //입력된 내용을 디비에 저장
                break;
            }

            case R.id.writing_body:{
                //입력된 내용을 캐리어에 저장
                break;
            }


        }
    }
}
