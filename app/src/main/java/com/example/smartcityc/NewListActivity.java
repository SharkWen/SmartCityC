package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Bean.NewsBean;
import com.example.smartcityc.Tool.Config;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class NewListActivity extends AppCompatActivity {
    private ListView newsListView;
    Context context;
    public static String id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);
        context = this;
        bindView();
        initData(id);
    }

    private void initData(String id) {
        if(id.equals("")){
            Tool.setDialog(context,"输入的内容在api接口数据没有查询到相关新闻资讯列表").show();
        }else {
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
                            newsListView.setAdapter(simpleAdapter);
                        }
                    });
                }
            });
        }

    }

    private void bindView() {
        newsListView = findViewById(R.id.news_list_views);
    }
}