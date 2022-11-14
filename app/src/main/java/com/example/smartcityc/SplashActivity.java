package com.example.smartcityc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Adapter.SplashAdapter;
import com.example.smartcityc.Tool.Tool;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private ViewPager viewpager;
    ImageView dot1;
    ImageView dot2;
    ImageView dot3;
    ImageView dot4;
    int[] ints = new int[]{R.drawable.i1, R.drawable.i2, R.drawable.i3, R.drawable.i4};
    List<View> views = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bindView();
        for (Integer i : ints) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundColor(Color.WHITE);
            Glide.with(this).load(i).apply(new RequestOptions().transform(new CenterCrop())).into(imageView);
            if (i == R.drawable.i4) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                });
            }
            views.add(imageView);
        }
        List<ImageView> dots = new ArrayList<>();

        ImageView dot1 = findViewById(R.id.dot1);
        ImageView dot2 = findViewById(R.id.dot2);
        ImageView dot3 = findViewById(R.id.dot3);
        ImageView dot4 = findViewById(R.id.dot4);

        dots.add(dot1);
        dots.add(dot2);
        dots.add(dot3);
        dots.add(dot4);
        SplashAdapter splashAdapter = new SplashAdapter(views);
        viewpager.setAdapter(splashAdapter);
        Tool.bannerNext(viewpager,1000,views.size());
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (ImageView dot : dots) {
                    dot.setImageResource(R.drawable.splash_dot);
                }
                dots.get(position).setImageResource(R.drawable.splash_dot_active);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void bindView() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        dot1 = (ImageView) findViewById(R.id.dot1);
        dot2 = (ImageView) findViewById(R.id.dot2);
        dot3 = (ImageView) findViewById(R.id.dot3);
        dot4 = (ImageView) findViewById(R.id.dot4);
    }
}