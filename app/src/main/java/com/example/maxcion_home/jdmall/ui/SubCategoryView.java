package com.example.maxcion_home.jdmall.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.activity.ProductListActivity;
import com.example.maxcion_home.jdmall.bean.RcategoryItem;
import com.example.maxcion_home.jdmall.bean.RsubCategory;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.cons.NetWorkCons;
import com.example.maxcion_home.jdmall.controls.CategoryController;
import com.example.maxcion_home.jdmall.interfaces.IModeChangeListener;
import com.example.maxcion_home.jdmall.interfaces.IViewContainer;

import java.util.List;

/**
 * Created by maxcion_home on 2017/9/16.
 */

public class SubCategoryView extends FlexiScrollView implements IViewContainer, IModeChangeListener,
        View.OnClickListener {
    private RcategoryItem mRcategoryItem;
    private LinearLayout mLinearLayout;
    private ImageView bannerImg;
    private CategoryController mController;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IdiyMessage.SUBCATEGORY_ACTION_RESULT:
                    handleSubCategory((List<RsubCategory>) msg.obj);
                    break;
            }
        }
    };
    private List<RcategoryItem> mThirdCategory;

    /**
     * @param obj 一级分类返回值内的二级分类集合
     */
    private void handleSubCategory(List<RsubCategory> obj) {
        /*
                根据三级分类的个数确定每个二级分类的行数
                每个二级分类的行数= 三级分类的个数/3
                如果三级分类%3 = 0 则行数=三级分类的个数/3
                否则 行数=三级分类的个数/3 +1；
                 */
        for (int i = 0; i < obj.size(); i++) {
            initSubCateTitletEST(obj, i);   //创建二级分类标题
            mThirdCategory = JSON.parseArray(obj.get(i).thirdCategory, RcategoryItem.class);
            int thirdCateSize = mThirdCategory.size();
            int lines = thirdCateSize / 3;
            lines += thirdCateSize % 3 == 0 ? 0 : 1;
            for (int j = 0; j < lines; j++) {

                LinearLayout subContain = new LinearLayout(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(8, 8, 8, 8);
                mLinearLayout.addView(subContain, params);

                initThirdCateotyItem(thirdCateSize, 3 * j, subContain);
                initThirdCateotyItem(thirdCateSize, 3 * j + 1, subContain);
                initThirdCateotyItem(thirdCateSize, 3 * j + 2, subContain);


            }
        }
    }

    /**
     * 根据每个三级分类的索引判断是否需要在二级分类中添加
     *
     * @param thirdCateSize 三级分类的item个数
     * @param itemIndex     三级分类Item索引
     * @param subContain    二级分类容器
     */
    private void initThirdCateotyItem(int thirdCateSize, int itemIndex, LinearLayout subContain) {
       /*
       第一列的item索引= 每行item的个数 * 当前行数
       第二列的item索引= 每行item的个数 * 当前行数 +1
       第三列的item索引= 每行item的个数 * 当前行数 +2

       如果当前item的索引小于或等于item总数，就创建三级分类的item
        */
        if (itemIndex <= thirdCateSize - 1) {
            RcategoryItem thirdCateItem = mThirdCategory.get(itemIndex);
            LinearLayout thirdContain = new LinearLayout(getContext());
            LinearLayout.LayoutParams thirdParams = new LinearLayout.LayoutParams(
                    (getWidth() - 16) / 3,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            thirdContain.setOrientation(LinearLayout.VERTICAL);
            thirdContain.setGravity(Gravity.CENTER_VERTICAL);
            thirdContain.setGravity(Gravity.CENTER_HORIZONTAL);
            TextView thirdItemTitle = new TextView(getContext());

            // 为三级分类item设置点击事件
            thirdContain.setOnClickListener(this);
            thirdContain.setTag(thirdCateItem);

            // 三级标题中添加图片
            ImageView thirdItemIv = new ImageView(getContext());
            LinearLayout.LayoutParams thirdIvParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, //宽度等于matchparent等于(getWidth() - 16) / 3
                    (getWidth() - 16) / 3);//让宽度等于高度就实现图片的正常显示
            Glide.with(getContext())
                    .load(NetWorkCons.BASE_URL + thirdCateItem.bannerUrl)
                    .into(thirdItemIv);
            thirdContain.addView(thirdItemIv, thirdIvParams);


            // 三级分类中添加标题文字
            LinearLayout.LayoutParams thridCategoryNameParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            thirdItemTitle.setText(thirdCateItem.name);
            thirdItemTitle.setTextSize(15);
            thirdItemTitle.setGravity(Gravity.CENTER_HORIZONTAL);

            thirdItemTitle.setLayoutParams(thridCategoryNameParams);
            thirdContain.addView(thirdItemTitle);
            subContain.addView(thirdContain, thirdParams);
        }
    }

    private void initSubCateTitletEST(List<RsubCategory> obj, int i) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        TextView textView = new TextView(getContext());
        textView.setText(obj.get(i).name);
        params.setMargins(8, 8, 8, 8);
        textView.setLayoutParams(params);
        mLinearLayout.addView(textView, params);
    }

    public SubCategoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLinearLayout = (LinearLayout) findViewById(R.id.child_container_ll);
        initController();
    }

    private void initController() {
        mController = new CategoryController(getContext());
        mController.setIModeChangeListener(this);
    }

    @Override
    public void onShow(Object... values) {
        mRcategoryItem = (RcategoryItem) values[0];
        mLinearLayout.removeAllViews();
        initBanner();
        mController.sendAsyncMessage(IdiyMessage.SUBCATEGORY_ACTION, mRcategoryItem.id);
    }

    private void initBanner() {
        bannerImg = new ImageView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(8, 8, 8, 8);
        bannerImg.setLayoutParams(params);
        bannerImg.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(getContext())
                .load(NetWorkCons.BASE_URL + mRcategoryItem.bannerUrl)
                .into(bannerImg);
        mLinearLayout.addView(bannerImg, params);
    }

    @Override
    public void onModeChanged(int action, Object... values) {
        mHandler.obtainMessage(action, values[0]).sendToTarget();
    }

    @Override
    public void onClick(View view) {
        RcategoryItem rCategoryItem = (RcategoryItem) view.getTag();
        Intent intent = new Intent(getContext(), ProductListActivity.class);
        intent.putExtra("productId",rCategoryItem.id);
        intent.putExtra("topcategoryid",mRcategoryItem.id);
        intent.putExtra("keyword","");
        getContext().startActivity(intent);

    }
}
