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
 * 待收货页
 */
public class WaitReceiveFragment extends BaseFragment implements IOrderItemClickListener {


    @BindView(R.id.wait_receive_rv)
    RecyclerView compeletOrderRv;
    Unbinder unbinder;
    private int uId;
    private OrderItemAdapter adapter;
    private List<RorderList> orders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wait_receive, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initControl();
        initData();
        initView();
        mControl.sendAsyncMessage(IdiyMessage.COMPLETE_ORDER_ACTION, OrderState.WAIT_RECEIVE_ORDER, uId);
    }

    private void initData() {
        JDApplication application = (JDApplication) getActivity().getApplication();
        uId = application.mRLoginResult.id;
        adapter = new OrderItemAdapter(getContext());
        adapter.setBtnClickListener(this);

    }

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what) {
            case IdiyMessage.COMPLETE_ORDER_ACTION_RESULT:
                handleOrderListResult(msg.obj);
                break;
            case IdiyMessage.CONFIRM_ORDER_ACTION_RESULT:
                handleRecevierProduct(msg.obj);
                break;

        }
    }




    private void handleRecevierProduct(Object obj) {
        Toast.makeText(getContext(),(String)obj,Toast.LENGTH_SHORT).show();
    }

    private void handleOrderListResult(Object obj) {
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
        compeletOrderRv.setLayoutManager(new LinearLayoutManager(getContext()));
        compeletOrderRv.setAdapter(adapter);
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

    }

    @Override
    public void tosendOrder(int position) {

    }

    @Override
    public void toCancleOrder(int position) {

    }

    @Override
    public void sureGetOrder(int position) {
        mControl.sendAsyncMessage(IdiyMessage.CONFIRM_ORDER_ACTION,uId,orders.get(position).oid);
    }
}
