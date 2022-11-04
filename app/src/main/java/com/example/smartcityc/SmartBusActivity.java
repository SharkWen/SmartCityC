package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smartcityc.Adapter.SmartBusAdapter;
import com.example.smartcityc.Bean.SmartBusBean;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SmartBusActivity extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private ListView busListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_bus);
        bindView();
        initData();
        initEvent();
    }

    private void initEvent() {
        tvBack.setOnClickListener(v->{
            finish();
        });
    }

    private void initData() {
        tvTitle.setText("智慧巴士");
        getSmartBusData();
    }

    private void getSmartBusData() {
        Tool.getData("/prod-api/api/bus/line/list", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String res = response.body().string();
               SmartBusBean smartBusBean = new Gson().fromJson(res, SmartBusBean.class);
                Tool.handler.post(() -> {
                    SmartBusAdapter.setListView(SmartBusActivity.this,smartBusBean,busListView);
                });
            }
        });
    }
    private void bindView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        busListView = (ListView) findViewById(R.id.bus_list_view);
    }
}