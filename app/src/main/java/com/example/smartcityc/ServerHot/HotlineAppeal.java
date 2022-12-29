package com.example.smartcityc.ServerHot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcityc.R;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HotlineAppeal extends AppCompatActivity {
    private TextView titleBarTitle;
    private ImageButton titleBarBack;
    private RecyclerView appealList;
    OkHttpClient client = new OkHttpClient();
    Intent intent;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotline_appeal);
        intent = getIntent();
        initView();
        loadList();
    }

    private void loadList() {
        client.newCall(new Request.Builder().url(Config.address+"/prod-api/api/gov-service-hotline/appeal/list?appealCategoryId="+intent.getIntExtra("id",0)).header("Authorization", Config.token).build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseText = response.body().string();
                HotlineMyListBean data = new Gson().fromJson(responseText, HotlineMyListBean.class);
                HotlineMyListAdapter adapter = new HotlineMyListAdapter(getApplicationContext(),data.getRows());
                handler.post(()->{
                    appealList.setAdapter(adapter);
                    appealList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                });
            }
        });
    }

    private void initView(){
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        titleBarTitle.setText(intent.getStringExtra("title"));
        titleBarBack = (ImageButton) findViewById(R.id.title_bar_back);
        titleBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        appealList = (RecyclerView) findViewById(R.id.appeal_list);
    }
}