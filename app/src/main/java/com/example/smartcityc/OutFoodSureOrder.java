package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcityc.Adapter.AddressAdapter;
import com.example.smartcityc.Adapter.OrderAdapter;
import com.example.smartcityc.Bean.AddressBean;
import com.example.smartcityc.Bean.MsgBean;
import com.example.smartcityc.Bean.OrderSureBean;
import com.example.smartcityc.Tool.Tool;
import com.example.smartcityc.data.DataStore;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OutFoodSureOrder extends AppCompatActivity {
    private LinearLayout llBg;
    private TextView tvBack;
    private TextView tvTitle;
    private TextView sureOrderText;
    private TextView sureOrderSelect;
    private RecyclerView recyclerView;
    private RecyclerView orderRecyclerView;
    private Button orderSure;

    Context context = this;
    Dialog dialog;
    String data;
    private DataStore dataStore;
    OrderSureBean orderSureBean = new OrderSureBean();
    List<OrderSureBean.OrderItemListDTO> listDTOS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_food_sure_order);
        data = getIntent().getStringExtra("data");
        dataStore = new Gson().fromJson(data, DataStore.class);
        System.out.println(dataStore.getFoodData().size());
        String amount = getIntent().getStringExtra("amount");
        orderSureBean.setAmount(Float.parseFloat(amount));
        orderSureBean.setSellerId(Integer.parseInt(getIntent().getStringExtra("sellerId")));
        bindView();
        initData();
        initEvent();
    }

    private void initEvent() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sureOrderSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        orderSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Tool.shp(context).contains("token")) {
                    Toast.makeText(context, "请注册登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sureOrderText.getText().length() == 0) {
                    Toast.makeText(context, "请选择地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                orderSureBean.setOrderItemList(listDTOS);
                String json = new Gson().toJson(orderSureBean);
                System.out.println(json);
                Tool.postTokenData("/prod-api/api/takeout/order/create", Tool.sp(context, "token"), json, new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        MsgBean msgBean = new Gson().fromJson(response.body().string(), MsgBean.class);
                        Tool.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("+++++++++++++++++++++++");
                                Tool.setDialog(context, msgBean.getMsg() + "\n订单号为:" + msgBean.getOrderNo()).show();
                                Tool.handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(context, OrderPayActivity.class);
                                        intent.putExtra("orderNo",msgBean.getOrderNo());
                                        intent.putExtra("total", orderSureBean.getAmount()+"");
                                        startActivity(intent);
                                    }
                                }, 1000);
                            }
                        });
                    }
                });
            }
        });
    }

    private void initData() {
        recyclerView = new RecyclerView(this);
        tvTitle.setText("确认订单");
        llBg.setBackgroundResource(R.color.yellow);
        setAddressRecyclerView();
        OrderAdapter orderAdapter = new OrderAdapter(this, dataStore, listDTOS);
        orderRecyclerView.setAdapter(orderAdapter);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setAddressRecyclerView() {
        Tool.getTokenData("/prod-api/api/takeout/address/list", Tool.sp(context, "token"), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                AddressBean addressBean = new Gson().fromJson(response.body().string(), AddressBean.class);
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        AddressAdapter addressAdapter = new AddressAdapter(context, addressBean.getData(), sureOrderText, orderSureBean);
                        recyclerView.setAdapter(addressAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        dialog = new AlertDialog.Builder(context).setView(recyclerView).setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create();
                    }
                });
            }
        });
    }

    private void bindView() {
        llBg = (LinearLayout) findViewById(R.id.ll_bg);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        sureOrderText = (TextView) findViewById(R.id.sure_order_text);
        sureOrderSelect = (TextView) findViewById(R.id.sure_order_select);
        orderRecyclerView = (RecyclerView) findViewById(R.id.order_recyclerView);
        orderSure = (Button) findViewById(R.id.order_sure);
    }
}