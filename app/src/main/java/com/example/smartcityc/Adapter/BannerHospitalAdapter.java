package com.example.smartcityc.Adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Bean.HospitalBannerBean;
import com.example.smartcityc.Tool.Config;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import java.util.List;

public class BannerHospitalAdapter extends BannerImageAdapter<HospitalBannerBean.DataDTO> {
    Context context;
    public BannerHospitalAdapter(List<HospitalBannerBean.DataDTO> mData,Context context) {
        super(mData);
        this.context = context;
    }

    @Override
    public void onBindView(BannerImageHolder bannerImageHolder, HospitalBannerBean.DataDTO dataDTO, int i, int i1) {
        Glide.with(context).load(Config.baseUrl+dataDTO.getImgUrl()).apply(new RequestOptions().transform(new CenterCrop(),new RoundedCorners(30))).into(bannerImageHolder.imageView);
    }
}
