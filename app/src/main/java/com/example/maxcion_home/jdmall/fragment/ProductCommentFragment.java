package com.example.maxcion_home.jdmall.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maxcion_home.jdmall.R;
import com.example.maxcion_home.jdmall.activity.ProductDetialsActivity;
import com.example.maxcion_home.jdmall.adapter.CommentDetailAdapter;
import com.example.maxcion_home.jdmall.bean.RcommentCount;
import com.example.maxcion_home.jdmall.bean.RcommentDetail;
import com.example.maxcion_home.jdmall.cons.IdiyMessage;
import com.example.maxcion_home.jdmall.controls.ProductDetailsController;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lean on 16/10/28.
 */

public class ProductCommentFragment extends BaseFragment {

    @BindView(R.id.all_comment_tip)
    TextView allCommentTip;
    @BindView(R.id.all_comment_tv)
    TextView allCommentTv;
    @BindView(R.id.all_comment_ll)
    LinearLayout allCommentLl;
    @BindView(R.id.positive_comment_tip)
    TextView positiveCommentTip;
    @BindView(R.id.positive_comment_tv)
    TextView positiveCommentTv;
    @BindView(R.id.positive_comment_ll)
    LinearLayout positiveCommentLl;
    @BindView(R.id.center_comment_tip)
    TextView centerCommentTip;
    @BindView(R.id.center_comment_tv)
    TextView centerCommentTv;
    @BindView(R.id.center_comment_ll)
    LinearLayout centerCommentLl;
    @BindView(R.id.nagetive_comment_tip)
    TextView nagetiveCommentTip;
    @BindView(R.id.nagetive_comment_tv)
    TextView nagetiveCommentTv;
    @BindView(R.id.nagetive_comment_ll)
    LinearLayout nagetiveCommentLl;
    @BindView(R.id.has_image_comment_tip)
    TextView hasImageCommentTip;
    @BindView(R.id.has_image_comment_tv)
    TextView hasImageCommentTv;
    @BindView(R.id.has_image_comment_ll)
    LinearLayout hasImageCommentLl;
    @BindView(R.id.comment_lv)
    RecyclerView commentRv;
    Unbinder unbinder;
    private ProductDetailsController mProductDetaController;
    private ProductDetialsActivity mActivity;

    public static final int ALL_COMMENT=0;
    public static final int GOOD_COMMENT=1;
    public static final int CENTER_COMMENT=2;
    public static final int BAD_COMMENT=3;
    public static final int HASIMAGE_COMMENT=4;
    private int mPid;
    private CommentDetailAdapter mCommentDetailAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_comment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initControl();
        initView();

    }

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what){
            case IdiyMessage.GET_COMMENT_COUNT_ACTION_RESULT:
                handleCommentCountResult(msg.obj);
                break;
         case IdiyMessage.GET_COMMENT_ACTION_RESULT:
                handleCommentDetailResult(msg.obj);
                break;


        }
    }

    private void handleCommentDetailResult(Object obj) {
        if (obj==null){
            Toast.makeText(getContext(),"数据异常",Toast.LENGTH_SHORT).show();
            return;
        }
        List<RcommentDetail> commentDetails = (List<RcommentDetail>) obj;
        mCommentDetailAdapter.setDatas(commentDetails);
        mCommentDetailAdapter.notifyDataSetChanged();
    }

    private void handleCommentCountResult(Object obj) {
        if (obj==null){
            Toast.makeText(getContext(),"数据异常",Toast.LENGTH_SHORT).show();
            return;
        }
        RcommentCount rCommentCount = (RcommentCount) obj;
        allCommentTv.setText(rCommentCount.allComment+"");
        positiveCommentTv.setText(rCommentCount.positiveCom+"");
        centerCommentTv.setText(rCommentCount.moderateCom+"");
        nagetiveCommentTv.setText(rCommentCount.negativeCom+"");
        hasImageCommentTv.setText(rCommentCount.hasImgCom+"");
    }

    @Override
    protected void initView() {
        allCommentLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allCommentTip.setSelected(true);
                allCommentTv.setSelected(true);
                mProductDetaController.sendAsyncMessage(IdiyMessage.GET_COMMENT_ACTION,mPid,ALL_COMMENT);
            }
        });
        mCommentDetailAdapter = new CommentDetailAdapter(getContext());
        commentRv.setLayoutManager(new LinearLayoutManager(getContext()));
        commentRv.setAdapter(mCommentDetailAdapter);
        allCommentLl.performClick();

    }

    @Override
    protected void initControl() {
        mActivity = (ProductDetialsActivity) getActivity();
        mProductDetaController = new ProductDetailsController(getContext());
        mProductDetaController.setIModeChangeListener(this);
        mPid = mActivity.mProductId;
        mProductDetaController.sendAsyncMessage(IdiyMessage.GET_COMMENT_COUNT_ACTION,mPid);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({/*R.id.all_comment_ll,*/ R.id.positive_comment_ll, R.id.center_comment_ll, R.id.nagetive_comment_ll, R.id.has_image_comment_ll})
    public void onViewClicked(View view) {
        defaultIndicator();
        switch (view.getId()) {
          /*  case R.id.all_comment_ll:

                break;*/
            case R.id.positive_comment_ll:
                positiveCommentTip.setSelected(true);
                positiveCommentTv.setSelected(true);
                mProductDetaController.sendAsyncMessage(IdiyMessage.GET_COMMENT_ACTION,mPid,GOOD_COMMENT);
                break;
            case R.id.center_comment_ll:
                centerCommentTip.setSelected(true);
                centerCommentTv.setSelected(true);
                mProductDetaController.sendAsyncMessage(IdiyMessage.GET_COMMENT_ACTION,mPid,CENTER_COMMENT);
                break;
            case R.id.nagetive_comment_ll:
                nagetiveCommentTip.setSelected(true);
                nagetiveCommentTv.setSelected(true);
                mProductDetaController.sendAsyncMessage(IdiyMessage.GET_COMMENT_ACTION,mPid,BAD_COMMENT);
                break;
            case R.id.has_image_comment_ll:
                hasImageCommentTip.setSelected(true);
                hasImageCommentTv.setSelected(true);
                mProductDetaController.sendAsyncMessage(IdiyMessage.GET_COMMENT_ACTION,mPid,HASIMAGE_COMMENT);
                break;
        }
    }

    private void defaultIndicator() {
        allCommentTip.setSelected(false);
        allCommentTv.setSelected(false);
        positiveCommentTip.setSelected(false);
        positiveCommentTv.setSelected(false);
        centerCommentTip.setSelected(false);
        centerCommentTv.setSelected(false);
        nagetiveCommentTip.setSelected(false);
        nagetiveCommentTv.setSelected(false);
        hasImageCommentTip.setSelected(false);
        hasImageCommentTv.setSelected(false);
    }
}
