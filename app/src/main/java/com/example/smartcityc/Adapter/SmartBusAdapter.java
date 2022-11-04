package com.example.smartcityc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.smartcityc.Bean.SmartBusBean;
import com.example.smartcityc.R;
import com.example.smartcityc.SmartLineActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmartBusAdapter {
    public static void setListView(Context context, SmartBusBean smartBusBean, ListView listView) {
        List<Map<String,Object>> list = new ArrayList<>();
        String[] strings = new String[]{"busLines","busFirst","busEnd","busStartTime","busMileage","busPrice"};
        int[] ints = new int[]{R.id.bus_lines,R.id.bus_first,R.id.bus_end,R.id.bus_startTime,R.id.bus_mileage,R.id.bus_price};
        for(int i = 0; i <smartBusBean.getRows().size();i++){
            Map<String, Object> map = new HashMap<>();
            map.put("busLines", smartBusBean.getRows().get(i).getName());
            map.put("busFirst","起点:  "+smartBusBean.getRows().get(i).getFirst());
            map.put("busEnd","终点:  "+smartBusBean.getRows().get(i).getEnd());
            map.put("busStartTime","运行时间:  "+smartBusBean.getRows().get(i).getStartTime());
            map.put("busMileage","里程:  "+smartBusBean.getRows().get(i).getMileage());
            map.put("busPrice","￥"+smartBusBean.getRows().get(i).getPrice());
            list.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(context, list, R.layout.smart_bus_list,strings,ints);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SmartLineActivity.bus_line = smartBusBean.getRows().get(i).getName();
                SmartLineActivity.bus_first = "起点:  "+smartBusBean.getRows().get(i).getFirst();
                SmartLineActivity.bus_end = "终点:  " + smartBusBean.getRows().get(i).getEnd();
                SmartLineActivity.bus_price = "票价:  ￥"+smartBusBean.getRows().get(i).getPrice();
                SmartLineActivity.bus_mileage = "里程:  " + smartBusBean.getRows().get(i).getMileage();
                context.startActivity(new Intent(context, SmartLineActivity.class));
            }
        });
    }
}
