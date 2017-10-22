package com.example.maxcion_home.jdmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by maxcion_home on 2017/9/10.
 */

public abstract class JDBaseAdapter<T> extends RecyclerView.Adapter {
    protected ArrayList<T> mData ;
    protected Context context;

    public void setmData(ArrayList<T> mData , Context context){
        this.mData = mData;
        this.context = context;
    }
    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position) ;

    @Override
    public int getItemCount() {
        return mData!=null?mData.size():0;
    }
}
