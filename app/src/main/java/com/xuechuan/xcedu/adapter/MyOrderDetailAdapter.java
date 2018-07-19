package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.OrderDetailVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/26 15:59
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyOrderDetailAdapter extends RecyclerView.Adapter<MyOrderDetailAdapter.ViewHodle> {
    private Context mContext;
    private List mData;
    private final LayoutInflater inflater;

    public MyOrderDetailAdapter(Context mContext, List mData) {
        this.mContext = mContext;
        this.mData = mData;
        inflater = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public ViewHodle onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_order_detail, null);
        return new ViewHodle(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodle holder, int position) {
        OrderDetailVo vo = (OrderDetailVo) mData.get(position);
        holder.mMyOrderName.setText(vo.getProductname());
        holder.mTvMyOrderPrice.setText(vo.getPrice() + "");
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHodle extends RecyclerView.ViewHolder {
        public TextView mMyOrderName;
        public TextView mTvMyOrderPrice;

        public ViewHodle(View itemView) {
            super(itemView);
            this.mMyOrderName = (TextView) itemView.findViewById(R.id.my_order_name);
            this.mTvMyOrderPrice = (TextView) itemView.findViewById(R.id.tv_my_order_price);
        }
    }

}
