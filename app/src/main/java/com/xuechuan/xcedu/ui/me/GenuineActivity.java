package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.mvp.model.ExchangeModelImpl;
import com.xuechuan.xcedu.mvp.presenter.ExchangePresenter;
import com.xuechuan.xcedu.mvp.view.ExchangeView;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ResultVo;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: GenuineActivity
 * @Package com.xuechuan.xcedu.ui.user
 * @Description: 正版验证
 * @author: L-BackPacker
 * @date: 2018/5/22 12:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/22
 */
public class GenuineActivity extends BaseActivity implements View.OnClickListener, ExchangeView {

    private EditText mEtMGCode;
    private Button mBtnMGValue;
    private Context mContext;
    private ExchangePresenter mPresenter;
    private AlertDialog dialog;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genuine);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_genuine);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = new ExchangePresenter(new ExchangeModelImpl(), this);

    }

    private void initView() {
        mEtMGCode = (EditText) findViewById(R.id.et_m_g_code);
        mBtnMGValue = (Button) findViewById(R.id.btn_m_g_value);

        mBtnMGValue.setOnClickListener(this);
        mContext = this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_m_g_value:
                submit();
                break;
        }
    }

    private void submit() {
        String code = getTextStr(mEtMGCode);
        if (TextUtils.isEmpty(code)) {
            T.showToast(mContext, R.string.code_empty1);
            return;
        }
        dialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_loading));
        mPresenter.requestExchangeWithCode(mContext, code);


    }

    @Override
    public void ExchangeSuccess(String com) {
        if (dialog!=null&&dialog.isShowing())
            dialog.dismiss();
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(com, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            T.showToast(mContext, vo.getData().getMessage());
        } else {
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
            L.e(vo.getStatus().getMessage());
        }
    }

    @Override
    public void ExchangeError(String com) {
        if (dialog!=null&&dialog.isShowing())
            dialog.dismiss();
        T.showToast(mContext,getStringWithId(R.string.net_error));
        L.e(com);
    }
}
