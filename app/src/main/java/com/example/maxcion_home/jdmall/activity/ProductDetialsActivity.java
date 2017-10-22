package com.example.maxcion_home.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.maxcion_home.jdmall.JDApplication;
import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.controls.ProductDetailsController;
import com.example.maxcion_home.jdmall.fragment.ProductCommentFragment;
import com.example.maxcion_home.jdmall.fragment.ProductDetailsFragment;
import com.example.maxcion_home.jdmall.fragment.ProductIntroduceFragment;
import com.example.maxcion_home.jdmall.interfaces.IOnProductVersionSelected;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetialsActivity extends BaseActivity implements IOnProductVersionSelected {

    @BindView(R.id.details_view)
    View detailsView;
    @BindView(R.id.details_ll)
    LinearLayout detailsLl;
    @BindView(R.id.introduce_view)
    View introduceView;
    @BindView(R.id.introduce_ll)
    LinearLayout introduceLl;
    @BindView(R.id.comment_tv)
    View commentTv;
    @BindView(R.id.comment_ll)
    LinearLayout commentLl;
    @BindView(R.id.more_iv)
    ImageView moreIv;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.add2shopcar)
    TextView add2shopcar;
    public int mProductId;
    private static ProductIntroduceFragment mIntroduceFragment;

    private int mProductCount;
    private String productVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detials);
        ButterKnife.bind(this);
        initData();
        initView();
        initController();
    }

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case IdiyMessage.ADD2SHOPCAR_ACTION_RESULT:
                handleAdd2CarResult((String) msg.obj);
                break;
        }
    }

    private void handleAdd2CarResult(String result) {
        tip(result);
    }

    private void initController() {
        mController = new ProductDetailsController(this);
        mController.setIModeChangeListener(this);
    }


    private void initView() {
        ContainAdapter containAdapter = new ContainAdapter(getSupportFragmentManager());
        mIntroduceFragment.setOnProVersionSelectedListener(ProductDetialsActivity.this);
        vp.setAdapter(containAdapter);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                initVPIndicator();
                switch (position) {
                    case 0:
                        introduceView.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        detailsView.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        commentTv.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initData() {
        Intent inten = getIntent();
        mProductId = inten.getIntExtra("productId", 0);
    }

    @OnClick({R.id.details_ll, R.id.introduce_ll, R.id.comment_ll})
    public void onViewClicked(View view) {
        initVPIndicator();
        switch (view.getId()) {
            case R.id.details_ll:
                detailsView.setVisibility(View.VISIBLE);
                vp.setCurrentItem(1);
                break;
            case R.id.introduce_ll:
                introduceView.setVisibility(View.VISIBLE);
                vp.setCurrentItem(0);
                break;
            case R.id.comment_ll:
                vp.setCurrentItem(2);
                commentTv.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initVPIndicator() {
        detailsView.setVisibility(View.INVISIBLE);
        introduceView.setVisibility(View.INVISIBLE);
        commentTv.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.add2shopcar)
    public void onViewClicked() {
        mProductCount = mIntroduceFragment.getCount();
        JDApplication jdApplication = (JDApplication) getApplication();

        if (mProductCount == 0) {
            tip("请设置购买的数量");
            return;
        }
        if (productVersion.equals("")) {
            tip("请设置购买的型号");
            return;
        }
        mController.sendAsyncMessage(IdiyMessage.ADD2SHOPCAR_ACTION, jdApplication.mRLoginResult.id,
                mProductId, mProductCount, productVersion);
    }

    @Override
    public void onProductVersionSelected(String version) {
        productVersion = version;
    }

    public static class ContainAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();

        public ContainAdapter(FragmentManager fm) {
            super(fm);
            ProductCommentFragment commentFragment = new ProductCommentFragment();
            ProductDetailsFragment detailsFragment = new ProductDetailsFragment();
            mIntroduceFragment = new ProductIntroduceFragment();
            mFragments.add(mIntroduceFragment);
            mFragments.add(detailsFragment);
            mFragments.add(commentFragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
