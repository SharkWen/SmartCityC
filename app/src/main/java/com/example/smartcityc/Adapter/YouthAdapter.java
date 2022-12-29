package com.example.smartcityc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Bean.YouthBean;
import com.example.smartcityc.R;
import com.example.smartcityc.Tool.Config;
import com.example.smartcityc.YouthDetails;

import java.util.List;

public class YouthAdapter extends RecyclerView.Adapter<YouthAdapter.MyViewHolder> {
    Context context;
    List<YouthBean.Data> dataList;

    public YouthAdapter(Context context, List<YouthBean.Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.home_frag_gl,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(Config.baseUrl + dataList.get(position).getImgUrl())
                .apply(new RequestOptions().transform(new CenterCrop(),new RoundedCorners(20)))
                .into(holder.homeFragIv);
        holder.homeFragTv.setText(dataList.get(position).getName());
        holder.homeFragIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, YouthDetails.class);
                intent.putExtra("id",dataList.get(position).getId()+"");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView homeFragIv;
        private TextView homeFragTv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            homeFragIv = itemView.findViewById(R.id.home_frag_iv);
            homeFragTv = itemView.findViewById(R.id.home_frag_tv);
        }
    }
}
