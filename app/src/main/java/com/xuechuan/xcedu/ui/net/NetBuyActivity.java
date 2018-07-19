package com.xuechuan.xcedu.ui.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xuechuan.xcedu.HomeActivity;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.model.PayModelImpl;
import com.xuechuan.xcedu.mvp.presenter.PayPresenter;
import com.xuechuan.xcedu.mvp.view.PayUtilView;
import com.xuechuan.xcedu.mvp.view.PayView;
import com.xuechuan.xcedu.ui.BuyResultActivity;
import com.xuechuan.xcedu.ui.bank.BankBuyActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.PayUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.BuyFromResultVo;
import com.xuechuan.xcedu.vo.BuyFromVo;
import com.xuechuan.xcedu.vo.BuyZfbResultVo;
import com.xuechuan.xcedu.vo.CoursesBeanVo;
import com.xuechuan.xcedu.vo.PayResult;
import com.xuechuan.xcedu.vo.WechatsignBeanVo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetBuyActivity
 * @Package com.xuechuan.xcedu.ui.net
 * @Description: 网课购买
 * @author: L-BackPacker
 * @date: 2018/5/25 10:41
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/25
 */
public class NetBuyActivity extends BaseActivity implements View.OnClickListener, PayUtilView {

    private ImageView mIvNetPayImg;
    private TextView mTvNetBookMame;
    private TextView mTvNetBookPrice;
    private TextView mTvNPayCount;
    private CheckBox mChbNetPayZfb;
    private LinearLayout mLlNetPayZfb;
    private CheckBox mChbNetPayWeixin;
    private LinearLayout mLlNetWeixin;
    private Button mBtnNetSubmitFrom;
    private LinearLayout mLlBBankPay;
    private static String PAYINFOM = "payinfom";
    private static String KID = "kid";
    private CoursesBeanVo mDataVo;
    private Context mContext;

    private int statusType = -1;
    private int STATUSTYPEWEIXIN = 2;
    private int STATUSTYPEZFB = 1;
    private AlertDialog dialog;
    private IWXAPI api;
    /**
     * 支付包结果
     */
    private static final int SDK_PAY_FLAG = 1;
    private PayUtil payUtil;
    private static String PRICE = "price";
    private static String ID = "id";
    private static String NAME = "name";
    private static String URLIMG = "urlimg";
    private double mPrice;
    private String mName;
    private int mId;
    private String mUrlImg;

    /**
     * @param context
     * @param payinfom
     * @param kid
     * @return
     */
    public static Intent newInstance(Context context, CoursesBeanVo payinfom, String kid) {
        Intent intent = new Intent(context, NetBuyActivity.class);
        intent.putExtra(PAYINFOM, (Serializable) payinfom);
        intent.putExtra(KID, kid);
        return intent;
    }

    /**
     * @param context
     * @return
     */
    public static Intent newInstance(Context context, double price, int id, String name, String urlimg) {
        Intent intent = new Intent(context, NetBuyActivity.class);
        intent.putExtra(PRICE, price);
        intent.putExtra(ID, id);
        intent.putExtra(NAME, name);
        intent.putExtra(URLIMG, urlimg);
        return intent;
    }

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_buy);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_net_buy);
        if (getIntent() != null) {
//            mDataVo = (CoursesBeanVo) getIntent().getSerializableExtra(PAYINFOM);
            mPrice = getIntent().getDoubleExtra(PRICE, 0);
            mName = getIntent().getStringExtra(NAME);
            mId = getIntent().getIntExtra(ID, 0);
            mUrlImg = getIntent().getStringExtra(URLIMG);

        }
        initView();
        initData();
    }

    private void initData() {
        api = WXAPIFactory.createWXAPI(mContext, DataMessageVo.APP_ID);
        api.registerApp(DataMessageVo.APP_ID);
//        mPresenter = new PayPresenter(new PayModelImpl(), this);
        payUtil = PayUtil.getInstance(mContext, NetBuyActivity.this);
        mTvNetBookMame.setText(mName);
        mTvNetBookPrice.setText(String.valueOf(mPrice));
        mTvNPayCount.setText(String.valueOf(mPrice));
        MyAppliction.getInstance().displayImages(mIvNetPayImg, mUrlImg, false);
        mLlNetPayZfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChb(true, false);
            }
        });
        mLlNetWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChb(false, true);
            }
        });
        mChbNetPayWeixin.setChecked(true);
        statusType = 2;
        mChbNetPayWeixin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!mChbNetPayWeixin.isPressed()) return;
                if (isChecked) {
                    showChb(false, true);
                } else {
                    showChb(false, false);
                }
            }
        });
        mChbNetPayZfb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!mChbNetPayZfb.isPressed()) return;
                if (isChecked) {
                    showChb(true, false);
                } else {
                    showChb(false, false);
                }
            }
        });

    }

    private void initView() {
        mContext = this;
        mIvNetPayImg = (ImageView) findViewById(R.id.iv_net_pay_img);
        mTvNetBookMame = (TextView) findViewById(R.id.tv_net_book_mame);
        mTvNetBookPrice = (TextView) findViewById(R.id.tv_net_book_price);
        mTvNPayCount = (TextView) findViewById(R.id.tv_n_pay_count);
        mChbNetPayZfb = (CheckBox) findViewById(R.id.chb_net_pay_zfb);
        mLlNetPayZfb = (LinearLayout) findViewById(R.id.ll_net_pay_zfb);
        mChbNetPayWeixin = (CheckBox) findViewById(R.id.chb_net_pay_weixin);
        mLlNetWeixin = (LinearLayout) findViewById(R.id.ll_net_weixin);
        mBtnNetSubmitFrom = (Button) findViewById(R.id.btn_net_submit_from);
        mLlBBankPay = (LinearLayout) findViewById(R.id.ll_b_bank_pay);
        mBtnNetSubmitFrom.setOnClickListener(this);
    }

    private void showChb(boolean zfb, boolean wein) {
        mChbNetPayZfb.setChecked(zfb);
        mChbNetPayWeixin.setChecked(wein);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_net_submit_from:
                submit();
                break;
        }
    }
