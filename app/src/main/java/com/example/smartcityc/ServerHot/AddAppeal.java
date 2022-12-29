package com.example.smartcityc.ServerHot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcityc.R;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddAppeal extends AppCompatActivity {
    private TextView titleBarTitle;
    private ImageButton titleBarBack;
    private EditText addAppealTitle;
    private EditText addAppealDanwei;
    private EditText addAppealContent;
    private Button addAppealCommit;
    Handler handler = new Handler();
    Intent intent;
    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appeal);
        intent = getIntent();
        initView();
    }

    private void initView() {
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        titleBarTitle.setText(intent.getStringExtra("title"));
        titleBarBack = (ImageButton) findViewById(R.id.title_bar_back);
        titleBarBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addAppealTitle = (EditText) findViewById(R.id.add_appeal_title);
        addAppealDanwei = (EditText) findViewById(R.id.add_appeal_danwei);
        addAppealContent = (EditText) findViewById(R.id.add_appeal_content);
        addAppealCommit = (Button) findViewById(R.id.add_appeal_commit);
        addAppealCommit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String json = "{\n" +
                        "\"appealCategoryId\": 8,\n" +
                        "\"title\": \""+addAppealTitle.getText().toString()+"\",\n" +
                        "\"content\": \""+addAppealContent.getText().toString()+"\",\n" +
                        "\"undertaker\": \""+addAppealDanwei.getText().toString()+"\",\n" +
                        "\"imgUrl\": \"/profile/abc.png\"\n" +
                        "}";
                RequestBody body = RequestBody.create(MediaType.parse("application/json"),json);
                Request request = new Request.Builder().url(Config.address+"/prod-api/api/gov-service-hotline/appeal").post(body).header("Authorization", Config.token).build();
                client.newCall(request).enqueue(new Callback(){
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        MessageBean messageBean = new Gson().fromJson(response.body().string(), MessageBean.class);
                        handler.post(()->{
                            Toast.makeText(AddAppeal.this, messageBean.getMsg(), Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    }

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }
                });
            }
        });

    }
}