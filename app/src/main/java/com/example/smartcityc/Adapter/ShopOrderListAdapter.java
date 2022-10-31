package com.example.smartcityc.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcityc.Bean.MsgBean;
import com.example.smartcityc.Bean.OrderListBean;
import com.example.smartcityc.Bean.OutFoodIconBean;
import com.example.smartcityc.Bean.OutFoodTjBean;
import com.example.smartcityc.Bean.ShopDetailBean;
import com.example.smartcityc.OrderPayActivity;
import com.example.smartcityc.R;
import com.example.smartcityc.ShopDetailsActivity;
import com.example.smartcityc.TakeOutFoodActivity;
import com.example.smartcityc.Tool.Config;
import com.example.smartcityc.Tool.Tool;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.interfaces.RSAKey;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShopOrderListAdapter extends RecyclerView.Adapter<ShopOrderListAdapter.MyHolder> {
    Context context;
    List<OrderListBean.RowsDTO> list;

    public ShopOrderListAdapter(Context context, List<OrderListBean.RowsDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.order_list_item, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Glide.with(context).load(Config.baseUrl + list.get(position).getSellerInfo().getImgUrl()).apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(20))).into(holder.shopOrderLogoType);
        holder.shopOrderTitle.setText(list.get(position).getSellerInfo().getName());
        holder.shopOrderState.setText(list.get(position).getOrderInfo().getStatus());
        holder.shopOrderPriceTotal.setText("￥" + list.get(position).getOrderInfo().getAmount());
        holder.shopOrderTime.setText(list.get(position).getOrderInfo().getCreateTime());
        holder.shopOrderPayType.setText(list.get(position).getOrderInfo().getPaymentType() == null ? "" : list.get(position).getOrderInfo().getPaymentType());
        ShopOrderAdapter shopOrderAdapter = new ShopOrderAdapter(context, list.get(position).getOrderInfo().getOrderItemList());
        holder.shopOrderLogo.setAdapter(shopOrderAdapter);
        holder.shopOrderLogo.setLayoutManager(new GridLayoutManager(context, 3));
        switch (list.get(position).getOrderInfo().getStatus()) {
            case "待评价":
                holder.shopOrderBtn1.setText("评价");
                holder.shopOrderBtn1.setBackgroundResource(R.drawable.btn_public_r);
                holder.shopOrderBtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View view1 = View.inflate(context,R.layout.evaluate_item,null);
                        RatingBar ratingBar = view1.findViewById(R.id.evaluate_ratingBar);
                        EditText editText = view1.findViewById(R.id.evaluate_editText);
                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                                ratingBar.setRating(v);
                            }
                        });
                        new AlertDialog.Builder(context).setTitle("评价").setView(view1)
                                .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String data = "{\n" +
                                                "\"content\": \""+editText.getText().toString()+"\",\n" +
                                                "\"orderNo\": \""+list.get(position).getOrderInfo().getOrderNo()+"\",\n" +
                                                "\"score\": "+ratingBar.getRating()+"\n" +
                                                "}";
                                        System.out.println(data);
                                        Tool.postTokenData("/prod-api/api/takeout/comment", Tool.sp(context, "token"), data, new Callback() {
                                            @Override
                                            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                            }

                                            @Override
                                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                                MsgBean msgBean = new Gson().fromJson(response.body().string(), MsgBean.class);
                                                if (msgBean.getCode() == 200) {
                                                    Tool.handler.post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Tool.setDialog(context, "评价成功").show();
                                                            Tool.handler.postDelayed(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    Intent intent  = new Intent(context, TakeOutFoodActivity.class);
                                                                    intent.putExtra("back","order");
                                                                    context.startActivity(intent);
                                                                }
                                                            }, 1000);
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .create().show();
                    }
                });
                holder.shopOrderBtn2.setVisibility(View.VISIBLE);
                holder.shopOrderBtn2.setText("退款");
                holder.shopOrderBtn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText editText = new EditText(context);
                        editText.setHint("请输入退款理由");
                        editText.setTextSize(13);
                        new AlertDialog.Builder(context).setView(editText).setTitle("是否退款")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String data = "{\n" +
                                                "\"orderNo\": \"" + list.get(position).getOrderInfo().getOrderNo() + "\",\n" +
                                                "\"reason\": \"" + editText.getText().toString() + "\"\n" +
                                                "}";
                                        System.out.println(data);
                                        Tool.postTokenData("/prod-api/api/takeout/order/refound", Tool.sp(context, "token"), data, new Callback() {
                                            @Override
                                            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                                            }

                                            @Override
                                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                                MsgBean msgBean = new Gson().fromJson(response.body().string(), MsgBean.class);
                                                if (msgBean.getCode() == 200) {
                                                    Tool.handler.post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Tool.setDialog(context, "退款成功").show();
                                                            Tool.handler.postDelayed(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    Intent intent  = new Intent(context, TakeOutFoodActivity.class);
                                                                    intent.putExtra("back","order");
                                                                    context.startActivity(intent);
                                                                }
                                                            }, 1000);
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .create().show();
                    }
                });
                break;
            case "完成":
                holder.shopOrderBtn1.setText("再来一单");
                holder.shopOrderBtn2.setVisibility(View.GONE);
                holder.shopOrderBtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ShopDetailsActivity.sellerId = list.get(position).getOrderInfo().getSellerId()+"";
                        Tool.getData("/prod-api/api/takeout/seller/" + list.get(position).getOrderInfo().getSellerId(), new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                ShopDetailBean shopDetailBean = new Gson().fromJson(response.body().string(),ShopDetailBean.class);
                                ShopDetailsActivity.image = Config.baseUrl + shopDetailBean.getData().getImgUrl();
                                ShopDetailsActivity.nameD = shopDetailBean.getData().getName();
                                ShopDetailsActivity.timeD = shopDetailBean.getData().getDeliveryTime() + "";
                                ShopDetailsActivity.distance = shopDetailBean.getData().getDistance() + "";
                                ShopDetailsActivity.score = shopDetailBean.getData().getScore() + "";
                                context.startActivity(new Intent(context,ShopDetailsActivity.class));
                            }
                        });
                    }
                });
                break;
            case "待支付":
                holder.shopOrderBtn1.setText("支付");
                holder.shopOrderBtn1.setBackgroundResource(R.drawable.btn_public_r);
                holder.shopOrderBtn2.setVisibility(View.GONE);
                holder.shopOrderBtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (holder.shopOrderBtn1.getText().toString().equals("评价")|| holder.shopOrderBtn1.getText().toString().equals("再来一单") || holder.shopOrderBtn1.getText().toString().equals("已退款"))
                            return;
                        Intent intent = new Intent(context, OrderPayActivity.class);
                        intent.putExtra("orderNo", list.get(position).getOrderInfo().getOrderNo());
                        intent.putExtra("total", list.get(position).getOrderInfo().getAmount() + "");
                        context.startActivity(intent);
                    }
                });
                break;
            case "退款":
                holder.shopOrderBtn1.setText("已退款");
                holder.shopOrderBtn1.setBackgroundResource(R.color.gray);
                holder.shopOrderBtn2.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private ImageView shopOrderLogoType;
        private TextView shopOrderTitle;
        private TextView shopOrderState;
        private RecyclerView shopOrderLogo;
        private TextView shopOrderPriceTotal;
        private TextView shopOrderTime;
        private TextView shopOrderPayType;
        private Button shopOrderBtn1;
        private Button shopOrderBtn2;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            shopOrderLogoType = itemView.findViewById(R.id.shop_order_logoType);
            shopOrderTitle = itemView.findViewById(R.id.shop_order_title);
            shopOrderState = itemView.findViewById(R.id.shop_order_state);
            shopOrderLogo = itemView.findViewById(R.id.shop_order_logo);
            shopOrderPriceTotal = itemView.findViewById(R.id.shop_order_priceTotal);
            shopOrderTime = itemView.findViewById(R.id.shop_order_time);
            shopOrderPayType = itemView.findViewById(R.id.shop_order_payType);
            shopOrderBtn1 = itemView.findViewById(R.id.shop_order_btn1);
            shopOrderBtn2 = itemView.findViewById(R.id.shop_order_btn2);
        }
    }
}
