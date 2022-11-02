package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Adapter.BannerVideoAdapter;
import com.example.smartcityc.Bean.ActivityBannerBean;
import com.example.smartcityc.Bean.VideoBean;
import com.example.smartcityc.Tool.Config;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;
import com.youth.banner.Banner;

import java.io.IOException;
import java.security.interfaces.RSAKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class VideoActivity extends AppCompatActivity {
    private ImageView ivBack;
    private AutoCompleteTextView edSearch;
    private Banner videoBanner;
    private ListView videoListView;
    private TextView videoMore;

    boolean isSome = true;
    String search = "";
    String[] strings = new String[]{"videoPic", "videoTitle", "videoContent","videoTime", "videoTimeout"};
    int[] ints = new int[]{R.id.video_pic, R.id.video_title,R.id.video_content, R.id.video_time, R.id.video_timeout};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        bindView();
        initData();
        initEvent();
    }

    private void initEvent() {
        ivBack.setImageResource(R.drawable.arrowleft);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(edSearch.getText().toString().length()<=2 && i == 67){
                    search = "";
                    getListView();
                }
                if(i==66 && keyEvent.getAction()==KeyEvent.ACTION_DOWN){
                    search = edSearch.getText().toString();
                    getListView();
                    return true;
                }
                return false;
            }
        });
        videoMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSome = false;
                videoMore.setVisibility(View.GONE);
                getListView();
            }
        });
    }

    private void initData() {
        getBanner();
        getListView();
    }

    private void getBanner() {
        Tool.getData("/prod-api/api/movie/rotation/list", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ActivityBannerBean activityBannerBean = new Gson().fromJson(response.body().string(),ActivityBannerBean.class);
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        BannerVideoAdapter bannerVideoAdapter = new BannerVideoAdapter(activityBannerBean.getRows(),VideoActivity.this);
                        videoBanner.setAdapter(bannerVideoAdapter);
                    }
                });
            }
        });
    }

    private void getListView() {
        List<Map<String,Object>> mapList = new ArrayList<>();
        Tool.getData("/prod-api/api/movie/film/list", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                VideoBean videoBean = new Gson().fromJson(response.body().string(),VideoBean.class);
                for(VideoBean.RowsDTO n :videoBean.getRows()){
                    if(n.getName().contains(search)){
                        Map<String,Object> map = new HashMap<>();
                        map.put("videoPic", Config.baseUrl + n.getCover());
                        map.put("videoTitle", n.getName());
                        map.put("videoContent", Tool.html(n.getIntroduction()));
                        map.put("videoTime", n.getPlayDate());
                        map.put("videoTimeout", "时长"+n.getDuration()+"分钟");
                        mapList.add(map);
                        if(n.getDuration() == 105 && isSome) break;
                    }
                }
                Tool.handler.post(()->{
                    SimpleAdapter simpleAdapter = new SimpleAdapter(VideoActivity.this,mapList,R.layout.video_item,strings,ints);
                    simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                        @Override
                        public boolean setViewValue(View view, Object o, String s) {
                            ImageView imageView = view.findViewById(R.id.video_pic);
                            if(imageView!=null){
                                Glide.with(VideoActivity.this).load(s).apply(new RequestOptions().transform(new CenterCrop())).into(imageView);
                                return true;
                            }
                            return false;
                        }
                    });
                    videoListView.setAdapter(simpleAdapter);
                    videoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            VideoDetails.detailsUrl = Config.baseUrl+ videoBean.getRows().get(i).getCover();
                            VideoDetails.detailsName = videoBean.getRows().get(i).getName();
                            VideoDetails.detailsScore = "评分:"+ videoBean.getRows().get(i).getScore()+"";
                            VideoDetails.detailsLike = "喜欢人数:"+videoBean.getRows().get(i).getLikeNum()+"";
                            VideoDetails.detailsContent = "简介:"+Tool.html(videoBean.getRows().get(i).getIntroduction());
                            VideoDetails.stars = videoBean.getRows().get(i).getScore();
                            startActivity(new Intent(VideoActivity.this,VideoDetails.class));
                        }
                    });
                });
            }
        });
    }

    private void bindView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        edSearch = (AutoCompleteTextView) findViewById(R.id.ed_search);
        videoBanner = (Banner) findViewById(R.id.video_banner);
        videoListView = (ListView) findViewById(R.id.video_list_view);
        videoMore = (TextView) findViewById(R.id.video_more);
    }
}