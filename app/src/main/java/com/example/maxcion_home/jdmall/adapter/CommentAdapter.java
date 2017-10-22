package com.example.maxcion_home.jdmall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.bean.RgoodComment;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.cons.NetWorkCons;
import com.example.maxcion_home.jdmall.ui.RatingBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maxcion_home on 2017/10/11.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private  Context context;
    private  List<RgoodComment> datas;

    public void setDatas(List<RgoodComment> datas){
        this.datas=datas;
    }

    public CommentAdapter(Context context) {
        this.context = context;

    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recommend_comment_item_view, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        RgoodComment rgoodComment = datas.get(position);
        holder.ratingBar.setRating(rgoodComment.rate);
        holder.nameTv.setText("用户:"+rgoodComment.userName);
        holder.contentTv.setText(rgoodComment.comment);
        //设置评论图片的显示
        initImgContainer(holder, rgoodComment);

    }

    private void initImgContainer(CommentViewHolder holder, RgoodComment rgoodComment) {
        List<String> imgUrls = JSON.parseArray(rgoodComment.imgUrls, String.class);
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
        @BindView(R.id.name_tv)
        TextView nameTv;
        @BindView(R.id.content_tv)
        TextView contentTv;
        @BindView(R.id.rating_bar)
        RatingBar ratingBar;
        @BindView(R.id.iamges_container)
        LinearLayout iamgesContainer;
        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
