package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
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
import com.example.smartcityc.Adapter.ShopPjAdapter;
import com.example.smartcityc.Bean.ShopDetailBean;
import com.example.smartcityc.Bean.ShopDetailsLeft;
import com.example.smartcityc.Bean.ShopPjBean;
import com.example.smartcityc.Bean.ShopRightBean;
import com.example.smartcityc.Tool.Config;
import com.example.smartcityc.Tool.Tool;
import com.example.smartcityc.data.DataStore;
import com.example.smartcityc.data.ShopStore;
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
    private LinearLayout shopDetailsDcBtn;
    private LinearLayout shopDetailsPjBtn;
    private LinearLayout shopDetailsJcBtn;
    private RecyclerView shopDetailsRv;
    private TextView shopDetailsIntroduction;



    ListView shopDetailsLeft;
    RecyclerView shop_details_right;
    boolean isSelected = false;
    public static String image, nameD, timeD, distance, score, sellerId, mouthSells = "";
    Context context = this;
    DataStore data;
    ShopStore shopStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        data = new DataStore();
        bindView();
        initData();
        initEvent();
    }

    private void initEvent() {

        shopDetailsSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OutFoodSureOrder.class);
                intent.putExtra("data", new Gson().toJson(data));
                intent.putExtra("amount", shopDetailsTotalPrice.getText().toString());
                intent.putExtra("sellerId", sellerId);
                startActivity(intent);
            }
        });

        if(!Tool.shp(context).contains("collection")){
            shopStore = new ShopStore();
        }else {
            shopStore = new Gson().fromJson(Tool.sp(context,"collection"),ShopStore.class);
        }
        if (shopStore.getSellerIds().contains(sellerId)) {
            isSelected = true;
            shopDetailsRatingBar.setRating(RatingBar.ACCESSIBILITY_LIVE_REGION_POLITE);
        }
        ShopStore.ShopDetails shopDetails = new ShopStore.ShopDetails();
        shopDetailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelected) {
                    shopDetailsRatingBar.setRating(0);
                    isSelected = false;
                    shopStore.getMap().remove(Integer.parseInt(sellerId));
                    shopStore.getSellerIds().remove(sellerId);
                    System.out.println(new Gson().toJson(shopStore));
                    Tool.shp(context).edit().putString("collection",new Gson().toJson(shopStore)).commit();
                } else {
                    shopDetailsRatingBar.setRating(RatingBar.ACCESSIBILITY_LIVE_REGION_POLITE);
                    shopStore.getSellerIds().add(sellerId);
                    shopDetails.setName(nameD);
                    shopDetails.setScore(score);
                    shopDetails.setUrl(image);
                    shopDetails.setMouthSells(mouthSells);
                    shopStore.getMap().put(Integer.parseInt(sellerId), shopDetails);
                    System.out.println(new Gson().toJson(shopStore));
                    Tool.shp(context).edit().putString("collection",new Gson().toJson(shopStore)).commit();
                    isSelected = true;
                }

            }
        });
        shopDetailsDc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBtn(shopDetailsDc);
                initLayoutVisibility(shopDetailsDcBtn);
            }
        });
        shopDetailsJc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBtn(shopDetailsJc);
                initLayoutVisibility(shopDetailsJcBtn);
            }
        });
        shopDetailsPj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBtn(shopDetailsPj);
                initLayoutVisibility(shopDetailsPjBtn);
            }
        });
        Left();
    }
    private void initLayoutVisibility(LinearLayout ll){
        shopDetailsDcBtn.setVisibility(View.GONE);
        shopDetailsJcBtn.setVisibility(View.GONE);
        shopDetailsPjBtn.setVisibility(View.GONE);
        ll.setVisibility(View.VISIBLE);
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
                if(!isSelected){
                    Intent intent = new Intent(context,TakeOutFoodActivity.class);
                    intent.putExtra("back","follow");
                    startActivity(intent);
                    return;
                }
                finish();
            }
        });
        tvTitle.setText("店家详情");
        Glide.with(this).load(image).apply(new RequestOptions().transform(new CenterCrop())).into(shopDetailsImage);
        shopDetailsName.setText(nameD);
        shopDetailsContent.setText("到店所需时间" + timeD + "分钟,距离" + distance + "米");
        shopDetailsScore.setText(score);
        llBg.setBackgroundResource(R.color.yellow);
        Tool.getData("/prod-api/api/takeout/seller/" + sellerId, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ShopDetailBean shopDetailBean = new Gson().fromJson(response.body().string(),ShopDetailBean.class);
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        shopDetailsIntroduction.setText(shopDetailBean.getData().getIntroduction());
                        Glide.with(context).load(Config.baseUrl + shopDetailBean.getData().getImgUrl()).apply(new RequestOptions().transform(new CenterCrop())).into(shopDetailsImage);
                        shopDetailsContent.setText("到店所需时间" + shopDetailBean.getData().getAvgCost() + "分钟,距离" + shopDetailBean.getData().getDistance() + "米");
                        shopDetailsScore.setText(shopDetailBean.getData().getScore()+"");
                        shopDetailsName.setText(shopDetailBean.getData().getName());
                    }
                });
            }
        });
        Tool.getData("/prod-api/api/takeout/comment/list?sellerId=" + sellerId, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ShopPjBean shopPjBean = new Gson().fromJson(response.body().string(),ShopPjBean.class);
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ShopPjAdapter shopPjAdapter = new ShopPjAdapter(context,shopPjBean.getRows());
                        shopDetailsRv.setAdapter(shopPjAdapter);
                        shopDetailsRv.setLayoutManager(new LinearLayoutManager(context));
                    }
                });
            }
        });
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
        shopDetailsDcBtn = (LinearLayout) findViewById(R.id.shop_details_dcBtn);
        shopDetailsPjBtn = (LinearLayout) findViewById(R.id.shop_details_pjBtn);
        shopDetailsJcBtn = (LinearLayout) findViewById(R.id.shop_details_jcBtn);
        shopDetailsRv = (RecyclerView) findViewById(R.id.shop_details_rv);
        shopDetailsIntroduction = (TextView) findViewById(R.id.shop_details_introduction);

    }
}