package com.example.smartcityc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

public class VideoDetails extends AppCompatActivity {
    private ImageView videoDetailsImage;
    private TextView videoDetailsName;
    private TextView videoDetailsScore;
    private TextView videoDetailsLike;
    private TextView videoDetailsContent;
    private Button videoDetailsBtn;
    private RatingBar videoDetailsStar;
    public static String detailsUrl;
    public static String detailsName;
    public static String detailsScore;
    public static String detailsLike;
    public static String detailsContent;
    public static int stars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
        initView();
        initData();
        initEvent();
    }
    private void initEvent() {
        videoDetailsBtn.setOnClickListener(v->{
            startActivity(new Intent(this,MainActivity.class));
        });
    }

    private void initData() {
        Glide.with(this).load(detailsUrl).apply(new RequestOptions().transform(new CenterCrop())).into(videoDetailsImage);
        videoDetailsName.setText(detailsName);
        videoDetailsScore.setText(detailsScore);
        videoDetailsLike.setText(detailsLike);
        videoDetailsContent.setText(detailsContent);
        videoDetailsBtn.setText("主页");
        videoDetailsStar.setRating(stars);

    }

    private void initView() {
        videoDetailsImage = (ImageView) findViewById(R.id.video_details_image);
        videoDetailsName = (TextView) findViewById(R.id.video_details_name);
        videoDetailsScore = (TextView) findViewById(R.id.video_details_score);
        videoDetailsLike = (TextView) findViewById(R.id.video_details_like);
        videoDetailsContent = (TextView) findViewById(R.id.video_details_content);
        videoDetailsBtn = (Button) findViewById(R.id.video_details_btn);
        videoDetailsStar = findViewById(R.id.video_details_star);
    }
}