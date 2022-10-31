package com.example.smartcityc.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartcityc.Adapter.MyViewPage2Adapter;
import com.example.smartcityc.OrderFragment.OrderAllFragment;
import com.example.smartcityc.OrderFragment.RefundFragment;
import com.example.smartcityc.OrderFragment.ToBeEvaluatedFragment;
import com.example.smartcityc.OrderFragment.ToBePaidFragment;
import com.example.smartcityc.R;
import com.example.smartcityc.Tool.Tool;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;


public class OutFoodOrderFragment extends Fragment {
    Context context;
    View view;
    private TabLayout orderFragTabLayout;
    private ViewPager2 orderFragViewPager2;
    List<Fragment> fragmentList = new ArrayList<>();
    List<String> stringList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_out_food_order, container, false);
        context = getContext();
        bindView();
        initData();
        return view;
    }

    private void initData() {
        if(!Tool.shp(context).contains("token")){
            Tool.setDialog(context,"请注册登录").show();
            return;
        }
        fragmentList.add(new OrderAllFragment());
        fragmentList.add(new ToBePaidFragment());
        fragmentList.add(new ToBeEvaluatedFragment());
        fragmentList.add(new RefundFragment());

        stringList.add("全部");
        stringList.add("待支付");
        stringList.add("待评价");
        stringList.add("退款");
        MyViewPage2Adapter viewPage2Adapter = new MyViewPage2Adapter((FragmentActivity) context, fragmentList);
        orderFragViewPager2.setAdapter(viewPage2Adapter);
        new TabLayoutMediator(orderFragTabLayout, orderFragViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(stringList.get(position));
            }
        }).attach();
    }

    private void bindView() {
        orderFragTabLayout = view.findViewById(R.id.order_frag_tabLayout);
        orderFragViewPager2 = view.findViewById(R.id.order_Frag_viewPager2);
    }
}