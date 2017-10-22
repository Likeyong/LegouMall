package com.example.maxcion_home.jdmall.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.cons.NetWorkCons;

import java.util.List;

/**
 * Created by maxcion_home on 2017/10/9.
 */

public class ProductAdAdaoter extends PagerAdapter {
    private List<String> data;
    private Context context;

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView adPicture = new ImageView(context);
        Glide.with(context).load(NetWorkCons.BASE_URL + data.get(position)).into(adPicture);
        container.addView(adPicture);
        return adPicture;
    }

    public void setData(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }
}
