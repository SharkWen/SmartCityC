package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcityc.Bean.BusBean;
import com.example.smartcityc.Bean.MsgBean;
import com.example.smartcityc.Tool.Tool;
import com.example.smartcityc.data.SmartBusOrderStore;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SmartLineCardPostActivity extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private TextView busTvName;
    private TextView busTvPhone;
    private TextView busTvFirst;
    private TextView busTvEnd;
    private Button busCardPostNext;
    private TextView busTvDate;
    SharedPreferences sp;
    BusBean busBean;
    MsgBean msgBean;
    SmartBusOrderStore smartBusOrderStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_line_card_post);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        tvBack.setOnClickListener(v -> {
            finish();
        });
        busCardPostNext.setOnClickListener(v -> {
            if (!sp.contains("token")) {
                Tool.setDialog(this, "请注册登录").show();
                return;
            }
            busBean.setPath(SmartLineActivity.bus_line);
            busBean.setStart(busTvFirst.getText().toString());
            busBean.setEnd(busTvEnd.getText().toString());
            busBean.setPrice(8 + "");
            String json = new Gson().toJson(busBean);
            Tool.postTokenData("/prod-api/api/bus/order", sp.getString("token", ""), json, new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String res = response.body().string();
                    msgBean = new Gson().fromJson(res, MsgBean.class);
                    Tool.handler.post(() -> {
                        if (msgBean.getCode() == 200) {
                            smartBusOrderStore.getMap().put(msgBean.getOrderNum(), SmartLineDatePicker.date);
                            System.out.println(new Gson().toJson(smartBusOrderStore));
                            Tool.shp(SmartLineCardPostActivity.this).edit().putString("SmartOrder", new Gson().toJson(smartBusOrderStore)).commit();
                            new AlertDialog.Builder(SmartLineCardPostActivity.this)
                                    .setTitle(msgBean.getMsg()+"\n订单号为:"+msgBean.getOrderNum())
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                               startActivity(new Intent(SmartLineCardPostActivity.this,SmartBusOrderActivity.class));
                                        }
                                    }).create().show();
                        }
                    });
                }
            });
        });
    }

    private void initData() {
        tvTitle.setText(SmartLineActivity.bus_line);
        busTvName.setText(SmartLineCardActivity.busName);
        busTvPhone.setText(SmartLineCardActivity.busPhone);
        busTvFirst.setText(SmartLineCardActivity.busFirst);
        busTvEnd.setText(SmartLineCardActivity.busEnd);
        busTvDate.setText(SmartLineDatePicker.date);
        sp = getSharedPreferences("data", Context.MODE_PRIVATE);
        if (!Tool.shp(this).contains("SmartOrder")) {
            smartBusOrderStore = new SmartBusOrderStore();
        } else {
            smartBusOrderStore = new Gson().fromJson(Tool.sp(this, "SmartOrder"), SmartBusOrderStore.class);
        }
    }


    private void initView() {
        tvBack = findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        busTvName = (TextView) findViewById(R.id.bus_tv_name);
        busTvPhone = (TextView) findViewById(R.id.bus_tv_phone);
        busTvFirst = (TextView) findViewById(R.id.bus_tv_first);
        busTvEnd = (TextView) findViewById(R.id.bus_tv_end);
        busCardPostNext = (Button) findViewById(R.id.bus_card_post_next);
        busTvDate = (TextView) findViewById(R.id.bus_tv_date);
        busBean = new BusBean();
    }
}