package com.example.maxcion_home.jdmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.bean.RcommentDetail;
import com.example.maxcion_home.jdmall.bean.RgoodComment;
import com.example.maxcion_home.jdmall.cons.NetWorkCons;
import com.example.maxcion_home.jdmall.ui.RatingBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maxcion_home on 2017/10/11.
 */

public class CommentDetailAdapter extends RecyclerView.Adapter<CommentDetailAdapter.CommentViewHolder> {

    private  Context context;
    private  List<RcommentDetail> datas;

    public void setDatas(List<RcommentDetail> datas){
        this.datas=datas;
    }

    public CommentDetailAdapter(Context context) {
        this.context = context;

    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_item_view, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        RcommentDetail rcommentDetail = datas.get(position);
        holder.ratingBar.setRating(rcommentDetail.rate);
        holder.nameTv.setText("用户:"+rcommentDetail.userName);
        holder.lovecountTv.setText("喜欢("+rcommentDetail.loveCount+")");
        holder.timeTv.setText(rcommentDetail.commentTime);
        holder.buytimeTv.setText("购买时间:"+rcommentDetail.buyTime);
        holder.buyversionTv.setText("型号:"+rcommentDetail.productType);
        holder.subcommentTv.setText("回复("+rcommentDetail.subComment+")");
        Glide.with(context)
                .load(NetWorkCons.BASE_URL+rcommentDetail.userImg)
                .into(holder.iconIv);

        holder.contentTv.setText(rcommentDetail.comment);
        //设置评论图片的显示
        initImgContainer(holder, rcommentDetail);

    }

    private void initImgContainer(CommentViewHolder holder, RcommentDetail rcommentDetail) {
        List<String> imgUrls = JSON.parseArray(rcommentDetail.imgUrls, String.class);
        holder.iamgesContainer.setVisibility(imgUrls.size()==0? View.GONE:View.VISIBLE);
        int imgUrlsSize = imgUrls.size();
        int chirdCount =holder.iamgesContainer.getChildCount();
        for (int i =0 ;i<chirdCount;i++){
            ImageView imageView = (ImageView) holder.iamgesContainer.getChildAt(i);
            if (i<imgUrlsSize){
                imageView.setVisibility(View.VISIBLE);
                Glide.with(context).load(NetWorkCons.BASE_URL+imgUrls.get(i)).into(imageView);
            }else {
                imageView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon_iv)
        ImageView iconIv;
        @BindView(R.id.name_tv)
        TextView nameTv;
        @BindView(R.id.time_tv)
        TextView timeTv;
        @BindView(R.id.content_tv)
        TextView contentTv;
        @BindView(R.id.iamges_container)
        LinearLayout iamgesContainer;
        @BindView(R.id.buytime_tv)
        TextView buytimeTv;
        @BindView(R.id.buyversion_tv)
        TextView buyversionTv;
        @BindView(R.id.lovecount_tv)
        TextView lovecountTv;
        @BindView(R.id.subcomment_tv)
        TextView subcommentTv;
        @BindView(R.id.rating_bar)
        RatingBar ratingBar;
        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
