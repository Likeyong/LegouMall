package com.example.maxcion_home.jdmall.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.activity.ProductDetialsActivity;
import com.example.maxcion_home.jdmall.adapter.CategoryTopAdapter;
import com.example.maxcion_home.jdmall.adapter.CommentAdapter;
import com.example.maxcion_home.jdmall.adapter.ProductAdAdaoter;
import com.example.maxcion_home.jdmall.adapter.TypeListAdapter;
import com.example.maxcion_home.jdmall.bean.RgoodComment;
import com.example.maxcion_home.jdmall.bean.RproductInfo;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.controls.ProductDetailsController;
import com.example.maxcion_home.jdmall.interfaces.INumberInputListener;
import com.example.maxcion_home.jdmall.interfaces.IOnProductVersionSelected;
import com.example.maxcion_home.jdmall.ui.NumberInputView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lean on 16/10/28.
 */

public class ProductIntroduceFragment extends BaseFragment implements CategoryTopAdapter.OnItemClickListener, INumberInputListener {

    @BindView(R.id.advp)
    ViewPager advp;
    @BindView(R.id.vp_indic_tv)
    TextView vpIndicTv;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.self_sale_tv)
    TextView selfSaleTv;
    @BindView(R.id.recommend_p_tv)
    TextView recommendPTv;
    @BindView(R.id.recommend_buy_tv)
    TextView recommendBuyTv;
    @BindView(R.id.price_tv)
    TextView priceTv;
    @BindView(R.id.tip_tv)
    TextView tipTv;
    @BindView(R.id.product_versions_lv)
    RecyclerView productVersionsLv;
    @BindView(R.id.number_input_et)
    NumberInputView numberInpt;
    @BindView(R.id.good_rate_tip)
    TextView goodRateTip;
    @BindView(R.id.good_rate_tv)
    TextView goodRateTv;
    @BindView(R.id.good_comment_tv)
    TextView goodCommentTv;
    @BindView(R.id.good_comment_lv)
    RecyclerView goodCommentRv;
    @BindView(R.id.scrollview)
    ScrollView scrollview;

    Unbinder unbinder;
    private ProductAdAdaoter mAdAdapter;
    private ProductDetailsController mDetailController;
    private ProductDetialsActivity mDetailActivity;
    private List<String> mImgUrlList;
    private Timer mTimer;
    private boolean isZero = true;
    private boolean isMax = false;
    private RproductInfo mProductInfo;
    private TypeListAdapter mTypeAdapte;
    private List<RgoodComment> mCommentList;
    private CommentAdapter mCommentAdapter;
    private IOnProductVersionSelected productVersionSelectedListener;
    private ArrayList<String> mTypes;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_introduce, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCommentAdapter = new CommentAdapter(getContext());
        initControl();
    }

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case IdiyMessage.PRODUCT_INFO_ACTION_RESULT:
                handleProductInfo(msg.obj);
                break;

            case IdiyMessage.GOOD_COMMENT_ACTION_RESULT:
                handleGoodCommentResult(msg.obj);
                break;
        }

    }

    private void handleGoodCommentResult(Object obj) {
        if (obj==null){
            Toast.makeText(getContext(),"评论获取失败", Toast.LENGTH_SHORT).show();
            return;
        }
        mCommentList = (List<RgoodComment>) obj;
        mCommentAdapter.setDatas(mCommentList);
        mCommentAdapter.notifyDataSetChanged();
    }

    private void handleProductInfo(Object obj) {
        if (obj == null) {
            Toast.makeText(getContext(), "数据异常", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return;
        }
        mProductInfo = (RproductInfo) obj;
        initView();
        handleAdBanner(mProductInfo.imgUrls);
        handleTypeList(mProductInfo.typeList);
    }

    private void handleTypeList(String typeList) {
        mTypes = (ArrayList<String>) JSON.parseArray(typeList, String.class);
        mTypeAdapte = new TypeListAdapter();
        mTypeAdapte.setOnItemClickListener(this);
        mTypeAdapte.setmData(mTypes, getContext());
        productVersionsLv.setLayoutManager(new LinearLayoutManager(getContext()));
        productVersionsLv.setAdapter(mTypeAdapte);
    }

    private void handleAdBanner(String imgUrls) {
        mImgUrlList = JSON.parseArray(imgUrls, String.class);
        mAdAdapter.setData(mImgUrlList, getContext());
        mAdAdapter.notifyDataSetChanged();
        vpIndicTv.setText(1 + "/" + mImgUrlList.size());
        //设置ViewPager无限巡播
        initVpCirculate();
    }

    public void setOnProVersionSelectedListener(IOnProductVersionSelected listener){
        productVersionSelectedListener = listener;
    }

    /**
     * 通过Timer实现每3秒切换一次内容
     */
    private void initVpCirculate() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = advp.getCurrentItem();
                        if (currentItem == 0) {
                            isZero = true;
                            isMax = false;
                        }
                        if (currentItem == mImgUrlList.size() - 1) {
                            isMax = true;
                            isZero = false;
                        }
                        if (isZero) {
                            currentItem++;
                        }
                        if (isMax) {
                            currentItem--;
                        }
                        currentItem = currentItem % (mImgUrlList.size());
                        advp.setCurrentItem(currentItem);
                    }
                });
            }
        }, 3 * 1000, 3 * 1000);
    }

    @Override
    protected void initView() {

        mAdAdapter = new ProductAdAdaoter();
        advp.setAdapter(mAdAdapter);
        advp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                vpIndicTv.setText(++position + "/" + mImgUrlList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        nameTv.setText(mProductInfo.name);
        selfSaleTv.setVisibility(mProductInfo.ifSaleOneself == true ? View.VISIBLE : View.GONE);
        recommendPTv.setText(mProductInfo.recomProduct);
        numberInpt.setMax(mProductInfo.stockCount);
        numberInpt.setListener(this);
        goodRateTv.setText(mProductInfo.favcomRate+"%好评率");
        goodCommentTv.setText(mProductInfo.commentCount+"人评价");

        //评论显示代码

        goodCommentRv.setLayoutManager(new LinearLayoutManager(getContext()));
        goodCommentRv.setHasFixedSize(true);;
        goodCommentRv.setNestedScrollingEnabled(false);
        goodCommentRv.setAdapter(mCommentAdapter);
    }

    @Override
    protected void initControl() {
        mDetailController = new ProductDetailsController(getContext());
        mDetailController.setIModeChangeListener(this);
        mDetailActivity = (ProductDetialsActivity) getActivity();
        mDetailController.sendAsyncMessage(IdiyMessage.PRODUCT_INFO_ACTION, mDetailActivity.mProductId);
        mDetailController.sendAsyncMessage(IdiyMessage.GOOD_COMMENT_ACTION,mDetailActivity.mProductId);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    /**
     * @param view    被点击的View
     * @param postion 当前被点击View的adapter位置
     *                每次点击的时候将点击位置告诉adapter。
     *                在onBindHolder（）中进行判断是否选中
     */
    @Override
    public void onItemClick(View view, int postion) {
        mTypeAdapte.setCurrentItemIndex(postion);
        mTypeAdapte.notifyDataSetChanged();
        productVersionSelectedListener.onProductVersionSelected(mTypes.get(postion));
        Log.d("TAG","是版本吗？？"+mTypes.get(postion));

    }

    public int getCount(){
        return  numberInpt.getCount();
    }

    @Override
    public void onTextChange(int num) {

    }
}