/*

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SDK_PAY_FLAG: {

                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    */

    /**
     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
     *//*

                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Intent intent = BuyResultActivity.newInstance(mContext, BuyResultActivity.STATUSSUCCESS);
                        intent.putExtra(BuyResultActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.buyStauts));
                        startActivity(intent);
                        Toast.makeText(NetBuyActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Intent intent = BuyResultActivity.newInstance(mContext, BuyResultActivity.STATUSERROR);
                        intent.putExtra(BuyResultActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.buyStauts));
                        startActivity(intent);
                        Toast.makeText(NetBuyActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
*/
    private void submit() {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(mId);
        if (mChbNetPayWeixin.isChecked()) {
            if (!api.isWXAppInstalled()) {
                T.showToast(mContext, R.string.weixin_installed);
                return;
            }
            statusType = STATUSTYPEWEIXIN;
        } else if (mChbNetPayZfb.isChecked()) {
            statusType = STATUSTYPEZFB;
        }
        if (statusType == -1) {
            T.showToast(mContext, getString(R.string.pay_type));
            return;
        }

        dialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_from));
//        mPresenter.submitPayFrom(mContext, String.valueOf(mDataVo.getPrice()),
//                integers, "app", "");
        payUtil.showDiaolog(dialog);
        if (statusType == 1) {
            payUtil.Submitfrom(PayUtil.ZFB, "", integers, "");
        } else if (statusType == 2) {
            payUtil.Submitfrom(PayUtil.WEIXIN, "", integers, "");
        }

    }
/*

    @Override
    public void SumbitFromSuccess(String con) {

        Gson gson = new Gson();
        BuyFromVo vo = gson.fromJson(con, BuyFromVo.class);
        if (vo.getStatus().getCode() == 200) {
            String orderid = vo.getData().getOrderid();
            if (statusType == 1) {//支付包
                mPresenter.submitPay(mContext, orderid, DataMessageVo.PAYTYPE_ZFB);
            } else if (statusType == 2) {//微信
                mPresenter.submitPay(mContext, orderid, DataMessageVo.PAYTYPE_WEIXIN);
            }
        } else {
            L.e(vo.getStatus().getMessage());
        }

    }

    @Override
    public void SumbitFromError(String con) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void SumbitPaySuccess(String con) {
        if (dialog != null) {
            dialog.dismiss();
        }
        Gson gson = new Gson();
        if (statusType == 2) {//微信
            BuyFromResultVo vo = gson.fromJson(con, BuyFromResultVo.class);
            if (vo.getStatus().getCode() == 200) {
                WechatsignBeanVo wechatsign = vo.getData().getWechatsign();
                requestWeiXinPay(wechatsign);
            } else {
                L.e(vo.getStatus().getMessage());
            }
        } else if (statusType == 1) {//支付包
            BuyZfbResultVo vo = gson.fromJson(con, BuyZfbResultVo.class);
            if (vo.getStatus().getCode() == 200) {
                BuyZfbResultVo.DataBean data = vo.getData();
                requestZFBPay(data);
            } else {
                L.e(vo.getStatus().getMessage());
            }
        }
    }

    private void requestZFBPay(BuyZfbResultVo.DataBean data) {
        final String orderInfo = data.getOrderstring();   // 订单信息
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(NetBuyActivity.this);
                Map<String, String> map = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = map;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void requestWeiXinPay(final WechatsignBeanVo wechatsign) {


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                PayReq request = new PayReq();
                request.appId = DataMessageVo.APP_ID;
                request.partnerId = wechatsign.getPartnerid();
                request.prepayId = wechatsign.getPrepayid();
                request.packageValue = "Sign=WXPay";
                request.nonceStr = wechatsign.getNoncestr();
                request.timeStamp = wechatsign.getTimespan();
                request.sign = wechatsign.getSign();
                api.sendReq(request);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Override
    public void SumbitPayError(String con) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void BookIDSuccess(String con) {

    }

    @Override
    public void BookIDError(String con) {

    }
*/

    @Override
    public void PaySuccess(String type) {
        HomeActivity.startInstance(mContext, HomeActivity.VIDEO);
        finish();
    }

    @Override
    public void PayError(String type) {

    }

    @Override
    public void Dialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
