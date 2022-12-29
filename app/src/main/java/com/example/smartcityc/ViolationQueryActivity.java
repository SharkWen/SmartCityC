package com.example.smartcityc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.smartcityc.Bean.CityDataBean;
import com.example.smartcityc.Tool.Config;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ViolationQueryActivity extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;;
    private Spinner violationSelect;
    private EditText violationCarNum;
    private EditText violationFdjNum;
    CityDataBean dataBean ;
    List<String> strings = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violation_query);
        bindView();
        initData();
    }

    private void initData() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvTitle.setText("违章查询");
        dataBean =  new Gson().fromJson(Config.cityData,CityDataBean.class);
        for(CityDataBean.DataDTO d : dataBean.getData()){
            strings.add(d.getCity_sort());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,strings);
        violationSelect.setAdapter(adapter);
    }

    private void bindView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        violationSelect = (Spinner) findViewById(R.id.violation_select);
        violationCarNum = findViewById(R.id.violation_carNum);
        violationFdjNum = findViewById(R.id.violation_fdj_num);
    }
}