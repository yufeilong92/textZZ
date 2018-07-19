package com.xuechuan.xcedu.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.BookJieAdapter;
import com.xuechuan.xcedu.adapter.BookOrderAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.BookHomePageVo;
import com.xuechuan.xcedu.vo.ChildrenBeanVo;
import com.xuechuan.xcedu.weight.DividerItemDecoration;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: BookInfomActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 教材详情页
 * @author: L-BackPacker
 * @date: 2018/4/19 16:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/19
 */
public class BookInfomActivity extends BaseActivity {
    /**
     * 章
     */
    private RecyclerView mRlvBookinfomZhang;
    /**
     * 节
     */
    private RecyclerView mRlvBookinfomJie;
    /**
     * 类型id
     */
    private static String CEX_INT_ID = "cex_int_id";
    private Context mContext;
    private String mOid;
    private AlertDialog mDialog;

    public static Intent newInstance(Context context, String cex_int_id) {
        Intent intent = new Intent(context, BookInfomActivity.class);
        intent.putExtra(CEX_INT_ID, cex_int_id);
        return intent;
    }
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_book_infom);
        if (getIntent() != null) {
            mOid = getIntent().getStringExtra(CEX_INT_ID);
        }
        initView();
        initData();
        initZhang();
        initJie();
    }

    private void initJie() {

    }

    private void initZhang() {

    }

    private void initData() {
        HomeService service = new HomeService(mContext);
        mDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
        service.requesthCapter(mOid, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                if (mDialog!=null){
                    mDialog.dismiss();
                }
                String message = response.body().toString();
                L.w("教材章节", message);
                Gson gson = new Gson();
                BookHomePageVo vo = gson.fromJson(message, BookHomePageVo.class);
                if (vo.getStatus().getCode() == 200) {
                    bindOrderData(vo.getDatas());
                } else {
                    L.e(vo.getStatus().getMessage());
//                    T.showToast(mContext, vo.getStatus().getMessage());
                }
            }

            @Override
            public void onError(Response<String> response) {
                if (mDialog != null&&mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                T.showToast(mContext,getStringWithId(R.string.net_error));
                L.e("教材章节", response.message());
            }
        });
    }

    /**
     * 绑定章
     *
     * @param datas
     */
    private void bindOrderData(List<BookHomePageVo.DatasBean> datas) {
        final BookOrderAdapter bookOrderAdapter = new BookOrderAdapter(mContext, datas,BookInfomActivity.this);
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
//        mRlvBookinfomZhang.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.BOTH_SET, R.drawable.recyclerline));
        mRlvBookinfomZhang.setLayoutManager(manager);
        mRlvBookinfomZhang.setAdapter(bookOrderAdapter);
        bookOrderAdapter.setClickListener(new BookOrderAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                bookOrderAdapter.selectItem(position);
                BookHomePageVo.DatasBean vo = (BookHomePageVo.DatasBean) obj;
                bindJieData(vo.getChildren());
            }
        });
    }



    /**
     * 绑定节数据
     *
     * @param children
     */
    public void bindJieData(List<ChildrenBeanVo> children) {
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        BookJieAdapter jieOrderAdapter = new BookJieAdapter(mContext, children,manager);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvBookinfomJie.setLayoutManager(manager);
        mRlvBookinfomJie.setAdapter(jieOrderAdapter);

        jieOrderAdapter.setClickListener(new BookJieAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
            }
        });
    }

    private void initView() {
        mRlvBookinfomZhang = (RecyclerView) findViewById(R.id.rlv_bookinfom_zhang);
        mRlvBookinfomJie = (RecyclerView) findViewById(R.id.rlv_bookinfom_jie);
        mContext = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
