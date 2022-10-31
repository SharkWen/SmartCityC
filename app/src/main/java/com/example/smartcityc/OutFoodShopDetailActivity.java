package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Bean.OutFoodTjBean;
import com.example.smartcityc.Tool.Config;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OutFoodShopDetailActivity extends AppCompatActivity {
    private LinearLayout llBg;
    private TextView tvBack;
    private TextView tvTitle;
    private ListView outFoodShopListview;
    Context context = this;
    public static String themeId = "";
    public static String shopName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_food_shop_detail);
        bindView();
        initData();
    }

    private void initData() {
        llBg.setBackgroundResource(R.color.yellow);
        tvTitle.setText(shopName);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String name = getIntent().getStringExtra("name");
        if (name!=null) {
            getListViewName(name);
        } else {
            getListView(themeId);
        }
    }

    private void getListViewName(String name) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        String[] strings = new String[]{"image", "name", "score", "yxl", "avgCost", "time", "distance"};
        int[] ints = new int[]{R.id.out_food_lv_image, R.id.out_food_lv_name, R.id.out_food_lv_score, R.id.out_food_lv_yxl, R.id.out_food_lv_avgCost, R.id.out_food_lv_time, R.id.out_food_lv_distance};
        Tool.getData("/prod-api/api/takeout/seller/list?name=" + name, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                OutFoodTjBean outFoodTjBean = new Gson().fromJson(response.body().string(), OutFoodTjBean.class);
                for (OutFoodTjBean.RowsDTO n : outFoodTjBean.getRows()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("image", Config.baseUrl + n.getImgUrl());
                    map.put("name", n.getName());
                    map.put("score", n.getScore());
                    map.put("yxl", "月销量:" + n.getSaleQuantity());
                    map.put("avgCost", "人均消费:" + n.getAvgCost() + "元");
                    map.put("time", "到店所需时间:" + n.getDeliveryTime() + "分钟");
                    map.put("distance", "距离" + n.getDistance() + "米");
                    mapList.add(map);
                }
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter simpleAdapter = new SimpleAdapter(context, mapList, R.layout.out_food_item, strings, ints);
                        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                            @Override
                            public boolean setViewValue(View view, Object o, String s) {
                                ImageView imageView = view.findViewById(R.id.out_food_lv_image);
                                if (imageView != null) {
                                    Glide.with(context).load(s).apply(new RequestOptions().transform(new CenterCrop())).into(imageView);
                                    return true;
                                }
                                return false;
                            }
                        });
                        outFoodShopListview.setAdapter(simpleAdapter);
                        outFoodShopListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                ShopDetailsActivity.image = Config.baseUrl + outFoodTjBean.getRows().get(i).getImgUrl();
                                ShopDetailsActivity.nameD = outFoodTjBean.getRows().get(i).getName();
                                ShopDetailsActivity.timeD = outFoodTjBean.getRows().get(i).getDeliveryTime() + "";
                                ShopDetailsActivity.distance = outFoodTjBean.getRows().get(i).getDistance() + "";
                                ShopDetailsActivity.score = outFoodTjBean.getRows().get(i).getScore() + "";
                                ShopDetailsActivity.mouthSells = outFoodTjBean.getRows().get(i).getSaleQuantity() + "";
                                ShopDetailsActivity.sellerId = outFoodTjBean.getRows().get(i).getId() + "";
                                startActivity(new Intent(context, ShopDetailsActivity.class));
                            }
                        });
                    }
                });
            }
        });
    }

    private void getListView(String themeId) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        String[] strings = new String[]{"image", "name", "score", "yxl", "avgCost", "time", "distance"};
        int[] ints = new int[]{R.id.out_food_lv_image, R.id.out_food_lv_name, R.id.out_food_lv_score, R.id.out_food_lv_yxl, R.id.out_food_lv_avgCost, R.id.out_food_lv_time, R.id.out_food_lv_distance};
        Tool.getData("/prod-api/api/takeout/seller/list?themeId=" + themeId, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                OutFoodTjBean outFoodTjBean = new Gson().fromJson(response.body().string(), OutFoodTjBean.class);
                for (OutFoodTjBean.RowsDTO n : outFoodTjBean.getRows()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("image", Config.baseUrl + n.getImgUrl());
                    map.put("name", n.getName());
                    map.put("score", n.getScore());
                    map.put("yxl", "月销量:" + n.getSaleQuantity());
                    map.put("avgCost", "人均消费:" + n.getAvgCost() + "元");
                    map.put("time", "到店所需时间:" + n.getDeliveryTime() + "分钟");
                    map.put("distance", "距离" + n.getDistance() + "米");
                    mapList.add(map);
                }
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter simpleAdapter = new SimpleAdapter(context, mapList, R.layout.out_food_item, strings, ints);
                        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                            @Override
                            public boolean setViewValue(View view, Object o, String s) {
                                ImageView imageView = view.findViewById(R.id.out_food_lv_image);
                                if (imageView != null) {
                                    Glide.with(context).load(s).apply(new RequestOptions().transform(new CenterCrop())).into(imageView);
                                    return true;
                                }
                                return false;
                            }
                        });
                        outFoodShopListview.setAdapter(simpleAdapter);
                        outFoodShopListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                ShopDetailsActivity.image = Config.baseUrl + outFoodTjBean.getRows().get(i).getImgUrl();
                                ShopDetailsActivity.nameD = outFoodTjBean.getRows().get(i).getName();
                                ShopDetailsActivity.timeD = outFoodTjBean.getRows().get(i).getDeliveryTime() + "";
                                ShopDetailsActivity.distance = outFoodTjBean.getRows().get(i).getDistance() + "";
                                ShopDetailsActivity.score = outFoodTjBean.getRows().get(i).getScore() + "";
                                ShopDetailsActivity.sellerId = outFoodTjBean.getRows().get(i).getId() + "";
                                startActivity(new Intent(context, ShopDetailsActivity.class));
                            }
                        });
                    }
                });
            }
        });
    }

    private void bindView() {
        llBg = (LinearLayout) findViewById(R.id.ll_bg);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        outFoodShopListview = (ListView) findViewById(R.id.out_food_shop_listview);
    }
}