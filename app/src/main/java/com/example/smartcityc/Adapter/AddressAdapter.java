package com.example.smartcityc.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcityc.Bean.AddressBean;
import com.example.smartcityc.Bean.OrderSureBean;
import com.example.smartcityc.R;
import com.example.smartcityc.Tool.Tool;

import java.util.ArrayList;
import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {

    Context context;
    List<AddressBean.DataDTO> list;
    TextView textView;
    List<TextView> textViewList = new ArrayList<>();
    OrderSureBean orderSureBean;

    public AddressAdapter(Context context, List<AddressBean.DataDTO> list, TextView textView,OrderSureBean orderSureBean) {
        this.context = context;
        this.list = list;
        this.textView = textView;
        this.orderSureBean = orderSureBean;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.address_item, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.addressItemAddress.setText("收货地址:" + list.get(position).getAddressDetail() + list.get(position).getLabel());
        holder.addressItemRelationship.setText("联系人:" + list.get(position).getName());
        holder.addressItemRelationshipTel.setText("联系电话:" + list.get(position).getPhone());
        holder.addressItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!textViewList.isEmpty()) {
                    for (TextView t : textViewList) {
                        t.setTextColor(Color.BLACK);
                    }
                }

                textView.setText(
                        "收货地址:" + list.get(position).getAddressDetail() + list.get(position).getLabel() + "\n" +
                                "联系人:" + list.get(position).getName() + "\n" +
                                "联系电话:" + list.get(position).getPhone()
                );
                orderSureBean.setAddressDetail(list.get(position).getAddressDetail());
                orderSureBean.setLabel(list.get(position).getLabel());
                orderSureBean.setName(list.get(position).getName());
                orderSureBean.setPhone(list.get(position).getPhone());
                holder.addressItemAddress.setTextColor(Color.RED);
                textViewList.add(holder.addressItemAddress);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list == null){
            Toast.makeText(context,"你还没有收货地址哦!",Toast.LENGTH_SHORT).show();
            return 0;
        }
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView addressItemAddress;
        private TextView addressItemRelationship;
        private TextView addressItemRelationshipTel;
        private LinearLayout addressItemLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            addressItemAddress = itemView.findViewById(R.id.address_item_address);
            addressItemRelationship = itemView.findViewById(R.id.address_item_relationship);
            addressItemRelationshipTel = itemView.findViewById(R.id.address_item_relationshipTel);
            addressItemLayout = itemView.findViewById(R.id.address_item_layout);
        }
    }
}
