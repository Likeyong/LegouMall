package com.example.maxcion_home.jdmall.interfaces;

import android.app.Activity;
import android.content.Intent;
import android.widget.SearchView;

import com.example.maxcion_home.jdmall.activity.ProductListActivity;

/**
 * Created by maxcion_home on 2017/10/30.
 */

public class OnSearchSubmit implements SearchView.OnQueryTextListener {
    private  Activity acticvty;

    public OnSearchSubmit(Activity activity) {
        this.acticvty = activity;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent intent = new Intent(acticvty, ProductListActivity.class);
        intent.putExtra("productId",61);
        intent.putExtra("topcategoryid",1);
        intent.putExtra("keyword",query);
        acticvty.startActivity(intent);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }
}
