package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcityc.Bean.HospitalBean;
import com.example.smartcityc.Bean.HospitalCardBean;
import com.example.smartcityc.Bean.MsgBean;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HospitalAddActivity extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private EditText hospitalAddName;
    private EditText hospitalAddSex;
    private EditText hospitalAddCardId;
    private EditText hospitalAddBirthday;
    private EditText hospitalAddPhoneNum;
    private EditText hospitalAddAddress;
    private Button hospitalAddSure;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_add);
        bindView();
        initData();
        initEvent();
    }

    private void initEvent() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HospitalAddActivity.this, HospitalCardActivity.class));
            }
        });
        hospitalAddSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HospitalCardBean hospitalCardBean = new HospitalCardBean();
                String name = hospitalAddName.getText().toString();
                String sex = hospitalAddSex.getText().toString();
                String cardID = hospitalAddCardId.getText().toString();
                String birthday = hospitalAddBirthday.getText().toString();
                String phoneNum = hospitalAddPhoneNum.getText().toString();
                String address = hospitalAddAddress.getText().toString();
                if (name.isEmpty() || sex.isEmpty() || cardID.isEmpty() || birthday.isEmpty() || phoneNum.isEmpty() || address.isEmpty()) {
                    Toast.makeText(HospitalAddActivity.this, "请输入相关内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                hospitalCardBean.setName(name);
                if(sex.equals("男")){
                    hospitalCardBean.setSex("0");
                }else {
                    hospitalCardBean.setSex("1");
                }
                hospitalCardBean.setCardId(cardID);
                hospitalCardBean.setBirthday(birthday);
                hospitalCardBean.setTel(phoneNum);
                hospitalCardBean.setAddress(address);
                String data = new Gson().toJson(hospitalCardBean);
                Tool.postTokenData("/prod-api/api/hospital/patient", Tool.sp(HospitalAddActivity.this, "token"), data, new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        System.out.println("Failure");
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        MsgBean msgBean = new Gson().fromJson(response.body().string(), MsgBean.class);
                        System.out.println(msgBean.getCode());
                        if (msgBean.getCode() == 200) {
                            startActivity(new Intent(HospitalAddActivity.this, HospitalCardActivity.class));
                        }else {
                            Tool.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(HospitalAddActivity.this, "输入格式有误", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void initData() {
        tvTitle.setText("添加就诊卡");
    }

    private void bindView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        hospitalAddName = findViewById(R.id.hospital_add_name);
        hospitalAddSex = findViewById(R.id.hospital_add_sex);
        hospitalAddCardId = findViewById(R.id.hospital_add_cardId);
        hospitalAddBirthday = findViewById(R.id.hospital_add_birthday);
        hospitalAddPhoneNum = findViewById(R.id.hospital_add_phoneNum);
        hospitalAddAddress = findViewById(R.id.hospital_add_address);
        hospitalAddSure = (Button) findViewById(R.id.hospital_add_sure);
    }
}