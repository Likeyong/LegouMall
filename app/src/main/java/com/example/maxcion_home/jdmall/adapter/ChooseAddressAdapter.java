package com.example.maxcion_home.jdmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.bean.RreceivrAddress;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maxcion_home on 2017/9/10.
 */

public class ChooseAddressAdapter extends RecyclerView.Adapter<ChooseAddressAdapter.ViewHolder> {

    protected List<RreceivrAddress> mData;
    protected Context context;

    private CategoryTopAdapter.OnItemClickListener listener;

    public void setmData(List<RreceivrAddress> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    public void setOnItemClickListener(CategoryTopAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public RreceivrAddress getItem(int position) {
        return mData.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.choose_address_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RreceivrAddress address = mData.get(position);
        holder.nameTv.setText(address.receiverName);
        holder.phoneTv.setText(address.receiverPhone);
        holder.addressTv.setText(address.receiverAddress);

    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.isDeafult_iv)
        ImageView isDeafultIv;
        @BindView(R.id.name_tv)
        TextView nameTv;
        @BindView(R.id.phone_tv)
        TextView phoneTv;
        @BindView(R.id.address_tv)
        TextView addressTv;
        @BindView(R.id.delete_tv)
        TextView deleteTv;
        @BindView(R.id.has_receiver_rl)
        RelativeLayout hasReceiverRl;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            deleteTv.setOnClickListener(this);
            hasReceiverRl.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            listener.onItemClick(view, getAdapterPosition());
        }
    }


}
