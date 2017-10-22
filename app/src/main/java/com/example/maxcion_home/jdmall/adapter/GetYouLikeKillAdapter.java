package com.example.maxcion_home.jdmall.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.bean.RgetLikeRows;
import com.example.maxcion_home.jdmall.bean.RsecKillRow;
import com.example.maxcion_home.jdmall.cons.NetWorkCons;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maxcion_home on 2017/9/10.
 */

public class GetYouLikeKillAdapter extends RecyclerView.Adapter<GetYouLikeKillAdapter.ViewHolder> {

    protected ArrayList<RgetLikeRows> mData;
    protected Context context;


    public void setmData(ArrayList<RgetLikeRows> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recommend_gv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RgetLikeRows rgetLikeRows = mData.get(position);
        holder.nameTv.setText(rgetLikeRows.name );
        holder.priceTv.setText(rgetLikeRows.price + "");
        Glide.with(context)
                .load(NetWorkCons.BASE_URL + rgetLikeRows.iconUrl)
                .into(holder.imageIv);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_iv)
        ImageView imageIv;
        @BindView(R.id.name_tv)
        TextView nameTv;
        @BindView(R.id.price_tv)
        TextView priceTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
