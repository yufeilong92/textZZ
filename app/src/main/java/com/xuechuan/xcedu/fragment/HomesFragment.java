package com.xuechuan.xcedu.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.HomsAdapter;
import com.xuechuan.xcedu.adapter.SpecsOrderAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.BaseVo;
import com.xuechuan.xcedu.mvp.contract.HomeContract;
import com.xuechuan.xcedu.mvp.model.HomeModel;
import com.xuechuan.xcedu.mvp.presenter.HomePresenter;
import com.xuechuan.xcedu.ui.home.AddressShowActivity;
import com.xuechuan.xcedu.ui.home.SearchActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.PushXmlUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.HomePageVo;
import com.xuechuan.xcedu.weight.AddressTextView;
import com.xuechuan.xcedu.weight.XRefreshViewLayout;

public class HomesFragment extends BaseFragment implements View.OnClickListener, HomeContract.View {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private AddressTextView mTvAddress;
    private LinearLayout mLiSearch;
    private RecyclerView mRlvContentContent;
    private XRefreshViewLayout mXfvHomeContent;
    private Context mContext;

    /**
     * 请求结果码
     */
    private int REQUESTCODE = 1001;
    /**
     * 请求回调码
     */
    public static final int REQUESTRESULT = 1002;
    /**
     * 省份
     */
    public static final String STR_INT_PROVINCE = "province";
    /**
     * code码
     */
    public static String STR_INT_CODE = "code";
    /**
     * 位标
     */
    public static String STR_INT_POSITION = "position";
    /**
     * 地址
     */
    private String provice;
    private long lastRefreshTime;
    private HomePresenter mPresenter;
    private LocationClient mLocationClient;
    private HomsAdapter adapter;

    private HomePageVo homePageVo;

    public HomesFragment() {
        // Required empty public constructor
    }

    public static HomesFragment newInstance(String param1, String param2) {
        HomesFragment fragment = new HomesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /*    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_homes, container, false);
            initView(view);
            return view;
        }*/
    @Override
    public void onStop() {
        super.onStop();
        if (mLocationClient != null)
            mLocationClient.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mLocationClient != null) {
            mLocationClient.restart();
        }
    }

    @Override
    protected int initInflateView() {
        return R.layout.fragment_homes;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
        initAdapter();
        initXrfresh();
//        mXfvHomeContent.startRefresh();
        requestData();
        initBaiduLocation();
    }

    private void initAdapter() {
        adapter = new HomsAdapter(mContext, homePageVo, code);
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvContentContent.setLayoutManager(manager);
        mRlvContentContent.setAdapter(adapter);
    }

    private void initBaiduLocation() {
        mLocationClient = new LocationClient(getActivity());
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(0);
        option.setOpenGps(true);
        option.setLocationNotify(false);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setWifiCacheTimeOut(5 * 60 * 1000);
        option.setEnableSimulateGps(false);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(locationListener);
        mLocationClient.start();

    }

    private String code;
    private BDAbstractLocationListener locationListener = new BDAbstractLocationListener() {


        @Override
        public void onReceiveLocation(BDLocation location) {
            if (StringUtil.isEmpty(location.getProvince())) {
                mLocationClient.restart();
                return;
            }
            String province = location.getProvince();    //获取省份
            mTvAddress.setText(province);
            L.d("定位位置", province);
            code = PushXmlUtil.getInstance().getLocationCode(mContext, province);
            if (!StringUtil.isEmpty(code)) {
                if (mXfvHomeContent != null) {
//                    mXfvHomeContent.startRefresh();
                }
                requestData();
            }
        }
    };

    private void initData() {
        mPresenter = new HomePresenter();
        mPresenter.initModelView(new HomeModel(), this);
        String code = PushXmlUtil.getInstance().getLocationCode(mContext, "全国");
        mPresenter.requestHomePager(mContext, code);
    }

    private void initXrfresh() {
        mXfvHomeContent.restoreLastRefreshTime(lastRefreshTime);
        mXfvHomeContent.setPullLoadEnable(false);
        mXfvHomeContent.setAutoLoadMore(false);
        mXfvHomeContent.setPullRefreshEnable(true);
        mXfvHomeContent.enableReleaseToLoadMore(false);
        mXfvHomeContent.enableRecyclerViewPullUp(true);
        mXfvHomeContent.enablePullUpWhenLoadCompleted(true);
        mXfvHomeContent.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                requestData();
            }
        });

    }

    private void requestData() {
        mPresenter.requestHomePager(mContext, code);
    }

    private void initView(View view) {
        mContext = getActivity();
        mTvAddress = (AddressTextView) view.findViewById(R.id.tv_address);
        mTvAddress.setOnClickListener(this);
        mLiSearch = (LinearLayout) view.findViewById(R.id.li_search);
        mLiSearch.setOnClickListener(this);
        mRlvContentContent = (RecyclerView) view.findViewById(R.id.rlv_content_content);
        mXfvHomeContent = (XRefreshViewLayout) view.findViewById(R.id.xfv_home_content);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.li_search:
                Intent searchIntent = new Intent(mContext, SearchActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.tv_address:
                String provice = getTextStr(mTvAddress);
                Intent intent = AddressShowActivity.newInstance(mContext, provice, AddressShowActivity.TYPEHOME);
                intent.putExtra(AddressShowActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.location));
                startActivityForResult(intent, REQUESTCODE);
                break;
        }
    }

    @Override
    public void Success(String com) {
        mXfvHomeContent.stopRefresh();
        mXfvHomeContent.restoreLastRefreshTime(mXfvHomeContent.getLastRefreshTime());
        Gson gson = new Gson();
        HomePageVo homePageVo = gson.fromJson(com, HomePageVo.class);
        BaseVo.StatusBean status = homePageVo.getStatus();
        if (status.getCode() == 200) {//成功
            adapter.setData(homePageVo, code);
            adapter.notifyDataSetChanged();
        } else {
            T.showToast(mContext, getStrWithId(R.string.net_error));
        }
    }

    @Override
    public void Error(String msg) {
        mXfvHomeContent.stopRefresh();
        adapter.notifyDataSetChanged();
        T.showToast(mContext, getStrWithId(R.string.net_error));
    }

    /**
     * 省份
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE && resultCode == REQUESTRESULT) {
            if (data != null) {
                provice = data.getStringExtra(STR_INT_PROVINCE);
                L.d("地址数据回调", provice);
                mTvAddress.setText(provice);
                code = PushXmlUtil.getInstance().getLocationCode(mContext, provice);
                if (!StringUtil.isEmpty(code)) {
                    if (mXfvHomeContent != null) {
                         mXfvHomeContent.startRefresh();
                    }

                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestData();
            }
        }, 800);
    }
}
