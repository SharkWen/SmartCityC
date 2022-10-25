package com.example.smartcityc.Adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.example.smartcityc.Bean.BannerBean;
import com.example.smartcityc.Tool.Config;
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
    }

}
