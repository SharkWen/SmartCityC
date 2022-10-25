package com.example.smartcityc.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smartcityc.ActivityActivity;
import com.example.smartcityc.Bean.ServiceBean;
import com.example.smartcityc.DataAnalysisActivity;
import com.example.smartcityc.FindHourseActivity;
import com.example.smartcityc.MengActivity;
import com.example.smartcityc.R;
import com.example.smartcityc.TakeOutFoodActivity;
import com.example.smartcityc.Tool.Config;
import com.example.smartcityc.Tool.Tool;
import com.example.smartcityc.VideoActivity;
import com.google.gson.Gson;

import org.w3c.dom.ls.LSException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ServiceFragment extends Fragment {
    View view;
    GridView gridView;
    Context context;
    private AutoCompleteTextView serveFrgEd;
    private TextView serveFrgSure;
    private TextView serveFrgSh;
    private TextView serveFrgCz;
    private TextView serveFrgPm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_service, container, false);
        context = getContext();
        bindView();
        initData();
        initEvent();
        return view;
    }

    private void initEvent() {
        serveFrgSh.setOnClickListener(view1 -> {
            initColor(serveFrgSh);
            getServiceData(serveFrgSh.getText().toString());
        });
        serveFrgCz.setOnClickListener(view1 -> {
            initColor(serveFrgCz);
            getServiceData(serveFrgCz.getText().toString());
        });
        serveFrgPm.setOnClickListener(view1 -> {
            initColor(serveFrgPm);
            getServiceData(serveFrgPm.getText().toString());
        });
    }

    private void initData() {
        getServiceData(serveFrgSh.getText().toString());
    }
    private void initColor(TextView tv){
        serveFrgSh.setBackgroundColor(Color.rgb(230,230,230));
        serveFrgCz.setBackgroundColor(Color.rgb(230,230,230));
        serveFrgPm.setBackgroundColor(Color.rgb(230,230,230));
        serveFrgSh.setTextColor(Color.rgb(111,109,109));
        serveFrgCz.setTextColor(Color.rgb(111,109,109));;
        serveFrgPm.setTextColor(Color.rgb(111,109,109));;
        tv.setBackgroundColor(Color.WHITE);
        tv.setTextColor(Color.rgb(3,169,244));
    }
    private void getServiceData(String type){
        List<Map<String, Object>> list = new ArrayList<>();
        Tool.getData("/prod-api/api/service/list?serviceType="+type, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ServiceBean serviceBean = new Gson().fromJson(response.body().string(), ServiceBean.class);
                for (ServiceBean.RowsDTO n : serviceBean.getRows()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("image_service", Config.baseUrl + n.getImgUrl());
                    map.put("text_service", n.getServiceName());
                    list.add(map);
                }
                Tool.handler.post(() -> {
                    SimpleAdapter simpleAdapter = new SimpleAdapter(context, list, R.layout.home_frag_gl
                            , new String[]{"image_service", "text_service"}, new int[]{R.id.home_frag_iv, R.id.home_frag_tv});
                    simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                        @Override
                        public boolean setViewValue(View view, Object o, String s) {
                            ImageView imageView = view.findViewById(R.id.home_frag_iv);
                            if (imageView != null) {
                                Glide.with(context).load(s).into(imageView);
                                return true;
                            }
                            return false;
                        }
                    });
                    gridView.setAdapter(simpleAdapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            if(type.equals("便民服务")){
                                switch (i){
                                    case 1:
                                        context.startActivity(new Intent(context, MengActivity.class));
                                        break;
                                    case 2:
                                        startActivity(new Intent(context, TakeOutFoodActivity.class));
                                        break;
                                    case 3:
                                        context.startActivity(new Intent(context, FindHourseActivity.class));
                                        break;
                                }
                            }
                            if(type.equals("生活服务")){
                                switch (i){
                                    case 2:
                                        context.startActivity(new Intent(context, VideoActivity.class));
                                        break;
                                    case 3:
                                        context.startActivity(new Intent(context, ActivityActivity.class));
                                        break;
                                    case 6:
                                        context.startActivity(new Intent(context, DataAnalysisActivity.class));
                                        break;
                                }
                            }
                        }
                    });
                });

            }
        });
    }
    private void bindView() {
        serveFrgEd = view.findViewById(R.id.serve_frg_ed);
        serveFrgSure = view.findViewById(R.id.serve_frg_sure);
        serveFrgSh = view.findViewById(R.id.serve_frg_sh);
        serveFrgCz = view.findViewById(R.id.serve_frg_cz);
        serveFrgPm = view.findViewById(R.id.serve_frg_pm);
        gridView = view.findViewById(R.id.serve_frg_gl);
    }
}