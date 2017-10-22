package com.example.maxcion_home.jdmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.bean.Rbrand;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maxcion_home on 2017/9/23.
 */

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ViewHolder> {
    protected ArrayList<Rbrand> mData;
    protected Context context;
    private CategoryTopAdapter.OnItemClickListener listener;
    private int currentItemIndex = -1;



    public void setCurrentItemIndex(int currentItemIndex) {
        this.currentItemIndex = currentItemIndex;
    }

    public void setOnItemClickListener(CategoryTopAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setmData(ArrayList<Rbrand> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.brand_lv_item_layout, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final BrandAdapter.ViewHolder holder, final int position) {
        Rbrand rbrand = mData.get(position);
        holder.brandTv.setText(rbrand.name);
        holder.brandTv.setSelected(position == currentItemIndex?true:false);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }


    public Rbrand getItem(int position) {
        return mData.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.brand_tv)
        TextView brandTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            brandTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(brandTv, getAdapterPosition());
                }
            });
        }

    }

}
