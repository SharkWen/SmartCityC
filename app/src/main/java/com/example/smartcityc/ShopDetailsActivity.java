package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Adapter.ShopDetailsAdapter;
import com.example.smartcityc.Bean.ShopDetailsLeft;
import com.example.smartcityc.Bean.ShopRightBean;
import com.example.smartcityc.Tool.Config;
import com.example.smartcityc.Tool.Tool;
import com.example.smartcityc.data.DataStore;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShopDetailsActivity extends AppCompatActivity {
    private LinearLayout llBg;
    private TextView tvBack;
    private TextView tvTitle;
    private ImageView shopDetailsImage;
    private TextView shopDetailsName;
    private TextView shopDetailsContent;
    private TextView shopDetailsScore;
    private RatingBar shopDetailsRatingBar;
    private Button shopDetailsDc;
    private Button shopDetailsPj;
    private Button shopDetailsJc;
    private LinearLayout shopDetailsLayout;
    private TextView shopDetailsTotalNum;
    private TextView shopDetailsTotalPrice;
    private Button shopDetailsSure;
    ListView shopDetailsLeft;
    RecyclerView shop_details_right;
    boolean isSelected = false;
    public static String image, nameD, timeD, distance, score, sellerId;
    Context context = this;
    DataStore data = new DataStore();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        bindView();
        initData();
        initEvent();
    }

    private void initEvent() {
        shopDetailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelected) {
                    shopDetailsRatingBar.setRating(0);
                    isSelected = false;
                } else {
                    shopDetailsRatingBar.setRating(RatingBar.ACCESSIBILITY_LIVE_REGION_POLITE);
                    isSelected = true;
                }

            }
        });
        shopDetailsDc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBtn(shopDetailsDc);
            }
        });
        shopDetailsJc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBtn(shopDetailsJc);
            }
        });
        shopDetailsPj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBtn(shopDetailsPj);
            }
        });
        Left();
    }

    private void right(String sellerId, String cateGoryId) {
        Tool.getData("/prod-api/api/takeout/product/list?categoryId=" + cateGoryId + "&sellerId=" + sellerId, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ShopRightBean shopRightBean = new Gson().fromJson(response.body().string(), ShopRightBean.class);
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ShopDetailsAdapter shopDetailsAdapter = new ShopDetailsAdapter(context, shopRightBean.getData(), shopDetailsTotalNum, shopDetailsTotalPrice, data);
                        shop_details_right.setAdapter(shopDetailsAdapter);
                        shop_details_right.setLayoutManager(new LinearLayoutManager(ShopDetailsActivity.this));
                    }
                });
            }
        });
    }

    private void Left() {
        Tool.getData("/prod-api/api/takeout/category/list?sellerId=" + sellerId, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ShopDetailsLeft shopDetailLeft = new Gson().fromJson(response.body().string(), ShopDetailsLeft.class);
                List<String> strings = new ArrayList<>();
                for (ShopDetailsLeft.DataDTO n : shopDetailLeft.getData()) {
                    strings.add(n.getName());
                }
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        right(sellerId, shopDetailLeft.getData().get(0).getId() + "");
                        ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.shop_details_left_item, strings);
                        shopDetailsLeft.setAdapter(arrayAdapter);
                        shopDetailsLeft.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                        shopDetailsLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                shopDetailsTotalNum.setText("0");
                                shopDetailsTotalPrice.setText("0");
                                right(sellerId, shopDetailLeft.getData().get(i).getId() + "");
                            }
                        });
                    }
                });
            }
        });
    }

    private void initBtn(Button bt) {
        shopDetailsDc.setBackgroundResource(R.color.yellow);
        shopDetailsPj.setBackgroundResource(R.color.yellow);
        shopDetailsJc.setBackgroundResource(R.color.yellow);
        shopDetailsDc.setTextSize(12);
        shopDetailsPj.setTextSize(12);
        shopDetailsJc.setTextSize(12);
        shopDetailsDc.setTextColor(Color.WHITE);
        shopDetailsPj.setTextColor(Color.WHITE);
        shopDetailsJc.setTextColor(Color.WHITE);
        bt.setBackgroundResource(R.color.activityBackground);
        bt.setTextSize(18);
        bt.setTextColor(Color.rgb(255, 193, 7));
    }

    private void initData() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvTitle.setText("店家详情");
        Glide.with(this).load(image).apply(new RequestOptions().transform(new CenterCrop())).into(shopDetailsImage);
        shopDetailsName.setText(nameD);
        shopDetailsContent.setText("到店所需时间" + timeD + "分钟,距离" + distance + "米");
        shopDetailsScore.setText(score);
        llBg.setBackgroundResource(R.color.yellow);
    }

    private void bindView() {
        llBg = (LinearLayout) findViewById(R.id.ll_bg);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        shopDetailsImage = (ImageView) findViewById(R.id.shop_details_image);
        shopDetailsName = (TextView) findViewById(R.id.shop_details_name);
        shopDetailsContent = (TextView) findViewById(R.id.shop_details_content);
        shopDetailsScore = (TextView) findViewById(R.id.shop_details_score);
        shopDetailsRatingBar = (RatingBar) findViewById(R.id.shop_details_ratingBar);
        shopDetailsDc = (Button) findViewById(R.id.shop_details_dc);
        shopDetailsPj = (Button) findViewById(R.id.shop_details_pj);
        shopDetailsJc = (Button) findViewById(R.id.shop_details_jc);
        shopDetailsLayout = findViewById(R.id.shop_details_layout);
        shopDetailsLeft = findViewById(R.id.shop_details_left);
        shop_details_right = findViewById(R.id.shop_details_right);
        shopDetailsTotalNum = (TextView) findViewById(R.id.shop_details_totalNum);
        shopDetailsTotalPrice = (TextView) findViewById(R.id.shop_details_totalPrice);
        shopDetailsSure = (Button) findViewById(R.id.shop_details_sure);
    }
}