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

public class NewsDetailsActivity extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private ImageView newsDetailsImage;
    private TextView newsDetailsContent;
    private ListView newsDetailsListview;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        bindView();
        initData();
    }

    private void initData() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.back = "index";
                startActivity(new Intent(context,MainActivity.class));
            }
        });
        tvTitle.setText(getIntent().getStringExtra("title"));
        Glide.with(context).load(getIntent().getStringExtra("image")).apply(new RequestOptions().transform(new CenterCrop(),new RoundedCorners(20))).into(newsDetailsImage);
        newsDetailsContent.setText(getIntent().getStringExtra("content"));
        getNewsData(getIntent().getStringExtra("id"));
    }

    private void getNewsData(String id) {
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
                        newsDetailsListview.setAdapter(simpleAdapter);
                        newsDetailsListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(context, NewsDetailsActivity.class);
                                intent.putExtra("id",id);
                                intent.putExtra("title",newsBean.getRows().get(i).getTitle());
                                intent.putExtra("image",Config.baseUrl + newsBean.getRows().get(i).getCover());
                                intent.putExtra("content",Tool.html(newsBean.getRows().get(i).getContent()));
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
    }

    private void bindView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        newsDetailsImage = (ImageView) findViewById(R.id.news_details_image);
        newsDetailsContent = (TextView) findViewById(R.id.news_details_content);
        newsDetailsListview = (ListView) findViewById(R.id.news_details_listview);
    }
}