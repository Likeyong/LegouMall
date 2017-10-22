package com.example.maxcion_home.jdmall.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.bean.RorderList;
import com.example.maxcion_home.jdmall.cons.NetWorkCons;
import com.example.maxcion_home.jdmall.interfaces.IOrderItemClickListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maxcion_home on 2017/10/14.
 */

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderViewHolder> {


    private Context mContext;
    private List<RorderList> mDatas;
    private IOrderItemClickListener listener;


    public OrderItemAdapter(Context context) {
        mContext = context;
    }

    public void setDates(List<RorderList> datas) {
        mDatas = datas;
    }

    public void setBtnClickListener(IOrderItemClickListener listener){this.listener=listener;}

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext,R.layout.order_list_item,null);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, int position) {
        RorderList rorderList = mDatas.get(position);
        List<String> imgUrls = JSON.parseArray(rorderList.items, String.class);
        holder.orderNoTv.setText("订单编号:"+rorderList.orderNum);
        holder.priceTv.setText("总价:"+rorderList.totalPrice);
        holder.doBtn.setVisibility(View.VISIBLE);
        switch (rorderList.status){
            case -1:
                holder.orderStateTv.setText("取消订单");
                holder.doBtn.setVisibility(View.GONE);
                break;
            case 0:
                holder.orderStateTv.setText("待支付");
                holder.doBtn.setText("去支付");
                holder.doBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.toPayOrder(holder.getAdapterPosition());
                    }
                });
                break;
            case 1:
                holder.orderStateTv.setText("待发货");
                holder.doBtn.setText("提醒发货");
                holder.doBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.tosendOrder(holder.getAdapterPosition());
                    }
                });
                break;
            case 2:
                holder.orderStateTv.setText("待收货 ");
                holder.doBtn.setText("确认收货");
                holder.doBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.sureGetOrder(holder.getAdapterPosition());
                    }
                });
                break;
            case 3:
                holder.orderStateTv.setText("完成交易 ");
                holder.doBtn.setText("删除");
                holder.doBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.toCancleOrder(holder.getAdapterPosition());
                    }
                });
                break;
        }
        holder.orderItemProductIv.setLayoutManager(
                new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        holder.orderItemProductIv.setAdapter(new CommonAdapter<String>(mContext,R.layout.settle_choose_lv_item,imgUrls) {


            @Override
            protected void convert(ViewHolder holders, String s, int position) {
                Glide.with(mContext)
                        .load(NetWorkCons.BASE_URL+s)
                        .into((ImageView) holders.getView(R.id.piv));
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDatas==null?0:mDatas.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.order_no_tv)
        TextView orderNoTv;
        @BindView(R.id.order_state_tv)
        TextView orderStateTv;
        @BindView(R.id.order_item_product_iv)
        RecyclerView orderItemProductIv;
        @BindView(R.id.price_tv)
        TextView priceTv;
        @BindView(R.id.do_btn)
        Button doBtn;
        public OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
