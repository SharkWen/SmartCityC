package com.example.smartcityc.OrderFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartcityc.Adapter.ShopOrderListAdapter;
import com.example.smartcityc.Bean.OrderListBean;
import com.example.smartcityc.R;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RefundFragment extends Fragment {

    View view;
    Context context;
    private RecyclerView orderFragAllRv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_refund, container, false);
        context = getContext();
        bindView();
        initData();
        return view;
    }
    private void initData() {
        Tool.getTokenData("/prod-api/api/takeout/order/list?status=退款&pageNum=1&pageSize=8", Tool.sp(context, "token"), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                OrderListBean orderListBean = new Gson().fromJson(response.body().string(),OrderListBean.class);
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ShopOrderListAdapter shopOrderListAdapter  = new ShopOrderListAdapter(context,orderListBean.getRows());
                        orderFragAllRv.setAdapter(shopOrderListAdapter);
                        orderFragAllRv.setLayoutManager(new LinearLayoutManager(context));
                    }
                });
            }
        });
    }

    private void bindView() {
        orderFragAllRv = view.findViewById(R.id.order_frag_all_rv);
    }
}