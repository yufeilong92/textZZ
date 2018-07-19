package com.xuechuan.xcedu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.listener.ExceptionHandler;
import com.vector.update_app.listener.IUpdateDialogFragmentListener;
import com.xuechuan.xcedu.event.ShowItemEvent;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.adapter.MyTagPagerAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.db.DbHelp.DBHelper;
import com.xuechuan.xcedu.fragment.BankFragment;
import com.xuechuan.xcedu.fragment.HomesFragment;
import com.xuechuan.xcedu.fragment.NetFragment;
import com.xuechuan.xcedu.fragment.PersionalFragment;
import com.xuechuan.xcedu.fragment.ShaopFragment;
import com.xuechuan.xcedu.service.UpdataLogSevice;
import com.xuechuan.xcedu.ui.AgreementActivity;
import com.xuechuan.xcedu.ui.InfomDetailActivity;
import com.xuechuan.xcedu.ui.home.YouZanActivity;
import com.xuechuan.xcedu.ui.me.SettingActivity;
import com.xuechuan.xcedu.utils.ArrayToListUtil;
import com.xuechuan.xcedu.utils.CProgressDialogUtils;
import com.xuechuan.xcedu.utils.CompareVersionUtil;
import com.xuechuan.xcedu.utils.FileReadUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.OkGoUpdateHttpUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.AppUpDataVo;
import com.xuechuan.xcedu.vo.VideoSettingVo;
import com.xuechuan.xcedu.weight.NoScrollViewPager;
import com.youzan.androidsdk.YouzanSDK;
import com.youzan.androidsdk.basic.YouzanBasicSDKAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: HomeActivity
 * @Package com.xuechuan.xcedu
 * @Description: 主页
 * @author: L-BackPacker
 * @date: 2018/4/17 8:30
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/17
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<Fragment> mFragmentLists;
    private FragmentManager mSfm;
    private Context mContext;
    /**
     * 填充的布局
     */
    private int mFragmentLayout = R.id.fl_content;
    private static String PARAMS = "Params";
    private static String TYPE = "type";
    public final static String BOOK = "1";
    public final static String VIDEO = "2";
    private String mType;
    private NoScrollViewPager mViewpageContetn;
    private MagicIndicator mMagicindicatorHome;
    public final static String LOGIN_HOME = "loginhome";
    private String mLoginType;
    public final static String mHomeMeType = "4";
    public final static String Type = "4";

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        OkGo.getInstance().cancelTag(mContext);
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }

    public static void newInstance(Context context, String params1) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(PARAMS, params1);
        context.startActivity(intent);
    }

    public static void startInstance(Context context, String type) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }


