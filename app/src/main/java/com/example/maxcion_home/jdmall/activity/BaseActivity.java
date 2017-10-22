package com.example.maxcion_home.jdmall.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.controls.BaseController;
import com.example.maxcion_home.jdmall.interfaces.IModeChangeListener;

public class BaseActivity extends AppCompatActivity implements IModeChangeListener {

    protected BaseController mController;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            handlerMessage(msg);
        }
    };

    protected void handlerMessage(Message msg) {
    }

    protected void start2Activity(Context context, Class c, boolean isFinish) {
        Intent i = new Intent(context, c);
        context.startActivity(i);
        if (isFinish) {
            ((Activity) context).finish();
        }

    }

    /*
子线程运行
 */
    @Override
    public void onModeChanged(int action, Object... values) {
        handler.obtainMessage(action, values[0]).sendToTarget();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    public void tip(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
