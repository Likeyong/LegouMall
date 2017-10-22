package com.example.maxcion_home.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.maxcion_home.jdmall.JDApplication;
import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.bean.ROrderParams;
import com.example.maxcion_home.jdmall.bean.RResult;
import com.example.maxcion_home.jdmall.bean.Raddress;
import com.example.maxcion_home.jdmall.bean.RaddressBean;
import com.example.maxcion_home.jdmall.bean.RreceivrAddress;
import com.example.maxcion_home.jdmall.bean.Rshopcar;
import com.example.maxcion_home.jdmall.bean.SAddOrderParams;
import com.example.maxcion_home.jdmall.bean.SaddOrderProductParams;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.cons.NetWorkCons;
import com.example.maxcion_home.jdmall.controls.ShopcarController;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettleActivity extends BaseActivity {


    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.address_tv)
    TextView addressTv;
    @BindView(R.id.choose_address_tv)
    TextView chooseAddressTv;
    @BindView(R.id.has_receiver_rl)
    RelativeLayout hasReceiverRl;
    @BindView(R.id.total_psize_tv)
    TextView totalPsizeTv;
    @BindView(R.id.all_price_tv)
    TextView allPriceTv;
    @BindView(R.id.all_price_val_tv)
    TextView allPriceValTv;
    @BindView(R.id.pay_online_tv)
    Button payOnlineTv;
    @BindView(R.id.pay_whenget_tv)
    Button payWhengetTv;
    @BindView(R.id.pay_money_tv)
    TextView payMoneyTv;
    @BindView(R.id.tv_add_address)
    TextView tvAddAddress;
    @BindView(R.id.settle_choose_rv)
    RecyclerView settleChooseRv;
    public static final int ADD_RECEIVER_ADDRESS = 1;
    public static final int CHOOSE_RECEIVER_ADDRESS = 2;
    @BindView(R.id.bt_submitClick)
    Button btSubmitClick;
    private List<Rshopcar> mPchoose;
    private double choose_all_money;
    private int mAddressId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle);
        ButterKnife.bind(this);
        initData();
        initController();
        initView();
    }

    private void initView() {
        totalPsizeTv.setText("共" + mPchoose.size() + "件");
        allPriceValTv.setText(choose_all_money + "");
        settleChooseRv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        settleChooseRv.setAdapter(new CommonAdapter<Rshopcar>(this, R.layout.settle_choose_lv_item, mPchoose) {

            @Override
            protected void convert(ViewHolder holder, Rshopcar rshopcar, int position) {
                Glide.with(SettleActivity.this)
                        .load(NetWorkCons.BASE_URL + rshopcar.pimageUrl)
                        .into((ImageView) holder.getView(R.id.piv));

                holder.setText(R.id.psize, "×" + rshopcar.buyCount);
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        mPchoose = (List<Rshopcar>) intent.getSerializableExtra(
                "CHOOSE_PRODUCT_LIST");
        choose_all_money = intent.getDoubleExtra("CHOOSE_ALL_MONEY", 0);
    }

    private void initController() {
        mController = new ShopcarController(this);
        mController.setIModeChangeListener(this);
    }

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case IdiyMessage.GET_DEFAULT_RECEIVER_ACTION_RESULT:
                handleDefaultAddress(msg.obj);
                break;
            case IdiyMessage.ADD_RECEIVER_ACTION_RESULT:
                handleDefaultAddressBean(msg.obj);
                break;
            case IdiyMessage.ADD_ORDER_ACTION_RESULT:
                handleAddOredere((RResult) msg.obj);
                break;
        }

    }

    private void handleAddOredere(RResult rResult) {
        if (rResult.success) {
            ROrderParams rOrderParams = JSON.parseObject(rResult.result, ROrderParams.class);
            if (rOrderParams.errorType == 1) {
                tip("您来晚了，小店商品被抢空了~~");
            } else if (rOrderParams.errorType == 2) {
                tip("系统错误，请您及时联系攻城狮-_-||");
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View view = View.inflate(this, R.layout.build_order_pop_view, null);
                builder.setView(view);
                ((TextView) view.findViewById(R.id.actual_price_tv)).setText("商品总价：￥"+rOrderParams.allPrice+"");
                ((TextView) view.findViewById(R.id.order_no_tv)).setText("订单编号"+rOrderParams.orderNum+"");
                ((TextView) view.findViewById(R.id.freight_tv)).setText("运费：￥"+rOrderParams.freight+"");
                ((TextView) view.findViewById(R.id.actual_price_tv)).setText("实付：￥"+rOrderParams.allPrice+"");
                final AlertDialog dialog = builder.show();
                ((Button)view.findViewById(R.id.sure_btn)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       dialog.dismiss();
                        start2Activity(SettleActivity.this,OrderListActivity.class,true);
                    }
                });

                ((Button)view.findViewById(R.id.cancal_btn)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }

        } else {
            tip(rResult.errorMsg);
        }
    }

    private void handleDefaultAddressBean(Object obj) {
        if (obj == null) {
            Toast.makeText(this, "信息异常", Toast.LENGTH_SHORT).show();
            return;
        }
        RaddressBean address = (RaddressBean) obj;
        nameTv.setText(address.receiverName);
        phoneTv.setText(address.receiverPhone);
        addressTv.setText(address.receiverAddress);
        mAddressId = address.id;
    }

    private void handleDefaultAddress(Object obj) {
        if (obj == null) {
            addressTv.setText("请填写您的收货地址");
            return;
        }
        RreceivrAddress receiveAddress = (RreceivrAddress) obj;
        nameTv.setText(receiveAddress.receiverName);
        nameTv.setText(receiveAddress.receiverPhone);
        addressTv.setText(receiveAddress.receiverAddress);
        mAddressId = receiveAddress.id;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ADD_RECEIVER_ADDRESS:
                if (resultCode == RESULT_OK) {
                    Raddress address = (Raddress) data.getSerializableExtra("ADDRESS_BEAN");
                    mController.sendAsyncMessage(IdiyMessage.ADD_RECEIVER_ACTION, address);
                }
                break;

            case CHOOSE_RECEIVER_ADDRESS:
                if (resultCode == RESULT_OK) {
                    RreceivrAddress address = (RreceivrAddress) data.getSerializableExtra("CHOOSE_ADDRESS");
                    nameTv.setText(address.receiverName);
                    phoneTv.setText(address.receiverPhone);
                    addressTv.setText(address.receiverAddress);
                    mAddressId = address.id;
                }
                break;
        }
    }

    @OnClick({R.id.tv_add_address, R.id.choose_address_tv, R.id.pay_online_tv, R.id.pay_whenget_tv, R.id.bt_submitClick})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_address:
                Intent toAddReceiverActivity = new Intent(this, AddReceiverActivity.class);
                startActivityForResult(toAddReceiverActivity, ADD_RECEIVER_ADDRESS);
                break;
            case R.id.choose_address_tv:
                Intent toChooseReceiverActivity = new Intent(this, ChooseReceiverActivity.class);
                startActivityForResult(toChooseReceiverActivity, CHOOSE_RECEIVER_ADDRESS);
                break;

            case R.id.pay_online_tv:
                payOnlineTv.setSelected(false);
                payWhengetTv.setSelected(false);
                payOnlineTv.setSelected(true);
                break;
            case R.id.pay_whenget_tv:
                payOnlineTv.setSelected(false);
                payOnlineTv.setSelected(false);
                payWhengetTv.setSelected(true);
                break;
            case R.id.bt_submitClick:
                SAddOrderParams params = new SAddOrderParams();
                if (mAddressId == 0) {
                    tip("请填写正确的收货地址");
                    return;
                }
                params.addrId = mAddressId;
                JDApplication app = (JDApplication) getApplication();
                params.userId = app.mRLoginResult.id;
                List<SaddOrderProductParams> products = new ArrayList<>();
                Rshopcar2AddOrderPP(products);
                params.products = products;
                params.payWay = payOnlineTv.isSelected() ? 0 : 1;

                mController.sendAsyncMessage(IdiyMessage.ADD_ORDER_ACTION, params);
                break;
        }
    }

    private void Rshopcar2AddOrderPP(List<SaddOrderProductParams> products) {
        for (Rshopcar rshopcar : mPchoose) {
            SaddOrderProductParams params1 = new SaddOrderProductParams();
            params1.buyCount = rshopcar.buyCount;
            params1.type = rshopcar.pversion;
            params1.pid = rshopcar.pid;
            products.add(params1);
        }
    }
}

