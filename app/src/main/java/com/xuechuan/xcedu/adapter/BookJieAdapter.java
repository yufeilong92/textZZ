package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.ui.AgreementActivity;
import com.xuechuan.xcedu.ui.InfomDetailActivity;
import com.xuechuan.xcedu.vo.ChildrenBeanVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 教程节适配器
 * @author: L-BackPacker
 * @date: 2018/4/19 16:51
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BookJieAdapter extends RecyclerView.Adapter<BookJieAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<ChildrenBeanVo> mData;
    private final LayoutInflater mInflater;
    private GridLayoutManager manager;
    private onItemClickListener clickListener;
    private ArrayList<Integer> mClickList = new ArrayList<>();

    public interface onItemClickListener {
        public void onClickListener(Object obj, int position);
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public BookJieAdapter(Context mContext, List<ChildrenBeanVo> mData, GridLayoutManager manager) {
        this.mContext = mContext;
        this.mData = mData;
        this.manager = manager;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_jie_layout, null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,  int position) {
        final  int mPostion=position;
        final ChildrenBeanVo vo = mData.get(mPostion);
        holder.mTvItemJieTitle.setText(vo.getTitle());
        if (mClickList.contains(mPostion)) {
            holder.mRlvJieContent.setVisibility(View.VISIBLE);
            holder.mIvJieGo.setImageResource(R.mipmap.ic_spread_gray);
            requestJieData(holder, vo);
        } else {
            holder.mIvJieGo.setImageResource(R.mipmap.ic_more_go);
            holder.mRlvJieContent.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isend = vo.isIsend();
                if (isend) {
                  /*  Intent intent = InfomDetailActivity.startInstance(mContext, vo.getGourl(),
                            String.valueOf(vo.getId()), DataMessageVo.USERTYPEA);*/
                    Intent intent = AgreementActivity.newInstance(mContext, vo.getGourl(),
                            AgreementActivity.NOSHAREMARK,"","");
                    intent.putExtra(InfomDetailActivity.CSTR_EXTRA_TITLE_STR, vo.getTitle());
                    mContext.startActivity(intent);
                    return;
                }
                if (!mClickList.contains(mPostion)) {
                    mClickList.add(mPostion);
                    holder.mIvJieGo.setImageResource(R.mipmap.ic_spread_gray);
                    holder.mRlvJieContent.setVisibility(View.VISIBLE);
                    requestJieData(holder, vo);
                } else {
                    for (int i = 0; i < mClickList.size(); i++) {
                        if (mClickList.get(i) == mPostion) {
                            mClickList.remove(i);
                            holder.mIvJieGo.setImageResource(R.mipmap.ic_more_go);
                            holder.mRlvJieContent.setVisibility(View.GONE);
                        }
                    }
                }

            }
        });
//        holder.itemView.setId(mPostion);
//        holder.itemView.setTag(vo);

    }

    /**
     * 请求节点下是否有数据
     *
     * @param holder
     * @param vo
     */
    private void requestJieData(ViewHolder holder, ChildrenBeanVo vo) {
        BookJieJieAdapter jieJieAdapter = new BookJieJieAdapter(mContext, vo.getChildren());
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        holder.mRlvJieContent.setLayoutManager(manager);
        holder.mRlvJieContent.setAdapter(jieJieAdapter);
        jieJieAdapter.setClickListener(new BookJieJieAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                ChildrenBeanVo vo = (ChildrenBeanVo) obj;
                String gourl = vo.getGourl();
     /*           Intent intent = InfomDetailActivity.startInstance(mContext, gourl,
                        String.valueOf(vo.getId()),
                        DataMessageVo.USERTYPEA);*/
                Intent intent = AgreementActivity.newInstance(mContext,
                        gourl,AgreementActivity.NOSHAREMARK,"","");
                mContext.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            clickListener.onClickListener(v.getTag(), v.getId());
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvItemJieTitle;
        public RecyclerView mRlvJieContent;
        public ImageView mIvJieGo;

        public ViewHolder(View rootView) {
            super(rootView);
            this.mTvItemJieTitle = (TextView) rootView.findViewById(R.id.tv_item_bookjie_title);
            this.mRlvJieContent = (RecyclerView) rootView.findViewById(R.id.rlv_bookjie_content);
            this.mIvJieGo = (ImageView) rootView.findViewById(R.id.iv_jie_go);
        }

    }
}
