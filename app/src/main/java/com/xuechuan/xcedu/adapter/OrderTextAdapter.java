package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.net.BankService;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 章节练习
 * @author: L-BackPacker
 * @date: 2018/4/26 16:06
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class OrderTextAdapter extends BaseRecyclerAdapter<OrderTextAdapter.ViewHolder> {
    private Context mContext;
    private List<?> mData;
    private final LayoutInflater mInflater;
    private ArrayList<Integer> mClickList = new ArrayList<>();

    public OrderTextAdapter(Context mContext, List<?> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View inflate = mInflater.inflate(R.layout.item_b_text_order, null);
        ViewHolder holder = new ViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,  int position, boolean isItem) {
        final int mPostion=position;
        if (mClickList.contains(mPostion)) {
            holder.mRlvTextBChild.setVisibility(View.VISIBLE);
        } else {
            holder.mRlvTextBChild.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mClickList.contains(mPostion)) {
                    mClickList.add(mPostion);
                    holder.mRlvTextBChild.setVisibility(View.VISIBLE);
                    requestOrderChild(holder);
                } else {
                    for (int i = 0; i < mClickList.size(); i++) {
                        if (mClickList.get(i) == mPostion) {
                            mClickList.remove(i);
                            holder.mRlvTextBChild.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });
    }

    private void requestOrderChild(ViewHolder holder) {
        // TODO: 2018/4/26 显示子节点
        OrderTextAdapter orderTextAdapter = new OrderTextAdapter(mContext,null);
        GridLayoutManager manager = new GridLayoutManager(mContext,1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        holder.mRlvTextBChild.setLayoutManager(manager);
        holder.mRlvTextBChild.setAdapter(orderTextAdapter);
    }

    @Override
    public int getAdapterItemCount() {
        return mData == null ? 0 : mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvBTextImg;
        public TextView mTvTextBTitle;
        public RecyclerView mRlvTextBChild;


        public ViewHolder(View itemView) {
            super(itemView);
            this.mIvBTextImg = (ImageView) itemView.findViewById(R.id.iv_b_text_img);
            this.mTvTextBTitle = (TextView) itemView.findViewById(R.id.tv_text_b_title);
            this.mRlvTextBChild = (RecyclerView) itemView.findViewById(R.id.rlv_text_b_child);

        }
    }


}
