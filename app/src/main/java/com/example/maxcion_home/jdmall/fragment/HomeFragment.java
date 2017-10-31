package com.example.maxcion_home.jdmall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.activity.HomeActivity;
import com.example.maxcion_home.jdmall.activity.ProductDetialsActivity;
import com.example.maxcion_home.jdmall.adapter.CategoryTopAdapter;
import com.example.maxcion_home.jdmall.adapter.GetYouLikeKillAdapter;
import com.example.maxcion_home.jdmall.adapter.SecondKillAdapter;
import com.example.maxcion_home.jdmall.bean.Banner;
import com.example.maxcion_home.jdmall.bean.RgetLikeRows;
import com.example.maxcion_home.jdmall.bean.RsecKillRow;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.cons.NetWorkCons;
import com.example.maxcion_home.jdmall.controls.HomeController;
import com.example.maxcion_home.jdmall.interfaces.OnSearchSubmit;
import com.example.maxcion_home.jdmall.ui.FlexiScrollView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by maxcion_home on 2017/9/8.
 */

public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener,
        CategoryTopAdapter.OnItemClickListener {

    @BindView(R.id.ad_vp)
    ViewPager adVp;
    @BindView(R.id.clock)
    ImageView clock;
    @BindView(R.id.seckill_tip_tv)
    TextView seckillTipTv;
    @BindView(R.id.seckill_tv)
    TextView seckillTv;
    @BindView(R.id.recommend_gv)
    RecyclerView recommendGv;
    @BindView(R.id.scrollbar)
    FlexiScrollView scrollbar;
    Unbinder unbinder;
    @BindView(R.id.ad_indicator)
    LinearLayout adIndicatorLl;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private ADAdapter mAdAdapter;
    private ArrayList<Banner> mBannerList;
    private ArrayList<ImageView> imageList;
    private Timer mTimer;
    private SecondKillAdapter secondKillAdapter;


    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case IdiyMessage.GET_BANNER_ACTION_RESULT:
                handleBannerResult((ArrayList<Banner>) msg.obj);
                break;
            case IdiyMessage.SECOND_KILL_ACTION_RESULT:
                handleSecondKillResult((ArrayList<RsecKillRow>) msg.obj);
                break;
            case IdiyMessage.RECOMMEND_PRODUCT_ACTION_RESULT:
                handleGetYouLike((ArrayList<RgetLikeRows>) msg.obj);
                break;
        }
    }

    private void handleGetYouLike(ArrayList<RgetLikeRows> obj) {
        GetYouLikeKillAdapter adapter = new GetYouLikeKillAdapter();
        adapter.setmData(obj, getContext());
        RecyclerView.LayoutManager manner = new GridLayoutManager(getContext(),
                2, LinearLayoutManager.VERTICAL, false);
        manner.setAutoMeasureEnabled(true);
        recommendGv.setLayoutManager(manner);
        recommendGv.setAdapter(adapter);
    }

    private void handleSecondKillResult(ArrayList<RsecKillRow> obj) {
        secondKillAdapter = new SecondKillAdapter();
        secondKillAdapter.setmData(obj, getContext());
        secondKillAdapter.setOnItemClickListener(this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(secondKillAdapter);
    }

    private void handleBannerResult(ArrayList<Banner> obj) {
        mBannerList = obj;
        mAdAdapter = new ADAdapter();
        mAdAdapter.setDatas(obj);

        initView();
        //设置指示器
        setPointIndicator(obj);

        //设置定时器
        setTimer();
    }

    private void setTimer() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = adVp.getCurrentItem();
                        currentItem = (currentItem + 1) % mBannerList.size();
                        adVp.setCurrentItem(currentItem);
                    }
                });
            }
        }, 3000, 3000);
    }

    private void setPointIndicator(ArrayList<Banner> obj) {
        for (int i = 0; i < obj.size(); i++) {
            View view = new View(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(25, 25);
            params.setMargins(15, 0, 0, 0);
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.ad_indicator_bg);
            if (i == 0) {
                view.setEnabled(true);
            } else {
                view.setEnabled(false);
            }
            adIndicatorLl.addView(view);
        }
    }

    @Override
    protected void initView() {
        loadImage();
        adVp.addOnPageChangeListener(this);
        adVp.setAdapter(mAdAdapter);
    }

    private void loadImage() {
        imageList = new ArrayList<>();
        for (int i = 0; i < mBannerList.size(); i++) {
            Banner banner = mBannerList.get(i);
            ImageView imageView = new ImageView(getContext());
            Glide.with(this)
                    .load(NetWorkCons.BASE_URL + banner.adUrl)
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageList.add(imageView);
        }

    }

    @Override
    protected void initControl() {
        mControl = new HomeController(getActivity());
        mControl.setIModeChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int childCount = adIndicatorLl.getChildCount();
        for (int i = 0; i < childCount; i++) {
            adIndicatorLl.getChildAt(i).setEnabled(position == i);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initControl();
        HomeActivity activity = (HomeActivity) getActivity();
        activity.hideTitle(View.VISIBLE);
        activity.reacherHead.setOnQueryTextListener(new OnSearchSubmit(getActivity()));
        mControl.sendAsyncMessage(IdiyMessage.GET_BANNER_ACTION, 1);
        mControl.sendAsyncMessage(IdiyMessage.SECOND_KILL_ACTION, 0);
        mControl.sendAsyncMessage(IdiyMessage.RECOMMEND_PRODUCT_ACTION, 0);
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    @Override
    public void onItemClick(View view, int postion) {
        RsecKillRow item = secondKillAdapter.getItem(postion);
        Intent intent = new Intent(getContext(), ProductDetialsActivity.class);
        intent.putExtra("productId",item.productId);
        startActivity(intent);
    }


    class ADAdapter extends PagerAdapter {

        private ArrayList<Banner> mData;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView((View) imageList.get(position % imageList.size()),
                    0);
            return imageList.get(position % imageList.size());
        }

        @Override
        public int getCount() {
            return mData != null ? mData.size() : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageList.get(position));

        }

        public void setDatas(ArrayList<Banner> obj) {
            mData = obj;
        }
    }
}
