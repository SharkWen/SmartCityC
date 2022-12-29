package com.example.smartcityc.ServerHot;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartcityc.R;

import java.util.List;

public class HotlineServiceAdapter extends RecyclerView.Adapter<HotlineServiceAdapter.HotlineServiceAdapterViewHolder>{
    Context context;
    List<HotlineServiceBean.RowsDTO> data;

    public HotlineServiceAdapter(Context context, List<HotlineServiceBean.RowsDTO> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public HotlineServiceAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.hotline_service_item,null);
        view.setLayoutParams(new ViewGroup.LayoutParams(270, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new HotlineServiceAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotlineServiceAdapterViewHolder holder, int position) {
        holder.text.setText(data.get(position).getName());
        new Glide().setUrl(Config.address+data.get(position).getImgUrl()).setImageView(holder.image).load();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(data.get(position).getName().equals("其他诉求")){
                    intent = new Intent(context, AddAppeal.class);
                }else {
                    intent = new Intent(context, HotlineAppeal.class);
                    intent.putExtra("id", data.get(position).getId());
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("title", data.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class HotlineServiceAdapterViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView text;
        public HotlineServiceAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.service_item_image);
            text = itemView.findViewById(R.id.service_item_name);
        }
    }
}
