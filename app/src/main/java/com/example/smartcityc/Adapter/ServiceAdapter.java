package com.example.smartcityc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcityc.Bean.ServiceBean;
import com.example.smartcityc.FindHourseActivity;
import com.example.smartcityc.MainActivity;
import com.example.smartcityc.MengActivity;
import com.example.smartcityc.ParkingLotActivity;
import com.example.smartcityc.R;
import com.example.smartcityc.ServerHot.HotlineAppeal;
import com.example.smartcityc.ServerHot.hotline;
import com.example.smartcityc.SmartBusActivity;
import com.example.smartcityc.Tool.Config;
import com.example.smartcityc.Tool.Tool;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    Context context;
    List<ServiceBean.RowsDTO> list;

    public ServiceAdapter(Context context, List<ServiceBean.RowsDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.home_frag_gl, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 9) {
            holder.imageView.setImageResource(R.drawable.gengduo);
            holder.imageView.setOnClickListener(view -> {
                MainActivity.back = "service";
                context.startActivity(new Intent(context, MainActivity.class));
            });
            holder.textView.setText("全部服务");
            return;
        }

        Glide.with(context).load(Config.baseUrl + list.get(position).getImgUrl()).into(holder.imageView);

        holder.textView.setText(list.get(position).getServiceName());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (list.get(position).getServiceName()){
                    case "政府服务热线":
                        context.startActivity(new Intent(context, hotline.class));
                        break;
                    case "停哪儿":
                        context.startActivity(new Intent(context, ParkingLotActivity.class));
                        break;
                    case "智慧巴士":
                        context.startActivity(new Intent(context, SmartBusActivity.class));
                        break;
                    case "门诊预约":
                        context.startActivity(new Intent(context, MengActivity.class));
                        break;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.home_frag_iv);
            textView = itemView.findViewById(R.id.home_frag_tv);
        }
    }
}
