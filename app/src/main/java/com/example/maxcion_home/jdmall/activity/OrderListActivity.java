package com.example.maxcion_home.jdmall.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.fragment.AllOrderFragment;
import com.example.maxcion_home.jdmall.fragment.CompletedOrderFragment;
import com.example.maxcion_home.jdmall.fragment.WaitPayFragment;
import com.example.maxcion_home.jdmall.fragment.WaitReceiveFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListActivity extends BaseActivity {

    @BindView(R.id.order_list_tablayou)
    TabLayout orderListTablayou;
    @BindView(R.id.order_list_vp)
    ViewPager orderListVp;
    private List<String> mTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        mTitles = new ArrayList<>();
        mTitles.add("全部订单");
        mTitles.add("待支付订单");
        mTitles.add("待收货订单");
        mTitles.add("已完成订单");
    }

    private void initView() {
        TabPageAdapter pageAdapter = new TabPageAdapter(getSupportFragmentManager());
        pageAdapter.setTitles(mTitles);
        orderListVp.setAdapter(pageAdapter);
        orderListTablayou.setupWithViewPager(orderListVp,true);

    }

    class TabPageAdapter extends FragmentPagerAdapter{
        List<Fragment> fragments = new ArrayList<>();
        private List<String> titles;

        public TabPageAdapter(FragmentManager fm) {
            super(fm);
            AllOrderFragment allOrderFragment = new AllOrderFragment();
            CompletedOrderFragment completedOrderFragment = new CompletedOrderFragment();
            WaitPayFragment waitPayFragment = new WaitPayFragment();
            WaitReceiveFragment waitReceiveFragment = new WaitReceiveFragment();
            fragments.add(allOrderFragment);
            fragments.add(waitPayFragment);
            fragments.add(waitReceiveFragment);
            fragments.add(completedOrderFragment);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void setTitles(List<String> titles){this.titles = titles;}

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}
