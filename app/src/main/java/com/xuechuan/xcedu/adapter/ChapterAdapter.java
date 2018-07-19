package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xuechuan.xcedu.R;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 全部章节
 * @author: L-BackPacker
 * @date: 2018/4/19 16:31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ChapterAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Object> mData;

    public ChapterAdapter(Context mContext, ArrayList<Object> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mData == null ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_zhang_layout, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView mTvItemChaperTitle;
        public TextView mTvItemChaperContent;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mTvItemChaperTitle = (TextView) rootView.findViewById(R.id.tv_item_chaper_title);
            this.mTvItemChaperContent = (TextView) rootView.findViewById(R.id.tv_item_chaper_content);
        }

    }
}
