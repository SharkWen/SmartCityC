package com.example.smartcityc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartcityc.Fragment.OutFoodFollowFragment;
import com.example.smartcityc.Fragment.OutFoodIndexFragment;
import com.example.smartcityc.Fragment.OutFoodMineFragment;
import com.example.smartcityc.Fragment.OutFoodOrderFragment;

import java.util.ArrayList;
import java.util.List;

public class TakeOutFoodActivity extends AppCompatActivity {
    private LinearLayout llBg;
    private TextView tvBack;
    private TextView tvTitle;
    private Button outFoodIndex;
    private Button outFoodFollow;
    private Button outFoodOrder;
    private Button outFoodMine;
    List<Button> buttons = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_out_food);
        bindView();
        initData();
    }

    private FragmentTransaction fragmentTransaction(Fragment fragment) {
        return getSupportFragmentManager().beginTransaction().replace(R.id.out_food_layout, fragment);
    }

    private void initData() {
        llBg.setBackgroundResource(R.color.yellow);
        MainActivity.back = "service";
        tvBack.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });
        tvTitle.setText("外卖点餐");
        fragmentTransaction(new OutFoodIndexFragment()).commit();
        buttons.add(outFoodIndex);
        outFoodMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initButton(outFoodMine);
                fragmentTransaction(new OutFoodMineFragment()).commit();
            }
        });
        outFoodIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initButton(outFoodIndex);
                fragmentTransaction(new OutFoodIndexFragment()).commit();
            }
        });
        outFoodFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initButton(outFoodFollow);
                fragmentTransaction(new OutFoodFollowFragment()).commit();
            }
        });
        outFoodOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initButton(outFoodOrder);
                fragmentTransaction(new OutFoodOrderFragment()).commit();
            }
        });
    }
    private void initButton(Button bt){
        if(!buttons.isEmpty()){
            for (Button b :buttons){
                b.setBackgroundResource(R.color.gray);
            }
        }
        bt.setBackgroundResource(R.color.yellow);
        buttons.add(bt);
    }
    private void bindView() {
        llBg = (LinearLayout) findViewById(R.id.ll_bg);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        outFoodIndex = (Button) findViewById(R.id.out_food_index);
        outFoodFollow = (Button) findViewById(R.id.out_food_follow);
        outFoodOrder = (Button) findViewById(R.id.out_food_order);
        outFoodMine = (Button) findViewById(R.id.out_food_mine);
    }
}