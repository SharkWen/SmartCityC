package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Adapter.BannerHorseAdapter;
import com.example.smartcityc.Bean.HorseBean;
import com.example.smartcityc.Tool.Config;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;
import com.youth.banner.Banner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FindHourseActivity extends AppCompatActivity {
    private ImageView ivBack;
    private AutoCompleteTextView edSearch;
    private Button horseEs;
    private Button horseZf;
    private Button horseLf;
    private Button horseZj;
    private ListView horseList;
    Banner banner;
    String key = "";
    String[] strings = new String[]{"horsePic", "horseTitle", "horseContent", "horsePrice", "horseMj"};
    int[] ints = new int[]{R.id.horse_pic, R.id.horse_title, R.id.horse_content, R.id.horse_price, R.id.horse_mj};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_hourse);
        bindView();
        initData();
        initEvent();
    }

    private void initEvent() {
        ivBack.setOnClickListener(view -> {
            MainActivity.back = "service";
            finish();
        });
        edSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == 66 && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    horseEs.setBackgroundColor(Color.WHITE);
                    horseZf.setBackgroundColor(Color.WHITE);
                    horseLf.setBackgroundColor(Color.WHITE);
                    horseZj.setBackgroundColor(Color.WHITE);
                    key = edSearch.getText().toString();
                    getListView();
                    return true;
                }
                return false;
            }
        });
        horseEs.setOnClickListener(view -> {
            initColor(horseEs);
            getListType(horseEs.getText().toString());
        });
        horseZf.setOnClickListener(view -> {
            initColor(horseZf);
            getListType(horseZf.getText().toString());
        });
        horseLf.setOnClickListener(view -> {
            initColor(horseLf);
            getListType(horseLf.getText().toString());
        });
        horseZj.setOnClickListener(view -> {
            initColor(horseZj);
            getListType(horseZj.getText().toString());
        });
    }

    private void initColor(Button tv) {
        horseEs.setBackgroundColor(Color.WHITE);
        horseZf.setBackgroundColor(Color.WHITE);
        horseLf.setBackgroundColor(Color.WHITE);
        horseZj.setBackgroundColor(Color.WHITE);
        horseEs.setTextColor(Color.rgb(111, 109, 109));
        horseZf.setTextColor(Color.rgb(111, 109, 109));
        horseLf.setTextColor(Color.rgb(111, 109, 109));
        horseZj.setTextColor(Color.rgb(111, 109, 109));
        tv.setBackgroundResource(R.color.activityBackground);
        tv.setTextColor(Color.rgb(3, 169, 244));
    }

    private void initData() {
        getBanner();
        horseEs.setBackgroundColor(Color.WHITE);
        horseZf.setBackgroundColor(Color.WHITE);
        horseLf.setBackgroundColor(Color.WHITE);
        horseZj.setBackgroundColor(Color.WHITE);
        horseEs.setTextColor(Color.rgb(111, 109, 109));
        horseZf.setTextColor(Color.rgb(111, 109, 109));
        horseLf.setTextColor(Color.rgb(111, 109, 109));
        horseZj.setTextColor(Color.rgb(111, 109, 109));
        ivBack.setImageResource(R.drawable.arrowleft);
        getListView();
    }

    private void getBanner(){
        List<Integer> integers = new ArrayList<>();
        integers.add(R.drawable.hourse);
        integers.add(R.drawable.hourse1);
        integers.add(R.drawable.hourse2);
        BannerHorseAdapter bannerHorseAdapter = new BannerHorseAdapter(integers);
        banner.setAdapter(bannerHorseAdapter);
    }
    private void getListType(String type) {
        List<Map<String, Object>> list = new ArrayList<>();
        Tool.getData("/prod-api/api/house/housing/list?houseType="+type, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                HorseBean horseBean = new Gson().fromJson(response.body().string(), HorseBean.class);
                for (HorseBean.RowsDTO n : horseBean.getRows()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("horsePic", Config.baseUrl + n.getPic());
                    map.put("horseTitle", n.getAddress());
                    map.put("horseContent", n.getDescription());
                    map.put("horsePrice", n.getPrice());
                    map.put("horseMj", n.getAreaSize() + "平方米");
                    list.add(map);
                }
                Tool.handler.post(() -> {
                    SimpleAdapter simpleAdapter = new SimpleAdapter(FindHourseActivity.this, list, R.layout.hourse_item, strings, ints);
                    simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                        @Override
                        public boolean setViewValue(View view, Object o, String s) {
                            ImageView imageView = view.findViewById(R.id.horse_pic);
                            if (imageView != null) {
                                Glide.with(FindHourseActivity.this).load(s)
                                        .apply(new RequestOptions().transform(new CenterCrop())).into(imageView);
                                return true;
                            }
                            return false;
                        }
                    });
                    horseList.setAdapter(simpleAdapter);
                    horseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            HorseDetailActivity.name = horseBean.getRows().get(i).getSourceName();
                            HorseDetailActivity.pic = Config.baseUrl + horseBean.getRows().get(i).getPic();
                            HorseDetailActivity.mj = "面积:"+horseBean.getRows().get(i).getAreaSize() + "平方米";
                            HorseDetailActivity.type = "租房类型:" + horseBean.getRows().get(i).getHouseType();
                            HorseDetailActivity.contented = horseBean.getRows().get(i).getDescription();
                            HorseDetailActivity.price = "单价:"+horseBean.getRows().get(i).getPrice();
                            HorseDetailActivity.tel = horseBean.getRows().get(i).getTel();
                            FindHourseActivity.this.startActivity(new Intent(FindHourseActivity.this,HorseDetailActivity.class));
                        }
                    });
                });
            }
        });
    }

    private void getListView() {
        List<Map<String, Object>> list = new ArrayList<>();
        Tool.getData("/prod-api/api/house/housing/list", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                HorseBean horseBean = new Gson().fromJson(response.body().string(), HorseBean.class);
                for (HorseBean.RowsDTO n : horseBean.getRows()) {
                    if(n.getSourceName().contains(key)){
                        Map<String, Object> map = new HashMap<>();
                        map.put("horsePic", Config.baseUrl + n.getPic());
                        map.put("horseTitle", n.getAddress());
                        map.put("horseContent", n.getDescription());
                        map.put("horsePrice", n.getPrice());
                        map.put("horseMj", n.getAreaSize() + "平方米");
                        list.add(map);
                    }
                }
                Tool.handler.post(() -> {
                    SimpleAdapter simpleAdapter = new SimpleAdapter(FindHourseActivity.this, list, R.layout.hourse_item, strings, ints);
                    simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                        @Override
                        public boolean setViewValue(View view, Object o, String s) {
                            ImageView imageView = view.findViewById(R.id.horse_pic);
                            if (imageView != null) {
                                Glide.with(FindHourseActivity.this).load(s)
                                        .apply(new RequestOptions().transform(new CenterCrop())).into(imageView);
                                return true;
                            }
                            return false;
                        }
                    });
                    horseList.setAdapter(simpleAdapter);
                    horseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            HorseDetailActivity.name = horseBean.getRows().get(i).getSourceName();
                            HorseDetailActivity.pic = Config.baseUrl + horseBean.getRows().get(i).getPic();
                            HorseDetailActivity.mj = "面积:"+horseBean.getRows().get(i).getAreaSize() + "平方米";
                            HorseDetailActivity.type = "租房类型:" + horseBean.getRows().get(i).getHouseType();
                            HorseDetailActivity.contented = horseBean.getRows().get(i).getDescription();
                            HorseDetailActivity.price = "单价:"+horseBean.getRows().get(i).getPrice();
                            HorseDetailActivity.tel = horseBean.getRows().get(i).getTel();
                            FindHourseActivity.this.startActivity(new Intent(FindHourseActivity.this,HorseDetailActivity.class));
                        }
                    });
                });
            }
        });
    }

    private void bindView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        edSearch = (AutoCompleteTextView) findViewById(R.id.ed_search);
        horseEs = (Button) findViewById(R.id.horse_es);
        horseZf = (Button) findViewById(R.id.horse_zf);
        horseLf = (Button) findViewById(R.id.horse_lf);
        horseZj = (Button) findViewById(R.id.horse_zj);
        horseList = (ListView) findViewById(R.id.horse_list);
        banner = findViewById(R.id.horse_banner);
    }
}