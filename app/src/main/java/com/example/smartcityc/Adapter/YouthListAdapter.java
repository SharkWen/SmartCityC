package com.example.smartcityc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Bean.YouthListBean;
import com.example.smartcityc.R;
import com.example.smartcityc.Tool.Config;
import com.example.smartcityc.YouthDetails;

import java.util.List;

public class YouthListAdapter extends RecyclerView.Adapter<YouthListAdapter.MyViewHolder> {
    Context context;
    List<YouthListBean.RowsDTO> list;
    Boolean isOpen = false;
    public YouthListAdapter(Context context, List<YouthListBean.RowsDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.youth_list_item,null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(Config.baseUrl + list.get(position).getCoverImgUrl())
                .apply(new RequestOptions().transform(new CenterCrop()))
                .into(holder.youthImage);
        holder.youthTitle.setText(list.get(position).getName());
        holder.youthBoy.setText("剩余床位: 男:"+list.get(position).getBedsCountBoy()+"/女:"+list.get(position).getBedsCountGirl());
        holder.youthAddress.setText("地址:"+list.get(position).getAddress());
        holder.youthDetails.setText("详情:"+list.get(position).getIntroduce());
        holder.youthDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpen || holder.youthDetails.getMaxLines()>2){
                    isOpen = false;
                    holder.youthJc.setVisibility(View.VISIBLE);
                    holder.youthDetails.setMaxLines(1);
                    holder.youthDetails.setEllipsize(TextUtils.TruncateAt.END);
                }else {

                }

            }
        });
        holder.youthJc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isOpen = true;
                holder.youthJc.setVisibility(View.GONE);
                holder.youthDetails.setMaxLines(10);
                holder.youthDetails.setEllipsize(null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView youthImage;
        private TextView youthTitle;
        private TextView youthBoy;
        private TextView youthAddress;
        private TextView youthDetails;
        private TextView youthJc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            youthImage = itemView.findViewById(R.id.youth_image);
            youthTitle = itemView.findViewById(R.id.youth_title);
            youthBoy = itemView.findViewById(R.id.youth_boy);
            youthAddress = itemView.findViewById(R.id.youth_address);
            youthDetails = itemView.findViewById(R.id.youth_details);
            youthJc = itemView.findViewById(R.id.youth_jc);
        }
    }
}
