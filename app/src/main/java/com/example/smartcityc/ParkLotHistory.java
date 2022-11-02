package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcityc.Bean.ParkLotHistoryBean;
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

public class ParkLotHistory extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private EditText parkLotEntryTime;
    private EditText parkLotOutTime;
    private ImageView parkLotQuery;
    private ListView parkLotHistoryListView;
    private TextView parkLotHistoryMore;
    String[] strings = new String[]{"parkLotHistoryParkName", "parkLotHistoryPlateNumber", "parkLotHistoryEntryTime", "parkLotHistoryOutTime", "parkLotHistoryMonetary"};
    int[] ints = new int[]{R.id.park_lot_history_parkName, R.id.park_lot_history_plateNumber, R.id.park_lot_history_entryTime, R.id.park_lot_history_outTime, R.id.park_lot_history_monetary};
    SimpleAdapter simpleAdapter;
    Map<String, Object> map;
    ParkLotHistoryBean parkLotHistoryBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_lot_history);
        bindView();
        initData();
        initEvent();
    }

    private void initEvent() {
        tvBack.setOnClickListener(v -> {
            finish();
        });
        parkLotHistoryMore.setOnClickListener(v -> {
            parkLotHistoryMore.setVisibility(View.GONE);
            initParkHistoryData(false);
        });
        parkLotQuery.setOnClickListener(v -> {
            String entryTime = parkLotEntryTime.getText().toString();
            String outTime = parkLotOutTime.getText().toString();
            if (entryTime.equals("")) {
                Toast.makeText(this, "请输入开始时间段", Toast.LENGTH_SHORT).show();
                return;
            }
            if (outTime.equals("")) {
                Toast.makeText(this, "请输入结束时间段", Toast.LENGTH_SHORT).show();
                return;
            }
            setParkHistoryData(entryTime, outTime);
        });
    }

    private void initData() {
        tvTitle.setText("停车记录");
        initParkHistoryData(true);
    }

    private void setParkHistoryData(String entryTime, String outTime) {
        Tool.getData("/prod-api/api/park/lot/record/list?entryTime=" + entryTime + "&outTime=" + outTime, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                parkLotHistoryBean = new Gson().fromJson(response.body().string(), ParkLotHistoryBean.class);
                List<Map<String, Object>> mapList = new ArrayList<>();
                for (int i = 0; i < parkLotHistoryBean.getRows().size(); i++) {
                    map = new HashMap<>();
                    map.put("parkLotHistoryParkName", parkLotHistoryBean.getRows().get(i).getParkName());
                    map.put("parkLotHistoryPlateNumber", "车牌号:      " + parkLotHistoryBean.getRows().get(i).getPlateNumber());
                    map.put("parkLotHistoryEntryTime", "入场时间:  " + parkLotHistoryBean.getRows().get(i).getEntryTime());
                    map.put("parkLotHistoryOutTime", "出场时间:  " + parkLotHistoryBean.getRows().get(i).getOutTime());
                    map.put("parkLotHistoryMonetary", "收费金额:  " + parkLotHistoryBean.getRows().get(i).getMonetary() + "元");
                    mapList.add(map);
                }
                Tool.handler.post(() -> {
                    if (parkLotHistoryBean.getRows().size() == 0) {
                        Tool.setDialog(ParkLotHistory.this, "输入格式有误或未能查询到相应记录").show();
                        return;
                    }
                    simpleAdapter = new SimpleAdapter(ParkLotHistory.this, mapList, R.layout.p_history_list,
                            strings, ints);
                    parkLotHistoryListView.setAdapter(simpleAdapter);
                });
            }
        });
    }

    private void initParkHistoryData(boolean isMore) {
        Tool.getData("/prod-api/api/park/lot/record/list", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                parkLotHistoryBean = new Gson().fromJson(response.body().string(), ParkLotHistoryBean.class);
                List<Map<String, Object>> mapList = new ArrayList<>();
                for (int i = 0; i < parkLotHistoryBean.getRows().size(); i++) {
                    if (i >= 5 && isMore) continue;
                    map = new HashMap<>();
                    map.put("parkLotHistoryParkName", parkLotHistoryBean.getRows().get(i).getParkName());
                    map.put("parkLotHistoryPlateNumber", "车牌号:      " + parkLotHistoryBean.getRows().get(i).getPlateNumber());
                    map.put("parkLotHistoryEntryTime", "入场时间:  " + parkLotHistoryBean.getRows().get(i).getEntryTime());
                    map.put("parkLotHistoryOutTime", "出场时间:  " + parkLotHistoryBean.getRows().get(i).getOutTime());
                    map.put("parkLotHistoryMonetary", "收费金额:  " + parkLotHistoryBean.getRows().get(i).getMonetary() + "元");
                    mapList.add(map);
                }
                Tool.handler.post(() -> {
                    simpleAdapter = new SimpleAdapter(ParkLotHistory.this, mapList, R.layout.p_history_list,
                            strings, ints);
                    parkLotHistoryListView.setAdapter(simpleAdapter);
                });
            }
        });
    }

    private void bindView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        parkLotEntryTime = (EditText) findViewById(R.id.park_lot_entryTime);
        parkLotOutTime = (EditText) findViewById(R.id.park_lot_outTime);
        parkLotQuery = (ImageView) findViewById(R.id.park_lot_query);
        parkLotHistoryListView = (ListView) findViewById(R.id.park_lot_history_listView);
        parkLotHistoryMore = (TextView) findViewById(R.id.park_lot_history_more);

    }
}