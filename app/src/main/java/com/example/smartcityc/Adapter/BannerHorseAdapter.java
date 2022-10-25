package com.example.smartcityc.Adapter;

import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import java.util.List;

public class BannerHorseAdapter extends BannerImageAdapter<Integer> {

    public BannerHorseAdapter(List<Integer> mData) {
        super(mData);
    }

    @Override
    public void onBindView(BannerImageHolder bannerImageHolder, Integer integer, int i, int i1) {
        bannerImageHolder.imageView.setImageResource(integer);
    }
}
