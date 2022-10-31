package com.example.smartcityc.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Bean.ShopPjBean;
import com.example.smartcityc.R;
import com.example.smartcityc.Tool.Config;
import com.google.gson.Gson;

import java.util.List;

public class ShopPjAdapter extends RecyclerView.Adapter<ShopPjAdapter.MyViewHolder> {
    public ShopPjAdapter(Context context, List<ShopPjBean.RowsDTO> list) {
        this.context = context;
        this.list = list;
    }

    Context context;
    List<ShopPjBean.RowsDTO> list;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.shop_detail_pj_item, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        System.out.println(Config.baseUrl + list.get(position).getAvatar());
        if (list.get(position).getAvatar().contains("https")) {
            Glide.with(context).load(list.get(position).getAvatar()).apply(new RequestOptions().transform(new RoundedCorners(200))).into(holder.shopPjImage);
        } else {
            Glide.with(context).load(Config.baseUrl + list.get(position).getAvatar()).apply(new RequestOptions().transform(new RoundedCorners(200))).into(holder.shopPjImage);
        }
        holder.shopPjNick.setText(list.get(position).getNickName());
        holder.shopPjRating.setRating(list.get(position).getScore());
        holder.shopPjContent.setText(list.get(position).getContent());
        holder.shopPjTime.setText(list.get(position).getCommentDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView shopPjImage;
        private TextView shopPjNick;
        private RatingBar shopPjRating;
        private TextView shopPjContent;
        private TextView shopPjTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            shopPjImage = itemView.findViewById(R.id.shop_pj_image);
            shopPjNick = itemView.findViewById(R.id.shop_pj_nick);
            shopPjRating = itemView.findViewById(R.id.shop_pj_rating);
            shopPjContent = itemView.findViewById(R.id.shop_pj_content);
            shopPjTime = itemView.findViewById(R.id.shop_pj_time);
        }
    }
}
