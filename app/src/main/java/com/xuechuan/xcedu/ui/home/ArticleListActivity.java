package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.ArticleListAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.BaseVo;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.ui.InfomDetailActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ArticleListVo;
import com.xuechuan.xcedu.vo.ArticleVo;
import com.xuechuan.xcedu.weight.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: ArticleListActivity
 * @Package com.xuechuan.xcedu.ui.home
 * @Description: 文章列表
 * @author: L-BackPacker
 * @date: 2018/4/20 9:02
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/20
 */
public class ArticleListActivity extends BaseActivity {

    private RecyclerView mRlvInfomList;
    private Context mContext;

    private static String SAFFID = "saffid";
    private int mSaffid;
    private XRefreshView mXfvContent;
    /**
     * 保存数据
     */
    private List mArray;
    /**
     * 防止重复刷新
     */
    private boolean isRefresh;
    private ArticleListAdapter adapter;
    private static long lastRefreshtime;

    /**
     * @param context
     * @param saffid  用户id
     */
    public static Intent newInstance(Context context, int saffid) {
        Intent intent = new Intent(context, ArticleListActivity.class);
        intent.putExtra(SAFFID, saffid);
        return intent;
    }

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_article_list);
        if (getIntent() != null) {
            mSaffid = getIntent().getIntExtra(SAFFID, 0);
        }
        initView();
        clearData();
        bindAdapterData();
        initXrfresh();
        mXfvContent.startRefresh();
    }

    private void initView() {
        mContext = this;
        mRlvInfomList = (RecyclerView) findViewById(R.id.rlv_infom_list);
        mXfvContent = (XRefreshView) findViewById(R.id.xfv_content);
    }

    private void initXrfresh() {
        mXfvContent.setPullLoadEnable(true);
        mXfvContent.setAutoRefresh(true);
        mXfvContent.setAutoLoadMore(true);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        mXfvContent.restoreLastRefreshTime(lastRefreshtime);
        mXfvContent.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                reqestData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                loadMoreData();

            }
        });
    }


    private void reqestData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        final HomeService service = HomeService.getInstance(mContext);
        service.requestArticleList(mSaffid, 1, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                mXfvContent.stopRefresh();
                lastRefreshtime = mXfvContent.getLastRefreshTime();
                isRefresh = false;
                String message = response.body().toString();
                L.w(message);
                Gson gson = new Gson();
                ArticleListVo vo = gson.fromJson(message, ArticleListVo.class);
                BaseVo.StatusBean status = vo.getStatus();
                if (status.getCode() == 200) {
                    List<ArticleVo> datas = vo.getDatas();
                    clearData();
                    if (datas != null && !datas.isEmpty()) {
                        addListData(datas);
                    }else {
                        adapter.notifyDataSetChanged();
                        mXfvContent.setLoadComplete(true);
                        return;
                    }
//                    if (mArray.size() == vo.getTotal().getTotal()) {
//                        mXfvContent.setLoadComplete(true);
//                    } else {
                    if (mArray != null && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                        mXfvContent.setPullLoadEnable(true);
                        mXfvContent.setLoadComplete(false);
                    } else {
                        mXfvContent.setLoadComplete(true);
                    }
//                    }
                    adapter.notifyDataSetChanged();
                } else {
                    T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
//                    T.showToast(mContext, status.getMessage());
                }
            }

            @Override
            public void onError(Response<String> response) {
                isRefresh = false;
                mXfvContent.stopRefresh();
                T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
                L.e(response.message());
            }
        });
    }

    private void loadMoreData() {
        if (isRefresh) {
            return;
        }
        isRefresh = true;
        final HomeService service = HomeService.getInstance(mContext);
        service.requestArticleList(mSaffid, getPager() + 1, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                isRefresh = false;
                String message = response.body().toString();
                L.w(message);
                Gson gson = new Gson();
                ArticleListVo vo = gson.fromJson(message, ArticleListVo.class);
                BaseVo.StatusBean status = vo.getStatus();
                if (status.getCode() == 200) {
                    List<ArticleVo> datas = vo.getDatas();
//                    clearData();
                    if (datas != null && !datas.isEmpty()) {
                        addListData(datas);
                    } else {
                        mXfvContent.setLoadComplete(true);
                        adapter.notifyDataSetChanged();
                        return;
                    }

                    if (mArray != null && mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                        mXfvContent.setPullLoadEnable(true);
                        mXfvContent.setLoadComplete(false);
                    } else {
                        mXfvContent.setLoadComplete(true);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    mXfvContent.setLoadComplete(false);
                    T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
//                    T.showToast(mContext, status.getMessage());
                }
            }

            @Override
            public void onError(Response<String> response) {
                isRefresh = false;
                mXfvContent.setLoadComplete(false);
                T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
//                L.e(response.message());
            }
        });
    }

    private void bindAdapterData() {
        adapter = new ArticleListAdapter(mContext, mArray);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvInfomList.setLayoutManager(gridLayoutManager);
        mRlvInfomList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.BOTH_SET, R.drawable.recyclerline));
        mRlvInfomList.setAdapter(adapter);
        adapter.setClickListener(new ArticleListAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                ArticleVo vo = (ArticleVo) obj;
                Intent intent = InfomDetailActivity.startInstance(mContext,
                        vo.getGourl(), String.valueOf(vo.getId())
                        , vo.getTitle());
//                Intent intent = InfomDetailActivity.startInstance(mContext, String.valueOf(vo.getId()), vo.getGourl(),DataMessageVo.USERTYPEA );
                mContext.startActivity(intent);
            }
        });

    }

    private void clearData() {
        if (mArray == null) {
            mArray = new ArrayList();
        } else {
            mArray.clear();
        }
    }

    private void addListData(List<?> list) {
        if (mArray == null) {
            clearData();
        }
        if (list == null || list.isEmpty()) {
            return;
        }
        mArray.addAll(list);
    }

    private int getPager() {
        if (mArray == null || mArray.isEmpty()) {
            return 0;
        }
        if (mArray.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
            return mArray.size() / DataMessageVo.CINT_PANGE_SIZE;
        } else {
            return mArray.size() / DataMessageVo.CINT_PANGE_SIZE + 1;
        }
    }

}
