package com.example.smartcityc.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Adapter.BannerAdapter;
import com.example.smartcityc.Adapter.ServiceAdapter;
import com.example.smartcityc.Bean.BannerBean;
import com.example.smartcityc.Bean.CategoryNewsBean;
import com.example.smartcityc.Bean.NewsBean;
import com.example.smartcityc.Bean.ServiceBean;
import com.example.smartcityc.NewListActivity;
import com.example.smartcityc.R;
import com.example.smartcityc.Tool.Config;
import com.example.smartcityc.Tool.Tool;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class NewsFragment extends Fragment {
    View view;
    Context context;
    private Banner bannerFragHome;
    private GridView homeFragGl;
    private TabLayout homeFragTab;
    private ListView homeFragList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);
        context = getContext();
        bindView();
        initData();
        return view;
    }

    private void initData() {
        getBanner();
        initHotNewsData();
        getTabLayoutData();
        getNews();
    }
    private void getTabLayoutData() {
        Tool.getData("/prod-api/press/category/list", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                CategoryNewsBean categoryNewsBean = new Gson().fromJson(response.body().string(), CategoryNewsBean.class);
                Tool.handler.post(() -> {
                    for (CategoryNewsBean.DataDTO n : categoryNewsBean.getData()) {
                        homeFragTab.addTab(homeFragTab.newTab().setText(n.getName()));
                    }
                    getNewsData(categoryNewsBean.getData().get(0).getId());
                    homeFragTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            int position = tab.getPosition();
                            getNewsData(categoryNewsBean.getData().get(position).getId());
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

    private void getNews() {
        List<String> stringList = new ArrayList<>();
        List<Map<String, Object>> list = new ArrayList<>();
        String[] strings = new String[]{"newsItemTitle", "newsItemContent", "newsItemDate", "newsItemLikeNum", "newsItemImage"};
        int[] ints = new int[]{R.id.news_item_title, R.id.news_item_content, R.id.news_item_date, R.id.news_item_likeNum, R.id.news_item_image};
        Tool.getData("/prod-api/press/press/list", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                NewsBean newsBean = new Gson().fromJson(response.body().string(), NewsBean.class);
                for (NewsBean.RowsDTO n : newsBean.getRows()) {
                    stringList.add(n.getTitle());
                    Map<String, Object> map = new HashMap<>();
                    map.put("newsItemTitle", n.getTitle());
                    map.put("newsItemContent", Tool.html(n.getContent()));
                    map.put("newsItemDate", n.getCreateTime());
                    map.put("newsItemLikeNum", n.getCommentNum() == null ? "0评" : n.getCommentNum() + "评");
                    map.put("newsItemImage", Config.baseUrl + n.getCover());
                    list.add(map);
                }
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter simpleAdapter = new SimpleAdapter(context, list, R.layout.news_item, strings, ints);
                        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                            @Override
                            public boolean setViewValue(View view, Object o, String s) {
                                ImageView imageView = view.findViewById(R.id.news_item_image);
                                if (imageView != null) {
                                    Glide.with(context).load(s)
                                            .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(20)))
                                            .into(imageView);
                                    return true;
                                }
                                return false;
                            }
                        });
                    }
                });
            }
        });
    }

    private void getNewsData(int id) {
        List<Map<String, Object>> list = new ArrayList<>();
        String[] strings = new String[]{"newsItemTitle", "newsItemContent", "newsItemDate", "newsItemLikeNum", "newsItemImage"};
        int[] ints = new int[]{R.id.news_item_title, R.id.news_item_content, R.id.news_item_date, R.id.news_item_likeNum, R.id.news_item_image};
        Tool.getData("/prod-api/press/press/list?type=" + id, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                NewsBean newsBean = new Gson().fromJson(response.body().string(), NewsBean.class);
                for (NewsBean.RowsDTO n : newsBean.getRows()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("newsItemTitle", n.getTitle());
                    map.put("newsItemContent", Tool.html(n.getContent()));
                    map.put("newsItemDate", n.getCreateTime());
                    map.put("newsItemLikeNum", n.getCommentNum() == null ? "0评" : n.getCommentNum() + "评");
                    map.put("newsItemImage", Config.baseUrl + n.getCover());
                    list.add(map);
                }
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter simpleAdapter = new SimpleAdapter(context, list, R.layout.news_item, strings, ints);
                        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                            @Override
                            public boolean setViewValue(View view, Object o, String s) {
                                ImageView imageView = view.findViewById(R.id.news_item_image);
                                if (imageView != null) {
                                    Glide.with(context).load(s)
                                            .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(20)))
                                            .into(imageView);
                                    return true;
                                }
                                return false;
                            }
                        });
                        homeFragList.setAdapter(simpleAdapter);
                    }
                });
            }
        });
    }

    private void initHotNewsData() {
        List<Map<String, Object>> list = new ArrayList<>();
        Tool.getData("/prod-api/press/press/list?hot=Y", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                NewsBean newsBean = new Gson().fromJson(response.body().string(), NewsBean.class);
                for (NewsBean.RowsDTO n : newsBean.getRows()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("image_service", Config.baseUrl + n.getCover());
                    map.put("text_service", n.getTitle());
                    list.add(map);
                }
                Tool.handler.post(() -> {
                    SimpleAdapter simpleAdapter = new SimpleAdapter(context, list, R.layout.home_frag_gl_news
                            , new String[]{"image_service", "text_service"}, new int[]{R.id.home_frag_iv, R.id.home_frag_tv});
                    simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                        @Override
                        public boolean setViewValue(View view, Object o, String s) {
                            ImageView imageView = view.findViewById(R.id.home_frag_iv);
                            if (imageView != null) {
                                Glide.with(context).load(s)
                                        .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(20)))
                                        .into(imageView);
                                return true;
                            }
                            return false;
                        }
                    });
                    homeFragGl.setAdapter(simpleAdapter);
                });

            }
        });
    }


    private void getBanner() {
        Tool.getData("/prod-api/api/rotation/list?pageNum=1&pageSize=8&type=2", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("失败");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                BannerBean bannerBean = new Gson().fromJson(response.body().string(), BannerBean.class);
                Tool.handler.post(() -> {
                    BannerAdapter bannerAdapter = new BannerAdapter(bannerBean.getRows(), context);
                    bannerFragHome.setAdapter(bannerAdapter).setIndicator(new CircleIndicator(context));
                });
            }
        });
    }
    private void bindView() {
        bannerFragHome = view.findViewById(R.id.banner_fragHome);
        homeFragGl = view.findViewById(R.id.home_frag_gl);
        homeFragTab = view.findViewById(R.id.home_frag_tab);
        homeFragList = view.findViewById(R.id.home_frag_list);
    }
}