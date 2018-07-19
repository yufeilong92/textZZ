package com.xuechuan.xcedu.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.adapter.HomeAllAdapter;
import com.xuechuan.xcedu.adapter.HomeContentAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.BaseVo;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.ui.AgreementActivity;
import com.xuechuan.xcedu.ui.InfomDetailActivity;
import com.xuechuan.xcedu.ui.home.AddressShowActivity;
import com.xuechuan.xcedu.ui.home.AdvisoryListActivity;
import com.xuechuan.xcedu.ui.home.ArticleListActivity;
import com.xuechuan.xcedu.ui.home.AtirlceListActivity;
import com.xuechuan.xcedu.ui.home.BookActivity;
import com.xuechuan.xcedu.ui.home.SearchActivity;
import com.xuechuan.xcedu.ui.home.SpecasListActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.PushXmlUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.AdvisoryBean;
import com.xuechuan.xcedu.vo.ArticleBean;
import com.xuechuan.xcedu.vo.BannerBean;
import com.xuechuan.xcedu.vo.HomePageVo;
import com.xuechuan.xcedu.vo.UserInfomVo;
import com.xuechuan.xcedu.weight.AddressTextView;
import com.xuechuan.xcedu.weight.DividerItemDecoration;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: HomeFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 首页展示
 * @author: L-BackPacker
 * @date: 2018/4/11 17:12
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/11
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Banner mBanHome;
    private Context mContext;
    private LocationClient mLocationClient;
    private AddressTextView mTvAddress;
    private LinearLayout mLiSearch;
    private ImageView mIvOption;
    private ImageView mIvLiftOption;
    private RecyclerView mRlvRecommendContent;
    private RecyclerView mRlvRecommendAll;
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
    public static final String STR_INT_CODE = "code";
    /**
     * 位标
     */
    public static final  String STR_INT_POSITION = "position";
    /**
     * 地址
     */
    private String provice;
    private ImageView mIvHomeBook;
    private ImageView mIvHomeStandard;
    private AlertDialog mDialog;
    private TextView mTvInfomMore;
    private TextView mTvArticleMore;
    private String code;
    private LinearLayout mLiRoot;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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

    /*
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home, null);
            initView(view);
            return view;
        }
    */
    public HomeFragment() {
    }

    /**
     * @param param1
     * @param param2
     * @return
     */
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initInflateView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
        initBaiduLocation();
    }

    private void initData() {
        code = PushXmlUtil.getInstance().getLocationCode(mContext, getTextStr(mTvAddress));
        if (!StringUtil.isEmpty(code)) {
//            mDialog = DialogUtil.showDialog(mContext, null, getStrWithId(R.string.loading));
            requestData(code);
        }
    }

    /**
     * 初始百度
     */
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


    /**
     * 请求资讯
     *
     * @param code
     */
    private void requestData(final String code) {
        HomeService service = HomeService.getInstance(mContext);
        service.requestHomePager(code, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                String message = response.body().toString();
                L.d("主界面数据", message);
                Gson gson = new Gson();
                HomePageVo homePageVo = gson.fromJson(message, HomePageVo.class);
                BaseVo.StatusBean status = homePageVo.getStatus();
                if (status.getCode() == 200) {//成功
                    mLiRoot.setVisibility(View.VISIBLE);
                    if (mDialog != null&&mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                    HomePageVo.DataBean data = homePageVo.getData();
                    List<AdvisoryBean> advisory = data.getAdvisory();
                    List<ArticleBean> article = data.getArticle();
                    List<BannerBean> banner = data.getBanner();
                    bindInfomData(advisory);
                    bindAllData(article);
                    bindBanner(banner);

                } else {//失败
                    if (mDialog != null&&mDialog.isShowing())
                        mDialog.dismiss();
                    T.showToast(mContext,getStrWithId(R.string.net_error));
                    L.e(status.getMessage());
                }
            }

            @Override
            public void onError(Response<String> response) {
                if (mDialog != null&&mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                T.showToast(mContext,getStrWithId(R.string.net_error));
                L.e("错误", response.message());
            }
        });

    }

    /**
     * banne图片
     *
     * @param banner
     */
    private void bindBanner(List<BannerBean> banner) {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> url = new ArrayList<>();
        for (int i = 0; i < banner.size(); i++) {
            list.add(banner.get(i).getImageurl());
            url.add(banner.get(i).getGourl());
        }
        bindData(list, url);
    }

    /**
     * 文章
     *
     * @param article
     */
    private void bindAllData(List<ArticleBean> article) {
        HomeAllAdapter allAdapter = new HomeAllAdapter(mContext, article);
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvRecommendAll.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.BOTH_SET, R.drawable.recyclerline));
        mRlvRecommendAll.setLayoutManager(manager);
        mRlvRecommendAll.setAdapter(allAdapter);
        allAdapter.setClickListener(new HomeAllAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                ArticleBean vo = (ArticleBean) obj;
                Intent intent = InfomDetailActivity.startInstance(mContext,
                        vo.getGourl(), String.valueOf(vo.getId())
                        ,vo.getTitle());
//                Intent intent = InfomDetailActivity.startInstance(mContext, String.valueOf(vo.getId()), vo.getGourl(),DataMessageVo.USERTYPEA );
                mContext.startActivity(intent);
            }

        });

    }

    /**
     * 资讯
     *
     * @param advisory
     */
    private void bindInfomData(List<AdvisoryBean> advisory) {
        HomeContentAdapter allAdapter = new HomeContentAdapter(mContext, advisory);
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvRecommendContent.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.BOTH_SET, R.drawable.recyclerline));
        mRlvRecommendContent.setLayoutManager(manager);
        mRlvRecommendContent.setAdapter(allAdapter);
        allAdapter.setClickListener(new HomeContentAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                AdvisoryBean vo = (AdvisoryBean) obj;
              /*  Intent intent = InfomDetailActivity.startInstance(mContext, vo.getGourl(),
                        String.valueOf(vo.getId()), DataMessageVo.USERTYPEA);*/
                Intent intent = AgreementActivity.newInstance(mContext, vo.getGourl(),
                        AgreementActivity.SHAREMARK,vo.getTitle(),vo.getShareurl());
                mContext.startActivity(intent);
            }
        });

    }

    private void initView(View view) {
        mLiRoot = view.findViewById(R.id.ll_home_root);
        mBanHome = view.findViewById(R.id.ban_home);
        mContext = getActivity();
        mTvAddress = (AddressTextView) view.findViewById(R.id.tv_address);
        mTvAddress.setOnClickListener(this);
        mLiSearch = (LinearLayout) view.findViewById(R.id.li_search);
        mLiSearch.setOnClickListener(this);
        mRlvRecommendContent = (RecyclerView) view.findViewById(R.id.rlv_recommend_content);
        mRlvRecommendContent.setOnClickListener(this);
        mRlvRecommendAll = (RecyclerView) view.findViewById(R.id.rlv_recommend_all);
        mRlvRecommendAll.setOnClickListener(this);
        mIvHomeBook = (ImageView) view.findViewById(R.id.iv_home_book);
        mIvHomeBook.setOnClickListener(this);
        mIvHomeStandard = (ImageView) view.findViewById(R.id.iv_home_standard);
        mIvHomeStandard.setOnClickListener(this);
        mTvInfomMore = (TextView) view.findViewById(R.id.tv_infom_more);
        mTvInfomMore.setOnClickListener(this);
        mTvArticleMore = (TextView) view.findViewById(R.id.tv_article_more);
        mTvArticleMore.setOnClickListener(this);
        mRlvRecommendAll.setHasFixedSize(true);
        mRlvRecommendAll.setNestedScrollingEnabled(false);
        mRlvRecommendContent.setHasFixedSize(true);
        mRlvRecommendContent.setNestedScrollingEnabled(false);
    }

    /**
     * banner图
     *
     * @param strings
     * @param list
     */
    private void bindData(ArrayList<String> strings, final ArrayList<String> list) {
        mBanHome.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanHome.setIndicatorGravity(BannerConfig.CENTER);
        mBanHome.setDelayTime(2000);
        mBanHome.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                MyAppliction.getInstance().displayImages(imageView, (String) path, false);
            }
        });
        mBanHome.setImages(strings);
        mBanHome.start();
        mBanHome.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
