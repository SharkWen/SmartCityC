package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.lights.LightState;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.smartcityc.Bean.MsgBean;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;

import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OrderPayActivity extends AppCompatActivity {
    private LinearLayout llBg;
    private TextView tvBack;
    private TextView tvTitle;
    private TextView payTotal;
    private RadioButton payWallet;
    private RadioButton payBao;
    private RadioButton payWechat;
    private Button paySure;
    List<RadioButton> radioButtons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay);
        bindView();
        initData();
        initEvent();
    }

    private void initEvent() {
        payWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initRadioButton(payWallet);
            }
        });
        payBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initRadioButton(payBao);
            }
        });
        payWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initRadioButton(payWechat);
            }
        });
        paySure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String payType = "";
                if (payWallet.isChecked()) {
                    payType = "电子钱包支付";
                }
                if (payBao.isChecked()) {
                    payType = "支付宝支付";
                }
                if (payWechat.isChecked()) {
                    payType = "微信支付";
                }
                String data = "{\n" +
                        "\"orderNo\": \"" + getIntent().getStringExtra("orderNo") + "\",\n" +
                        "\"paymentType\":\"" + payType + "\"\n" +
                        "}";
                Tool.postTokenData("/prod-api/api/takeout/pay", Tool.sp(OrderPayActivity.this, "token"), data, new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        MsgBean msgBean = new Gson().fromJson(response.body().string(),MsgBean.class);
                        if(msgBean.getCode() == 200){
                            Tool.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Tool.setDialog(OrderPayActivity.this,"支付成功").show();
                                    Tool.handler.postDelayed(()->{
                                        startActivity(new Intent(OrderPayActivity.this,TakeOutFoodActivity.class));
                                    },1000);
                                }
                            });
                        }

                    }
                });
            }
        });
    }


    private void initData() {
        llBg.setBackgroundResource(R.color.yellow);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvTitle.setText("确认支付");
        payTotal.setText("￥" + getIntent().getStringExtra("total"));
    }

    private void initRadioButton(RadioButton rd) {
        if (!radioButtons.isEmpty()) {
            for (RadioButton r : radioButtons) {
                r.setChecked(false);
            }
        }
        rd.setChecked(true);
        radioButtons.add(rd);
    }

    private void bindView() {
        llBg = (LinearLayout) findViewById(R.id.ll_bg);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        payTotal = (TextView) findViewById(R.id.pay_total);
        payWallet = findViewById(R.id.pay_wallet);
        payBao = findViewById(R.id.pay_bao);
        payWechat = findViewById(R.id.pay_wechat);
        paySure = (Button) findViewById(R.id.pay_sure);
    }
}