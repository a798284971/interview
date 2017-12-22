package com.hevttc.jdr.interiew.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 主页面ViewPage适配器
 * 防止页面销毁
 * <p>
 * Created by Scout
 * Created on 2017/10/23 16:27.
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {
 private List<Fragment> mFragments;
    private List<String> mTitles;


    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MainViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    public MainViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    /*fragmentPagerAdapter无需重写该方法*/
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return super.isViewFromObject(view,object);
//    }
//
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles == null ? "null" : mTitles.get(position);
    }
}
