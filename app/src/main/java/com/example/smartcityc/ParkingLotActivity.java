package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.smartcityc.Bean.ParkingLotBean;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ParkingLotActivity extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private TextView tvList;
    private ListView parkingListView;
    private TextView parkLotMore;
    String[] strings = new String[]{"parkLotParkName", "parkLotVacancy", "parkLotAdress", "parkLotPrice", "parkLotDistance"};
    int[] ints = new int[]{R.id.park_lot_parkName, R.id.park_lot_vacancy, R.id.park_lot_adress, R.id.park_lot_price, R.id.park_lot_distance};
    Map<String, Object> map;
    SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_lot);
        bindView();
        initData();
        initEvent();
    }

    private void initEvent() {
        parkLotMore.setOnClickListener(v -> {
            parkLotMore.setVisibility(View.GONE);
            getParkLotData(false);
        });
        tvList.setOnClickListener(v -> {
            startActivity(new Intent(this, ParkLotHistory.class));
        });
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvTitle.setText("停车场");
    }

    private void initData() {
        tvList.setBackgroundResource(R.drawable.p_list);
        getParkLotData(true);
    }

    private void getParkLotData(boolean isMore) {
        Tool.getData("/prod-api/api/park/lot/list", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ParkingLotBean parkingLotBean = new Gson().fromJson(response.body().string(), ParkingLotBean.class);
                Collections.sort(parkingLotBean.getRows(), new Comparator<ParkingLotBean.RowsDTO>() {
                    @Override
                    public int compare(ParkingLotBean.RowsDTO rowsDTO, ParkingLotBean.RowsDTO t1) {
                        return Integer.parseInt(rowsDTO.getDistance()) - Integer.parseInt(t1.getDistance());
                    }
                });
                List<Map<String, Object>> mapList = new ArrayList<>();
                for (int i = 0; i < parkingLotBean.getRows().size(); i++) {
                    if (i >= 5 && isMore) continue;
                    map = new HashMap<>();
                    map.put("parkLotParkName", parkingLotBean.getRows().get(i).getParkName());
                    map.put("parkLotVacancy", "空位数量: " + parkingLotBean.getRows().get(i).getVacancy());
                    map.put("parkLotAdress", "地址:        " + parkingLotBean.getRows().get(i).getAddress());
                    map.put("parkLotPrice", "收费价格: " + parkingLotBean.getRows().get(i).getRates() + "元/小时");
                    map.put("parkLotDistance", parkingLotBean.getRows().get(i).getDistance() + "km");
                    mapList.add(map);
                }
                Tool.handler.post(() -> {
                    simpleAdapter = new SimpleAdapter(ParkingLotActivity.this, mapList, R.layout.p_list, strings, ints);
                    parkingListView.setAdapter(simpleAdapter);
                    parkingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ParkLotDetail.parkName = parkingLotBean.getRows().get(position).getParkName();
                            ParkLotDetail.adress = "地址:            " + parkingLotBean.getRows().get(position).getAddress();
                            ParkLotDetail.distance = "距离:            " + parkingLotBean.getRows().get(position).getDistance() + "km";
                            if (parkingLotBean.getRows().get(position).getOpen().equals("Y")) {
                                ParkLotDetail.open = "对外开放:    是";
                            } else {
                                ParkLotDetail.open = "对外开放:  否";
                            }
                            ParkLotDetail.allPark = "总车位:         " + parkingLotBean.getRows().get(position).getAllPark();
                            ParkLotDetail.vacancy = "剩余车位:     " + parkingLotBean.getRows().get(position).getVacancy();
                            ParkLotDetail.rates = "费用:    " + parkingLotBean.getRows().get(position).getRates() + "元/小时      " +
                                    parkingLotBean.getRows().get(position).getPriceCaps() + "元/天";
                            ParkingLotActivity.this.startActivity(new Intent(ParkingLotActivity.this, ParkLotDetail.class));
                        }
                    });
                });
            }
        });
    }

    private void bindView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvList = (TextView) findViewById(R.id.tv_list);
        parkingListView = (ListView) findViewById(R.id.parking_list_view);
        parkLotMore = (TextView) findViewById(R.id.park_lot_more);
    }
}