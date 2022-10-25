package com.example.smartcityc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

public class HospitalDetailActivity extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private ImageView hospitalDetailsImage;
    private TextView hospitalDetailsContent;
    private Button hospitalDetailsSure;
    public static String title,contentD,image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);
        bindView();
        initData();
        initEvent();
    }

    private void initEvent() {
    }

    private void initData() {
        Glide.with(this).load(image).apply(new RequestOptions().transform(new CenterCrop())).into(hospitalDetailsImage);
        tvTitle.setText(title);
        hospitalDetailsContent.setText(contentD);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        hospitalDetailsSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HospitalDetailActivity.this,HospitalCardActivity.class));
            }
        });
    }

    private void bindView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        hospitalDetailsImage = (ImageView) findViewById(R.id.hospital_details_image);
        hospitalDetailsContent = (TextView) findViewById(R.id.hospital_details_content);
        hospitalDetailsSure = (Button) findViewById(R.id.hospital_details_sure);
    }
}