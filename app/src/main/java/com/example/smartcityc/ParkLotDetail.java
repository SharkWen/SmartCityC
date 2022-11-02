package com.example.smartcityc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ParkLotDetail extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private TextView parkLotParkNameDetails;
    private TextView parkLotAdressDetails;
    private TextView parkLotDistanceDetails;
    private TextView parkLotOpen;
    private TextView parkLotAllPark;
    private TextView parkLotVacancyDetails;
    private TextView parkLotRatesDetails;
    public static String  parkName,adress,distance,open,allPark,vacancy,rates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_lot_detail);
        bindView();
        initData();
    }
    private void initData() {
        tvTitle.setText("停车场详情");
        parkLotParkNameDetails.setText(parkName);
        parkLotAdressDetails.setText(adress);
        parkLotDistanceDetails.setText(distance);
        parkLotOpen.setText(open);
        parkLotAllPark.setText(allPark);
        parkLotVacancyDetails.setText(vacancy);
        parkLotRatesDetails.setText(rates);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void bindView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        parkLotParkNameDetails = (TextView) findViewById(R.id.park_lot_parkName_details);
        parkLotAdressDetails = (TextView) findViewById(R.id.park_lot_adress_details);
        parkLotDistanceDetails = (TextView) findViewById(R.id.park_lot_distance_details);
        parkLotOpen = (TextView) findViewById(R.id.park_lot_open);
        parkLotAllPark = (TextView) findViewById(R.id.park_lot_allPark);
        parkLotVacancyDetails = (TextView) findViewById(R.id.park_lot_vacancy_details);
        parkLotRatesDetails = (TextView) findViewById(R.id.park_lot_rates_details);
    }
}