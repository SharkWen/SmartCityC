package com.example.smartcityc.Tool;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Tool {
    public static OkHttpClient client = new OkHttpClient();

    public static void getData(String url, Callback callback) {
        Request request = new Request.Builder().url(Config.baseUrl + url).build();
        client.newCall(request).enqueue(callback);
    }

    public static void postData(String url, String data, Callback callback) {
        RequestBody requestBody = RequestBody.create(data, MediaType.parse("application/json"));
        Request request = new Request.Builder().url(Config.baseUrl + url).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    public static void getTokenData(String url, String token, Callback callback) {
        Request request = new Request.Builder()
                .addHeader("Authorization", token)
                .url(Config.baseUrl + url).build();
        client.newCall(request).enqueue(callback);
    }

    public static void putTokenData(String url, String token, String data, Callback callback) {
        RequestBody requestBody = RequestBody.create(data, MediaType.parse("application/json"));
        Request request = new Request.Builder().url(Config.baseUrl + url).addHeader("Authorization", token).put(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    public static void postTokenData(String url, String token, String data, Callback callback) {
        RequestBody requestBody = RequestBody.create(data, MediaType.parse("application/json"));
        Request request = new Request.Builder().url(Config.baseUrl + url).addHeader("Authorization", token).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    public static Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    public static final String html(String ht) {
        ht = ht.replace("   ", "");
        ht = ht.replace("&nbsp;", "");
        ht = ht.replaceAll("<[^>]+>", "");
        return "        " +ht;
    }

    public static AlertDialog setDialog(Context context, String title) {
        return new AlertDialog.Builder(context).setTitle(title).create();
    }

    public static String sp(Context context,String data) {
        return context.getSharedPreferences("data", Context.MODE_PRIVATE).getString(data,"");
    }
    public static SharedPreferences shp(Context context){
        return context.getSharedPreferences("data",Context.MODE_PRIVATE);
    }
    public static List<Map<String,Object>> getMapList(){
        return new ArrayList<>();
    }
    public static Map<String,Object> getMap(){
        return new HashMap<>();
    }

    public static void bannerNext(ViewPager vp, long time, int size){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(vp.getCurrentItem() == size-1){
                    vp.setCurrentItem(0);
                }else{
                    vp.setCurrentItem(vp.getCurrentItem()+1);
                }
                handler.postDelayed(this,time);
            }
        },time);
    }
}
