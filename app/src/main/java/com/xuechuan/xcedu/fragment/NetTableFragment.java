package com.xuechuan.xcedu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.NetTableAdapter;
import com.xuechuan.xcedu.adapter.SpecsOrderAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.model.NetBookInfomModelImpl;
import com.xuechuan.xcedu.mvp.presenter.NetBookInfomPresenter;
import com.xuechuan.xcedu.mvp.view.NetBookInfomView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ChaptersBeanVo;
import com.xuechuan.xcedu.vo.NetBookTableVo;
import com.xuechuan.xcedu.vo.SpecasChapterListVo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetTableFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 未购买课程表
 * @author: L-BackPacker
 * @date: 2018/5/15 16:40
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/15
 */
public class NetTableFragment extends BaseFragment {

    private static final String CLASSID = "classid";
    private RecyclerView mRlvSpecaContent;
    private Context mContext;
    private NetTableAdapter adapter;
    private List<ChaptersBeanVo> mBookInfoms;
    private TextView empty;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBookInfoms = (List<ChaptersBeanVo>) getArguments().getSerializable(CLASSID);
        }
    }

    public NetTableFragment() {

    }

    public static NetTableFragment newInstance(List<ChaptersBeanVo> list) {
        NetTableFragment fragment = new NetTableFragment();
        Bundle args = new Bundle();
        args.putSerializable(CLASSID, (Serializable) list);
        fragment.setArguments(args);
        return fragment;
    }

    /*
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_net_table, container, false);
            initView(view);
            return view;
        }
    */
    @Override
    protected int initInflateView() {
        return R.layout.fragment_net_table;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        if (mBookInfoms == null || mBookInfoms.isEmpty()) {
            mRlvSpecaContent.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
            return;
        }else {
            mRlvSpecaContent.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
        }
        bindAdapterData();
    }

    private void bindAdapterData() {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        adapter = new NetTableAdapter(mContext, mBookInfoms);
        mRlvSpecaContent.setLayoutManager(gridLayoutManager);
        mRlvSpecaContent.setAdapter(adapter);
    }

    private void initView(View view) {
        mContext = getActivity();
        empty = view.findViewById(R.id.tv_net_empty_content);
        mRlvSpecaContent = (RecyclerView) view.findViewById(R.id.rlv_speca_content);
    }
}

