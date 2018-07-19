package com.xuechuan.xcedu.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.adapter.HomeEvaluateAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.TimeSampUtil;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.EvalueVo;
import com.xuechuan.xcedu.vo.SpecasChapterListVo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: InfomActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 评价页详情(暂无)
 * @author: L-BackPacker
 * @date: 2018/4/19 16:35
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/19
 */
public class EvalueDetialActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 文章编号
     */
    private static String ARTICLEID = "articleid ";
    /***
     * commentid
     */
    private static String COMMENTID = "commentid ";

    private static String EVALUEDATA = "evaluedata";
    private RecyclerView mRlvEvalueDetail;
    private XRefreshView mXfvEvauleContent;
    private boolean isRefresh;
    private List mArray;
    private Context mContext;
    private HomeEvaluateAdapter adapter;

    private String mArticleid;
    private String mCommentid;
    private EvalueVo.DatasBean mDataVo;
    private ImageView mIvEvaluateHear;
    private TextView mTvEvalueUserName;
    private TextView mTvEvalueContent;
    private TextView mTvEvalueTime;
    private TextView mTvEvalueEvalue;
    private CheckBox mChbEvaluaIssupper;
    private TextView mTvEvalueSuppernumber;
    private ImageView mTvXfrContentEmpty;

    /**
     * @param context
     * @param articleid
     * @param commentid
     * @return
     */
    public static Intent newInstance(Context context, EvalueVo.DatasBean vo, String articleid, String commentid) {
        Intent intent = new Intent(context, EvalueDetialActivity.class);
        intent.putExtra(ARTICLEID, articleid);
        intent.putExtra(EVALUEDATA, (Serializable) vo);
        intent.putExtra(COMMENTID, commentid);
        return intent;
    }

