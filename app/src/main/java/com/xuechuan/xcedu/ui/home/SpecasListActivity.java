package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.SpecsOrderAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.SpecasChapterListVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: SpecasActivity
 * @Package com.xuechuan.xcedu.ui.home
 * @Description: 规范页
 * @author: L-BackPacker
 * @date: 2018/4/19 17:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/19
 */
public class SpecasListActivity extends BaseActivity {

    private RecyclerView mRlvSpecaContent;

    private static String PARAME = "PARAME";
    private static String PARAME1 = "PARAME";
    private String parame;
    private String parame1;
    private Context mContext;
    private XRefreshView mXrfvSpecaRefresh;
    private List mArrary;
    /**
     * 刷新时间
     */
    public static long lastRefreshTime;
    private SpecsOrderAdapter adapter;
    /**
     * 防止冲突
     */
    private boolean isRefresh = false;
    private ImageView mTvNetEmptyContent;

    public static Intent newInstance(Context context, String parame, String parame1) {
        Intent intent = new Intent(context, SpecasListActivity.class);
        intent.putExtra(PARAME, parame);
        intent.putExtra(PARAME1, parame1);
        return intent;
    }

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specas);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_specas);
        if (getIntent() != null) {
            parame = getIntent().getStringExtra(PARAME);
            parame1 = getIntent().getStringExtra(PARAME1);
        }
        initView();
        clearData();
        bindAdapterData();
        initXrfresh();
        mXrfvSpecaRefresh.startRefresh();
    }

    private void requestData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        HomeService service = HomeService.getInstance(mContext);
        service.requestChapterList(1, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                mXrfvSpecaRefresh.stopRefresh();
                isRefresh = false;
                String s = response.body().toString();
                L.e("获取规范章节列表数据" + s);
                Gson gson = new Gson();
                SpecasChapterListVo vo = gson.fromJson(s, SpecasChapterListVo.class);
                if (vo.getStatus().getCode() == 200) {//成功
                    List list = vo.getDatas();
                    clearData();
                    if (list != null && !list.isEmpty()) {
                        addListData(list);
                    } else {
                        mXrfvSpecaRefresh.setLoadComplete(true);
                        adapter.notifyDataSetChanged();
                        return;
                    }

//                    if ( mArrary.size() == vo.getTotal().getTotal()) {
//                        mXrfvSpecaRefresh.setLoadComplete(true);
//                    } else {
                    //判断是否能整除
                    if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                        mXrfvSpecaRefresh.setLoadComplete(false);
                        mXrfvSpecaRefresh.setPullLoadEnable(true);
                    } else {
                        mXrfvSpecaRefresh.setLoadComplete(true);
                    }
//                    }
                    adapter.notifyDataSetChanged();
                } else {
                    isRefresh = false;
                    T.showToast(mContext, getStringWithId(R.string.net_error));
                    L.e(vo.getStatus().getMessage());
                }

            }

            @Override
            public void onError(Response<String> response) {
                isRefresh = false;
                mXrfvSpecaRefresh.stopRefresh();
                T.showToast(mContext, getStringWithId(R.string.net_error));
                L.e("获取规范章节错误" + response.message());
            }
        });
    }

    private void LoadMoreData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        HomeService service = HomeService.getInstance(mContext);
        service.requestChapterList(getNowPage() + 1, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                isRefresh = false;
                String s = response.body().toString();
                L.e("获取规范章节列表数据" + s);
                L.e(getNowPage() + "集合长度" + mArrary.size());
                Gson gson = new Gson();
                SpecasChapterListVo vo = gson.fromJson(s, SpecasChapterListVo.class);
                if (vo.getStatus().getCode() == 200) {//成功
                    List list = vo.getDatas();
//                    clearData();
                    if (list != null && !list.isEmpty()) {
                        addListData(list);
                    } else {
                        mXrfvSpecaRefresh.setLoadComplete(true);
                        adapter.notifyDataSetChanged();
                        return;
                    }
                    //判断是否能整除
                    if (!mArrary.isEmpty() && mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                        mXrfvSpecaRefresh.setLoadComplete(false);
                        mXrfvSpecaRefresh.setPullLoadEnable(true);
                    } else {
                        mXrfvSpecaRefresh.setLoadComplete(true);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    isRefresh = false;
                    mXrfvSpecaRefresh.setLoadComplete(false);
                    T.showToast(mContext, getStringWithId(R.string.net_error));
                    L.e(vo.getStatus().getMessage());
                }

            }

            @Override
            public void onError(Response<String> response) {
                isRefresh = false;
                mXrfvSpecaRefresh.setLoadComplete(false);
                T.showToast(mContext, getStringWithId(R.string.net_error));
                L.e("获取规范章节错误" + response.message());
            }
        });
    }

    /**
     * 绑定适配器数据
     */
    private void bindAdapterData() {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        adapter = new SpecsOrderAdapter(mContext, mArrary, gridLayoutManager);
        mRlvSpecaContent.setLayoutManager(gridLayoutManager);
        mRlvSpecaContent.addItemDecoration(new DividerItemDecoration(mContext, GridLayoutManager.VERTICAL));
        mRlvSpecaContent.setAdapter(adapter);


    }

    private void initXrfresh() {
        mXrfvSpecaRefresh.restoreLastRefreshTime(lastRefreshTime);
        mXrfvSpecaRefresh.setPullLoadEnable(true);
        mXrfvSpecaRefresh.setAutoLoadMore(true);
        mXrfvSpecaRefresh.setPullRefreshEnable(true);
        mXrfvSpecaRefresh.setEmptyView(mTvNetEmptyContent);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        mXrfvSpecaRefresh.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                requestData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                LoadMoreData();
            }
        });
        adapter.setClickListener(new SpecsOrderAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {

            }
        });
    }


    private void initView() {
        mContext = this;
        mRlvSpecaContent = (RecyclerView) findViewById(R.id.rlv_speca_content);
        mXrfvSpecaRefresh = (XRefreshView) findViewById(R.id.xrfv_speca_refresh);

        mTvNetEmptyContent = (ImageView) findViewById(R.id.tv_net_empty_content);
    }

    private void clearData() {
        if (mArrary == null) {
            mArrary = new ArrayList();
        } else {
            mArrary.clear();
        }
    }

    private void addListData(List<?> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (mArrary == null) {
            clearData();
        }
        mArrary.addAll(list);
    }

    /**
     * 当前数据有几页
     *
     * @return
     */
    private int getNowPage() {
        if (mArrary == null || mArrary.isEmpty())
            return 0;
        if (mArrary.size() % DataMessageVo.CINT_PANGE_SIZE == 0)
            return mArrary.size() / DataMessageVo.CINT_PANGE_SIZE;
        else
            return mArrary.size() / DataMessageVo.CINT_PANGE_SIZE + 1;
    }

}
