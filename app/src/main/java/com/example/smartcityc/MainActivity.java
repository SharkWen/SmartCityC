package com.example.smartcityc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.smartcityc.Fragment.HomeFragment;
import com.example.smartcityc.Fragment.MineFragment;
import com.example.smartcityc.Fragment.NewsFragment;
import com.example.smartcityc.Fragment.ServiceFragment;
import com.example.smartcityc.Fragment.ZhylFragment;

public class MainActivity extends AppCompatActivity {
    private ImageView indexMain;
    private ImageView serveMain;
    private ImageView ylMain;
    private ImageView newsMain;
    private ImageView mineMain;
    public static String back = "index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        initData();
        initEvent();
    }

    private void initEvent() {
        indexMain.setOnClickListener(view -> {
            initBottomTab();
            indexMain.setImageResource(R.drawable.icon11);
            fragmentTransaction(new HomeFragment()).commit();
        });
        serveMain.setOnClickListener(view -> {
            initBottomTab();
            serveMain.setImageResource(R.drawable.icon22);
            fragmentTransaction(new ServiceFragment()).commit();
        });
        ylMain.setOnClickListener(view -> {
            initBottomTab();
            ylMain.setImageResource(R.drawable.icon44);
            fragmentTransaction(new ZhylFragment()).commit();
        });
        newsMain.setOnClickListener(view -> {
            initBottomTab();
            newsMain.setImageResource(R.drawable.icon33);
            fragmentTransaction(new NewsFragment()).commit();
        });
        mineMain.setOnClickListener(view -> {
            initBottomTab();
            mineMain.setImageResource(R.drawable.icon55);
            fragmentTransaction(new MineFragment()).commit();
        });

    }

    private void initBottomTab() {
        indexMain.setImageResource(R.drawable.icon1);
        serveMain.setImageResource(R.drawable.icon2);
        ylMain.setImageResource(R.drawable.icon4);
        newsMain.setImageResource(R.drawable.icon3);
        mineMain.setImageResource(R.drawable.icon5);
    }

    private void initData() {
        switch (back) {
            case "index":
                initBottomTab();
                indexMain.setImageResource(R.drawable.icon11);
                fragmentTransaction(new HomeFragment()).commit();
                break;
            case "service":
                initBottomTab();
                serveMain.setImageResource(R.drawable.icon22);
                fragmentTransaction(new ServiceFragment()).commit();
                break;
            case "mine":
                initBottomTab();
                mineMain.setImageResource(R.drawable.icon55);
                fragmentTransaction(new MineFragment()).commit();
                break;
        }
    }

    private FragmentTransaction fragmentTransaction(Fragment fragment) {
        return getSupportFragmentManager().beginTransaction().replace(R.id.frag_main, fragment);
    }

    private void bindView() {
        indexMain = findViewById(R.id.index_main);
        serveMain = findViewById(R.id.serve_main);
        ylMain = findViewById(R.id.yl_main);
        newsMain = findViewById(R.id.news_main);
        mineMain = findViewById(R.id.mine_main);
    }
}