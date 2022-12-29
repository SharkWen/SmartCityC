package com.example.smartcityc.ServerHot;

import android.content.Context;
import android.os.Handler;

import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import java.util.List;

public class HotlineBannerAdapter extends BannerImageAdapter<HotlineBannerBean.DataDTO> {
    Context context;
    Handler handler = new Handler();
    public HotlineBannerAdapter(List<HotlineBannerBean.DataDTO> mData, Context context) {
        super(mData);
        this.context = context;
    }

    @Override
    public void onBindView(BannerImageHolder bannerImageHolder, HotlineBannerBean.DataDTO dataDTO, int i, int i1) {
        handler.post(()->{
            new Glide().setImageView(bannerImageHolder.imageView).setUrl(Config.address+dataDTO.getImgUrl()).load();
        });
    }
}
