package com.wenliu.bookshare.profile;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ProfileViewPagerAdapter extends FragmentPagerAdapter {
    private ProfileContract.Presenter mPresenter;
    private FragmentManager mFragmentManager;
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public ProfileViewPagerAdapter(FragmentManager fm,
                                   ProfileContract.Presenter presenter,
                                   List<Fragment> fragments,
                                   List<String> titles) {
        super(fm);
        mFragmentManager = fm;
        mPresenter = presenter;
        mFragments = fragments;
        mTitles = titles;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
