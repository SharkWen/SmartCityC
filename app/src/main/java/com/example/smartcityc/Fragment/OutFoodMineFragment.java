package com.example.smartcityc.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Address;
import com.example.smartcityc.R;
import com.example.smartcityc.Tool.Tool;

public class OutFoodMineFragment extends Fragment {
    View view;
    Context context;
    private ImageView outFoodMineAvatar;
    private TextView outFoodMineName;
    private LinearLayout outFoodMineAddress;
    private LinearLayout outFoodMineCollection;
    private LinearLayout outFoodFoodMine;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_out_food_mine, container, false);
        context = getContext();
        bindView();
        initData();
        initEvent();
        return view;
    }

    private void initEvent() {
        outFoodMineAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, Address.class));
            }
        });
    }

    private void initData() {
        if(!Tool.shp(context).contains("token")){
            Tool.setDialog(context,"请登录").show();
            return;
        }
        Glide.with(context).load(Tool.sp(context,"avaterurl")).apply(new RequestOptions().transform(new RoundedCorners(200))).into(outFoodMineAvatar);
        outFoodMineName.setText(Tool.sp(context,"nick"));
    }

    private void bindView() {
        outFoodMineAvatar = view.findViewById(R.id.out_food_mine_avatar);
        outFoodMineName = view.findViewById(R.id.out_food_mine_name);
        outFoodMineAddress = view.findViewById(R.id.out_food_mine_address);
        outFoodMineCollection = view.findViewById(R.id.out_food_mine_collection);
        outFoodFoodMine = view.findViewById(R.id.out_food_food_mine);
    }
}