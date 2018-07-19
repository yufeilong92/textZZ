package com.xuechuan.xcedu.ui.bank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.AtricleTreeAdapter;
import com.xuechuan.xcedu.adapter.SpeciaListAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.model.SpecaliModellmpl;
import com.xuechuan.xcedu.mvp.presenter.SpecailPresenter;
import com.xuechuan.xcedu.mvp.view.SpecailView;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.SpecialDataVo;

public class SpecialListActivity extends BaseActivity implements SpecailView {

    private RecyclerView mRlvSpecialContent;
    private Context mContext;
    /**
     * 问题id
     */
    private static String MTYPEOID = "mtypeoid";
    private String mTypeOid;
    private AlertDialog mDialog;

    public static Intent newInstance(Context context, String mtypeoid) {
        Intent intent = new Intent(context, SpecialListActivity.class);
        intent.putExtra(MTYPEOID, mtypeoid);
        return intent;
    }
/*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_list);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_special_list);
        if (getIntent() != null) {
            mTypeOid = getIntent().getStringExtra(MTYPEOID);
        }
        initView();
        initData();

    }

    private void initData() {
        SpecailPresenter mPresenter = new SpecailPresenter(this, new SpecaliModellmpl());
        mPresenter.getQuestionTags(mContext, mTypeOid);
        mDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));

    }

    private void initView() {
        mContext = this;
        mRlvSpecialContent = (RecyclerView) findViewById(R.id.rlv_special_content);
    }

    @Override
    public void RequestionWithtagsError(String con) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        T.showToast(mContext, getStringWithId(R.string.net_error));
    }

    @Override
    public void RequestionWithtagsSuccess(String con) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        Gson gson = new Gson();
        SpecialDataVo vo = gson.fromJson(con, SpecialDataVo.class);
        if (vo!=null&&vo.getDatas() != null && !vo.getDatas().isEmpty())
            initAdapter(vo);

    }

    private void initAdapter(SpecialDataVo vo) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        SpeciaListAdapter adapter = new SpeciaListAdapter(mContext, vo.getDatas());
        mRlvSpecialContent.setLayoutManager(gridLayoutManager);
        mRlvSpecialContent.addItemDecoration(new DividerItemDecoration(this, GridLayoutManager.VERTICAL));
        mRlvSpecialContent.setAdapter(adapter);
        adapter.setClickListener(new SpeciaListAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                SpecialDataVo.DatasBean bean = (SpecialDataVo.DatasBean) obj;
                Intent intent = AnswerActivity.newInstance(mContext, mTypeOid,
                        String.valueOf(bean.getId()), 0);
                startActivity(intent);
            }
        });
    }

}
