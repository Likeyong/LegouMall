package com.example.maxcion_home.jdmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.bean.RcategoryItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maxcion_home on 2017/9/10.
 */

public class CategoryTopAdapter extends RecyclerView.Adapter<CategoryTopAdapter.ViewHolder> {

    protected ArrayList<RcategoryItem> mData;
    protected Context context;
    private OnItemClickListener listener ;
    private int currentItemIndex = 0;


    public void setCurrentItemIndex(int currentItemIndex){
        this.currentItemIndex=currentItemIndex;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    public void setmData(ArrayList<RcategoryItem> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.top_category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        RcategoryItem rCategoryItem = mData.get(position);
        holder.tv.setText(rCategoryItem.name + "");

        if (position == currentItemIndex) {
            holder.tv.setSelected(true);
            holder.tv.setBackgroundResource(R.drawable.tongcheng_all_bg01);
             holder.divider.setVisibility(View.INVISIBLE);
        } else {
            holder.tv.setSelected(false);
            holder.tv.setBackgroundColor(0xFFFAFAFA);
            holder.divider.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }



    public RcategoryItem getItem(int position) {
      return mData.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.divider)
        View divider;
        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.item_ll)
        RelativeLayout itemLl;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(itemLl,getAdapterPosition());
                }
            });
        }

    }



    public  interface OnItemClickListener{
        void onItemClick(View view ,int postion);
    }
}
