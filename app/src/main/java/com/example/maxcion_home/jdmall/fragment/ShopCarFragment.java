package com.example.maxcion_home.jdmall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maxcion_home.jdmall.JDApplication;
import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.activity.HomeActivity;
import com.example.maxcion_home.jdmall.activity.SettleActivity;
import com.example.maxcion_home.jdmall.adapter.CategoryTopAdapter;
import com.example.maxcion_home.jdmall.adapter.ShopcarAdapter;
import com.example.maxcion_home.jdmall.bean.Rshopcar;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.controls.ShopcarController;
import com.example.maxcion_home.jdmall.interfaces.IshopcarDelectListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by maxcion_home on 2017/9/8.
 */

public class ShopCarFragment extends BaseFragment implements CategoryTopAdapter.OnItemClickListener, IshopcarDelectListener {

    @BindView(R.id.shopcar_rv)
    RecyclerView shopcarRv;
    @BindView(R.id.all_cbx)
    CheckBox allCbx;
    @BindView(R.id.all_money_tv)
    TextView allMoneyTv;
    @BindView(R.id.settle_tv)
    TextView settleTv;
    Unbinder unbinder;
    private List<Rshopcar> mShopcars;
    private ShopcarAdapter mShopcarAdapter;
    private List<Rshopcar> mPchoice;
    private int userId;
    private double allMoney;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopcar, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initControl();
        initView();
        mPchoice = new ArrayList();
        JDApplication application = (JDApplication) getActivity().getApplication();
        userId = application.mRLoginResult.id;
        mControl.sendAsyncMessage(IdiyMessage.SHOPCAR_LIST_ACTION, userId);
    }

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case IdiyMessage.SHOPCAR_LIST_ACTION_RESULT:
                handleShopcarResult(msg.obj);
                break;
            case IdiyMessage.DELET_SHOPCAR_ACTION_RESULT:
                handleDelectResult(msg.obj);
                break;
        }
    }

    private void handleDelectResult(Object obj) {
        if ("删除成功".equals((String) obj)) {
            mShopcarAdapter.notifyDataSetChanged();
            mControl.sendAsyncMessage(IdiyMessage.SHOPCAR_LIST_ACTION, userId);
        } else {
            Toast.makeText(getContext(), (String) obj, Toast.LENGTH_SHORT).show();
        }
    }

    private void handleShopcarResult(Object obj) {
        if (obj == null) {
            Toast.makeText(getContext(), "数据异常", Toast.LENGTH_SHORT).show();
            return;
        }
        mShopcars = (List<Rshopcar>) obj;
        mShopcarAdapter = new ShopcarAdapter();
        mShopcarAdapter.setOnItemClickListener(this);
        mShopcarAdapter.setDelectListener(this);
        mShopcarAdapter.setDatas(mShopcars, getContext());
        shopcarRv.setLayoutManager(new LinearLayoutManager(getContext()));
        shopcarRv.setAdapter(mShopcarAdapter);
        mShopcarAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        HomeActivity activity = (HomeActivity) getActivity();
        activity.hideTitle(View.GONE);
    }

    @Override
    protected void initControl() {
        mControl = new ShopcarController(getContext());
        mControl.setIModeChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(View view, int postion) {
        CheckBox checkBox = (CheckBox) view;
        Rshopcar currentShopcar = mShopcars.get(postion);
        if (checkBox.isChecked()) {
            mPchoice.add(currentShopcar);
        } else {
            mPchoice.remove(currentShopcar);
        }
        allMoney = 0;
        for (Rshopcar rshopcar : mPchoice) {
            allMoney += rshopcar.buyCount * rshopcar.pprice;
        }
        allMoneyTv.setText("总额: ￥ " + allMoney);
        settleTv.setText("去结算(" + mPchoice.size() + ")");
    }

    @Override
    public void onDelect(Rshopcar rshopcar) {
        mControl.sendAsyncMessage(IdiyMessage.DELET_SHOPCAR_ACTION, userId, rshopcar.id);
    }

    @OnClick(R.id.settle_tv)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), SettleActivity.class);
        if (mPchoice.size()==0){
            Toast.makeText(getContext(),"请选择商品",Toast.LENGTH_SHORT).show();
            return;
        }
        intent.putExtra("CHOOSE_PRODUCT_LIST", (Serializable) mPchoice);
        intent.putExtra("CHOOSE_ALL_MONEY",allMoney);
        getActivity().startActivity(intent);
    }
}
