package com.example.smartcityc.ServerHot;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.NonNull;


import com.example.smartcityc.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Glide {
    private String url;
    private ImageView imageView;
    private Handler handle = new Handler();

    public Glide setUrl(String url) {
        this.url = url;
        return this;
    }

    public Glide setImageView(ImageView imageView) {
        this.imageView = imageView;
        return this;
    }

    public void load(){
        System.out.println("Load:"+this.url);
        System.out.println(this.url.startsWith("http://")||this.url.startsWith("https://"));
        if(this.url.startsWith("http://")||this.url.startsWith("https://")){
            OkHttpClient httpClient = new OkHttpClient();
            Request request = new Request.Builder().url(this.url).build();
            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    System.out.println("Glide Error: " + e.getMessage());
                    handle.post(()->{
                        imageView.setImageResource(R.drawable.ic_launcher_background);
                    });
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if(response.code() != 200){
                        handle.post(() -> {
                            imageView.setImageResource(R.drawable.ic_launcher_background);
                        });
                        return;
                    }
                    byte[] bytes = response.body().bytes();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    handle.post(() -> {
                        imageView.setImageBitmap(bitmap);
                    });
                }
            });
        }else{
            handle.post(() -> {
                imageView.setImageResource(R.drawable.ic_launcher_background);
            });
        }
    }
}
