package com.example.maxcion_home.jdmall.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import com.example.maxcion_home.jdmall.controls.BaseController;
import com.example.maxcion_home.jdmall.interfaces.IModeChangeListener;


public abstract class BaseFragment extends Fragment implements IModeChangeListener {

    protected BaseController mControl ;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            handlerMessage(msg);
        }
    };

    protected abstract void handlerMessage(Message msg) ;

    protected abstract void initView();

    protected abstract void initControl();

    @Override
    public void onModeChanged(int action, Object... values) {
        handler.obtainMessage(action, values[0]).sendToTarget();
    }
}
