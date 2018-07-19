package com.xuechuan.xcedu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DBHelper;
import com.xuechuan.xcedu.db.DbHelp.DbHelperAssist;
import com.xuechuan.xcedu.db.UserInfomDb;
import com.xuechuan.xcedu.jg.RegisterTag;
import com.xuechuan.xcedu.mvp.model.RefreshTokenModelImpl;
import com.xuechuan.xcedu.mvp.presenter.RefreshTokenPresenter;
import com.xuechuan.xcedu.mvp.view.RefreshTokenView;
import com.xuechuan.xcedu.ui.LoginActivity;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.SaveIsDoneUtil;
import com.xuechuan.xcedu.utils.SaveUUidUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.TokenVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;

import com.xuechuan.xcedu.vo.YouzanuserBean;

import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: PiloActivity
 * @Package com.xuechuan.xcedu
 * @Description: 引导页
 * @author: L-BackPacker
 * @date: 2018/5/14 8:59
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/14
 */

public class PiloActivity extends BaseActivity implements RefreshTokenView, EasyPermissions.PermissionCallbacks {

    private Context mContext;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilo);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pilo);

        initView();
//        startActivity(new Intent(PiloActivity.this, MainActivity.class));
        requesPermission();
    }

    private void requesPermission() {
        if (EasyPermissions.hasPermissions(this, DataMessageVo.Persmission)) {
            initData();
        } else {
            PermissionRequest build = new PermissionRequest.Builder(PiloActivity.this, 0, DataMessageVo.Persmission)
                    .setRationale("请允许使用该app申请的权限，否则，该APP无法正常使用")
                    .setNegativeButtonText(R.string.cancel)
                    .setPositiveButtonText(R.string.allow)
                    .build();
            EasyPermissions.requestPermissions(build);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void initData() {
        DBHelper.initDb(MyAppliction.getInstance());
        String userId = SaveUUidUtil.getInstance().getUserId();
        UserInfomDb userInfomDb = DbHelperAssist.getInstance(mContext).queryWithuuId(userId);
        if (userInfomDb != null && userInfomDb.getVo() != null) {
            MyAppliction.getInstance().setUserInfom(userInfomDb.getVo());
            RefreshTokenPresenter presenter = new RefreshTokenPresenter(new RefreshTokenModelImpl(), this);
            presenter.refreshToken(mContext, userInfomDb.getToken());
        } else {
            String id = SaveIsDoneUtil.getInstance().getUserId();
            if (!StringUtil.isEmpty(id)) {
                Intent intent1 = new Intent(mContext, LoginActivity.class);
                startActivity(intent1);
            } else {
                Intent intent1 = new Intent(mContext, GuideActivity.class);
                startActivity(intent1);
            }
            this.finish();
        }
    }

    @Override
    public void TokenSuccess(String con) {
        L.d(con);
        Gson gson = new Gson();
        TokenVo tokenVo = gson.fromJson(con, TokenVo.class);
        if (tokenVo.getStatus().getCode() == 200) {
            int statusX = tokenVo.getData().getStatusX();
            TokenVo.DataBean data = tokenVo.getData();
            switch (statusX) {
                case -1:
                    SaveUUidUtil.getInstance().delectUUid();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                    this.finish();
                    break;
                case -2:
                    SaveUUidUtil.getInstance().delectUUid();
                    Intent intent1 = new Intent(mContext, LoginActivity.class);
                    startActivity(intent1);
                    this.finish();
                    break;
                case 1:
                    updataToken(data);
                    break;
                default:
            }
        } else {
            SaveUUidUtil.getInstance().delectUUid();
            Intent intent1 = new Intent(mContext, LoginActivity.class);
            startActivity(intent1);
            this.finish();
            L.e(tokenVo.getStatus().getMessage());
        }
    }

    @Override
    public void TokenError(String con) {
        SaveUUidUtil.getInstance().delectUUid();
        Intent intent1 = new Intent(mContext, LoginActivity.class);
        startActivity(intent1);
        this.finish();
    }

    private void updataToken(TokenVo.DataBean data) {
        TokenVo.DataBean.TokenBean token = data.getToken();
//        UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
//        UserBean user = userInfom.getData().getUser();
        YouzanuserBean dataYouZanUser = data.getYouZanUser();
        YouzanuserBean youZanUser = new YouzanuserBean();
        if (dataYouZanUser != null) {
            youZanUser.setAccess_token(dataYouZanUser.getAccess_token());
            youZanUser.setCookie_key(dataYouZanUser.getCookie_key());
            youZanUser.setCookie_value(dataYouZanUser.getCookie_value());
        }

        UserInfomVo userInfom = new UserInfomVo();
        UserBean bean = new UserBean();
        bean.setId(token.getStaffid());
        bean.setToken(token.getSigntoken());
        bean.setTokenexpire(token.getExpiretime());

        UserInfomVo.DataBean dataBean = new UserInfomVo.DataBean();
        dataBean.setUser(bean);
        dataBean.setYouzanuser(youZanUser);

        userInfom.setData(dataBean);

        MyAppliction.getInstance().setUserInfom(userInfom);
        DbHelperAssist.getInstance(mContext).saveUserInfom(userInfom);
        HomeActivity.newInstance(mContext, HomeActivity.LOGIN_HOME);
        //注册激光
        RegisterTag tag = RegisterTag.getInstance(getApplicationContext());
        tag.registJG();
        tag.setTagAndAlias(String.valueOf(data.getToken().getStaffid()));
        this.finish();
    }

    private void initView() {
        mContext = this;
        // TODO: 2018/5/30 激光
//        JPushInterface.stopPush(getApplicationContext());
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {//某些权限已被授予
        List<String> mDATA = Arrays.asList(DataMessageVo.Persmission);
        if (perms.contains(mDATA)) {
            initData();
        } else {
            requesPermission();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {//某些权限已被拒绝
        if (requestCode == 0) {
            List<String> mDATA = Arrays.asList(DataMessageVo.Persmission);
            if (perms.size() != 0) {
                StringBuilder builder = new StringBuilder();
//                if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                for (String perm : perms) {
                    if (perm.equals(DataMessageVo.Persmission[0])) {
                        builder.append("写内存卡、");
                    }
                    if (perm.equals(DataMessageVo.Persmission[1])) {
                        builder.append("读取内存卡、");
                    }
                    if (perm.equals(DataMessageVo.Persmission[2])) {
                        builder.append("定位、");
                    }
                    if (perm.equals(DataMessageVo.Persmission[3])) {
                        builder.append("相机、");
                    }

                }
                new AppSettingsDialog.Builder(this)
                        .setPositiveButton(R.string.allow)
                        .setTitle("权限申请")
                        .setNegativeButton(R.string.cancel)
                        .setRationale("请允许使用该app申请" + builder.toString() + "等权限,\n否则，该APP无法正常使用\n")
                        .build()
                        .show();
//                }
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (EasyPermissions.hasPermissions(mContext, DataMessageVo.Persmission)) {
                initData();
            } else {
                requesPermission();
            }
        }
    }

    public void showPermission(String persion, String cons) {
        PermissionRequest build = new PermissionRequest.Builder(PiloActivity.this,
                0, persion)
                .setRationale("请允许使用该app申请的" + cons + "+权限，否则，该APP无法正常使用")
                .setNegativeButtonText(R.string.cancel)
                .setPositiveButtonText(R.string.allow)
                .build();
        EasyPermissions.requestPermissions(build);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
        MyAppliction.getInstance().finishActivity(this);

    }
}
