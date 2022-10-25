package com.example.smartcityc.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Adapter.BannerOutFoodAdapter;
import com.example.smartcityc.Bean.OutFoodBannerBean;
import com.example.smartcityc.Bean.OutFoodIconBean;
import com.example.smartcityc.Bean.OutFoodTjBean;
import com.example.smartcityc.OutFoodShopDetailActivity;
import com.example.smartcityc.R;
import com.example.smartcityc.ShopDetailsActivity;
import com.example.smartcityc.Tool.Config;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;
import com.youth.banner.Banner;

import org.w3c.dom.ls.LSException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OutFoodIndexFragment extends Fragment {
    View view;
    Context context;
    private EditText outFoodEtSearch;
    private Button outFoodBtSearch;
    Banner outFoodBanner;
    GridView gridView, gridView1;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_out_food_index, container, false);
        context = getContext();
        bindView();
        initData();
        return view;
    }

    private void initData() {
        getBannerData();
        getIconData();
        getTjData();
        getListView();
        outFoodBtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,OutFoodShopDetailActivity.class);
                intent.putExtra("name",outFoodEtSearch.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void getTjData() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        String[] strings = new String[]{"image", "score", "num"};
        int[] ints = new int[]{R.id.out_food_tj_image, R.id.out_food_score, R.id.out_food_tj_num};
        Tool.getData("/prod-api/api/takeout/seller/list?recommend=Y", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                OutFoodTjBean outFoodTjBean = new Gson().fromJson(response.body().string(), OutFoodTjBean.class);
                for (OutFoodTjBean.RowsDTO n : outFoodTjBean.getRows()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("image", Config.baseUrl + n.getImgUrl());
                    map.put("score", n.getScore());
                    map.put("num", "近三个小时" + n.getSaleNum3hour() + "单");
                    mapList.add(map);
                }
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter simpleAdapter = new SimpleAdapter(context, mapList, R.layout.out_food_tj_item, strings, ints);
                        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                            @Override
                            public boolean setViewValue(View view, Object o, String s) {
                                ImageView imageView = view.findViewById(R.id.out_food_tj_image);
                                if (imageView != null) {
                                    Glide.with(context).load(s).apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(20))).into(imageView);
                                    return true;
                                }
                                return false;
                            }
                        });
                        gridView1.setAdapter(simpleAdapter);
                    }
                });
            }
        });
    }

    private void getIconData() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Tool.getData("/prod-api/api/takeout/theme/list", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                OutFoodIconBean iconBean = new Gson().fromJson(response.body().string(), OutFoodIconBean.class);
                for (OutFoodIconBean.DataDTO n : iconBean.getData()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("icon", Config.baseUrl + n.getImgUrl());
                    map.put("text", n.getThemeName());
                    mapList.add(map);
                }
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter simpleAdapter = new SimpleAdapter(context, mapList, R.layout.home_frag_gl, new String[]{"icon", "text"}, new int[]{R.id.home_frag_iv, R.id.home_frag_tv});
                        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                            @Override
                            public boolean setViewValue(View view, Object o, String s) {
                                ImageView imageView = view.findViewById(R.id.home_frag_iv);
                                if (imageView != null) {
                                    Glide.with(context).load(s).apply(new RequestOptions().transform(new CenterCrop())).into(imageView);
                                    return true;
                                }
                                return false;
                            }
                        });
                        gridView.setAdapter(simpleAdapter);
                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                TextView textView = view.findViewById(R.id.home_frag_tv);
                                OutFoodShopDetailActivity.themeId = iconBean.getData().get(i).getId() + "";
                                OutFoodShopDetailActivity.shopName = textView.getText().toString();
                                startActivity(new Intent(context, OutFoodShopDetailActivity.class));
                            }
                        });
                    }
                });
            }
        });
    }


    private void getBannerData() {
        Tool.getData("/prod-api/api/takeout/rotation/list", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                OutFoodBannerBean outFoodBannerBean = new Gson().fromJson(response.body().string(), OutFoodBannerBean.class);
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("fsdf++++++++++++++++++++++++++++++");
                        BannerOutFoodAdapter bannerOutFoodAdapter = new BannerOutFoodAdapter(context, outFoodBannerBean.getRows());
                        outFoodBanner.setAdapter(bannerOutFoodAdapter);
                    }
                });
            }
        });
    }

    private void getListView() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        String[] strings = new String[]{"image", "name", "score", "yxl", "avgCost", "time", "distance"};
        int[] ints = new int[]{R.id.out_food_lv_image, R.id.out_food_lv_name, R.id.out_food_lv_score, R.id.out_food_lv_yxl, R.id.out_food_lv_avgCost, R.id.out_food_lv_time, R.id.out_food_lv_distance};
        Tool.getData("/prod-api/api/takeout/seller/list", new Callback() {
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
                        listView.setAdapter(simpleAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        outFoodEtSearch = view.findViewById(R.id.out_food_et_search);
        outFoodBtSearch = view.findViewById(R.id.out_food_bt_search);
        outFoodBanner = view.findViewById(R.id.out_food_banner);
        gridView = view.findViewById(R.id.out_food_gridView);
        gridView1 = view.findViewById(R.id.out_food_tj);
        listView = view.findViewById(R.id.out_food_listView);
    }
}