package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.multilevel.treelist.Node;
import com.multilevel.treelist.TreeRecyclerAdapter;
import com.xuechuan.xcedu.R;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: AtricleTreeAdapter.java
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 树状列表
 * @author: YFL
 * @date: 2018/5/1 14:31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/1 星期二
 * 注意：本内容仅限于学川教育有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class FreeQuestionAdapter extends TreeRecyclerAdapter {
    public FreeQuestionAdapter(RecyclerView mTree, Context context, List<Node> datas, int defaultExpandLevel, int iconExpand, int iconNoExpand) {
        super(mTree, context, datas, defaultExpandLevel, iconExpand, iconNoExpand);
    }

    public FreeQuestionAdapter(RecyclerView mTree, Context context, List<Node> datas, int defaultExpandLevel) {
        super(mTree, context, datas, defaultExpandLevel);
    }

    @Override
    public void onBindViewHolder(final Node node, RecyclerView.ViewHolder holder, int position) {

        final FreeViewHolder holder1 = (FreeViewHolder) holder;
        holder1.mChbFreeQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChecked(node,holder1.mChbFreeQuestion.isChecked());
            }
        });
        if (node.isChecked()){
           holder1.mChbFreeQuestion.setChecked(true);
        }else {
           holder1.mChbFreeQuestion.setChecked(false);
        }
        holder1.mTvFreeTitle.setText(node.getName());


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FreeViewHolder holder = new FreeViewHolder(View.inflate(mContext, R.layout.item_free_question, null));
        return holder;
    }

    public static class FreeViewHolder extends RecyclerView.ViewHolder{
        public CheckBox mChbFreeQuestion;
        public TextView mTvFreeTitle;

        public FreeViewHolder(View itemView) {
            super(itemView);
            this.mChbFreeQuestion = (CheckBox) itemView.findViewById(R.id.chb_free_question);
            this.mTvFreeTitle = (TextView) itemView.findViewById(R.id.tv_free_title);
        }

    }
}
