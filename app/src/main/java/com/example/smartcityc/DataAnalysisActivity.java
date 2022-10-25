package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartcityc.Bean.NewsBean;
import com.example.smartcityc.Tool.Tool;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DataAnalysisActivity extends AppCompatActivity {
    ArrayList<BarEntry>  values = new ArrayList<>();
    BarDataSet set1;
    private BarChart chart;
    XAxis xAxis;
    private TextView tvBack;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_analysis);
        chart = findViewById(R.id.chart1);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("新闻数据分析");
        xAxis = chart.getXAxis();
        getData();
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public void getData() {

        Tool.getData("/prod-api/press/press/list", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                NewsBean newsBean = new Gson().fromJson(response.body().string(), NewsBean.class);
                String[] strings = new String[5];
                for (int i = 0; i < 5; i++) {
                    strings[i] = newsBean.getRows().get(i).getTitle();
                    float y = newsBean.getRows().get(i).getLikeNum();
                    values.add(new BarEntry(i, y));
                }
                xAxis.setValueFormatter(new IndexAxisValueFormatter(strings));
                Tool.handler.post(() -> {
                    set1 = new BarDataSet(values, "新闻分析");
                    set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
                    ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                    dataSets.add(set1);
                    chart.setData(new BarData(dataSets));
                    //绘制图表
                    chart.invalidate();
                    chart.getData().setValueTextSize(15);
                    chart.setPinchZoom(true);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
                    chart.animateXY(2000, 3000);
                });
            }
        });
    }
}