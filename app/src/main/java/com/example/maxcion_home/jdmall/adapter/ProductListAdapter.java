package com.example.maxcion_home.jdmall.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.bean.RProductList;
import com.example.maxcion_home.jdmall.cons.NetWorkCons;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxcion_home on 2017/9/27.
 */

public class ProductListAdapter extends CommonAdapter<RProductList> {

    private ArrayList<RProductList> mData;
    private CategoryTopAdapter.OnItemClickListener listener;

    public ProductListAdapter(Context context, int layoutId, List<RProductList> datas) {
        super(context, layoutId, datas);
        mData= (ArrayList<RProductList>) datas;
    }


    public void setOnItemClickListener(CategoryTopAdapter.OnItemClickListener listener){
        this.listener=listener;
    }
    public void setmDatas(ArrayList<RProductList> mData){
        this.mData=mData;
    }

    public RProductList getItem(int position){
        return mData.get(position);
    }
    @Override
    protected void convert(ViewHolder holder, RProductList rProductList, final int position) {
        holder.setText(R.id.name_tv, rProductList.name + "");
        holder.setText(R.id.price_tv, rProductList.price + "");
        holder.setText(R.id.commrate_tv, rProductList.commentCount + "条评论");
        Glide.with(mContext).load(NetWorkCons.BASE_URL + rProductList.iconUrl).into((ImageView) holder.getView(R.id.product_iv));
        holder.setOnClickListener(R.id.productItem_rl, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view,position);
            }
        });
    }
}
