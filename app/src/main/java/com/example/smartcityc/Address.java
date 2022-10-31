package com.example.smartcityc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcityc.Adapter.AddressAdapter;
import com.example.smartcityc.Bean.AddressBean;
import com.example.smartcityc.Bean.MsgBean;
import com.example.smartcityc.Bean.OrderSureBean;
import com.example.smartcityc.Bean.ProvinceNameBean;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Address extends AppCompatActivity {
    private LinearLayout llBg;
    private TextView tvBack;
    private TextView tvTitle;
    private RecyclerView addressRecyclerView;
    private TextView tvAdd;
    Context context;


    private TextView cardMovingProvince;
    private TextView cardMovingProvinceName;
    private TextView carMovingCity;
    private EditText carMovingDetails;
    private EditText addressRelationship;
    private EditText addressRelationshipTel;
    private Button carMovingPost;
    AlertDialog dialog, dialog1;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    View view1;
    ProvinceNameBean provinceNameBean;
    boolean isSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        context = this;
        view1 = View.inflate(context, R.layout.alert_dialog, null);
        bindView();
        initData();
        initEvent();
    }

    private void initEvent() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        cardMovingProvince.setOnClickListener(v -> {
            cardMovingProvinceName.setText("选择市区");
            carMovingCity.setText("选择城市");
            getCarProvince();
            isSelected = false;
        });
        cardMovingProvinceName.setOnClickListener(v -> {
            isSelected = false;
            String name = cardMovingProvince.getText().toString();
            if (name.equals("选择省份")) {
                Toast.makeText(context, "请先选择省份", Toast.LENGTH_SHORT).show();
                return;
            }
            getCarProvinceName(name);
            carMovingCity.setText("选择城市");
            dialog.setTitle("请选择市区");
        });
        carMovingCity.setOnClickListener(v -> {
            if (cardMovingProvinceName.getText().toString().equals("选择市区")) {
                Toast.makeText(context, "请先选择市区", Toast.LENGTH_SHORT).show();
                return;
            }
            dialog.setTitle("请选择城市");
            dialog.show();
        });
        carMovingPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "{\n" +
                        "\"addressDetail\": \"" + cardMovingProvince.getText().toString() + cardMovingProvinceName.getText().toString() + carMovingCity.getText().toString() + "\",\n" +
                        "\"label\": \"" + carMovingDetails.getText().toString() + "\",\n" +
                        "\"name\": \"" + addressRelationship.getText().toString() + "\",\n" +
                        "\"phone\": \"" + addressRelationshipTel.getText().toString() + "\"\n" +
                        "}";
                Tool.postTokenData("/prod-api/api/takeout/address", Tool.sp(context, "token"), data, new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        MsgBean msgBean = new Gson().fromJson(response.body().string(), MsgBean.class);
                        Tool.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, msgBean.getMsg(), Toast.LENGTH_SHORT).show();
                                setAddressListView();
                                dialog1.dismiss();
                            }
                        });
                    }
                });
            }
        });
        dialog1 = new AlertDialog.Builder(context).setView(view1).create();
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Tool.shp(context).contains("token")){
                    Tool.setDialog(context,"请注册登录").show();
                    return;
                }
                dialog1.show();
            }
        });
    }

    private void setAddressListView() {
        Tool.getTokenData("/prod-api/api/takeout/address/list", Tool.sp(context, "token"), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                AddressBean addressBean = new Gson().fromJson(response.body().string(), AddressBean.class);
                Tool.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        AddressAdapter addressAdapter = new AddressAdapter(context, addressBean.getData(),new TextView(context),new OrderSureBean());
                        addressRecyclerView.setAdapter(addressAdapter);
                        addressRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                    }
                });
            }
        });
    }

    private void initData() {
        listView = new ListView(context);
        dialog = new AlertDialog.Builder(context).setTitle("请选择省份").setView(listView).create();
        tvTitle.setText("我的收货地址");
        llBg.setBackgroundResource(R.color.yellow);
        tvAdd.setText("新增地址");
        setAddressListView();
    }

    private void getCarProvince() {
        Tool.getData("/prod-api/api/common/gps/province", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                provinceNameBean = new Gson().fromJson(response.body().string(), ProvinceNameBean.class);
                List<String> list = new ArrayList<>();
                for (int i = 0; i < provinceNameBean.getData().size(); i++) {
                    list.add(provinceNameBean.getData().get(i).getName());
                }
                Tool.handler.post(() -> {
                    arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_checked, list);
                    listView.setAdapter(arrayAdapter);
                    listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    dialog.show();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String name = provinceNameBean.getData().get(position).getName();
                            cardMovingProvince.setText(name);
                            getCarProvinceName(name);
                            dialog.dismiss();
                        }
                    });
                });
            }
        });
    }

    private void getCarProvinceName(String name) {
        Tool.getData("/prod-api/api/common/gps/city?provinceName=" + name, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                provinceNameBean = new Gson().fromJson(response.body().string(), ProvinceNameBean.class);
                List<String> list = new ArrayList<>();
                for (int i = 0; i < provinceNameBean.getData().size(); i++) {
                    list.add(provinceNameBean.getData().get(i).getName());
                }
                Tool.handler.post(() -> {
                    arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_checked, list);
                    listView.setAdapter(arrayAdapter);
                    listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    dialog.show();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String name = provinceNameBean.getData().get(position).getName();
                            cardMovingProvinceName.setText(name);
                            getCarProvinceCity(name);
                            dialog.dismiss();
                        }
                    });
                });
            }
        });
    }

    private void getCarProvinceCity(String cityName) {
        Tool.getData("/prod-api/api/common/gps/area?cityName=" + cityName, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                provinceNameBean = new Gson().fromJson(response.body().string(), ProvinceNameBean.class);
                List<String> list = new ArrayList<>();
                for (int i = 0; i < provinceNameBean.getData().size(); i++) {
                    list.add(provinceNameBean.getData().get(i).getName());
                }
                Tool.handler.post(() -> {
                    arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_checked, list);
                    listView.setAdapter(arrayAdapter);
                    listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    dialog.show();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String name = provinceNameBean.getData().get(position).getName();
                            carMovingCity.setText(name);
                            dialog.dismiss();
                            isSelected = true;
                        }
                    });
                });
            }
        });
    }

    private void bindView() {
        llBg = (LinearLayout) findViewById(R.id.ll_bg);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvAdd = (TextView) findViewById(R.id.tv_add);
        addressRecyclerView = findViewById(R.id.address_RecyclerView);

        cardMovingProvince = view1.findViewById(R.id.card_moving_province);
        cardMovingProvinceName = view1.findViewById(R.id.card_moving_provinceName);
        carMovingCity = view1.findViewById(R.id.car_moving_city);
        carMovingDetails = view1.findViewById(R.id.car_moving_details);
        addressRelationship = view1.findViewById(R.id.address_relationship);
        addressRelationshipTel = view1.findViewById(R.id.address_relationship_tel);
        carMovingPost = view1.findViewById(R.id.car_moving_post);
    }
}