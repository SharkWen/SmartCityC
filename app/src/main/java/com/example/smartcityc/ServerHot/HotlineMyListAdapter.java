package com.example.smartcityc.ServerHot;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.smartcityc.R;

import java.util.List;

public class HotlineMyListAdapter extends RecyclerView.Adapter<HotlineMyListAdapter.MyListViewHolder> {
    Context context;
    List<HotlineMyListBean.RowsDTO> data;

    public HotlineMyListAdapter(Context context, List<HotlineMyListBean.RowsDTO> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.hotline_my_list_item, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new MyListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyListViewHolder holder, int position) {
        holder.hotlineMyListItemTitle.setText(data.get(position).getTitle());
        holder.hotlineMyListItemDanwei.setText("处理单位:"+data.get(position).getUndertaker());
        holder.hotlineMyListItemTime.setText("创建时间:"+data.get(position).getCreateTime());
        if(data.get(position).getState() == null){
            holder.hotlineMyListItemStatus.setText("状态:未完成");
            return;
        }
        holder.hotlineMyListItemStatus.setText("状态:"+(data.get(position).getState().equals("0")?"未完成":"已完成"));
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AppealInfo.class);
                intent.putExtra("title", data.get(position).getTitle());
                intent.putExtra("content", data.get(position).getContent());
                intent.putExtra("time", data.get(position).getCreateTime());
                intent.putExtra("danwei", data.get(position).getUndertaker());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class MyListViewHolder extends RecyclerView.ViewHolder{
        TextView hotlineMyListItemTitle;
        TextView hotlineMyListItemDanwei;
        TextView hotlineMyListItemTime;
        TextView hotlineMyListItemStatus;
        public MyListViewHolder(@NonNull View itemView) {
            super(itemView);
            hotlineMyListItemTitle = (TextView) itemView.findViewById(R.id.hotline_my_list_item_title);
            hotlineMyListItemDanwei = (TextView) itemView.findViewById(R.id.hotline_my_list_item_danwei);
            hotlineMyListItemTime = (TextView) itemView.findViewById(R.id.hotline_my_list_item_time);
            hotlineMyListItemStatus = (TextView) itemView.findViewById(R.id.hotline_my_list_item_status);

        }
    }
}
