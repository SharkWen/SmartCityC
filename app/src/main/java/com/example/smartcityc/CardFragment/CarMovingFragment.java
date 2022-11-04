package com.example.smartcityc.CardFragment;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcityc.Bean.ProvinceNameBean;
import com.example.smartcityc.R;
import com.example.smartcityc.Tool.Tool;
import com.example.smartcityc.data.MySqlLite;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CarMovingFragment extends Fragment {

    View view;
    Context context;
    ListView listView;
    ProvinceNameBean provinceNameBean;
    ArrayAdapter<String> arrayAdapter;
    AlertDialog dialog;
    MySqlLite mySqlLite;
    boolean isSelected = false;
    String parkName, phone;
    private EditText carMovingParkName;
    private EditText carMovingName;
    private EditText carMovingPhone;
    private EditText carMovingCardId;
    private TextView cardMovingProvince;
    private TextView cardMovingProvinceName;
    private TextView carMovingCity;
    private EditText carMovingDetails;
    private ImageView carMovingUpload;
    private Button carMovingPost;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_car_moving, container, false);
        context = getContext();
        initView();
        initData();
        initEvent();
        return view;
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
                    arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list);
                    listView.setAdapter(arrayAdapter);
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
                    arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list);
                    listView.setAdapter(arrayAdapter);
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
                    arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list);
                    listView.setAdapter(arrayAdapter);
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

    private void initData() {
        listView = new ListView(context);
        dialog = new AlertDialog.Builder(context).setView(listView).create();
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    carMovingUpload.setImageBitmap(bitmap);
                }
            }
        });
        mySqlLite = MySqlLite.getMyInterface(context);
    }

    private void initEvent() {
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
        carMovingUpload.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
            activityResultLauncher.launch(intent);
        });
        carMovingPost.setOnClickListener(v -> {
            parkName = carMovingParkName.getText().toString();
            phone = carMovingPhone.getText().toString();
            if (parkName.equals("")) {
                Toast.makeText(context, "请输入车牌号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (phone.equals("")) {
                Toast.makeText(context, "请输入手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isSelected) {
                Toast.makeText(context, "请选择地址", Toast.LENGTH_SHORT).show();
                return;
            }
            insert();
            Toast.makeText(context, "提交成功", Toast.LENGTH_SHORT).show();
        });
    }

    private void insert() {
        SQLiteDatabase db = mySqlLite.getWritableDatabase();
        if (db.isOpen()) {
            String address = cardMovingProvince.getText().toString()
                    + cardMovingProvinceName.getText().toString()
                    + carMovingCity.getText().toString()
                    + carMovingDetails.getText().toString();
            ContentValues cv = new ContentValues();
            cv.put("parkname",parkName);
            cv.put("phone",phone);
            cv.put("address",address);
            db.insert("carmoving",null,cv);
            db.close();
            System.out.println("提交成功");
        }
    }

    private void initView() {
        carMovingParkName = view.findViewById(R.id.car_moving_parkName);
        carMovingName = view.findViewById(R.id.car_moving_name);
        carMovingPhone = view.findViewById(R.id.car_moving_phone);
        carMovingCardId = view.findViewById(R.id.car_moving_cardId);
        cardMovingProvince = view.findViewById(R.id.card_moving_province);
        cardMovingProvinceName = view.findViewById(R.id.card_moving_provinceName);
        carMovingCity = view.findViewById(R.id.car_moving_city);
        carMovingDetails = view.findViewById(R.id.car_moving_details);
        carMovingUpload = view.findViewById(R.id.car_moving_upload);
        carMovingPost = view.findViewById(R.id.car_moving_post);
    }
}