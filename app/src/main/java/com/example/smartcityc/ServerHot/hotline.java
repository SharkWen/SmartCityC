package com.example.smartcityc.ServerHot;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcityc.R;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;
import com.youth.banner.Banner;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class hotline extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();
    Handler handler = new Handler();
    Context context = this;
    private Banner banner;
    private RecyclerView service;
    private RecyclerView myList;
    private TextView titleBarTitle;
    private ImageButton titleBarBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotline);
        Config.token = Tool.sp(this, "token");
        initView();
        initBanners();
        initService();
        initMyList();
    }

    private void initMyList() {
        client.newCall(new Request.Builder().url(Config.address + "/prod-api/api/gov-service-hotline/appeal/my-list").header("Authorization", Config.token).build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseText = response.body().string();
                HotlineMyListBean data = new Gson().fromJson(responseText, HotlineMyListBean.class);
                handler.post(() -> {
                    if (data.getCode() != 200) {
                        Tool.setDialog(context, data.getMsg()).show();
                        return;
                    }
                    HotlineMyListAdapter adapter = new HotlineMyListAdapter(getApplicationContext(), data.getRows());
                    myList.setAdapter(adapter);
                    myList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                });
            }
        });
    }

    private void initService() {
        client.newCall(new Request.Builder().url(Config.address + "/prod-api/api/gov-service-hotline/appeal-category/list").header("Authorization", Config.token).build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseText = response.body().string();
                System.out.println(responseText);
                System.out.println(responseText);
                HotlineServiceBean data = new Gson().fromJson(responseText, HotlineServiceBean.class);
                handler.post(() -> {
                    if (data.getCode() != 200) {
                        Tool.setDialog(context, data.getMsg()).show();
                        return;
                    }
                    HotlineServiceAdapter adapter = new HotlineServiceAdapter(getApplicationContext(), data.getRows());
                    service.setAdapter(adapter);
                    service.setLayoutManager(new GridLayoutManager(getApplicationContext(), 8));
                });
            }
        });
    }

    private void initBanners() {
        client.newCall(new Request.Builder().url(Config.address + "/prod-api/api/gov-service-hotline/ad-banner/list").header("Authorization", Config.token).build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseText = response.body().string();
                HotlineBannerBean bean = new Gson().fromJson(responseText, HotlineBannerBean.class);
                handler.post(() -> {
                    if (bean.getCode() != 200) {
                        Tool.setDialog(context, bean.getMsg()).show();
                        return;
                    }
                    HotlineBannerAdapter adapter = new HotlineBannerAdapter(bean.getData(), getApplicationContext());
                    banner.setAdapter(adapter);
                });
            }
        });
    }

    private void initView() {
        banner = findViewById(R.id.hotline_banner);
        service = findViewById(R.id.hotline_service);
        myList = findViewById(R.id.hotline_my_list);
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        titleBarTitle.setText("政府热线");
        titleBarBack = (ImageButton) findViewById(R.id.title_bar_back);
        titleBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}