package com.example.smartcityc.BusOrderFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.smartcityc.Adapter.BusOrderAdapter;
import com.example.smartcityc.Adapter.OrderAdapter;
import com.example.smartcityc.Bean.OrderBean;
import com.example.smartcityc.R;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class OrderYesFragment extends Fragment {
    View view;
    ListView listView;
    SharedPreferences sp;
    Context context;
    OrderBean orderBean;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order_yes, container, false);
        initView();
        initData();
        return view;
    }
    private void initData() {
        context = getContext();
        sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        getOrderListData();
    }

    private void initView() {
        listView = view.findViewById(R.id.order_list_y_view);
    }
    private void getOrderListData(){
        if (!sp.contains("token")) {
            Tool.setDialog(context, "请注册登录").show();
            return;
        }
        Tool.getTokenData("/prod-api/api/bus/order/list", sp.getString("token", ""), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String result = response.body().string();
                orderBean = new Gson().fromJson(result, OrderBean.class);
                Tool.handler.post(() -> {
                    BusOrderAdapter.setListView(context, orderBean, listView, true);
                });
            }
        });
    }
}