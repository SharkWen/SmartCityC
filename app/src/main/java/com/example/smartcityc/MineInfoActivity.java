package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Bean.MineInfoPutBean;
import com.example.smartcityc.Bean.MsgBean;
import com.example.smartcityc.Fragment.MineFragment;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MineInfoActivity extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private ImageView mineInfoIv;
    private EditText mineInfoNick;
    private RadioButton mineInfoSex0;
    private RadioButton mineInfoSex1;
    private EditText mineInfoPhone;
    private EditText mineInfoCardId;
    private Button mineInfoBtn;
    public static String url, nick, sex, phone, cardId;
    SharedPreferences sp;
    Context context;
    private RadioGroup radioGroup;
    MineInfoPutBean mineInfoPutBean = new MineInfoPutBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_info);
        context = this;
        sp = getSharedPreferences("data", MODE_PRIVATE);
        bindView();
        initData();
        initEvent();
    }

    private void initEvent() {
        tvBack.setOnClickListener(view -> {
            MainActivity.back = "mine";
            startActivity(new Intent(this, MainActivity.class));
        });
        mineInfoBtn.setOnClickListener(view -> {
            if (radioGroup.getCheckedRadioButtonId() == R.id.mine_info_sex0) {
                mineInfoPutBean.setSex("1");
            } else {
                mineInfoPutBean.setSex("0");
            }
            mineInfoPutBean.setIdCard(mineInfoCardId.getText().toString());
            mineInfoPutBean.setNickName(mineInfoNick.getText().toString());
            mineInfoPutBean.setPhonenumber(mineInfoPhone.getText().toString());
            String data = new Gson().toJson(mineInfoPutBean);
            Tool.putTokenData("/prod-api/api/common/user", sp.getString("token", ""), data, new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    MsgBean msgBean = new Gson().fromJson(response.body().string(), MsgBean.class);
                    Tool.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Tool.setDialog(context, msgBean.getMsg()).show();
                        }
                    });
                }
            });
        });
    }

    private void initData() {
        tvTitle.setText("个人信息");
        Glide.with(this).load(url).apply(new RequestOptions().transform(new RoundedCorners(200))).into(mineInfoIv);
        mineInfoNick.setText(nick);
        if (sex.equals("1")) {
            mineInfoSex0.setChecked(true);
        } else {
            mineInfoSex1.setChecked(true);
        }
        mineInfoPhone.setText(phone);
        mineInfoCardId.setText(cardId);
    }

    private void bindView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        mineInfoIv = (ImageView) findViewById(R.id.mine_info_iv);
        mineInfoNick = (EditText) findViewById(R.id.mine_info_nick);
        mineInfoSex0 = (RadioButton) findViewById(R.id.mine_info_sex0);
        mineInfoSex1 = (RadioButton) findViewById(R.id.mine_info_sex1);
        mineInfoPhone = (EditText) findViewById(R.id.mine_info_phone);
        mineInfoCardId = (EditText) findViewById(R.id.mine_info_cardId);
        mineInfoBtn = (Button) findViewById(R.id.mine_info_btn);
        radioGroup = findViewById(R.id.mine_info_radioGroup);
    }
}