package com.example.smartcityc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.smartcityc.Bean.BannerBean;
import com.example.smartcityc.NewsDetailsActivity;
import com.example.smartcityc.Tool.Config;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import java.util.List;

public class BannerAdapter extends BannerImageAdapter<BannerBean.RowsDTO> {
    List<BannerBean.RowsDTO> list;
    Context context;
    public BannerAdapter(List<BannerBean.RowsDTO> list, Context context) {
        super(list);
        this.list = list;
        this.context = context;
    }
    @Override
    public void onBindView(BannerImageHolder bannerImageHolder, BannerBean.RowsDTO rowsDTO, int i, int i1) {
        Glide.with(context).load(Config.baseUrl+list.get(i).getAdvImg()).into(bannerImageHolder.imageView);
        bannerImageHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(new Gson().toJson(list));
                Intent intent = new Intent(context, NewsDetailsActivity.class);
                intent.putExtra("id","22");
                intent.putExtra("title",list.get(i).getAdvTitle());
                intent.putExtra("image",Config.baseUrl + list.get(i).getAdvImg());
                intent.putExtra("content", Tool.html(list.get(i).getServModule()));
                context.startActivity(intent);
            }
        });
    }

}
