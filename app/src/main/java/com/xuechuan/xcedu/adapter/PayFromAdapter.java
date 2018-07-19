package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.vo.BankValueVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 购买信息
 * @author: L-BackPacker
 * @date: 2018/5/22 20:20
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class PayFromAdapter extends RecyclerView.Adapter<PayFromAdapter.ViewHodler> implements View.OnClickListener {
    private Context mContext;
    private List<BankValueVo.DatasBean> mData;
    private final LayoutInflater mInflater;

    public PayFromAdapter(Context mContext, List<BankValueVo.DatasBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    private onItemClickListener clickListener;

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onClickListener((BankValueVo.DatasBean) v.getTag(), v.getId());
        }
    }

    public interface onItemClickListener {
        public void onClickListener(BankValueVo.DatasBean obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_b_pay, null);
        ViewHodler hodler = new ViewHodler(view);
        view.setOnClickListener(this);
        return hodler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        BankValueVo.DatasBean bean = mData.get(position);
        holder.mChbBPaySkill.setText(bean.getName());
        holder.mTvBSkillPayValue.setText(String.valueOf(bean.getPrice()));
        holder.itemView.setTag(bean);
        holder.itemView.setId(position);

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        public CheckBox mChbBPaySkill;
        public TextView mTvBSkillPayValue;

        public ViewHodler(View itemView) {
            super(itemView);
            this.mChbBPaySkill = (CheckBox) itemView.findViewById(R.id.chb_b_pay_skill);
            this.mTvBSkillPayValue = (TextView) itemView.findViewById(R.id.tv_b_skill_pay_value);
        }
    }


}