//                String url = list.get(position - 1);
//                InfomDetailActivity.newInstance(mContext, url);

            }
        });
    }

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
            if (!StringUtil.isEmpty(code))
                requestData(code);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_address://定位
                String provice = getTextStr(mTvAddress);
                Intent intent = AddressShowActivity.newInstance(mContext, provice, AddressShowActivity.TYPEHOME);
                intent.putExtra(AddressShowActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.location));
                startActivityForResult(intent, REQUESTCODE);
                break;
            case R.id.li_search://搜索
                Intent searchIntent = new Intent(mContext, SearchActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.iv_home_book://教材
                Intent intent3 = BookActivity.newInstance(mContext, null, null);
                intent3.putExtra(BookActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.home_teacherMateri));
                startActivity(intent3);
                break;
            case R.id.iv_home_standard://规范
                Intent intent2 = SpecasListActivity.newInstance(mContext, null, null);
                intent2.putExtra(SpecasListActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.home_specs));
                startActivity(intent2);
                break;
            case R.id.tv_infom_more://资讯更多
                String str = null;
                if (StringUtil.isEmpty(code)) {
                    str = getTextStr(mTvAddress);
                    code = PushXmlUtil.getInstance().getLocationCode(mContext, str);
                } else {
                    str = PushXmlUtil.getInstance().getLocationProvice(mContext, code);
                }
                Intent intent1 = AdvisoryListActivity.newInstance(mContext, code,str);
//                intent1.putExtra(AdvisoryListActivity.CSTR_EXTREA_TITLE, str);
                startActivity(intent1);
                break;
            case R.id.tv_article_more://文章更多
                Intent instance = AtirlceListActivity.newInstance(mContext, "");
                instance.putExtra(ArticleListActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.home_infom_all));
                startActivity(instance);
                break;
        }
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
                if (!StringUtil.isEmpty(code))
                    requestData(code);
            }
        }
    }


}
