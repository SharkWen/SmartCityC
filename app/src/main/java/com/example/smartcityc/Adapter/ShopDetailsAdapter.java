package com.example.smartcityc.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcityc.Bean.ShopRightBean;
import com.example.smartcityc.R;
import com.example.smartcityc.Tool.Config;
import com.example.smartcityc.data.DataStore;

import org.w3c.dom.Text;

import java.util.List;

public class ShopDetailsAdapter extends RecyclerView.Adapter<ShopDetailsAdapter.ViewHolder> {
    Context context;
    List<ShopRightBean.DataDTO> list;
    TextView totalNum, totalPrices;
    DataStore data;

    public ShopDetailsAdapter(Context context, List<ShopRightBean.DataDTO> list, TextView totalNum, TextView totalPrices, DataStore data) {
        this.data = data;
        this.context = context;
        this.list = list;
        this.totalNum = totalNum;
        this.totalPrices = totalPrices;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.shop_details_right_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        update();
        int count = 0;
        if (data.getFoodData().get(list.get(position).getId()) == null) {
            count = 0;
        } else {
            count = data.getFoodData().get(list.get(position).getId()).getCount();
        }
        holder.outFoodTotals.setText(count + "");
        Glide.with(context).load(Config.baseUrl + list.get(position).getImgUrl()).into(holder.shopDetailsRightImage);
        holder.shopDetailsRightName.setText(list.get(position).getName());
        holder.shopDetailsRightScore.setText("￥" + list.get(position).getPrice() + "");
        holder.shopDetailsRightHpl.setText("好评率:" + list.get(position).getFavorRate() + "%");
        holder.shopDetailsRightYxl.setText("月销量:" + list.get(position).getSaleQuantity() + "单");
        holder.outFoodPlug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.getFoodData().get(list.get(position).getId()) == null) {
                    if (!data.keyList.contains(list.get(position).getId())) {
                        data.keyList.add(list.get(position).getId());
                    }
                    data.getFoodData().put(list.get(position).getId(), new DataStore.Food(0, list.get(position).getPrice(), Config.baseUrl + list.get(position).getImgUrl(), list.get(position).getName()));
                }
                data.getFoodData().get(list.get(position).getId()).setCount(data.getFoodData().get(list.get(position).getId()).getCount() + 1);
                holder.outFoodTotals.setText(data.getFoodData().get(list.get(position).getId()).getCount() + "");
                update();
            }
        });
        holder.outFoodReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.getFoodData().get(list.get(position).getId()) == null) {
                    if (!data.keyList.contains(list.get(position).getId())) {
                        data.keyList.add(list.get(position).getId());
                    }
                    data.getFoodData().put(list.get(position).getId(), new DataStore.Food(0, list.get(position).getPrice(), Config.baseUrl + list.get(position).getImgUrl(), list.get(position).getName()));
                }
                if (data.getFoodData().get(list.get(position).getId()).getCount() != 0) {
                    data.getFoodData().get(list.get(position).getId()).setCount(data.getFoodData().get(list.get(position).getId()).getCount() - 1);
                }
                holder.outFoodTotals.setText(data.getFoodData().get(list.get(position).getId()).getCount() + "");
                update();
            }
        });
    }

    private void update() {
        float allCount = 0;
        float allPrice = 0;
        for (Integer id : data.keyList) {
            allCount += data.getFoodData().get(id).getCount();
            allPrice += data.getFoodData().get(id).getPrice() * data.getFoodData().get(id).getCount();
        }
        totalNum.setText(allCount + "");
        totalPrices.setText(allPrice + "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView shopDetailsRightImage;
        private TextView shopDetailsRightName;
        private TextView shopDetailsRightScore;
        private ImageView outFoodReduce;
        private TextView outFoodTotals;
        private ImageView outFoodPlug;
        private TextView shopDetailsRightHpl;
        private TextView shopDetailsRightYxl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopDetailsRightImage = itemView.findViewById(R.id.shop_details_right_image);
            shopDetailsRightName = itemView.findViewById(R.id.shop_details_right_name);
            shopDetailsRightScore = itemView.findViewById(R.id.shop_details_right_score);
            outFoodReduce = itemView.findViewById(R.id.out_food_reduce);
            outFoodTotals = itemView.findViewById(R.id.out_food_totals);
            outFoodPlug = itemView.findViewById(R.id.out_food_plug);
            shopDetailsRightHpl = itemView.findViewById(R.id.shop_details_right_hpl);
            shopDetailsRightYxl = itemView.findViewById(R.id.shop_details_right_yxl);
        }
    }
}
