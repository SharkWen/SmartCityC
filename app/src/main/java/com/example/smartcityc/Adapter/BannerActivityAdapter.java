package com.example.smartcityc.Adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Bean.ActivityBannerBean;
import com.example.smartcityc.Tool.Config;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import java.util.List;

public class BannerActivityAdapter extends BannerImageAdapter<ActivityBannerBean.RowsDTO> {
    Context context;

    public BannerActivityAdapter(List<ActivityBannerBean.RowsDTO> mData, Context context) {
        super(mData);
        this.context = context;
    }

    @Override
    public void onBindView(BannerImageHolder bannerImageHolder, ActivityBannerBean.RowsDTO rowsDTO, int i, int i1) {
        System.out.println(Config.baseUrl + rowsDTO.getAdvImg());
        Glide.with(this.context).load(Config.baseUrl + rowsDTO.getAdvImg()).into(bannerImageHolder.imageView);
    }
}
