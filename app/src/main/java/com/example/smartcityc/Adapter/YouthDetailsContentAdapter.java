package com.example.smartcityc.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcityc.Bean.YouthDetailsContentBean;
import com.example.smartcityc.R;

import java.util.List;

public class YouthDetailsContentAdapter extends RecyclerView.Adapter<YouthDetailsContentAdapter.MyViewHolder> {
    Context context;
    List<YouthDetailsContentBean.DataDTO> list;

    public YouthDetailsContentAdapter(Context context, List<YouthDetailsContentBean.DataDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.youth_details_content_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.youthContentTitle.setText(list.get(position).getContent());
        holder.youthContentTime.setText(list.get(position).getCreateTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView youthContentTitle;
        private TextView youthContentTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            youthContentTitle = itemView.findViewById(R.id.youth_content_title);
            youthContentTime = itemView.findViewById(R.id.youth_content_time);
        }
    }
}
