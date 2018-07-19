package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.xuechuan.xcedu.R;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/28 15:45
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AnsewerResultAdpater extends BaseRecyclerAdapter<AnsewerResultAdpater.ViewHoler>{

    private Context mContext;
    private List<?> mData;
    private final LayoutInflater mInflater;

    public AnsewerResultAdpater(Context mContext, List<?> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHoler getViewHolder(View view) {
        return new ViewHoler(view);
    }

    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_answer_resulte, null);
        ViewHoler holer = new ViewHoler(view);
        return holer;
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position, boolean isItem) {

    }

    @Override
    public int getAdapterItemCount() {
        return mData.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        public ViewHoler(View itemView) {
            super(itemView);
        }
    }
}
