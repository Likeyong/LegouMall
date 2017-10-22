package com.example.maxcion_home.jdmall.ui;

/**
 * Created by maxcion_home on 2017/10/10.
 */
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.interfaces.INumberInputListener;


/**
 *	1. 提供输入操作 至少为0 至多为max
 *	2. 提供减操作
 *	3. 提供加操作
 *	4. 提供获取数据的操作
 */
public class NumberInputView extends LinearLayout implements OnClickListener{

    private ImageView mIncreaseIv;
    private EditText mNumberTv;
    private ImageView mDecreaseIv;
    private int mMax;
    private int mCurrentNum;
    private INumberInputListener mListener;

    public void setListener(INumberInputListener mListener) {
        this.mListener = mListener;
    }

    public NumberInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMax(int mMax) {
        this.mMax = mMax;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mIncreaseIv=(ImageView) findViewById(R.id.increase_count);
        mDecreaseIv=(ImageView) findViewById(R.id.decrease_count);
        mIncreaseIv.setOnClickListener(this);
        mDecreaseIv.setOnClickListener(this);
        mNumberTv=(EditText) findViewById(R.id.number_et);
        mNumberTv.addTextChangedListener(new TextChangedListener());
    }

    public boolean ifChanged=true;
    public class TextChangedListener implements TextWatcher{


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!ifChanged) {
                return;
            }
            checkNumber();
        }

    }

    private void checkNumber() {
        ifChanged=false;
        String number = mNumberTv.getText().toString();
        if (!TextUtils.isEmpty(number)) {
            mCurrentNum=Integer.parseInt(number);
            if (mCurrentNum<1) {
                mCurrentNum=1;
                mNumberTv.setText(mCurrentNum+"");
            }
            if (mCurrentNum>mMax) {
                mCurrentNum=mMax;
                mNumberTv.setText(mCurrentNum+"");
            }
        }else {
            mNumberTv.setText("1");
        }
        if (mListener!=null) {
            mListener.onTextChange(Integer.parseInt(mNumberTv.getText().toString()));
        }
        ifChanged=true;
    }

    @Override
    public void onClick(View v) {
        String number = mNumberTv.getText().toString();
        if (TextUtils.isEmpty(number)) {
            mNumberTv.setText("0");
            return;
        }
        mCurrentNum=Integer.parseInt(number);
        if (v.getId()==R.id.increase_count) {
            mCurrentNum++;
        }else if (v.getId()==R.id.decrease_count) {
            mCurrentNum--;
        }
        mNumberTv.setText(mCurrentNum+"");
    }


    public int getCount(){
        return Integer.valueOf(mNumberTv.getText().toString());
    }
}
