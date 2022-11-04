package com.example.smartcityc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class SmartLineDatePicker extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private CalendarView calendarView;
    private TextView calendarDate;
    private Button busNext;
    private Calendar cal;
    int year, month, day, hour, minutes, second;
    public static String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_line_date_picker);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        tvBack.setOnClickListener(v -> {
            finish();
        });
        calendarView.setOnDateChangeListener((calendarView, i, i1, i2) -> {
            date = i + "年" + (i1 + 1) + "月" + i2 + "日";
            calendarDate.setText(date);
        });
        busNext.setOnClickListener(v -> {
            startActivity(new Intent(this, SmartLineCardActivity.class));
        });
    }

    private void initData() {
        tvTitle.setText(SmartLineActivity.bus_line);
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minutes = cal.get(Calendar.MINUTE);
        second = cal.get(Calendar.SECOND);
        date = year + "年" + month + "月" + day + "日";
        calendarDate.setText(date);
    }

    private void initView() {
        tvBack = findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarDate = (TextView) findViewById(R.id.calendar_date);
        busNext = (Button) findViewById(R.id.bus_next);

    }
}