package com.example.smartcityc;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcityc.Bean.NewsBean;
import com.example.smartcityc.Tool.Tool;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DataAnalysisActivity extends AppCompatActivity {

    List<BarEntry> barEntries = new ArrayList<>();
    List<String> strings = new ArrayList<>();
    private BarChart chart;
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
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int i = 0;
                        for (NewsBean.RowsDTO rowsDTO : newsBean.getRows()) {
                            strings.add(rowsDTO.getTitle().substring(0,4)+"...");
                            float y = rowsDTO.getLikeNum();
                            barEntries.add(new BarEntry(i,y));
                            i++;
                        }
                        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(strings));
                        chart.getXAxis().setGranularityEnabled(true);
                        BarDataSet dataSet = new BarDataSet(barEntries,"新闻分析");
                        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                        List<IBarDataSet> iBarDataSets = new ArrayList<>();
                        iBarDataSets.add(dataSet);
                        chart.animateXY(2000,2000);
                        chart.setData(new BarData(iBarDataSets));
                        chart.invalidate();
                    }
                });
            }
        });
    }
}