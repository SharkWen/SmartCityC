package com.example.smartcityc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SmartLineActivity extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private TextView smartLineFirst;
    private TextView smartLineEnd;
    private TextView smartLinePrice;
    private TextView smartLineMileage;
    private Button smartLineNext;
    public static String bus_line;
    public static String bus_first;
    public static String bus_end;
    public static String bus_price;
    public static String bus_mileage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_line);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        tvBack.setOnClickListener(v->{
            finish();
        });
        smartLineNext.setOnClickListener(v->{
            startActivity(new Intent(this,SmartLineDatePicker.class));
        });
    }

    private void initData() {
        tvTitle.setText(bus_line);
        smartLineFirst.setText(bus_first);
        smartLineEnd.setText(bus_end);
        smartLinePrice.setText(bus_price);
        smartLineMileage.setText(bus_mileage);
    }

    private void initView() {
        tvBack = findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        smartLineFirst = (TextView) findViewById(R.id.smart_line_first);
        smartLineEnd = (TextView) findViewById(R.id.smart_line_end);
        smartLinePrice = (TextView) findViewById(R.id.smart_line_price);
        smartLineMileage = (TextView) findViewById(R.id.smart_line_mileage);
        smartLineNext = findViewById(R.id.smart_line_next);
    }
}