package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Bean.MineInfoBean;
import com.example.smartcityc.Bean.MsgBean;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private EditText edUsername;
    private EditText edPassword;
    private Button btLogin;
    private TextView tvRegister;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindView();
        initData();
    }

    private void initData() {
        tvTitle.setText("用户登录");
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,RegisterActivity.class));
            }
        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edUsername.getText().toString().equals("")) {
                    Toast.makeText(context, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edPassword.getText().toString().equals("")) {
                    Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String data = "{\n" +
                        "    \"username\":\"" + edUsername.getText().toString() + "\",\n" +
                        "    \"password\":\"" + edPassword.getText().toString() + "\"\n" +
                        "}";
                System.out.println(data);
                Tool.postData("/prod-api/api/login", data, new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String res = response.body().string();
                        MsgBean msgBean = new Gson().fromJson(res, MsgBean.class);
                        Tool.shp(context).edit().putString("token", msgBean.getToken()).commit();
                        Tool.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (msgBean.getCode() == 200) {
                                    Tool.setDialog(context, "登录成功").show();
                                    Tool.handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            MainActivity.back = "mine";
                                            startActivity(new Intent(context, MainActivity.class));
                                        }
                                    }, 1000);
                                }
                            }
                        });
                    }
                });
            }
        });

    }

    private void bindView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        edUsername = (EditText) findViewById(R.id.ed_username);
        edPassword = (EditText) findViewById(R.id.ed_password);
        btLogin = (Button) findViewById(R.id.bt_login);
        tvRegister = (TextView) findViewById(R.id.tv_register);

    }
}