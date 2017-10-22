package com.example.maxcion_home.jdmall.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.maxcion_home.jdmall.JDApplication;
import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.adapter.OrderItemAdapter;
import com.example.maxcion_home.jdmall.bean.RorderList;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.cons.OrderState;
import com.example.maxcion_home.jdmall.controls.OrderController;
import com.example.maxcion_home.jdmall.interfaces.IOrderItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 全部订单
 */
public class AllOrderFragment extends BaseFragment implements IOrderItemClickListener {

    @BindView(R.id.all_order_rv)
    RecyclerView allOrderRv;
    Unbinder unbinder;
    private int uId;
    private OrderItemAdapter adapter;
    private List<RorderList> orders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initControl();
        initData();
        initView();
        mControl.sendAsyncMessage(IdiyMessage.ALL_ORDER_ACTION, OrderState.WAIT_SEND_ORDER, uId);
    }

    private void initData() {
        JDApplication application = (JDApplication) getActivity().getApplication();
        uId = application.mRLoginResult.id;
        adapter = new OrderItemAdapter(getContext());
        adapter.setBtnClickListener( this);

    }

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case IdiyMessage.ALL_ORDER_ACTION_RESULT:
                handleAllOrderListResult(msg.obj);
                break;
            case IdiyMessage.SEND_PRODUCT_RESULT:
                handleSendProduct(msg.obj);
                break;
            case IdiyMessage.CONFIRM_ORDER_ACTION:
                handleRecevierProduct(msg.obj);
                break;
            case IdiyMessage.CANCLE_ORDER_RESULT:
                handleCancleOrder(msg.obj);
                break;
        }
    }

    private void handleCancleOrder(Object obj) {
        Toast.makeText(getContext(),(String)obj,Toast.LENGTH_SHORT).show();
    }

    private void handleRecevierProduct(Object obj) {
        Toast.makeText(getContext(),(String)obj,Toast.LENGTH_SHORT).show();
    }

    private void handleSendProduct(Object obj) {
        Toast.makeText(getContext(),(String)obj,Toast.LENGTH_SHORT).show();
    }

    private void handleAllOrderListResult(Object obj) {
        if (obj == null) {
            Toast.makeText(getContext(), "数据异常", Toast.LENGTH_SHORT).show();
            return;
        }
        orders = (List<RorderList>) obj;
        adapter.setDates(orders);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void initView() {
        allOrderRv.setLayoutManager(new LinearLayoutManager(getContext()));
        allOrderRv.setAdapter(adapter);
    }

    @Override
    protected void initControl() {
        mControl = new OrderController(getContext());
        mControl.setIModeChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void toPayOrder(int position) {
        Toast.makeText(getContext(),"请在支付宝进行支付",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void tosendOrder(int position) {
        mControl.sendAsyncMessage(IdiyMessage.SEND_PRODUCT,uId,orders.get(position).oid);
    }

    @Override
    public void toCancleOrder(int position) {
        mControl.sendAsyncMessage(IdiyMessage.CANCLE_ORDER,uId,orders.get(position).oid);
    }

    @Override
    public void sureGetOrder(int position) {
        mControl.sendAsyncMessage(IdiyMessage.CONFIRM_ORDER_ACTION,uId,orders.get(position).oid);
    }
}
