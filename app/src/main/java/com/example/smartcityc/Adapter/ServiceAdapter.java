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
import com.example.smartcityc.R;
import com.example.smartcityc.Tool.Config;
import com.example.smartcityc.Tool.Tool;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    Context context;
    List<ServiceBean.RowsDTO> list;
    int[] ints = new int[]{R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e};
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
        if(position==9){
            holder.imageView.setImageResource(R.drawable.gengduo);
            holder.imageView.setOnClickListener(view -> {
                MainActivity.back = "service";
                context.startActivity(new Intent(context,MainActivity.class));
            });
            holder.textView.setText("全部服务");
            return;
        }
        if(position<=4){
            holder.imageView.setImageResource(ints[position]);
        }else {
            Glide.with(context).load(Config.baseUrl+list.get(position).getImgUrl()).into(holder.imageView);
        }
        holder.textView.setText(list.get(position).getServiceName());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,FindHourseActivity.class));
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
