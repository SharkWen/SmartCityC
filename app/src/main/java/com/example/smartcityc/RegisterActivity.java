package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcityc.Bean.MsgBean;
import com.example.smartcityc.Bean.UserBean;
import com.example.smartcityc.Tool.Config;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText userName;
    private EditText nickName;
    private EditText password;
    private EditText confirmPassword;
    private EditText phoneNumber;
    private EditText email;
    private EditText idCard;
    private Button btRegister;
    private UserBean userBean;
    private RadioGroup radioGroup;
    private TextView tv_back, tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initEvent();
    }

    private void initEvent() {
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void initView() {
        userName = findViewById(R.id.userName);
        nickName = findViewById(R.id.nickName);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        phoneNumber = findViewById(R.id.phonenumber);
        email = findViewById(R.id.email);
        idCard = findViewById(R.id.idCard);
        btRegister = findViewById(R.id.bt_register);
        radioGroup = findViewById(R.id.radio_group);
        tv_back = findViewById(R.id.tv_back);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("用户注册");
        userBean = new UserBean();

    }

    final Handler myHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 404:
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    break;
                case 10000:
                    MsgBean mb = (MsgBean) msg.obj;
                    if (mb.getCode() == 200) {
                        new AlertDialog.Builder(RegisterActivity.this)
                                .setTitle(mb.getMsg() + ",请登录")
                                .setPositiveButton("确定", (dialog, which) -> {
                                    RegisterActivity.this.finish();
                                }).create().show();
                    } else {
                        Toast.makeText(RegisterActivity.this, mb.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    private void register() {
        if (userName.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (nickName.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (confirmPassword.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "请确认密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!confirmPassword.getText().toString().trim().equals(password.getText().toString().trim())) {
            Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phoneNumber.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "请输入电话号码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "请选择性别", Toast.LENGTH_SHORT).show();
            return;
        }
        userBean.setAvatar("");
        userBean.setUserName(userName.getText().toString());
        userBean.setNickName(nickName.getText().toString());
        userBean.setPassword(password.getText().toString());
        userBean.setPhonenumber(phoneNumber.getText().toString());
        if (radioGroup.getCheckedRadioButtonId() == R.id.sex0) {
            userBean.setSex("0");
        } else {
            userBean.setSex("1");
        }
        userBean.setEmail(email.getText().toString());
        userBean.setIdCard(idCard.getText().toString());
        Gson gson = new Gson();
        String json = gson.toJson(userBean);
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json;charset=utf-8"));
        Request req = new Request.Builder()
                .url(Config.baseUrl + "/prod-api/api/register")
                .post(requestBody).build();
        okHttpClient.newCall(req)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Message msg = myHandler.obtainMessage();
                        msg.what = 404;
                        msg.sendToTarget();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String res = response.body().string();
                        Gson gson1 = new Gson();
                        MsgBean msgBean = gson1.fromJson(res, MsgBean.class);
                        Message msg = myHandler.obtainMessage();
                        msg.what = 10000;
                        msg.obj = msgBean;
                        msg.sendToTarget();
                    }
                });
    }
}