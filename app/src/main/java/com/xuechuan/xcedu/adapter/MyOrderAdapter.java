package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.MyOrderVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 我的订单适配器
 * @author: L-BackPacker
 * @date: 2018/5/26 13:24
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyOrderAdapter extends BaseRecyclerAdapter<MyOrderAdapter.ViewHodler> {
    private Context mContext;
    private List mData;
    private final LayoutInflater mInflater;

    public MyOrderAdapter(Context mContext, List mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    private onItemClickListener clickListener;

    public interface onItemClickListener {
        public void onClickListener(MyOrderVo.DatasBean obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private onItemPayClickListener payClickListener;

    public interface onItemPayClickListener {
        public void onPayClickListener(MyOrderVo.DatasBean obj, int position);
    }

    public void setClickListener(onItemPayClickListener clickListener) {
        this.payClickListener = clickListener;
    }

    private onItemCancelClickListener cancelClickListener;

    public interface onItemCancelClickListener {
        public void onCancelClickListener(MyOrderVo.DatasBean obj, int position);
    }

    public void setClickListener(onItemCancelClickListener clickListener) {
        this.cancelClickListener = clickListener;
    }

    private onItemDelClickListener delClickListener;

    public interface onItemDelClickListener {
        public void onDelClickListener(MyOrderVo.DatasBean obj, int position);
    }

    public void setClickListener(onItemDelClickListener clickListener) {
        this.delClickListener = clickListener;
    }


    @Override
    public ViewHodler getViewHolder(View view) {
        return new ViewHodler(view);
    }

    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_my_order, null);
        ViewHodler hodler = new ViewHodler(view);
        return hodler;
    }

    @Override
    public void onBindViewHolder(ViewHodler holder, final int position, boolean isItem) {
        final MyOrderVo.DatasBean vo = (MyOrderVo.DatasBean) mData.get(position);
        holder.mTvMyOrderCode.setText(vo.getOrdernum());
        if (vo.getState() == -1) {
            holder.mIvMyOrderStatus.setImageResource(R.mipmap.m_order_de);
            showBtn(holder, true, true, false);
        } else if (vo.getState()==-10){
            holder.mIvMyOrderStatus.setImageResource(R.mipmap.m_order_yitui);
            showBtn(holder, false, false, true);
        }else {
            holder.mIvMyOrderStatus.setImageResource(R.mipmap.m_order_yi);
            showBtn(holder, false, false, true);
        }

        if (vo.getDiscounts() == 0) {//显示优惠
            showYlayout(holder, false);
        } else {
            showYlayout(holder, true);
        }

        holder.mTvMyOrderYPrice.setText(vo.getDiscounts() + "");
        holder.mTvMyOrderAllPrice.setText(vo.getTotalprice() + "");
        setlistener(holder, position, vo);
        bindViewData(holder, vo);
    }

    private void bindViewData(ViewHodler holder, MyOrderVo.DatasBean vo) {
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        holder.mRlvMyoderInfom.setLayoutManager(manager);
        MyOrderDetailAdapter adapter = new MyOrderDetailAdapter(mContext, vo.getDetails());
        holder.mRlvMyoderInfom.setAdapter(adapter);

    }

    private void setlistener(ViewHodler holder, final int position, final MyOrderVo.DatasBean vo) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClickListener(vo, position);
                }
            }
        });
        holder.mBtnMyOrderCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelClickListener != null) {
                    cancelClickListener.onCancelClickListener(vo, position);
                }
            }
        });
        holder.mBtnMyOrderPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payClickListener != null) {
                    payClickListener.onPayClickListener(vo, position);
                }
            }
        });
        holder.mBtnMyOrderDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delClickListener != null) {
                    delClickListener.onDelClickListener(vo, position);
                }
            }
        });
    }

    /**
     * 显示状态按钮
     *
     * @param holder
     * @param cancel
     * @param pay
     * @param del
     */
    private void showBtn(ViewHodler holder, boolean cancel, boolean pay, boolean del) {
        holder.mBtnMyOrderCancel.setVisibility(cancel ? View.VISIBLE : View.GONE);
        holder.mBtnMyOrderDel.setVisibility(del ? View.VISIBLE : View.GONE);
        holder.mBtnMyOrderPay.setVisibility(pay ? View.VISIBLE : View.GONE);
    }

    private void showYlayout(ViewHodler holder, boolean isShow) {
        holder.mLlMyOrderYh.setVisibility(isShow ? View.VISIBLE : View.GONE);
        holder.mViewLineGray.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getAdapterItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        public TextView mTvMyOrderCode;
        public ImageView mIvMyOrderStatus;
        public RecyclerView mRlvMyoderInfom;
        public View mViewLineGray;
        public TextView mTvMyOrderYPrice;
        public LinearLayout mLlMyOrderYh;
        public View mViewLineGra;
        public TextView mTvMyOrderAllPrice;
        public Button mBtnMyOrderCancel;
        public Button mBtnMyOrderPay;
        public Button mBtnMyOrderDel;

        public ViewHodler(View itemView) {
            super(itemView);
            this.mTvMyOrderCode = (TextView) itemView.findViewById(R.id.tv_my_order_code);
            this.mIvMyOrderStatus = (ImageView) itemView.findViewById(R.id.iv_my_order_status);
            this.mRlvMyoderInfom = (RecyclerView) itemView.findViewById(R.id.rlv_myoder_infom);
            this.mViewLineGray = (View) itemView.findViewById(R.id.view_line_gray);
            this.mTvMyOrderYPrice = (TextView) itemView.findViewById(R.id.tv_my_order_y_price);
            this.mLlMyOrderYh = (LinearLayout) itemView.findViewById(R.id.ll_my_order_yh);
            this.mViewLineGra = (View) itemView.findViewById(R.id.view_line_gra);
            this.mTvMyOrderAllPrice = (TextView) itemView.findViewById(R.id.tv_my_order_all_price);
            this.mBtnMyOrderCancel = (Button) itemView.findViewById(R.id.btn_my_order_cancel);
            this.mBtnMyOrderPay = (Button) itemView.findViewById(R.id.btn_my_order_pay);
            this.mBtnMyOrderDel = (Button) itemView.findViewById(R.id.btn_my_order_del);
        }
    }


}
