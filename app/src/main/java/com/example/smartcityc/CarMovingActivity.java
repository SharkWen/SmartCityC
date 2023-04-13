package com.example.smartcityc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcityc.CardFragment.CarMovingFragment;
import com.example.smartcityc.CardFragment.CarMovingHistoryFragment;

public class CarMovingActivity extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private Button carMovingAuto;
    private Button carMovingHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_moving);
        initView();
        initData();
        initEvent();
    }

    private FragmentTransaction fragmentTransaction(Fragment frag) {
        return getSupportFragmentManager().beginTransaction().replace(R.id.car_moving_layout, frag);
    }

    private void initEvent() {
        tvBack.setOnClickListener(v -> {
            MainActivity.back = "service";
            startActivity(new Intent(this, MainActivity.class));
        });
        carMovingAuto.setOnClickListener(v -> {
            carMovingAuto.setBackgroundResource(R.color.activityBackground);
            carMovingAuto.setTextColor(Color.rgb(3, 169, 244));
            carMovingHistory.setBackgroundResource(R.color.blue);
            carMovingHistory.setTextColor(Color.WHITE);
            fragmentTransaction(new CarMovingFragment()).commit();
        });
        carMovingHistory.setOnClickListener(v -> {
            carMovingHistory.setBackgroundResource(R.color.activityBackground);
            carMovingHistory.setTextColor(Color.rgb(3, 169, 244));
            carMovingAuto.setBackgroundResource(R.color.blue);
            carMovingAuto.setTextColor(Color.WHITE);
            fragmentTransaction(new CarMovingHistoryFragment()).commit();
        });
    }

    private void initData() {
        tvTitle.setText("堵车移车");
        fragmentTransaction(new CarMovingFragment()).commit();
    }

    private void initView() {
        carMovingAuto = (Button) findViewById(R.id.car_moving_auto);
        carMovingHistory = (Button) findViewById(R.id.car_moving_history);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
    }
}