package com.example.smartcityc.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Bean.MineInfoBean;
import com.example.smartcityc.Bean.MsgBean;
import com.example.smartcityc.MineInfoActivity;
import com.example.smartcityc.MinePassPutActivity;
import com.example.smartcityc.MineYjActivity;
import com.example.smartcityc.R;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MineFragment extends Fragment {

    View view;
    Context context;
    private ImageView mineFragImage;
    private TextView mineFragTitle;
    private LinearLayout mineFragXx;
    private LinearLayout mineFragDd;
    private LinearLayout mineFragPass;
    private LinearLayout mineFragYj;
    private Button mineFragLogin;
    SharedPreferences sp;
    boolean isLogin = true;
    MineInfoBean mineInfoBean;
    public static String nickName,sex,tel;
    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    String pass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        context = getContext();
        sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        bindView();
        initData();
        initEvent();
        return view;
    }

    private void initData() {
        mineFragLogin.setText("退出登录");
        String data = "{\n" +
                "    \"username\":\"helloyuyiii\",\n" +
                "    \"password\":\"" + (Tool.sp(context, "pass").equals("") ? "lll" : sp.getString("pass", "")) + "\"\n" +
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
                sp.edit().putString("token", msgBean.getToken()).commit();
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Tool.getTokenData("/prod-api/api/common/user/getInfo", sp.getString("token", ""), new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                mineInfoBean = new Gson().fromJson(response.body().string(), MineInfoBean.class);
                                Tool.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mineInfoBean.getUser() == null) return;
                                        Tool.shp(context).edit().putString("avaterurl",mineInfoBean.getUser().getAvatar()).commit();
                                        Tool.shp(context).edit().putString("nick",mineInfoBean.getUser().getNickName()).commit();
                                        Glide.with(context).load(mineInfoBean.getUser().getAvatar())
                                                .apply(new RequestOptions().transform(new RoundedCorners(100)))
                                                .into(mineFragImage);
                                        mineFragTitle.setText(mineInfoBean.getUser().getNickName());
                                        sex = mineInfoBean.getUser().getSex();
                                        nickName = mineInfoBean.getUser().getNickName();
                                        tel = mineInfoBean.getUser().getPhonenumber();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

    }

    private void initEvent() {
        mineFragLogin.setOnClickListener(view1 -> {
            isLogin = !isLogin;
            if (isLogin) {
                initData();
            } else {
                mineFragImage.setImageResource(R.drawable.ppheard);
                mineFragTitle.setText("昵称");
                mineFragLogin.setText("登录");
            }
        });
        mineFragXx.setOnClickListener(view1 -> {
            if (!isLogin) {
                Tool.setDialog(context, "请登录").show();
                return;
            }
            MineInfoActivity.url = mineInfoBean.getUser().getAvatar();
            MineInfoActivity.sex = mineInfoBean.getUser().getSex();
            MineInfoActivity.nick = mineInfoBean.getUser().getNickName();
            MineInfoActivity.phone = mineInfoBean.getUser().getPhonenumber();
            MineInfoActivity.cardId = mineInfoBean.getUser().getIdCard();
            startActivity(new Intent(context, MineInfoActivity.class));
        });
        mineFragPass.setOnClickListener(view1 -> {
            if (!isLogin) {
                Tool.setDialog(context, "请登录").show();
                return;
            }
            context.startActivity(new Intent(context, MinePassPutActivity.class));
        });
        mineFragYj.setOnClickListener(view1 -> {
            startActivity(new Intent(context, MineYjActivity.class));
        });
    }

    private void bindView() {
        mineFragImage = view.findViewById(R.id.mine_frag_image);
        mineFragTitle = view.findViewById(R.id.mine_frag_title);
        mineFragXx = view.findViewById(R.id.mine_frag_xx);
        mineFragDd = view.findViewById(R.id.mine_frag_dd);
        mineFragPass = view.findViewById(R.id.mine_frag_pass);
        mineFragYj = view.findViewById(R.id.mine_frag_yj);
        mineFragLogin = view.findViewById(R.id.mine_frag_login);
    }
}