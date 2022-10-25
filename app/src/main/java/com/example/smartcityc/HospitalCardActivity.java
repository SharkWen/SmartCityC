package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.smartcityc.Bean.HospitalBean;
import com.example.smartcityc.Bean.HospitalCardBean;
import com.example.smartcityc.Bean.HospitalListBean;
import com.example.smartcityc.Bean.MsgBean;
import com.example.smartcityc.Fragment.LmPtFragment;
import com.example.smartcityc.Fragment.MineFragment;
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

public class HospitalCardActivity extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private ListView hospitalCardListView;
    private Button hospitalCardSure;
    HospitalCardBean hospitalCardBean;

//    hospitalItemName = (TextView) findViewById(R.id.hospital_item_name);
//    hospitalItemSex = (TextView) findViewById(R.id.hospital_item_sex);
//    hospitalItemTel = (TextView) findViewById(R.id.hospital_item_tel);

    String[] strings = new String[]{"name", "sex", "tel", "image"};
    int[] ints = new int[]{R.id.hospital_item_name, R.id.hospital_item_sex, R.id.hospital_item_tel, R.id.hospital_item_arrow};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_card);
        bindView();
        initData();
        initEvent();
    }

    private void initEvent() {
        hospitalCardSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HospitalCardActivity.this, HospitalAddActivity.class));
            }
        });
    }

    private void initData() {
        if (!Tool.shp(this).contains("token")) {
            Tool.setDialog(this, "请注册登录").show();
            return;
        }
        hospitalCardBean = new HospitalCardBean();
        tvTitle.setText("就诊卡");
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Tool.getTokenData("/prod-api/api/hospital/patient/list", Tool.sp(this, "token"), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                HospitalListBean hospitalListBean = new Gson().fromJson(response.body().string(), HospitalListBean.class);
                if (hospitalListBean.getTotal() == 0) {
                    initUserInfoData();
                } else {
                    initListView();
                }
            }
        });

    }

    private void initListView() {
        Tool.getTokenData("/prod-api/api/hospital/patient/list", Tool.sp(this, "token"), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                HospitalListBean hospitalListBean = new Gson().fromJson(response.body().string(), HospitalListBean.class);
                List<Map<String, Object>> list = new ArrayList<>();
                for (HospitalListBean.RowsDTO n : hospitalListBean.getRows()) {
                    if (n.getName() != null) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", "姓名:" + n.getName());
                        if (n.getSex().equals("0")) {
                            map.put("sex", "性别:男");
                        } else {
                            map.put("sex", "性别:女");
                        }
                        map.put("tel", "手机号:" + n.getTel());
                        map.put("image", n.getName());
                        list.add(map);
                    }
                }

                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SimpleAdapter simpleAdapter = new SimpleAdapter(HospitalCardActivity.this, list, R.layout.hospital_card_item, strings, ints);
                        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {

                            @Override
                            public boolean setViewValue(View view, Object o, String s) {
                                ImageView imageView = view.findViewById(R.id.hospital_item_arrow);
                                if (imageView != null) {
                                    imageView.setOnClickListener(view1 -> {
//                                        LmPtFragment.patientName = s;
                                        HospitalCardActivity.this.startActivity(new Intent(HospitalCardActivity.this, HospitalKHActivity.class));
                                    });
                                    return true;
                                }
                                return false;
                            }
                        });
                        hospitalCardListView.setAdapter(simpleAdapter);
                        hospitalCardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                HospitalCardActivity.this.startActivity(new Intent(HospitalCardActivity.this, HospitalAddActivity.class));
                            }
                        });
                    }
                });
            }
        });
    }

    private void initUserInfoData() {
        hospitalCardBean.setName(MineFragment.nickName);
        hospitalCardBean.setSex(MineFragment.sex);
        hospitalCardBean.setTel(MineFragment.tel);
        String data = new Gson().toJson(hospitalCardBean);
        Tool.postTokenData("/prod-api/api/hospital/patient", Tool.sp(this, "token"), data, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                MsgBean msgBean = new Gson().fromJson(response.body().string(), MsgBean.class);
                if (msgBean.getCode() == 200) {
                    initListView();
                }
            }
        });
    }

    private void bindView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        hospitalCardListView = (ListView) findViewById(R.id.hospital_card_listView);
        hospitalCardSure = (Button) findViewById(R.id.hospital_card_sure);
    }
}