package com.example.smartcityc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcityc.Tool.Tool;

public class SmartLineCardActivity extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private EditText busEtName;
    private EditText busEtPhone;
    private EditText busEtFirst;
    private EditText busEtEnd;
    private Button busNext;
    public static String busName, busPhone, busFirst, busEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_line_card);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        tvBack.setOnClickListener(v -> {
            finish();
        });
        busNext.setOnClickListener(v -> {
            busName = busEtName.getText().toString();
            busPhone = busEtPhone.getText().toString();
            busFirst = busEtFirst.getText().toString();
            busEnd = busEtEnd.getText().toString();
            if(busEtName.equals("") || busPhone.equals("") || busFirst.equals("") || busEtEnd.equals("")){
                Toast.makeText(this,"内容不能为空,请输入相应的内容", Toast.LENGTH_SHORT).show();
                return;
            }
          startActivity(new Intent(this, SmartLineCardPostActivity.class));
        });
    }

    private void initData() {
        tvTitle.setText(SmartLineActivity.bus_line);
    }


    private void initView() {
        tvBack = findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        busEtName = (EditText) findViewById(R.id.bus_et_name);
        busEtPhone = (EditText) findViewById(R.id.bus_et_phone);
        busEtFirst = (EditText) findViewById(R.id.bus_et_first);
        busEtEnd = (EditText) findViewById(R.id.bus_et_end);
        busNext = findViewById(R.id.bus_card_next);
    }
}