package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.smartcityc.Bean.HospitalCategoryBean;
import com.example.smartcityc.Fragment.LmPtFragment;
import com.example.smartcityc.Fragment.LmZjFragment;
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

public class HospitalKHActivity extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private ListView hospitalKsListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_khactivity);
        bindView();
        initData();
        initEvent();
    }

    private void initEvent() {
       tvBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });
    }

    private void initData() {
        tvTitle.setText("门诊科室");
        Tool.getData("/prod-api/api/hospital/category/list", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                HospitalCategoryBean hospitalCategoryBean = new Gson().fromJson(response.body().string(),HospitalCategoryBean.class);
                List<Map<String,Object>> list = new ArrayList<>();
                for(HospitalCategoryBean.RowsDTO n : hospitalCategoryBean.getRows()){
                    Map<String,Object> map = new HashMap<>();
                    map.put("name",n.getCategoryName());
                    list.add(map);
                }
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter simpleAdapter = new SimpleAdapter(HospitalKHActivity.this,list,R.layout.hospital_category_item,new String[]{"name"},new int[]{R.id.hospital_item_name});
                        hospitalKsListView.setAdapter(simpleAdapter);
                        hospitalKsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                LmPtFragment.categoryId = hospitalCategoryBean.getRows().get(i).getId()+"";
                                LmPtFragment.money = hospitalCategoryBean.getRows().get(i).getMoney()+"";
                                LmPtFragment.lmType = hospitalCategoryBean.getRows().get(i).getType();
                                LmPtFragment.patientName = hospitalCategoryBean.getRows().get(i).getCategoryName();
                                startActivity(new Intent(HospitalKHActivity.this,HospitalLMActivity.class));
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
        hospitalKsListView = (ListView) findViewById(R.id.hospital_ks_listView);
    }
}