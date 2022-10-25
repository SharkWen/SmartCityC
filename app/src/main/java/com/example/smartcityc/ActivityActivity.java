package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Adapter.BannerActivityAdapter;
import com.example.smartcityc.Bean.ActivityBannerBean;
import com.example.smartcityc.Bean.ActivityBean;
import com.example.smartcityc.Bean.ActivityCategoryBean;
import com.example.smartcityc.Bean.CategoryNewsBean;
import com.example.smartcityc.Bean.HorseBean;
import com.example.smartcityc.Tool.Config;
import com.example.smartcityc.Tool.Tool;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.youth.banner.Banner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ActivityActivity extends AppCompatActivity {
    private Banner activityBanner;
    private TabLayout activityTabLayout;
    private ListView activityListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
        bindView();
        initData();
    }

    private void initData() {
        getBanner();
        getTabLayout();
    }

    private void getTabLayout() {
        Tool.getData("/prod-api/api/activity/category/list", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ActivityCategoryBean activityCategoryBean = new Gson().fromJson(response.body().string(), ActivityCategoryBean.class);
                Tool.handler.post(() -> {
                    getListView(activityCategoryBean.getData().get(0).getId());
                    for (ActivityCategoryBean.DataDTO n : activityCategoryBean.getData()) {
                        activityTabLayout.addTab(activityTabLayout.newTab().setText(n.getName()));
                    }
                    activityTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            int position = tab.getPosition();
                            getListView(activityCategoryBean.getData().get(position).getId());
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {

                        }
                    });
                });
            }
        });
    }

    private void getBanner() {
        Tool.getData("/prod-api/api/activity/rotation/list", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ActivityBannerBean activityBannerBean = new Gson().fromJson(response.body().string(), ActivityBannerBean.class);
                Tool.handler.post(() -> {
                    BannerActivityAdapter bannerActivityAdapter = new BannerActivityAdapter(activityBannerBean.getRows(), ActivityActivity.this);
                    activityBanner.setAdapter(bannerActivityAdapter);
                });
            }
        });
    }

    private void getListView(int type) {
        String[] strings = new String[]{"horsePic", "horseTitle", "horsePrice", "horseMj"};
        int[] ints = new int[]{R.id.horse_pic, R.id.horse_title, R.id.horse_price, R.id.horse_mj};
        List<Map<String, Object>> list = new ArrayList<>();
        Tool.getData("/prod-api/api/activity/activity/list?categoryId="+type, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ActivityBean activityBean = new Gson().fromJson(response.body().string(), ActivityBean.class);
                for (ActivityBean.RowsDTO n : activityBean.getRows()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("horsePic", Config.baseUrl + n.getImgUrl());
                    map.put("horseTitle", n.getName());
                    map.put("horsePrice", n.getPublishTime());
                    map.put("horseMj", n.getLikeNum());
                    list.add(map);
                }
                Tool.handler.post(() -> {
                    SimpleAdapter simpleAdapter = new SimpleAdapter(ActivityActivity.this, list, R.layout.activity_item, strings, ints);
                    simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                        @Override
                        public boolean setViewValue(View view, Object o, String s) {
                            ImageView imageView = view.findViewById(R.id.horse_pic);
                            if (imageView != null) {
                                Glide.with(ActivityActivity.this).load(s)
                                        .apply(new RequestOptions().transform(new CenterCrop())).into(imageView);
                                return true;
                            }
                            return false;
                        }
                    });
                    activityListView.setAdapter(simpleAdapter);
                    activityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            ActivityDetails.title = activityBean.getRows().get(i).getName();
                            ActivityDetails.contentH = activityBean.getRows().get(i).getContent();
                            ActivityActivity.this.startActivity(new Intent(ActivityActivity.this,ActivityDetails.class));
                        }
                    });
                });
            }
        });
    }

    private void bindView() {
        activityBanner = (Banner) findViewById(R.id.activity_banner);
        activityTabLayout = (TabLayout) findViewById(R.id.activity_tabLayout);
        activityListView = (ListView) findViewById(R.id.activity_list_view);
    }
}