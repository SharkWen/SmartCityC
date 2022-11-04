package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartcityc.Adapter.MyViewPage2Adapter;
import com.example.smartcityc.BusOrderFragment.OrderNoFragment;
import com.example.smartcityc.BusOrderFragment.OrderYesFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class SmartBusOrderActivity extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private TabLayout busOrderTabLayout;
    private ViewPager2 busOrderViewPager2;
    List<Fragment> fragmentList = new ArrayList<>();
    List<String> stringList = new ArrayList<>();
    Context context = this;
    public static String replace = "nonePay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_bus_order);
        bindView();
        initData();
    }

    private void initData() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.back = "mine";
                startActivity(new Intent(context, MainActivity.class));
            }
        });
        tvTitle.setText("巴士订单列表");
        stringList.add("未支付");
        stringList.add("已支付");
        fragmentList.add(new OrderNoFragment());
        fragmentList.add(new OrderYesFragment());
        MyViewPage2Adapter viewPage2Adapter = new MyViewPage2Adapter(this, fragmentList);
        busOrderViewPager2.setAdapter(viewPage2Adapter);
        new TabLayoutMediator(busOrderTabLayout, busOrderViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(stringList.get(position));
            }
        }).attach();
    }

    private void bindView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        busOrderTabLayout = (TabLayout) findViewById(R.id.bus_order_tabLayout);
        busOrderViewPager2 = (ViewPager2) findViewById(R.id.bus_order_viewPager2);
    }
}