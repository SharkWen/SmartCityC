package com.example.smartcityc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class ActivityDetails extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private WebView webView;
    public static  String title,contentH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        bindView();
        initData();
    }

    private void initData() {
        tvBack.setOnClickListener(view -> {
            finish();
        });
        tvTitle.setText(title);
        webView.setWebViewClient(new WebViewClient());
        webView.loadData(getContentH(contentH),"text/html","utf-8");
    }
    private String getContentH(String body){
        String head = "<head><style>img{max-width:100%;height:auto;}</style></head>";
        return "<html>"+head+"<body>"+body+"</body></html>";
    }
    private void bindView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        webView = (WebView) findViewById(R.id.webView);
    }
}