/*
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evalue_detial);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_evalue_detial);
        if (getIntent() != null) {
            mArticleid = getIntent().getStringExtra(ARTICLEID);
            mCommentid = getIntent().getStringExtra(ARTICLEID);
            mDataVo = (EvalueVo.DatasBean) getIntent().getSerializableExtra(EVALUEDATA);
        }
        initView();
        clearData();
        initData();
        initAdapter();
        initRxfresh();
        mXfvEvauleContent.startRefresh();
    }

    private void initData() {
        MyAppliction.getInstance().displayImages(mIvEvaluateHear,mDataVo.getHeadicon(),true);
        mTvEvalueContent.setText(mDataVo.getContent());
        mTvEvalueTime.setText(TimeSampUtil.getStringTimeStamp(mDataVo.getCreatetime()));
        mTvEvalueUserName.setText(mDataVo.getNickname());

    }

    private void initAdapter() {
        adapter = new HomeEvaluateAdapter(mContext, mArray);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvEvalueDetail.setLayoutManager(gridLayoutManager);
        mRlvEvalueDetail.setAdapter(adapter);

    }

    private void initRxfresh() {
        mXfvEvauleContent.setMoveForHorizontal(true);
        mXfvEvauleContent.setPullLoadEnable(true);
        mXfvEvauleContent.setAutoLoadMore(true);
        mXfvEvauleContent.setPullRefreshEnable(true);
        mXfvEvauleContent.setEmptyView(findViewById(R.id.tv_xfr_content_empty));
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        mXfvEvauleContent.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onLoadMore(boolean isSilence) {
                loadMoreData();
            }

            @Override
            public void onRefresh(boolean isPullDown) {
                loadNewData();
            }
        });
    }

    private void loadMoreData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        HomeService service = new HomeService(mContext);
        service.requestCommentCommentList(getNowPage() + 1, mArticleid, mCommentid, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                isRefresh = false;
                mXfvEvauleContent.stopRefresh();
                String s = response.body().toString();
                L.w(s);
                L.e("获取规范章节列表数据" + s);
                Gson gson = new Gson();
                SpecasChapterListVo vo = gson.fromJson(s, SpecasChapterListVo.class);
                if (vo.getStatus().getCode() == 200) {//成功
                    List list = vo.getDatas();
//                    clearData();
                    if (list != null && !list.isEmpty()) {
                        addListData(list);
                    } else {
                        mXfvEvauleContent.setLoadComplete(true);
                        adapter.notifyDataSetChanged();
                        return;
                    }
                    //判断是否能整除
                    if (!mArray.isEmpty() && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                        mXfvEvauleContent.setLoadComplete(false);
                        mXfvEvauleContent.setPullLoadEnable(true);
                    } else {
                        mXfvEvauleContent.setLoadComplete(true);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    isRefresh = false;
                    T.showToast(mContext,getStringWithId(R.string.net_error));
                }
            }

            @Override
            public void onError(Response<String> response) {
                isRefresh = false;
                T.showToast(mContext,getStringWithId(R.string.net_error));
            }
        });

    }

    private void loadNewData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        HomeService service = new HomeService(mContext);
        service.requestCommentCommentList(1, mArticleid, mCommentid, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                isRefresh = false;
                mXfvEvauleContent.stopRefresh();
                String message = response.body().toString();
                L.w(message);
                Gson gson = new Gson();
                EvalueVo vo = gson.fromJson(message, EvalueVo.class);
                if (vo.getStatus().getCode() == 200) {//成功
                    List list = vo.getDatas();
                    clearData();
                    if (list != null && !list.isEmpty()) {
                        addListData(list);
                    } else {
                        mXfvEvauleContent.setLoadComplete(true);
                        adapter.notifyDataSetChanged();
                        return;
                    }
                    if (mArray.size() < DataMessageVo.CINT_PANGE_SIZE || mArray.size() == vo.getTotal().getTotal()) {
                        mXfvEvauleContent.setLoadComplete(true);
                    } else {
                        mXfvEvauleContent.setPullLoadEnable(true);
                        mXfvEvauleContent.setLoadComplete(false);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    isRefresh = false;
                    T.showToast(mContext,getStringWithId(R.string.net_error));
                }
            }

            @Override
            public void onError(Response<String> response) {
                isRefresh = false;
                T.showToast(mContext,getStringWithId(R.string.net_error));
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    private void initView() {
        mContext = this;
        mRlvEvalueDetail = (RecyclerView) findViewById(R.id.rlv_evalue_detail);
        mXfvEvauleContent = (XRefreshView) findViewById(R.id.xfv_evaule_content);
        mIvEvaluateHear = (ImageView) findViewById(R.id.iv_evaluate_hear);
        mIvEvaluateHear.setOnClickListener(this);
        mTvEvalueUserName = (TextView) findViewById(R.id.tv_evalue_user_name);
        mTvEvalueUserName.setOnClickListener(this);
        mTvEvalueContent = (TextView) findViewById(R.id.tv_evalue_content);
        mTvEvalueContent.setOnClickListener(this);
        mTvEvalueTime = (TextView) findViewById(R.id.tv_evalue_time);
        mTvEvalueTime.setOnClickListener(this);
        mTvEvalueEvalue = (TextView) findViewById(R.id.tv_evalue_evalue);
        mTvEvalueEvalue.setOnClickListener(this);
        mChbEvaluaIssupper = (CheckBox) findViewById(R.id.chb_evalua_issupper);
        mChbEvaluaIssupper.setOnClickListener(this);
        mTvEvalueSuppernumber = (TextView) findViewById(R.id.tv_evalue_suppernumber);
        mTvEvalueSuppernumber.setOnClickListener(this);
        mTvXfrContentEmpty = (ImageView) findViewById(R.id.tv_xfr_content_empty);
        mTvXfrContentEmpty.setOnClickListener(this);
    }

    /**
     * 当前数据有几页
     *
     * @return
     */
    private int getNowPage() {
        if (mArray == null || mArray.isEmpty())
            return 0;
        if (mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0)
            return mArray.size() / DataMessageVo.CINT_PANGE_SIZE;
        else
            return mArray.size() / DataMessageVo.CINT_PANGE_SIZE + 1;
    }

    private void clearData() {
        if (mArray == null) {
            mArray = new ArrayList();
        } else {
            mArray.clear();
        }
    }

    private void addListData(List<?> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (mArray == null) {
            clearData();
        }
        mArray.addAll(list);
    }


}
