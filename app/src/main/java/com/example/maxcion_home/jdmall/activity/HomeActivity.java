package com.example.maxcion_home.jdmall.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.fragment.CategoryFragment;
import com.example.maxcion_home.jdmall.fragment.HomeFragment;
import com.example.maxcion_home.jdmall.fragment.MyJDFragment;
import com.example.maxcion_home.jdmall.fragment.ShopCarFragment;
import com.example.maxcion_home.jdmall.interfaces.IBottomBarClickListener;
import com.example.maxcion_home.jdmall.ui.BottomBar;

import butterknife.ButterKnife;

public class HomeActivity extends FragmentActivity implements IBottomBarClickListener{


    private FragmentManager man;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        man = getSupportFragmentManager();
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        bottomBar.setIBottomBarClickListener(this);
        bottomBar.findViewById(R.id.frag_main_ll).performClick();
    }

    @Override
    public void onItemClick(int action) {

        FragmentTransaction transaction = man.beginTransaction();
        switch (action){
            case R.id.frag_main_ll:
                transaction.replace(R.id.top_bar,new HomeFragment());
                break;
            case R.id.frag_category_ll:
                transaction.replace(R.id.top_bar,new CategoryFragment());
                break;
            case R.id.frag_shopcar_ll:
                transaction.replace(R.id.top_bar,new ShopCarFragment());
                break;
            case R.id.frag_mine_ll:
                transaction.replace(R.id.top_bar,new MyJDFragment());
                break;

        }
        transaction.commit();
    }
}
