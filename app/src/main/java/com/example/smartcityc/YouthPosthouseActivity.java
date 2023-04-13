package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartcityc.Adapter.YouthAdapter;
import com.example.smartcityc.Adapter.YouthListAdapter;
import com.example.smartcityc.Bean.YouthBean;
import com.example.smartcityc.Bean.YouthListBean;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class YouthPosthouseActivity extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private RecyclerView yzRecycler;
    private RecyclerView yzRecycler1;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youth_posthouse);
        bindView();
        initData();
    }

    private void initData() {
        tvTitle.setText("青年驿站");
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Tool.getTokenData("/prod-api/api/youth-inn/talent-policy-area/list", Tool.sp(this, "token"), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                YouthBean youthBean = new Gson().fromJson(response.body().string(),YouthBean.class);
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(youthBean.getCode() != 200){
                            Tool.setDialog(context,youthBean.getMsg()).show();
                            return;
                        }
                        YouthAdapter youthAdapter = new YouthAdapter(context,youthBean.getDataList());
                        yzRecycler.setLayoutManager(new GridLayoutManager(context,3));
                        yzRecycler.setAdapter(youthAdapter);
                    }
                });
            }
        });
    Tool.getTokenData("/prod-api/api/youth-inn/youth-inn/list", Tool.sp(context, "token"), new Callback() {
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {

        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            YouthListBean youthListBean = new Gson().fromJson(response.body().string(),YouthListBean.class);
            Tool.handler.post(new Runnable() {
                @Override
                public void run() {
                    if(youthListBean.getCode() != 200){
                        Tool.setDialog(context,youthListBean.getMsg()).show();
                        return;
                    }
                    YouthListAdapter youthListAdapter = new YouthListAdapter(context,youthListBean.getRows());
                    yzRecycler1.setLayoutManager(new LinearLayoutManager(context));
                    yzRecycler1.setAdapter(youthListAdapter);
                }
            });
        }
    });
    }

    private void bindView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);;
        yzRecycler = (RecyclerView) findViewById(R.id.yz_recycler);
        yzRecycler1 = (RecyclerView) findViewById(R.id.yz_recycler1);

    }
}