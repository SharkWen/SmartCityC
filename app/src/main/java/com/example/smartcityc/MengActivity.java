package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Adapter.BannerHospitalAdapter;
import com.example.smartcityc.Bean.HospitalBannerBean;
import com.example.smartcityc.Bean.HospitalBean;
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

public class MengActivity extends AppCompatActivity {
    private ImageView ivBack;
    private AutoCompleteTextView edSearch;
    private Banner hospitalBanner;
    private ListView hospitalListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meng);
        bindView();
        initData();
    }

    private void initData() {
        ivBack.setImageResource(R.drawable.arrowleft);
        ivBack.setOnClickListener(view -> {
            finish();
        });
        Tool.getData("/prod-api/api/hospital/banner/list", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                HospitalBannerBean hospitalBannerBean = new Gson().fromJson(response.body().string(), HospitalBannerBean.class);
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        BannerHospitalAdapter hospitalAdapter = new BannerHospitalAdapter(hospitalBannerBean.getData(), MengActivity.this);
                        hospitalBanner.setAdapter(hospitalAdapter);
                    }
                });
            }
        });
        setHospitalListView();
    }

    private void  setHospitalListView(){
        List<Map<String,Object>> mapList = new ArrayList<>();
        String[] strings = new String[]{"image","name","ratingBar","content"};
        int[] ints = new int[]{R.id.hospital_image,R.id.hospital_name,R.id.hospital_ratingBar,R.id.hospital_content};
        Tool.getData("/prod-api/api/hospital/hospital/list", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                HospitalBean hospitalBean = new Gson().fromJson(response.body().string(),HospitalBean.class);
                for(HospitalBean.RowsDTO n:hospitalBean.getRows()){
                    Map<String,Object> map = new HashMap<>();
                    map.put("image", Config.baseUrl+n.getImgUrl());
                    map.put("name",n.getHospitalName());
                    map.put("ratingBar",n.getLevel());
                    map.put("content",Tool.html(n.getBrief()));
                    mapList.add(map);
                }
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter simpleAdapter = new SimpleAdapter(MengActivity.this,mapList,R.layout.hospital_item,strings,ints);
                        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                            @Override
                            public boolean setViewValue(View view, Object o, String s) {
                                ImageView imageView = view.findViewById(R.id.hospital_image);
                                RatingBar ratingBar = view.findViewById(R.id.hospital_ratingBar);
                                if(imageView!=null){
                                    Glide.with(MengActivity.this).load(s).apply(new RequestOptions().transform(new CenterCrop())).into(imageView);
                                    return true;
                                }
                                if(ratingBar!=null){
                                    ratingBar.setRating(Integer.parseInt(s));
                                    return true;
                                }
                                return false;
                            }
                        });
                        hospitalListView.setAdapter(simpleAdapter);
                        hospitalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                HospitalDetailActivity.image = Config.baseUrl+ hospitalBean.getRows().get(i).getImgUrl();
                                HospitalDetailActivity.title = hospitalBean.getRows().get(i).getHospitalName();
                                HospitalDetailActivity.contentD =Tool.html(hospitalBean.getRows().get(i).getBrief());
                                MengActivity.this.startActivity(new Intent(MengActivity.this,HospitalDetailActivity.class));
                            }
                        });
                    }
                });
            }
        });
    }
    private void bindView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        edSearch = (AutoCompleteTextView) findViewById(R.id.ed_search);
        hospitalBanner = (Banner) findViewById(R.id.hospital_banner);
        hospitalListView = (ListView) findViewById(R.id.hospital_listView);
    }
}