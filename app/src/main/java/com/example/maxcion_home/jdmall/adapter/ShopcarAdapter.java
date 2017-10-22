package com.example.maxcion_home.jdmall.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.bean.Rshopcar;
import com.example.maxcion_home.jdmall.cons.NetWorkCons;
import com.example.maxcion_home.jdmall.interfaces.IshopcarDelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maxcion_home on 2017/9/27.
 */

public class ShopcarAdapter extends RecyclerView.Adapter<ShopcarAdapter.ShopcarViewHolder> {


    private List<Rshopcar> mData;
    private CategoryTopAdapter.OnItemClickListener listener;
    private IshopcarDelectListener delectListener;
    private Context mContext;
    private List<Integer> selectedPositionList = new ArrayList<>();


    public void setDelectListener(IshopcarDelectListener listener) {
        delectListener = listener;
    }

    public void setOnItemClickListener(CategoryTopAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setDatas(List<Rshopcar> mData, Context context) {
        this.mData = mData;
        mContext = context;
    }

    public Rshopcar getItem(int position) {
        return mData.get(position);
    }


    @Override
    public ShopcarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.shopcar_lv_item, parent, false);
        return new ShopcarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopcarViewHolder holder, int position) {
        Rshopcar shopcar = mData.get(position);
        holder.pnameTv.setText(shopcar.pname);
        holder.pversionTv.setText(shopcar.pversion);
        holder.priceTv.setText(shopcar.pprice + "");
        holder.buyCountTv.setText(" × " + shopcar.buyCount);
        Glide.with(mContext).load(NetWorkCons.BASE_URL + shopcar.pimageUrl)
                .into(holder.productIv);

        holder.cbx.setChecked(selectedPositionList.contains((Integer) position) ? true : false);

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    class ShopcarViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll)
        CardView ll;
        @BindView(R.id.cbx)
        CheckBox cbx;
        @BindView(R.id.product_iv)
        ImageView productIv;
        @BindView(R.id.pname_tv)
        TextView pnameTv;
        @BindView(R.id.pversion_tv)
        TextView pversionTv;
        @BindView(R.id.price_tv)
        TextView priceTv;
        @BindView(R.id.buyCount_tv)
        TextView buyCountTv;
        @BindView(R.id.delete_product)
        Button deleteProduct;

        public ShopcarViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            deleteProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delectListener.onDelect(mData.get(getAdapterPosition()));
                    Log.d("TAG","wobeidianjile");
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                    1.将点击事件交给调用者处理，这样能更好统计购物车的总钱数和商品个数
                    2.通过回调将被点击的item中cbx和当前的position传出去，在shopcarFragment中
                      就能根据position获取被选中的商品信息，包括数目，价格
                     */
                    cbx.setChecked(!cbx.isChecked());

                    /*
                    记录被点击的item位置索引，在onbindViewHolder中可以进行判定，
                    如果准备显示的item位置，在被记录的item索引集合中，就设置为选中
                     */
                    if (cbx.isChecked()) {
                        selectedPositionList.add((Integer) getAdapterPosition());
                    } else {
                        selectedPositionList.remove((Integer) getAdapterPosition());
                    }
                    listener.onItemClick(cbx, getAdapterPosition());
                }
            });
        }
    }
}
