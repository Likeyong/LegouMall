package com.example.maxcion_home.jdmall.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.activity.ProductDetialsActivity;
import com.example.maxcion_home.jdmall.cons.NetWorkCons;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lean on 16/10/28.
 */

public class ProductDetailsFragment extends BaseFragment {

    @BindView(R.id.webview)
    WebView webview;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    @Override
    protected void handlerMessage(Message msg) {

    }

    @Override
    protected void initView() {
        ProductDetialsActivity activity = (ProductDetialsActivity) getActivity();
        webview.loadUrl(NetWorkCons.PRODUCTDETAIL_URL+"?productId="+activity.mProductId);
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });
        webview.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    protected void initControl() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
