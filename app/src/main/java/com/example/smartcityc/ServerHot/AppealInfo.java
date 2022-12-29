package com.example.smartcityc.ServerHot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.smartcityc.R;

public class AppealInfo extends AppCompatActivity {
    private TextView titleBarTitle;
    private ImageButton titleBarBack;
    private TextView appealInfoTime;
    private TextView appealInfoDanwei;
    private TextView appealInfoContent;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appeal_info);
        intent = getIntent();
        initView();
    }

    private void initView() {
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        titleBarTitle.setText(intent.getStringExtra("title"));
        titleBarBack = (ImageButton) findViewById(R.id.title_bar_back);
        titleBarBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        appealInfoTime = (TextView) findViewById(R.id.appeal_info_time);
        appealInfoTime.setText(intent.getStringExtra("time"));
        appealInfoDanwei = (TextView) findViewById(R.id.appeal_info_danwei);
        appealInfoDanwei.setText(intent.getStringExtra("danwei"));
        appealInfoContent = (TextView) findViewById(R.id.appeal_info_content);
        appealInfoContent.setText(intent.getStringExtra("content"));

    }
}