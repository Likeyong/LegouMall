package com.example.maxcion_home.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.adapter.BrandAdapter;
import com.example.maxcion_home.jdmall.adapter.CategoryTopAdapter;
import com.example.maxcion_home.jdmall.adapter.ProductListAdapter;
import com.example.maxcion_home.jdmall.bean.ProductSendArg;
import com.example.maxcion_home.jdmall.bean.RProductList;
import com.example.maxcion_home.jdmall.bean.Rbrand;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.controls.CategoryController;
import com.example.maxcion_home.jdmall.interfaces.IProductSortChanegListener;
import com.example.maxcion_home.jdmall.ui.FlexiScrollView;
import com.example.maxcion_home.jdmall.ui.popwindow.ProductSortPopuWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductListActivity extends BaseActivity implements CategoryTopAdapter.OnItemClickListener,
        IProductSortChanegListener {

    @BindView(R.id.bt_back)
    ImageView btBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.all_indicator)
    TextView allIndicator;
    @BindView(R.id.sale_indicator)
    TextView saleIndicator;
    @BindView(R.id.price_indicator)
    TextView priceIndicator;
    @BindView(R.id.choose_indicator)
    TextView chooseIndicator;
    @BindView(R.id.product_lv)
    RecyclerView productLv;
    @BindView(R.id.content_view)
    LinearLayout contentView;
    @BindView(R.id.jd_take_tv)
    TextView jdTakeTv;
    @BindView(R.id.paywhenreceive_tv)
    TextView paywhenreceiveTv;
    @BindView(R.id.justhasstock_tv)
    TextView justhasstockTv;
    @BindView(R.id.minPrice_et)
    EditText minPriceEt;
    @BindView(R.id.maxPrice_et)
    EditText maxPriceEt;
    @BindView(R.id.brand_gv)
    RecyclerView brandGv;
    @BindView(R.id.slide_view)
    FlexiScrollView slideView;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;
    @BindView(R.id.chooseSearchClick)
    TextView chooseSearchClick;
    private int topCategoryId;
    private int productId;
    private List<Rbrand> mRbrans;
    private BrandAdapter brandAdapter;
    ProductSendArg sendArg;
    private ProductSortPopuWindow popuWindow;
    private ProductListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);

        sendArg = new ProductSendArg();
        initData();
        initController();
        initView();
        mController.sendAsyncMessage(IdiyMessage.BRAND_ACTION, topCategoryId);
        mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, sendArg);


    }

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case IdiyMessage.BRAND_ACTION_RESULT:
                mRbrans = (List<Rbrand>) msg.obj;
                handleBrandRecyclerView(mRbrans);
                break;
            case IdiyMessage.PRODUCT_LIST_ACTION_RESULT:
                handleProductListResult((ArrayList<RProductList>) msg.obj);
        }
    }

    private void handleProductListResult(ArrayList<RProductList> productList) {
        mAdapter = new ProductListAdapter(this, R.layout.product_lv_item,productList);
        mAdapter.setOnItemClickListener(new CategoryTopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                RProductList item = mAdapter.getItem(postion);
                Intent intent = new Intent(ProductListActivity.this,ProductDetialsActivity.class);
                intent.putExtra("productId",item.id);
                startActivity(intent);
            }
        });
        productLv.setLayoutManager(new LinearLayoutManager(this));
        productLv.setAdapter(mAdapter);

    }

    private void handleBrandRecyclerView(final List<Rbrand> obj) {
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 3);
        brandAdapter = new BrandAdapter();
        brandAdapter.setOnItemClickListener(this);
        brandAdapter.setmData((ArrayList<Rbrand>) mRbrans, this);
        brandGv.setLayoutManager(manager);
        brandGv.setAdapter(brandAdapter);
        brandAdapter.notifyDataSetChanged();
    }

    private void initController() {
        mController = new CategoryController(this);
        mController.setIModeChangeListener(this);
    }

    private void initView() {
        initMainView();
        initDrawView();
    }

    private void initDrawView() {

    }

    private void initMainView() {
        popuWindow = new ProductSortPopuWindow(this);
        popuWindow.setProductSortListener(this);
    }

    private void initData() {
        productId = getIntent().getIntExtra("productId", 0);
        topCategoryId = getIntent().getIntExtra("topcategoryid", 0);
        sendArg.categoryId = productId;
        sendArg.keyword =getIntent().getStringExtra("keyword") ;
//        Toast.makeText(this,getIntent().getStringExtra("keyword"),0).show();
        if (productId == 0) {
            finish();
            Toast.makeText(this, "数据异常", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.bt_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onItemClick(View view, int postion) {
        sendArg.brandId = mRbrans.get(postion).id;
        brandAdapter.setCurrentItemIndex(postion);
        brandAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.all_indicator, R.id.sale_indicator, R.id.price_indicator,
            R.id.choose_indicator, R.id.chooseSearchClick, R.id.jd_take_tv,
            R.id.paywhenreceive_tv, R.id.justhasstock_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all_indicator:

                popuWindow.onShow(allIndicator);
                break;
            case R.id.sale_indicator:
                int sortType = sendArg.sortType;
                sendArg.sortType = sortType == 0 ? 1 : 0;
                mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, sendArg);
                break;
            case R.id.price_indicator:
                //				排序  0-默认  1-销量 2-价格高到低  3-价格低到高
                sortType = sendArg.sortType;
                if (sortType == 0 || sortType == 1 || sortType == 3) {
                    sendArg.sortType = 2;
                }
                if (sortType == 0 || sortType == 1 || sortType == 2) {
                    sendArg.sortType = 3;
                }
                mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, sendArg);
                break;
            case R.id.choose_indicator:
                drawerlayout.openDrawer(slideView);
                break;
            case R.id.jd_take_tv:
            case R.id.paywhenreceive_tv:
            case R.id.justhasstock_tv:
                view.setSelected(!view.isSelected());
                break;
            case R.id.chooseSearchClick:
                sendArg.deliverChoose = 0;
                if (jdTakeTv.isSelected()) {
                    sendArg.deliverChoose += 1;
                }
                if (paywhenreceiveTv.isSelected()) {
                    sendArg.deliverChoose += 2;
                }
                if (justhasstockTv.isSelected()) {
                    sendArg.deliverChoose += 4;
                }
                String max = maxPriceEt.getText().toString();
                String min = minPriceEt.getText().toString();

                if (!TextUtils.isEmpty(max) && !TextUtils.isEmpty(min)) {
                    sendArg.maxPrice = (int) Double.parseDouble(max);
                    sendArg.minPrice = (int) Double.parseDouble(min);
                }
                mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, sendArg);
                drawerlayout.closeDrawer(slideView);
                break;
        }
    }

    @Override
    public void onSortChanged(int action) {
        switch (action) {
            case IProductSortChanegListener.ALLSORT:
                allIndicator.setText("综合");
                sendArg.filterType = 1;
                mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, sendArg);
                break;
            case IProductSortChanegListener.NEWSSORT:
                allIndicator.setText("新品");
                sendArg.filterType = 2;
                mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, sendArg);
                break;
            case IProductSortChanegListener.COMMENTSORT:
                allIndicator.setText("评价");
                sendArg.filterType = 3;
                mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, sendArg);
                break;


        }
    }


}
