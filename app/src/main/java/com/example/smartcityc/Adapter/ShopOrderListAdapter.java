package com.example.smartcityc.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Bean.OrderListBean;
import com.example.smartcityc.Bean.OutFoodIconBean;
import com.example.smartcityc.Bean.OutFoodTjBean;
import com.example.smartcityc.R;
import com.example.smartcityc.Tool.Config;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.interfaces.RSAKey;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShopOrderListAdapter extends RecyclerView.Adapter<ShopOrderListAdapter.MyHolder> {
    Context context;
    List<OrderListBean.RowsDTO> list;

    public ShopOrderListAdapter(Context context, List<OrderListBean.RowsDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.order_list_item, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Glide.with(context).load(Config.baseUrl + list.get(position).getSellerInfo().getImgUrl()).apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(20))).into(holder.shopOrderLogoType);
        holder.shopOrderTitle.setText(list.get(position).getSellerInfo().getName());
        holder.shopOrderState.setText(list.get(position).getOrderInfo().getStatus());
        holder.shopOrderPriceTotal.setText("￥" + list.get(position).getOrderInfo().getAmount());
        holder.shopOrderTime.setText(list.get(position).getOrderInfo().getCreateTime());
        holder.shopOrderPayType.setText(list.get(position).getOrderInfo().getPaymentType() == null ? "" : list.get(position).getOrderInfo().getPaymentType());
        ShopOrderAdapter shopOrderAdapter = new ShopOrderAdapter(context,list.get(position).getOrderInfo().getOrderItemList());
        holder.shopOrderLogo.setAdapter(shopOrderAdapter);
        holder.shopOrderLogo.setLayoutManager(new GridLayoutManager(context,3));
        switch (list.get(position).getOrderInfo().getStatus()){
            case "待评价":
                holder.shopOrderBtn1.setText("评价");
                holder.shopOrderBtn2.setVisibility(View.VISIBLE);
                holder.shopOrderBtn2.setText("退款");
                break;
            case "待支付":
                holder.shopOrderBtn1.setText("支付");
                holder.shopOrderBtn2.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private ImageView shopOrderLogoType;
        private TextView shopOrderTitle;
        private TextView shopOrderState;
        private RecyclerView shopOrderLogo;
        private TextView shopOrderPriceTotal;
        private TextView shopOrderTime;
        private TextView shopOrderPayType;
        private Button shopOrderBtn1;
        private Button shopOrderBtn2;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            shopOrderLogoType = itemView.findViewById(R.id.shop_order_logoType);
            shopOrderTitle = itemView.findViewById(R.id.shop_order_title);
            shopOrderState = itemView.findViewById(R.id.shop_order_state);
            shopOrderLogo = itemView.findViewById(R.id.shop_order_logo);
            shopOrderPriceTotal = itemView.findViewById(R.id.shop_order_priceTotal);
            shopOrderTime = itemView.findViewById(R.id.shop_order_time);
            shopOrderPayType = itemView.findViewById(R.id.shop_order_payType);
            shopOrderBtn1 = itemView.findViewById(R.id.shop_order_btn1);
            shopOrderBtn2 =itemView.findViewById(R.id.shop_order_btn2);
        }
    }
}
