package com.example.smartcityc.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartcityc.Adapter.ShopFollowAdapter;
import com.example.smartcityc.R;
import com.example.smartcityc.Tool.Tool;
import com.example.smartcityc.data.ShopStore;
import com.google.gson.Gson;

import java.util.List;

public class OutFoodFollowFragment extends Fragment {
    View view;
    Context context;
    private RecyclerView shopFollow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_out_food_follow, container, false);
        context = getContext();
        initData();
        return view;
    }

    private void initData() {
        shopFollow = view.findViewById(R.id.shop_follow);
        ShopStore shopStore = new Gson().fromJson(Tool.sp(getContext(), "collection"), ShopStore.class);
        if (shopStore == null) {
            Tool.setDialog(context,"你还没有收藏哦!").show();
            return;
        }
        ShopFollowAdapter shopFollowAdapter = new ShopFollowAdapter(getContext(), shopStore.getMap(), shopStore.getSellerIds());
        System.out.println("shopFollowAdapter");
        shopFollow.setAdapter(shopFollowAdapter);
        shopFollow.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}