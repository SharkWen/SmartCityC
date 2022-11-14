package com.example.smartcityc.CardFragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.smartcityc.R;
import com.example.smartcityc.data.MySqlLite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarMovingHistoryFragment extends Fragment {
    View view;
    Context context;
    SimpleAdapter simpleAdapter;
    List<Map<String, Object>> list = new ArrayList<>();
    Map<String, Object> map;
    private ListView carMovingHistoryListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_car_moving_history, container, false);
        context = getContext();
        initView();
        initData();
        return view;
    }
    private void initData() {
        SQLiteDatabase db =  MySqlLite.getMyInterface(context).getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from carmoving", null);
            while (cursor.moveToNext()) {
                int n = cursor.getColumnIndex("parkname");
                int p = cursor.getColumnIndex("phone");
                int a = cursor.getColumnIndex("address");
                map = new HashMap<>();
                map.put("name","车牌号: "+ cursor.getString(n));
                map.put("phone","手机号: "+ cursor.getString(p));
                map.put("address","地   址:  "+ cursor.getString(a));
                list.add(map);
            }
            cursor.close();
        }
        db.close();
        simpleAdapter = new SimpleAdapter(context,list,R.layout.car_moving_item,
                new String[]{"name","phone","address"},new int[]{R.id.car_park_name,R.id.car_phone,R.id.car_address});
        carMovingHistoryListView.setAdapter(simpleAdapter);
    }

    private void initView() {
        carMovingHistoryListView = view.findViewById(R.id.car_moving_history_list_view);
    }
}