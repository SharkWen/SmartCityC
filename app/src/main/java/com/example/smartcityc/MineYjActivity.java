package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.smartcityc.Bean.MsgBean;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MineYjActivity extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private EditText mineFkTitle;
    private EditText mineFkContent;
    private Button mineFkSure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_yj);
        bindView();
        initData();
        initEvent();

    }

    private void initEvent() {
        tvBack.setOnClickListener(view -> {
            finish();
        });
        mineFkSure.setOnClickListener(view -> {
            String data = "{\n" +
                    "\"content\": \""+mineFkTitle.getText().toString()+"\",\n" +
                    "\"title\": \""+mineFkContent.getText().toString()+"\"\n" +
                    "}";
            Tool.postTokenData("/prod-api/api/common/feedback", Tool.sp(this, "token"), data, new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    MsgBean msgBean = new Gson().fromJson(response.body().string(),MsgBean.class);
                    Tool.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Tool.setDialog(MineYjActivity.this,msgBean.getMsg()).show();
                        }
                    });
                }
            });
        });
    }

    private void initData() {
       tvTitle.setText("内容反馈");
    }

    private void bindView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        mineFkTitle = (EditText) findViewById(R.id.mine_fk_title);
        mineFkContent = (EditText) findViewById(R.id.mine_fk_content);
        mineFkSure = (Button) findViewById(R.id.mine_fk_sure);
    }
}