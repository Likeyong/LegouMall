package com.example.maxcion_home.jdmall.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.adapter.CategoryTopAdapter;
import com.example.maxcion_home.jdmall.bean.RcategoryItem;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.controls.CategoryController;
import com.example.maxcion_home.jdmall.ui.SubCategoryView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by maxcion_home on 2017/9/8.
 */

public class CategoryFragment extends BaseFragment implements CategoryTopAdapter.OnItemClickListener {


    Unbinder unbinder;
    @BindView(R.id.top_lv)
    RecyclerView topLv;
    @BindView(R.id.subcategory)
    SubCategoryView subcategory;
    private CategoryTopAdapter categoryTopAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initControl();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case IdiyMessage.TOPCATEGORY_ACTION_RESULT:
                hanldeTopCategoryResult((ArrayList<RcategoryItem>) msg.obj);
                break;
        }
    }

    private void hanldeTopCategoryResult(ArrayList<RcategoryItem> obj) {
        categoryTopAdapter = new CategoryTopAdapter();
        categoryTopAdapter.setmData(obj, getContext());
        categoryTopAdapter.setOnItemClickListener(this);
        LinearLayoutManager manner = new LinearLayoutManager(getContext());
        topLv.setLayoutManager(manner);
        topLv.setAdapter(categoryTopAdapter);
        topLv.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if(topLv.findViewHolderForAdapterPosition(0)!=null )
                {
                    topLv.findViewHolderForAdapterPosition(0).itemView.performClick();
                }
            }
        },50);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initControl() {
        CategoryController control = new CategoryController(getContext());
        control.setIModeChangeListener(this);
        control.sendAsyncMessage(IdiyMessage.TOPCATEGORY_ACTION, 0);
    }

    @Override
    public void onItemClick(View view, int postion) {
        categoryTopAdapter.setCurrentItemIndex(postion);
        categoryTopAdapter.notifyDataSetChanged();
        subcategory.onShow(categoryTopAdapter.getItem(postion));

    }
}
