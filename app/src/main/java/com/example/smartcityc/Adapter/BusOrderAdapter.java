package com.example.smartcityc.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;

import com.example.smartcityc.Bean.MsgBean;
import com.example.smartcityc.Bean.OrderBean;
import com.example.smartcityc.R;
import com.example.smartcityc.SmartBusOrderActivity;
import com.example.smartcityc.SmartLineDatePicker;
import com.example.smartcityc.Tool.Tool;
import com.example.smartcityc.data.SmartBusOrderStore;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BusOrderAdapter {
    public static void setListView(Context context, OrderBean orderBean, ListView listView, Boolean isPay) {
        SharedPreferences sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        List<String> ids = new ArrayList<>();
        List<Map<String, Object>> list = new ArrayList<>();
        SmartBusOrderStore busOrderStore = new Gson().fromJson(Tool.sp(context,"SmartOrder"),SmartBusOrderStore.class);
        String[] strings = new String[]{"orderName", "orderFirst", "orderEnd", "orderScore", "orderNum", "orderPay","orderTime"};
        int[] ints = new int[]{R.id.order_name, R.id.order_first, R.id.order_end, R.id.order_score, R.id.order_num, R.id.order_pay,R.id.order_time};
        for (int i = 0; i < orderBean.getRows().size(); i++) {
            if (isPay) {
                if (orderBean.getRows().get(i).getPayTime() == null) continue;
            } else {
                if (orderBean.getRows().get(i).getPayTime() != null) continue;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("orderName", "路线名称:" + orderBean.getRows().get(i).getPath());
            map.put("orderFirst", "起点:" + orderBean.getRows().get(i).getStart());
            map.put("orderEnd", "终点:" + orderBean.getRows().get(i).getEnd());
            map.put("orderScore", "票价: ￥" + orderBean.getRows().get(i).getPrice());
            map.put("orderNum", "订单编号:" + orderBean.getRows().get(i).getOrderNum());
            map.put("orderTime",busOrderStore.getMap().get(orderBean.getRows().get(i).getOrderNum()));
            map.put("orderPay", "支付");
            ids.add(orderBean.getRows().get(i).getOrderNum());
            list.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(context, list, R.layout.bus_order_item, strings, ints);
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            int id = 0;
            MsgBean msgBean = new MsgBean();

            @Override
            public boolean setViewValue(View view, Object o, String s) {
                Button b = view.findViewById(R.id.order_pay);
                if (b != null) {
                    if (isPay) {
                        b.setText("已支付");
                        b.setBackgroundResource(R.color.gray);
                        return true;
                    } else {
                        if (id >= ids.size()) return false;
//                        Log.e("id----------", "{\n" +
//                                "\"orderNum\": \"" + ids.get(id++) + "\",\n" +
//                                "\"paymentType\": \"电子支付\"\n" +
//                                "}");
                        String json = "{\n" +
                                "\"orderNum\": \"" + ids.get(id++) + "\",\n" +
                                "\"paymentType\": \"电子支付\"\n" +
                                "}";
                        b.setOnClickListener(view1 -> {
                            Tool.postTokenData("/prod-api/api/bus/pay", sp.getString("token", ""), json, new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                    String res = response.body().string();
                                    msgBean = new Gson().fromJson(res, MsgBean.class);
                                    Tool.handler.post(() -> {
                                        new AlertDialog.Builder(context).setTitle(msgBean.getMsg())
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        SmartBusOrderActivity.replace = "yesPay";
                                                        context.startActivity(new Intent(context, SmartBusOrderActivity.class));
                                                    }
                                                })
                                                .create().show();
                                    });
                                }
                            });
                        });
                        return true;
                    }
                }
                return false;
            }
        });
        listView.setAdapter(simpleAdapter);
    }
}
