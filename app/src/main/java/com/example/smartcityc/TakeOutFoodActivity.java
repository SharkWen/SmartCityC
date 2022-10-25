package com.example.smartcityc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartcityc.Fragment.OutFoodIndexFragment;

public class TakeOutFoodActivity extends AppCompatActivity {
    private LinearLayout llBg;
    private TextView tvBack;
    private TextView tvTitle;
    private LinearLayout outFoodLayout;
    private Button outFoodIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_out_food);
        bindView();
        initData();
    }
    private FragmentTransaction fragmentTransaction(Fragment fragment){
        return getSupportFragmentManager().beginTransaction().replace(R.id.out_food_layout,fragment);
    }
    private void initData() {
        llBg.setBackgroundResource(R.color.yellow);
        MainActivity.back = "service";
        tvBack.setOnClickListener(view -> {
            startActivity(new Intent(this,MainActivity.class));
        });
        tvTitle.setText("外卖点餐");
        fragmentTransaction(new OutFoodIndexFragment()).commit();
    }

    private void bindView() {
        llBg = (LinearLayout) findViewById(R.id.ll_bg);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        outFoodLayout = (LinearLayout) findViewById(R.id.out_food_layout);
        outFoodIndex = (Button) findViewById(R.id.out_food_index);
    }
}