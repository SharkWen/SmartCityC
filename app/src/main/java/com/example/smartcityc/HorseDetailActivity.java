package com.example.smartcityc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

public class HorseDetailActivity extends AppCompatActivity {
    private ImageView horseDetailPic;
    private TextView horseDetailName;
    private TextView horseDetailMj;
    private TextView horseDetailPrice;
    private TextView horseDetailType;
    private TextView horseDetailContent;
    private Button horseDetailCall;
    private Button horseDetailIndex;
    public static String pic,name,mj,price,type,contented,tel = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horse_detail);
        bindView();
        initData();
        initEvent();
    }

    private void initEvent() {
        horseDetailIndex.setOnClickListener(view -> {
            finish();
        });
        horseDetailCall.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+tel));
            startActivity(intent);
        });
    }

    private void initData() {
        Glide.with(this).load(pic).apply(new RequestOptions().transform(new CenterCrop())).into(horseDetailPic);
        horseDetailName.setText(name);
        horseDetailMj.setText(mj);
        horseDetailPrice.setText(price);
        horseDetailType.setText(type);
        horseDetailContent.setText(contented);
    }

    private void bindView() {
        horseDetailPic = (ImageView) findViewById(R.id.horse_detail_pic);
        horseDetailName = (TextView) findViewById(R.id.horse_detail_name);
        horseDetailMj = (TextView) findViewById(R.id.horse_detail_mj);
        horseDetailPrice = (TextView) findViewById(R.id.horse_detail_price);
        horseDetailType = (TextView) findViewById(R.id.horse_detail_type);
        horseDetailContent = (TextView) findViewById(R.id.horse_detail_content);
        horseDetailCall = (Button) findViewById(R.id.horse_detail_call);
        horseDetailIndex = findViewById(R.id.horse_detail_index);
    }
}