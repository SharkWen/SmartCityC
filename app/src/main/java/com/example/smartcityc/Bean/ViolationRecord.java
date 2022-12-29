package com.example.smartcityc.Bean;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartcityc.R;
import com.google.gson.Gson;

import java.lang.reflect.GenericSignatureFormatError;
import java.util.ArrayList;
import java.util.List;

public class ViolationRecord extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private RecyclerView violationRecordList;
    ViolationRecordBean violationRecordBean;
    List<ViolationRecordBean> list  = new ArrayList<>();
    String data = "{\n" +
            "    \"id\": 52,\n" +
            "    \"licencePlate\": \"辽 B123456\",\n" +
            "    \"disposeState\": \"未缴款\",\n" +
            "    \"badTime\": \"2021-04-20 11:51:17\",\n" +
            "    \"money\": \"200\",\n" +
            "    \"deductMarks\": \"6\",\n" +
            "    \"illegalSites\": \"大连市万达广场\",\n" +
            "    \"noticeNo\": \"2021042110040387379\",\n" +
            "    \"engineNumber\": \"12345611\",\n" +
            "    \"trafficOffence\": \"闯红灯\",\n" +
            "    \"catType\": \"大型汽车\",\n" +
            "    \"performOffice\": \"交警队\",\n" +
            "    \"performDate\": \"2021-04-20\",\n" +
            "    \"imgUrl\": \"https://img1.baidu.com/it/u=2820222784,2850106904&fm=253\"\n" +
            "}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violation_record);
        bindView();
        initData();
    }

    private void bindView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        violationRecordList = (RecyclerView) findViewById(R.id.violation_record_list);
    }

    private void initData() {
        violationRecordBean = new Gson().fromJson(data,ViolationRecordBean.class);
        for(int i = 0; i<=6;i++){
            list.add(violationRecordBean);
        }

    }
}