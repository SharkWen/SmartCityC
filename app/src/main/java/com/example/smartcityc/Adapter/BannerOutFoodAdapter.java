package com.example.smartcityc.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.example.smartcityc.Bean.OutFoodBannerBean;
import com.example.smartcityc.Tool.Config;
import com.example.smartcityc.Tool.Tool;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BannerOutFoodAdapter extends BannerImageAdapter<OutFoodBannerBean.RowsDTO> {
    Context context;
    List<OutFoodBannerBean.RowsDTO> list;

    public BannerOutFoodAdapter( Context context, List<OutFoodBannerBean.RowsDTO> list) {
        super(list);
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindView(BannerImageHolder bannerImageHolder, OutFoodBannerBean.RowsDTO rowsDTO, int i, int i1) {
        OkHttpClient client = new OkHttpClient();
        client.newCall(new Request.Builder().url(Config.baseUrl + list.get(i).getAdvImg()).build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                byte[] data = response.body().bytes();
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                Tool.handler.post(()->{
                    bannerImageHolder.imageView.setImageBitmap(bitmap);
                });
            }
        });
    }
}
