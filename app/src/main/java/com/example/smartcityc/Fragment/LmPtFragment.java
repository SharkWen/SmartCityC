package com.example.smartcityc.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcityc.Bean.MsgBean;
import com.example.smartcityc.HospitalCardActivity;
import com.example.smartcityc.MainActivity;
import com.example.smartcityc.MengActivity;
import com.example.smartcityc.R;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class LmPtFragment extends Fragment {
    View view;
    private TextView lmName1;
    private TextView lmName2;
    private TextView lmName3;
    private TextView lmTime1;
    private Button lmBtn1;
    private TextView lmTime2;
    private Button lmBtn2;
    private TextView lmTime3;
    private Button lmBtn3;
    public static String categoryId, money, patientName, reserveTime, lmType;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_lm_pt, container, false);
        context = getContext();
        bindView();
        initData();
        return view;
    }

    private void initData() {
        if (!Tool.shp(context).contains("token")) {
            Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
            return;
        }
        lmName1.setText(patientName);
        lmName2.setText(patientName);
        lmName3.setText(patientName);
        lmBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sure(lmTime1);
            }
        });
        lmBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sure(lmTime2);
            }
        });
        lmBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sure(lmTime3);
            }
        });
    }
    private void sure(TextView tv){
        reserveTime = tv.getText().toString();
        String data = "{\n" +
                "\"categoryId\": " + categoryId + ",\n" +
                "\"money\": " + money + ",\n" +
                "\"patientName\": \"" + patientName + "\",\n" +
                "\"reserveTime\": \""+reserveTime+"\",\n" +
                "\"type\": " + lmType + "\n" +
                "}";
        System.out.println(data);
        Tool.postTokenData("/prod-api/api/hospital", Tool.sp(context, "token"), data, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                MsgBean msgBean = new Gson().fromJson(response.body().string(),MsgBean.class);
                System.out.println(msgBean.getMsg());
                if(msgBean.getCode()==200){
                    Tool.handler.post(new Runnable() {
                        @Override
                        public void run() {
                           new AlertDialog.Builder(context).setTitle(msgBean.getMsg()).setPositiveButton("预约成功", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i) {
                                   startActivity(new Intent(context, MengActivity.class));
                               }
                           }).create().show();
                        }
                    });
                }else {
                    Tool.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "预约失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    private void bindView() {
        lmName1 = view.findViewById(R.id.lm_name1);
        lmName2 = view.findViewById(R.id.lm_name2);
        lmName3 = view.findViewById(R.id.lm_name3);
        lmTime1 = view.findViewById(R.id.lm_time1);
        lmBtn1 = view.findViewById(R.id.lm_btn1);
        lmTime2 = view.findViewById(R.id.lm_time2);
        lmBtn2 = view.findViewById(R.id.lm_btn2);
        lmTime3 = view.findViewById(R.id.lm_time3);
        lmBtn3 = view.findViewById(R.id.lm_btn3);
    }
}