/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();

    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        if (getIntent() != null) {
            mType = getIntent().getStringExtra(TYPE);
            mLoginType = getIntent().getStringExtra(PARAMS);
        }
        DBHelper.initDb(MyAppliction.getInstance());
        initView();
        initData();
        initMagicIndicator1();
 /*       if (!StringUtil.isEmpty(mType)) {
            if (mType.equals(BOOK)) {
                mViewpageContetn.setCurrentItem(1);
            } else if (mType.equals(VIDEO)) {
                mViewpageContetn.setCurrentItem(2);
            }
        }*/
        doShareActivity();
        initAppUpData();
    }

    /**
     * 更新版本
     */
    private void initAppUpData() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/xuechuan/" + new Date().getTime();
        String hear = mContext.getResources().getString(R.string.app_content_heat);
        String updata = mContext.getResources().getString(R.string.http_upApp);
        String url = hear.concat(updata);
        new UpdateAppManager.Builder()
                .setActivity(HomeActivity.this)
                .setHttpManager(new OkGoUpdateHttpUtil())
                .setUpdateUrl(url)
                .setPost(false)
                .setTopPic(R.mipmap.pop_img_update)
                .setTargetPath(path)
                .setThemeColor(0xf1e4655d)
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        e.printStackTrace();
                    }
                })
                .setUpdateDialogFragmentListener(new IUpdateDialogFragmentListener() {
                    @Override
                    public void onUpdateNotifyDialogCancel(UpdateAppBean updateApp) {

                    }
                })
                .build()
                .checkNewApp(new UpdateCallback() {
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        try {
                            new JsonParser().parse(json);
                            OkGo.getInstance().cancelTag(mContext);
                        } catch (JsonParseException e) {
                            L.e("数据异常");
                            T.showToast(mContext, "服务器正在更新,请稍候点击");
                            e.printStackTrace();
                            return null;
                        }
                        Gson gson = new Gson();

                        AppUpDataVo vo = gson.fromJson(json, AppUpDataVo.class);
                        if (vo.getStatus().getCode() == 200) {
                            UpdateAppBean updateAppBean = new UpdateAppBean();
                            boolean isConstraint = false;
                            AppUpDataVo.DataBean data = vo.getData();
                            String versionCode = Utils.getVersionName(mContext);
                            int i = CompareVersionUtil.compareVersion(data.getVersion(), versionCode);
                            String updata = "No";
                            if (i == 0 || i == -1) {
                                updata = "No";
                            } else if (i == 1) {
                                updata = "Yes";
                                if (data.getType().equals("0")) {
                                    isConstraint = false;
                                } else if (data.getType().equals("1")) {
                                    isConstraint = true;
                                }
                            }
                            updateAppBean.setApkFileUrl(data.getUrl())
                                    //（必须）是否更新Yes,No
                                    .setUpdate(updata)
                                    .setNewVersion(data.getVersion())
                                    //（必须）下载地址
                                    .setApkFileUrl(data.getUrl())
                                    //（必须）更新内容
                                    .setUpdateLog(data.getDepict())
                                    //大小，不设置不显示大小，可以不设置
                                    .setTargetSize(data.getAppsize())
                                    //是否强制更新，可以不设置
                                    .setConstraint(isConstraint);
                            return updateAppBean;
                        } else {
                            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
                            L.e(vo.getStatus().getMessage());
                        }
                        return null;
                    }

                    @Override
                    protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
//                        super.hasNewApp(updateApp, updateAppManager);
                        updateAppManager.showDialogFragment();
//                        showDiyDialog(updateApp, updateAppManager);
                    }

                    @Override
                    protected void onAfter() {
                        CProgressDialogUtils.cancelProgressDialog(HomeActivity.this);
                    }

                    @Override
                    protected void noNewApp(String error) {
//                        T.showToast(mContext, getString(R.string.latest_version));
                    }

                    @Override
                    protected void onBefore() {
                        CProgressDialogUtils.showProgressDialog(HomeActivity.this);
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MianSelectShowItem(ShowItemEvent event) {
        if (mViewpageContetn != null)
            mViewpageContetn.setCurrentItem(0);
        doShareActivity();

    }

    private void doShareActivity() {
        if (!StringUtil.isEmpty(MyAppliction.getInstance().getIsAtricle())) {
            if (MyAppliction.getInstance().getIsAtricle().equals("0")) {
                doIntentAct(new Infom(), MyAppliction.getInstance().getShareParems());
                return;
            }
            if (MyAppliction.getInstance().getIsAtricle().equals("1")) {
                doIntentAct(new WenZhang(), MyAppliction.getInstance().getShareParems());
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!StringUtil.isEmpty(mType)) {
            if (mType.equals(BOOK)) {
                mViewpageContetn.setCurrentItem(1);
            } else if (mType.equals(VIDEO)) {
                mViewpageContetn.setCurrentItem(2);
            }
        }/* else if (!StringUtil.isEmpty(mLoginType) && mLoginType.equals(LOGIN_HOME)) {
            mViewpageContetn.setCurrentItem(0);
        }*/ else if (!StringUtil.isEmpty(mType) && mType.equals(mHomeMeType)) {
            mViewpageContetn.setCurrentItem(3);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mLoginType = "";
        mType = "";
    }

    private void initData() {
        List<Fragment> fragments = getcreateFragment();
        MyTagPagerAdapter adapter = new MyTagPagerAdapter(getSupportFragmentManager(), fragments);
        mViewpageContetn.setAdapter(adapter);
        requestVideoSetting();
//        UpdataLogSevice.startActionFoo(mContext,"13245","15649846");
    }

    private void requestVideoSetting() {
        String videoSetting = mContext.getResources().getString(R.string.http_setting);
        String http = mContext.getResources().getString(R.string.app_content_heat);
        String concat = http.concat(videoSetting);
        OkGo.<String>get(concat)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String com = response.body().toString();
                        L.e("视频设置==" + com);
                        Gson gson = new Gson();
                        VideoSettingVo vo = gson.fromJson(com, VideoSettingVo.class);
                        if (vo.getStatus().getCode() == 200) {
                            if (vo.getData() == null) {
                                return;
                            }
                            YouzanSDK.init(mContext, vo.getData().getYouzanappsetting().getClient_id(), new YouzanBasicSDKAdapter());
                            MyAppliction.getInstance().saveVideoSetting(vo.getData());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    private void initMagicIndicator1() {
        final ArrayList<String> list = ArrayToListUtil.arraytoList(mContext, R.array.home_order_title);
        mMagicindicatorHome.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                View customLayout = LayoutInflater.from(context).inflate(R.layout.simple_pager_title_layout, null);
                final ImageView radioButton = (ImageView) customLayout.findViewById(R.id.rdb_home);
                final TextView titleText = (TextView) customLayout.findViewById(R.id.title_text);
                if (index == 0) {
                    radioButton.setImageResource(R.drawable.ic_tab_home_n);
                } else if (index == 1) {
                    radioButton.setImageResource(R.drawable.ic_home_tab_bank_n);
                } else if (index == 2) {
                    radioButton.setImageResource(R.drawable.common_tab_shop);
                } else if (index == 3) {
                    radioButton.setImageResource(R.drawable.ic_home_tab_course_n);
                } else if (index == 4) {
                    radioButton.setImageResource(R.drawable.ic_home_tab_mine_n);
                }
                titleText.setText(list.get(index));
                commonPagerTitleView.setContentView(customLayout);
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {

                    @Override
                    public void onSelected(int index, int totalCount) {
                        switch (index) {
                            case 0:
                                radioButton.setImageResource(R.drawable.ic_tab_home_s);
                                break;
                            case 1:
                                radioButton.setImageResource(R.drawable.ic_tab_bank_s);
                                break;
                            case 2:
                                //退出有赞
//                                YouzanSDK.userLogout(mContext);
                                Intent intent = YouZanActivity.newInstance(mContext, MyAppliction.getInstance().getYouZanSet().getHomeurl());
                                intent.putExtra(YouZanActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.shaop));
                                startActivity(intent);
                                radioButton.setImageResource(R.drawable.common_tab_shop);
                                break;
                            case 3:
                                radioButton.setImageResource(R.drawable.ic_home_tab_course_s);
                                break;
                            case 4:
                                radioButton.setImageResource(R.drawable.ic_home_tab_mine_s);
                                break;
                        }
                        titleText.setTextColor(getResources().getColor(R.color.red_text));
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        switch (index) {
                            case 0:
                                radioButton.setImageResource(R.drawable.ic_tab_home_n);
                                break;
                            case 1:
                                radioButton.setImageResource(R.drawable.ic_home_tab_bank_n);
                                break;
                            case 2:
                                radioButton.setImageResource(R.drawable.common_tab_shop);
                                break;
                            case 3:
                                radioButton.setImageResource(R.drawable.ic_home_tab_course_n);
                                break;
                            case 4:
                                radioButton.setImageResource(R.drawable.ic_home_tab_mine_n);
                                break;
                        }
                        titleText.setTextColor(getResources().getColor(R.color.text_fu_color));
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
//                        titleImg.setScaleX(1.3f + (0.8f - 1.3f) * leavePercent);
//                        titleImg.setScaleY(1.3f + (0.8f - 1.3f) * leavePercent);
                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
//                        titleImg.setScaleX(0.8f + (1.3f - 0.8f) * enterPercent);
//                        titleImg.setScaleY(0.8f + (1.3f - 0.8f) * enterPercent);
                    }
                });

                commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewpageContetn.setCurrentItem(index);
                    }
                });

                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        mMagicindicatorHome.setNavigator(commonNavigator);
        boolean pad = Utils.isPad(mContext);
        if (pad) {
            mViewpageContetn.setOffscreenPageLimit(2);
        } else {
            mViewpageContetn.setOffscreenPageLimit(4);
        }
        ViewPagerHelper.bind(mMagicindicatorHome, mViewpageContetn);
        mViewpageContetn.setCurrentItem(0);
    }


    protected void initView() {
        mContext = this;
        mViewpageContetn = (NoScrollViewPager) findViewById(R.id.viewpage_contetn);
        mViewpageContetn.setOnClickListener(this);
        mViewpageContetn.setPagingEnabled(false);
        mMagicindicatorHome = (MagicIndicator) findViewById(R.id.magicindicator_home);
        mMagicindicatorHome.setOnClickListener(this);
    }


    private void addFragmentData() {
        mFragmentLists = new ArrayList<>();
//        HomeFragment homeFragment = new HomeFragment();
        HomesFragment homeFragment = new HomesFragment();
        BankFragment bankFragment = new BankFragment();
        NetFragment netFragment = new NetFragment();
        PersionalFragment persionalFragment = new PersionalFragment();
        mFragmentLists.add(homeFragment);
        mFragmentLists.add(bankFragment);
        mFragmentLists.add(netFragment);
        mFragmentLists.add(persionalFragment);
        mSfm = getSupportFragmentManager();
        for (int i = 0; i < mFragmentLists.size(); i++) {
            FragmentTransaction transaction = mSfm.beginTransaction();
            Fragment fragment = mFragmentLists.get(i);
            transaction.add(mFragmentLayout, fragment).hide(fragment).commit();
        }
        FragmentTransaction transaction = mSfm.beginTransaction();
        transaction.show(homeFragment).commit();
    }

    @Override
    public void onClick(final View v) {

    }

    public List<Fragment> getcreateFragment() {
        ArrayList<Fragment> list = new ArrayList<>();
        HomesFragment homeFragment = new HomesFragment();
        BankFragment bankFragment = new BankFragment();
        NetFragment netFragment = new NetFragment();
        ShaopFragment shaopFragment = new ShaopFragment();
        PersionalFragment persionalFragment = new PersionalFragment();
        list.add(homeFragment);
        list.add(bankFragment);
        list.add(shaopFragment);
        list.add(netFragment);
        list.add(persionalFragment);
        return list;
    }


}
