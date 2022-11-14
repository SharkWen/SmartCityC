package com.example.smartcityc.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Bean.OrderSureBean;
import com.example.smartcityc.R;
import com.example.smartcityc.Tool.Config;
import com.example.smartcityc.data.DataStore;

import java.util.HashMap;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyHolder> {
    Context context;
    DataStore dataStore;
    List<OrderSureBean.OrderItemListDTO> listDTOS;
    public OrderAdapter(Context context, DataStore dataStore,List<OrderSureBean.OrderItemListDTO> listDTOS) {
        this.context = context;
        this.dataStore = dataStore;
        this.listDTOS = listDTOS;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.order_item, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if(dataStore.getFoodData().get(dataStore.keyList.get(position)).getCount()==0){
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(0,0));
            return;
        }
        Glide.with(context).load(dataStore.getFoodData().get(dataStore.keyList.get(position)).getUrl()).apply(new RequestOptions().transform(new CenterCrop())).into(holder.orderImage);
        holder.orderNum.setText("X" + dataStore.getFoodData().get(dataStore.keyList.get(position)).getCount() + "");
        float total = (float) (dataStore.getFoodData().get(dataStore.keyList.get(position)).getCount() * dataStore.getFoodData().get(dataStore.keyList.get(position)).getPrice());
        holder.orderPrice.setText("￥" + total);
        holder.orderName.setText(dataStore.getFoodData().get(dataStore.keyList.get(position)).getName());

        OrderSureBean.OrderItemListDTO dto = new OrderSureBean.OrderItemListDTO();
        dto.setProductId(dataStore.keyList.get(position));
        dto.setQuantity(dataStore.getFoodData().get(dataStore.keyList.get(position)).getCount());
        listDTOS.add(dto);
    }

    @Override
    public int getItemCount() {
        return dataStore.getFoodData().size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView orderName;
        private ImageView orderImage;
        private TextView orderNum;
        private TextView orderPrice;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            orderName = itemView.findViewById(R.id.order_name);
            orderImage = itemView.findViewById(R.id.order_image);
            orderNum = itemView.findViewById(R.id.order_num);
            orderPrice = itemView.findViewById(R.id.order_price);
        }
    }
}
