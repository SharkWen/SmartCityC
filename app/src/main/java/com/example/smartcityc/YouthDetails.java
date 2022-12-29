package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Adapter.YouthDetailsContentAdapter;
import com.example.smartcityc.Bean.YouthDetailsBean;
import com.example.smartcityc.Bean.YouthDetailsContentBean;
import com.example.smartcityc.Tool.Config;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class YouthDetails extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private ImageView youthDetailsImage;
    private TextView youthDetailsContent;
    private RecyclerView youthDetailsRecyclerView;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youth_details);
        bindView();
        initData();
    }

    private void initData() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvTitle.setText("人才页");
        Tool.getTokenData("/prod-api/api/youth-inn/talent-policy-area/"+getIntent().getStringExtra("id"), Tool.sp(this, "token"), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                YouthDetailsBean youthDetailsBean = new Gson().fromJson(response.body().string(),YouthDetailsBean.class);
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(youthDetailsBean.getCode() == 200){
                            Glide.with(context).load(Config.baseUrl + youthDetailsBean.getData().getImgUrl())
                                    .apply(new RequestOptions().transform(new CenterCrop()))
                                    .into(youthDetailsImage);
                            youthDetailsContent.setText(youthDetailsBean.getData().getIntroduce());
                        }
                    }
                });
            }
        });
//        Tool.getTokenData("/prod-api/api/youth-inn/talent-policy/" + getIntent().getStringExtra("id"), Tool.sp(context, "token"), new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                YouthDetailsContentBean bean = new Gson().fromJson(response.body().string(),YouthDetailsContentBean.class);
//                Tool.handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        YouthDetailsContentAdapter adapter = new YouthDetailsContentAdapter(context,bean.getData());
//                        youthDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
//                        youthDetailsRecyclerView.setAdapter(adapter);
//                    }
//                });
//            }
//        });
    }

    private void bindView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        youthDetailsImage = (ImageView) findViewById(R.id.youth_details_image);
        youthDetailsContent = (TextView) findViewById(R.id.youth_details_content);
        youthDetailsRecyclerView = (RecyclerView) findViewById(R.id.youth_details_recyclerView);
    }
}