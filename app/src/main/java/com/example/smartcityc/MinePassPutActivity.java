package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.smartcityc.Bean.MsgBean;
import com.example.smartcityc.Bean.PassBean;
import com.example.smartcityc.Fragment.MineFragment;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MinePassPutActivity extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private EditText oldPass;
    private EditText newPass;
    private Button minePassSure;
    PassBean passBean = new PassBean();
    EditText surePass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_pass_put);
        bindView();
        initData();
        initEvent();
    }

    private void initEvent() {
        tvBack.setOnClickListener(view -> {
            finish();
        });
        minePassSure.setOnClickListener(view -> {
            passBean.setOldPassword(oldPass.getText().toString());
            passBean.setNewPassword(newPass.getText().toString());
            if(!surePass.getText().toString().equals(newPass.getText().toString())){
                Tool.setDialog(MinePassPutActivity.this,"密码不一致").show();
                return;
            }
            String data = new Gson().toJson(passBean);
            Tool.putTokenData("/prod-api/api/common/user/resetPwd", Tool.sp(this, "token"), data, new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    MsgBean msgBean = new Gson().fromJson(response.body().string(), MsgBean.class);
                    Tool.handler.post(() -> {
                        if (msgBean.getCode() == 200) {
                            Tool.shp(MinePassPutActivity.this).edit().putString("pass", newPass.getText().toString()).commit();
                        }
                        Tool.setDialog(MinePassPutActivity.this, msgBean.getMsg()).show();
                    });
                }
            });
        });
    }

    private void initData() {
        tvTitle.setText("密码修改");
    }

    private void bindView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        oldPass = (EditText) findViewById(R.id.old_pass);
        newPass = (EditText) findViewById(R.id.new_pass);
        minePassSure = (Button) findViewById(R.id.mine_pass_sure);
        surePass = findViewById(R.id.new_sure_pass);
    }
}