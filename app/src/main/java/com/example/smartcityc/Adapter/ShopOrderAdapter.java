package com.example.smartcityc.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcityc.Bean.OrderListBean;
import com.example.smartcityc.R;
import com.example.smartcityc.Tool.Config;

import java.util.List;

public class ShopOrderAdapter extends RecyclerView.Adapter<ShopOrderAdapter.MyHolder> {
    Context context;
    List<OrderListBean.RowsDTO.OrderInfoDTO.OrderItemListDTO> list;

    public ShopOrderAdapter(Context context, List<OrderListBean.RowsDTO.OrderInfoDTO.OrderItemListDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(View.inflate(context, R.layout.orde_item_list, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Glide.with(context).load(Config.baseUrl + list.get(position).getProductImage()).into(holder.orderItemListImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        private ImageView orderItemListImage;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            orderItemListImage = itemView.findViewById(R.id.order_item_list_image);
        }
    }
}
