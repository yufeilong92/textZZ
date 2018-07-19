package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 主界面切换适配器
 * @author: L-BackPacker
 * @date: 2018/4/11 17:53
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BankFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<String> mTitles;
    private ArrayList<Fragment> mData;
    private Context mContext;
    public BankFragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    public BankFragmentAdapter(FragmentManager fm, Context mContext, ArrayList<Fragment> mData,
                               ArrayList<String > mTitles) {
        super(fm);
        this.mData = mData;
        this.mTitles=mTitles;
        this.mContext = mContext;
    }
    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

/*    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }*/
}
