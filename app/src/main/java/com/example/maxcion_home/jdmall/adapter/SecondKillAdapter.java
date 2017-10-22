package com.example.maxcion_home.jdmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.activity.ProductDetialsActivity;
import com.example.maxcion_home.jdmall.activity.ProductListActivity;
import com.example.maxcion_home.jdmall.bean.RsecKillRow;
import com.example.maxcion_home.jdmall.cons.NetWorkCons;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maxcion_home on 2017/9/10.
 */

public class SecondKillAdapter extends RecyclerView.Adapter<SecondKillAdapter.ViewHolder> {

    protected ArrayList<RsecKillRow> mData;
    protected Context context;
    private CategoryTopAdapter.OnItemClickListener listener;

    public void setmData(ArrayList<RsecKillRow> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    public void setOnItemClickListener(CategoryTopAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public RsecKillRow getItem(int position){
        return mData.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_seckill_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RsecKillRow rseckillRow = mData.get(position);
        holder.nowpriceTv.setText(rseckillRow.pointPrice + "");
        holder.normalpriceTv.setText(rseckillRow.allPrice + "");
        holder.normalpriceTv.getPaint()
                .setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        Glide.with(context)
                .load(NetWorkCons.BASE_URL + rseckillRow.iconUrl)
                .into(holder.imageIv);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.image_iv)
        ImageView imageIv;
        @BindView(R.id.nowprice_tv)
        TextView nowpriceTv;
        @BindView(R.id.normalprice_tv)
        TextView normalpriceTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(view, getAdapterPosition());
        }
    }


}
