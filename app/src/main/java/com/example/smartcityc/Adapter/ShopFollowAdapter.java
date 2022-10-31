package com.example.smartcityc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.R;
import com.example.smartcityc.ShopDetailsActivity;
import com.example.smartcityc.data.ShopStore;

import java.time.chrono.IsoChronology;
import java.util.List;
import java.util.Map;

public class ShopFollowAdapter extends RecyclerView.Adapter<ShopFollowAdapter.MyHolder> {
    public ShopFollowAdapter(Context context, Map<Integer,ShopStore.ShopDetails> map, List<String> ids) {
        this.context = context;
        this.map = map;
        this.ids = ids;
    }

    Context context;
    Map<Integer,ShopStore.ShopDetails> map;
    List<String> ids;
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.shop_follow_item,null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if(map==null) return;
        ShopStore.ShopDetails data = map.getOrDefault(Integer.parseInt(ids.get(position)),new ShopStore.ShopDetails());
        Glide.with(context).load(data.getUrl()).apply(new RequestOptions().transform(new RoundedCorners(20),new CenterCrop())).into(holder.shopFollowImage);
        holder.shopFollowName.setText(data.getName());
        holder.shopFollowScore.setRating(Float.parseFloat(data.getScore()));
        holder.shopFollowMouthSell.setText("月销量:"+data.getMouthSells());
        holder.shopFollowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopDetailsActivity.sellerId = ids.get(position);
                context.startActivity(new Intent(context, ShopDetailsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return map.keySet().size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        private ImageView shopFollowImage;
        private TextView shopFollowName;
        private RatingBar shopFollowScore;
        private TextView shopFollowMouthSell;
        private LinearLayout shopFollowLayout;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            shopFollowImage = itemView.findViewById(R.id.shop_follow_image);
            shopFollowName = itemView.findViewById(R.id.shop_follow_name);
            shopFollowScore = itemView.findViewById(R.id.shop_follow_score);
            shopFollowMouthSell = itemView.findViewById(R.id.shop_follow_mouthSell);
            shopFollowLayout = itemView.findViewById(R.id.shop_follow_layout);
        }
    }
}
